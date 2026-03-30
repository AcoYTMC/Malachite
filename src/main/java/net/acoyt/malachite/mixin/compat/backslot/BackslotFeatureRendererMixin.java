package net.acoyt.malachite.mixin.compat.backslot;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.acoyt.malachite.impl.index.MalachiteItems;
import net.medecoole.backslot.registry.BackslotFeatureRenderer;
import net.medecoole.backslot.registry.BackslotItemComponent;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BackslotFeatureRenderer.class)
public abstract class BackslotFeatureRendererMixin extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public BackslotFeatureRendererMixin(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context) {
        super(context);
    }

    @WrapOperation(
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;" +
                    "Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/network/AbstractClientPlayerEntity;FFFFFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/render/item/ItemRenderer;renderItem(Lnet/minecraft/item/ItemStack;" +
                            "Lnet/minecraft/client/render/model/json/ModelTransformationMode;IILnet/minecraft/client/util/math/MatrixStack;" +
                            "Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;I)V"
            )
    )
    private void customMode(ItemRenderer instance, ItemStack stack, ModelTransformationMode transformationType, int light, int overlay, MatrixStack matrices, VertexConsumerProvider vertexConsumers, World world, int seed, Operation<Void> original) {
        ModelTransformationMode mode = stack.isOf(MalachiteItems.MALACHITE_LONGSWORD) || stack.isOf(MalachiteItems.MALACHITE_GREATAXE) ? ModelTransformationMode.HEAD : transformationType;
        original.call(instance, stack, mode, light, overlay, matrices, vertexConsumers,  world, seed);
    }

    @WrapOperation(
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;" +
                    "Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/network/AbstractClientPlayerEntity;FFFFFF)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/util/math/MatrixStack;translate(DDD)V",
                    ordinal = 2
            )
    )
    private void imTooLazyToRemakeTheTransformation(MatrixStack instance, double x, double y, double z, Operation<Void> original, @Local(argsOnly = true) AbstractClientPlayerEntity entity) {
        BackslotItemComponent component = BackslotItemComponent.get(entity);
        if (component.getStack().isOf(MalachiteItems.MALACHITE_LONGSWORD) || component.getStack().isOf(MalachiteItems.MALACHITE_GREATAXE)) {
            original.call(instance, x, y, -z);
            return;
        }

        original.call(instance, x, y, z);
    }
}
