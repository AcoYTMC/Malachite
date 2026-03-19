package net.acoyt.malachite.impl.client.entity.render;

import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.client.entity.model.EnergyBeamEntityModel;
import net.acoyt.malachite.impl.entity.EnergyBeamEntity;
import net.acoyt.malachite.impl.index.client.MalachiteModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class EnergyBeamEntityRenderer extends EntityRenderer<EnergyBeamEntity> {
    public static final Identifier TEXTURE = Malachite.id("textures/entity/energy_beam.png");
    private final EnergyBeamEntityModel model;

    public EnergyBeamEntityRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.model = new EnergyBeamEntityModel(context.getPart(MalachiteModelLayers.ENERGY_BEAM));
    }

    public void render(EnergyBeamEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw())));
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch()) + 90));

        matrices.translate(-0.5f, 0, -0.5f);

        matrices.translate(0, -entity.ticksExisted / 1.7F, 0);

//        matrices.translate(0, 0.2, 0);
//
//        matrices.multiply(RotationAxis.NEGATIVE_Y.rotationDegrees(entity.getYaw()));
//        matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(90 - entity.getPitch()));

        BeaconBlockEntityRenderer.renderBeam(
                matrices,
                vertexConsumers,
                BeaconBlockEntityRenderer.BEAM_TEXTURE,
                tickDelta,
                1.0f,
                entity.getWorld().getTime(),
                0,
                entity.ticksExisted,
                0xFF53efac,
                0.2f,
                0.25f * (1 + 0.2f * MathHelper.sin(0.7f * (entity.ticksExisted + tickDelta)))
        );

//        this.model.render(
//                matrices,
//                vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(this.getTexture(entity))),
//                light, OverlayTexture.DEFAULT_UV,
//                0xFFFFFF
//        );
//
//        this.model.render(
//                matrices,
//                vertexConsumers.getBuffer(RenderLayer.getEyes(this.getTexture(entity))),
//                light, OverlayTexture.DEFAULT_UV,
//                0xFFFFFF
//        );

        matrices.pop();
    }

    public Identifier getTexture(EnergyBeamEntity entity) {
        return TEXTURE;
    }
}
