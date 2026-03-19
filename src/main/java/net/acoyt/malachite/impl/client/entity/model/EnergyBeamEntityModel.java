package net.acoyt.malachite.impl.client.entity.model;

import net.acoyt.malachite.impl.entity.EnergyBeamEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class EnergyBeamEntityModel extends SinglePartEntityModel<EnergyBeamEntity> {
    private final ModelPart root;
    private final ModelPart beam;

    public EnergyBeamEntityModel(ModelPart root) {
        this.root = root.getChild("root");
        this.beam = this.root.getChild("beam");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.NONE);

        ModelPartData beam = root.addChild("beam", ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3.0F, -32.0F, -3.0F, 6.0F, 32.0F, 6.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    public void setAngles(EnergyBeamEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        //
    }

    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        this.root.render(matrices, vertices, light, overlay, color);
    }

    public ModelPart getPart() {
        return this.root;
    }
}
