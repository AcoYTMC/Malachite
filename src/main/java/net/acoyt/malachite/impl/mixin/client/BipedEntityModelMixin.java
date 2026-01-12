package net.acoyt.malachite.impl.mixin.client;

import net.acoyt.malachite.impl.index.MalachiteItems;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
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

    @Inject(
            method = {"animateArms"},
            at = @At("TAIL")
    )
    private void malachite$twoHanding(T entity, float animationProgress, CallbackInfo ci) {
        if (!(this.handSwingProgress <= 0.0F) && entity.getMainHandStack().isOf(MalachiteItems.MALACHITE_LONGSWORD)) {
            Arm arm = this.getPreferredArm(entity).getOpposite();
            ModelPart modelPart = this.getArm(arm);
            double f = (double)1.0F - Math.pow(1.0F - this.handSwingProgress, 3.0F);
            float h = MathHelper.sin(this.handSwingProgress * (float)Math.PI) * -(this.head.pitch - 0.7F) * 0.75F;
            modelPart.pitch -= MathHelper.sin((float)(f * Math.PI)) * 1.2F + h;
            modelPart.yaw += this.body.yaw * 2.0F;
            modelPart.roll += MathHelper.sin(this.handSwingProgress * (float)Math.PI) * -0.4F;
        }
    }
}
