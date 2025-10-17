package net.acoyt.malachite.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.acoyt.malachite.component.MalachiteComponent;
import net.acoyt.malachite.entity.MalachiteDaggerEntity;
import net.acoyt.malachite.index.MalachiteDataComponents;
import net.acoyt.malachite.index.MalachiteItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyReturnValue(method = "getKnockbackAgainst", at = @At(value = "RETURN", ordinal = 0))
    private float malachiteDagger(float original, Entity target, DamageSource source) {
        LivingEntity living = (LivingEntity)(Object)this;
        ItemStack stack = living.getMainHandStack();
        float f = (float)living.getAttributeValue(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
        if (stack.isOf(MalachiteItems.MALACHITE_DAGGER)) {
            MalachiteComponent component = stack.getOrDefault(MalachiteDataComponents.MALACHITE, MalachiteComponent.DAGGER);
            if (component.charge() >= component.maxCharge()) {
                return f + 3f;
            }
        }

        if (source.getSource() instanceof MalachiteDaggerEntity malachiteDagger) {
            ItemStack itemStack = malachiteDagger.getItem();
            if (itemStack.isOf(MalachiteItems.MALACHITE_DAGGER)) {
                MalachiteComponent component = itemStack.getOrDefault(MalachiteDataComponents.MALACHITE, MalachiteComponent.DAGGER);
                if (component.charge() >= component.maxCharge()) {
                    return f + 3f;
                }
            }
        }

        return original;
    }
}
