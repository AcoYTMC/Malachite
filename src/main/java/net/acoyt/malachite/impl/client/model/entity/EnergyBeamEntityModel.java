package net.acoyt.malachite.impl.client.model.entity;

import net.acoyt.malachite.impl.entity.EnergyBeamEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class EnergyBeamEntityModel extends EntityModel<EnergyBeamEntity> {
    private final ModelPart beam;

    public EnergyBeamEntityModel(ModelPart root) {
        this.beam = root.getChild("beam");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData beam = modelPartData.addChild("beam", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -32.0F, -3.0F, 6.0F, 32.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    public void setAngles(EnergyBeamEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        //
    }

    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        this.beam.render(matrices, vertices, light, overlay, color);
    }
}
