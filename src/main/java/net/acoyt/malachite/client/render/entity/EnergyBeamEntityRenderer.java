package net.acoyt.malachite.client.render.entity;

import net.acoyt.malachite.client.model.entity.EnergyBeamEntityModel;
import net.acoyt.malachite.entity.EnergyBeamEntity;
import net.acoyt.malachite.index.MalachiteModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class EnergyBeamEntityRenderer extends ProjectileEntityRenderer<EnergyBeamEntity> {
    private final EnergyBeamEntityModel model;

    public EnergyBeamEntityRenderer(EntityRendererFactory.Context context) {
        super(context);

        this.model = new EnergyBeamEntityModel(context.getPart(MalachiteModelLayers.ENERGY_BEAM));
    }

    @SuppressWarnings("deprecation")
    @Override
    public Identifier getTexture(EnergyBeamEntity entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }

    @Override
    public void render(EnergyBeamEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw())));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch()) + 90));

        matrices.translate(-0.5f, 0, -0.5f);
        //this.model.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(BeaconBlockEntityRenderer.BEAM_TEXTURE)), light, OverlayTexture.DEFAULT_UV, 0x53efac);
        BeaconBlockEntityRenderer.renderBeam(matrices, vertexConsumers, BeaconBlockEntityRenderer.BEAM_TEXTURE, tickDelta, 1.0f, entity.getWorld().getTime(), 0, entity.ticksExisted, 0x53efac, 0.2f, 0.25f * (1 + 0.2f * MathHelper.sin(0.7f * (entity.ticksExisted + tickDelta))));

        matrices.pop();
    }
}
