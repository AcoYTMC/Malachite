package net.acoyt.malachite.impl.mixin.client;

import net.acoyt.malachite.impl.index.MalachiteItems;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel.ArmPose;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(
            method = {"getArmPose"},
            at = {@At("HEAD")},
            cancellable = true
    )
    private static void malachite$twoHanding(AbstractClientPlayerEntity player, Hand hand, CallbackInfoReturnable<ArmPose> cir) {
        ItemStack stack = player.getStackInHand(hand);

        if (stack.isOf(MalachiteItems.MALACHITE_LONGSWORD)) {
            boolean blocking = player.getActiveItem() != null && player.getActiveItem().isOf(MalachiteItems.MALACHITE_LONGSWORD);
            if (blocking) {
                cir.setReturnValue(ArmPose.CROSSBOW_HOLD);
            } else {
                cir.setReturnValue(ArmPose.CROSSBOW_CHARGE);
            }

            if (blocking) {
                player.bodyYaw = player.headYaw;
                player.prevBodyYaw = player.prevHeadYaw;
            }
        }
    }
}
