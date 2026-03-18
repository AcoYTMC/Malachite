package net.acoyt.malachite.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.acoyt.malachite.impl.index.MalachiteEnchantmentEffects;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @WrapOperation(
            method = "attack",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/entity/LivingEntity;takeKnockback(DDD)V"
            )
    )
    private void malachite$replaceKnockback(LivingEntity instance, double strength, double x, double z, Operation<Void> original) {
        if (EnchantmentHelper.hasAnyEnchantmentsWith(this.getMainHandStack(), MalachiteEnchantmentEffects.MAGNETIC)) {
            strength *= 1.0 - instance.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);

            instance.velocityDirty = true;
            Vec3d vec3d = instance.getVelocity();

            Vec3d vec3d2 = new Vec3d(x, 0.0, z).normalize().multiply(strength);
            instance.setVelocity(
                    (vec3d.x / 2.0 - vec3d2.x) * -30,
                    instance.isOnGround() ? Math.min(0.4, vec3d.y / 2.0 + strength) : vec3d.y,
                    (vec3d.z / 2.0 - vec3d2.z) * -30
            );

            return;
        }

        original.call(instance, strength, x, z);
    }
}
