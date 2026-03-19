package net.acoyt.malachite.impl.client.entity.render;

import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.client.entity.model.EnergyOrbEntityModel;
import net.acoyt.malachite.impl.entity.EnergyOrbEntity;
import net.acoyt.malachite.impl.index.client.MalachiteModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class EnergyOrbEntityRenderer extends EntityRenderer<EnergyOrbEntity> {
    public static final Identifier TEXTURE = Malachite.id("textures/entity/energy_orb.png");
    private final EnergyOrbEntityModel model;

    public EnergyOrbEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model = new EnergyOrbEntityModel(ctx.getPart(MalachiteModelLayers.ENERGY_ORB));
    }

    public void render(EnergyOrbEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        matrices.translate(0, 0.25, 0);

        this.model.setAngles(entity, 0.0F, 0.0F, tickDelta, entity.getYaw(), entity.getPitch());

        this.model.render(
                matrices,
                vertexConsumers.getBuffer(RenderLayer.getEntityCutoutNoCull(this.getTexture(entity))),
                light, OverlayTexture.DEFAULT_UV,
                0xFFFFFF
        );

        this.model.render(
                matrices,
                vertexConsumers.getBuffer(RenderLayer.getEyes(this.getTexture(entity))),
                light, OverlayTexture.DEFAULT_UV,
                0xFFFFFF
        );

        matrices.pop();

        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    public Identifier getTexture(EnergyOrbEntity entity) {
        return TEXTURE;
    }
}
