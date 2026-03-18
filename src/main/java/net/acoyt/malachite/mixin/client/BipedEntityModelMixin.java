package net.acoyt.malachite.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.acoyt.acornlib.api.util.ItemUtils;
import net.acoyt.malachite.impl.index.MalachiteItems;
import net.acoyt.malachite.impl.item.MalachiteLongswordItem;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin<T extends LivingEntity> extends AnimalModel<T> {
    @Shadow @Final public ModelPart body;
    @Shadow @Final public ModelPart head;

    @Shadow protected abstract Arm getPreferredArm(T entity);
    @Shadow protected abstract ModelPart getArm(Arm arm);

    @WrapOperation(
            method = "positionRightArm",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/entity/model/BipedEntityModel$ArmPose;ordinal()I"
            )
    )
    private int rightArm(BipedEntityModel.ArmPose instance, Operation<Integer> original, T entity) {
        int ordinal = original.call(instance);
        if (entity instanceof PlayerEntity player && player.getMainArm() == Arm.LEFT && ItemUtils.getHeldStacks(player).stream().anyMatch(stack -> stack.isOf(MalachiteItems.MALACHITE_LONGSWORD))) {
            return Math.min(ordinal, 1);
        }

        return ordinal;
    }

    @WrapOperation(
            method = "positionLeftArm",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/entity/model/BipedEntityModel$ArmPose;ordinal()I"
            )
    )
    private int leftArm(BipedEntityModel.ArmPose instance, Operation<Integer> original, T entity) {
        int ordinal = original.call(instance);
        if (entity instanceof PlayerEntity player && player.getMainArm() == Arm.RIGHT && ItemUtils.getHeldStacks(player).stream().anyMatch(stack -> stack.isOf(MalachiteItems.MALACHITE_LONGSWORD))) {
            return Math.min(ordinal, 1);
        }

        return ordinal;
    }

    @Inject(method = "animateArms", at = @At("TAIL"))
    private void malachite$twoHandedAttack(T entity, float animationProgress, CallbackInfo ci) {
        if (this.handSwingProgress >= 0.0F && entity.getMainHandStack().getItem() instanceof MalachiteLongswordItem) {
            Arm arm = this.getPreferredArm(entity).getOpposite();
            ModelPart modelPart = this.getArm(arm);
            double d = 1.0 - Math.pow(1.0F - this.handSwingProgress, 3.0F);
            float h = MathHelper.sin(this.handSwingProgress * MathHelper.PI) * -(this.head.pitch - 0.7F) * 0.75F;

            modelPart.pitch -= MathHelper.sin((float)(d * Math.PI)) * 1.2F + h;
            modelPart.yaw += this.body.yaw * 2.0F;
            modelPart.roll += MathHelper.sin(this.handSwingProgress * (float) Math.PI) * -0.4F;
        }
    }
}
