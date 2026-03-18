package net.acoyt.malachite.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.acoyt.malachite.impl.cca.entity.NearbyPylonComponent;
import net.acoyt.malachite.impl.component.MalachiteComponent;
import net.acoyt.malachite.impl.entity.MalachiteDaggerEntity;
import net.acoyt.malachite.impl.index.MalachiteDataComponents;
import net.acoyt.malachite.impl.index.MalachiteEffects;
import net.acoyt.malachite.impl.index.MalachiteEnchantmentEffects;
import net.acoyt.malachite.impl.index.MalachiteItems;
import net.acoyt.malachite.impl.index.data.MalachiteDamageTypes;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow protected abstract void applyDamage(DamageSource source, float amount);
    @Shadow @Nullable public abstract StatusEffectInstance getStatusEffect(RegistryEntry<StatusEffect> effect);

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyReturnValue(method = "getKnockbackAgainst", at = @At(value = "RETURN", ordinal = 0))
    private float malachiteDagger(float original, Entity target, DamageSource source) {
        LivingEntity living = (LivingEntity)(Object)this; // Attacker/Player
        ItemStack stack = living.getMainHandStack();
        float f = (float)living.getAttributeValue(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
        if (stack.isOf(MalachiteItems.MALACHITE_DAGGER)) {
            MalachiteComponent component = stack.getOrDefault(MalachiteDataComponents.MALACHITE, MalachiteComponent.DAGGER);
            if (component.charge() >= component.maxCharge()) {
                return (f + 6.5f) * (EnchantmentHelper.hasAnyEnchantmentsWith(stack, MalachiteEnchantmentEffects.MAGNETIC) ? -3 : 1);
            }
        }

        if (source.getSource() instanceof MalachiteDaggerEntity malachiteDagger) {
            ItemStack itemStack = malachiteDagger.getItem();
            if (itemStack.isOf(MalachiteItems.MALACHITE_DAGGER)) {
                MalachiteComponent component = itemStack.getOrDefault(MalachiteDataComponents.MALACHITE, MalachiteComponent.DAGGER);
                if (component.charge() >= component.maxCharge()) {
                    return (f + 6.5f) * (malachiteDagger.isMagnetic() ? -3 : 1);
                }
            }
        }

        if (target instanceof LivingEntity entity && entity.hasStatusEffect(MalachiteEffects.OVERCHARGED)) {
            return original * 3;
        }

        return original;
    }

    @Inject(
            method = "damage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V"
            )
    )
    private void malachite$moreDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        StatusEffectInstance instance = this.getStatusEffect(MalachiteEffects.OVERCHARGED);
        if (instance != null) {
            this.applyDamage(MalachiteDamageTypes.create(this.getWorld(), MalachiteDamageTypes.OVERCHARGED), instance.getAmplifier());
        }
    }

    @ModifyArgs(
            method = "damage",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V"
            )
    )
    private void modifyDamageAmount(Args args) {
        LivingEntity living = (LivingEntity)(Object)this;
        NearbyPylonComponent component = NearbyPylonComponent.KEY.get(living);
        float amount = args.get(1);
        float prevAmount = amount;

        if (component.isNearby()) {
            amount = prevAmount * 0.75f;
        }

        args.set(1, amount);
    }
}
