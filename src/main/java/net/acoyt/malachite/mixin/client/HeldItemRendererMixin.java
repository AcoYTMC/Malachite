package net.acoyt.malachite.mixin.client;

import net.acoyt.malachite.impl.index.MalachiteItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @Inject(method = "resetEquipProgress", at = @At("HEAD"), cancellable = true)
    private void malachite$quickSwitching(Hand hand, CallbackInfo ci) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null && player.getStackInHand(hand).isOf(MalachiteItems.MALACHITE_LONGSWORD)) {
            ci.cancel();
        }
    }
}
