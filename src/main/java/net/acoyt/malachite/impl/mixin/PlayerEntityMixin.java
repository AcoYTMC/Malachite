package net.acoyt.malachite.impl.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.acoyt.malachite.api.BlockingItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @WrapOperation(
            method = {"applyDamage"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/player/PlayerEntity;modifyAppliedDamage(Lnet/minecraft/entity/damage/DamageSource;F)F"
            )}
    )
    private float swordBlock(PlayerEntity player, DamageSource source, float amount, @NotNull Operation<Float> original) {
        float base = original.call(player, source, amount);
        ItemStack stack = player.getMainHandStack();
        if (!this.getWorld().isClient() && (!source.isIn(DamageTypeTags.BYPASSES_SHIELD) || source.isOf(DamageTypes.FALL)) && stack.getItem() instanceof BlockingItem blockingItem && player.isUsingItem()) {
            Vec3d damagePos = source.getPosition();
            if (source.isOf(DamageTypes.FALL)) {
                if (isLookingDown(player)) {
                    this.getWorld().playSoundFromEntity(null, this, blockingItem.blockSound(), SoundCategory.HOSTILE, 1.0F, 1.0F + this.getWorld().getRandom().nextFloat() * 0.4F);
                    blockingItem.absorbDamage(player, source, stack, base / 2.0F);
                    return base / 4.0F;
                }
            } else if (damagePos != null) {
                Vec3d rotVec = this.getRotationVec(1.0F);

                Vec3d difference = damagePos.relativize(this.getEyePos()).normalize();
                double angle = difference.dotProduct(rotVec);
                if (!(angle < -1.0F) && angle < -0.35) {
                    this.getWorld().playSoundFromEntity(null, this, blockingItem.blockSound(), SoundCategory.HOSTILE, 1.0F, 1.0F + this.getWorld().getRandom().nextFloat() * 0.4F);
                    blockingItem.absorbDamage(player, source, stack, base);
                    return base / 2.0F;
                }
            }
        }

        return base;
    }

    @Unique
    private static boolean isLookingDown(Entity entity) {
        return entity.getPitch() > 60.2F;
    }
}
