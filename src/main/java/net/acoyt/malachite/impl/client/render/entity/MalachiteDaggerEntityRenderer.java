package net.acoyt.malachite.impl.client.render.entity;

import net.acoyt.malachite.impl.entity.MalachiteDaggerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class MalachiteDaggerEntityRenderer extends EntityRenderer<MalachiteDaggerEntity> {
    public final ItemRenderer itemRenderer;

    public MalachiteDaggerEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();
        this.shadowRadius = 0.5F;
        this.shadowOpacity = 0.5F;
    }

    public void render(MalachiteDaggerEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        ItemStack itemStack = entity.getItem();
        BakedModel iBakedModel = this.itemRenderer.getModel(itemStack, entity.getWorld(), null, 1);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()) + 180));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch()) - 90));
        //matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90.0f));
        this.itemRenderer.renderItem(itemStack, ModelTransformationMode.THIRD_PERSON_RIGHT_HAND, false, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV, iBakedModel);
        matrices.scale(1, 1, 1);

        matrices.pop();

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @SuppressWarnings("deprecation")
    public Identifier getTexture(MalachiteDaggerEntity entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }
}
