package net.acoyt.malachite.mixin.mialib;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.acoyt.malachite.networking.AttackingPayload;
import net.acoyt.malachite.networking.UsingPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow @Final public GameOptions options;

    @Unique private boolean malachite$attacking = false;
    @Unique private boolean malachite$using = false;

    @WrapOperation(method = "handleInputEvents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;handleBlockBreaking(Z)V"))
    private void malachite$holding(MinecraftClient instance, boolean bl, Operation<Void> original) {
        var attacking = this.options.attackKey.isPressed();
        if (attacking != this.malachite$attacking) {
            this.malachite$attacking = attacking;
            ClientPlayNetworking.send(new AttackingPayload(attacking));
        }

        var using = this.options.useKey.isPressed();
        if (using != this.malachite$using) {
            this.malachite$using = using;
            ClientPlayNetworking.send(new UsingPayload(using));
        }

        original.call(instance, bl);
    }
}
