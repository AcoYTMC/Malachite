package net.acoyt.malachite.impl.client.entity.model;

import net.acoyt.malachite.impl.client.entity.animation.EnergyOrbAnimations;
import net.acoyt.malachite.impl.entity.EnergyOrbEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class EnergyOrbEntityModel extends SinglePartEntityModel<EnergyOrbEntity> {
    private final ModelPart root;
    private final ModelPart orb;

    public EnergyOrbEntityModel(ModelPart root) {
        this.root = root.getChild("root");
        this.orb = this.root.getChild("orb");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.NONE);

        ModelPartData orb = root.addChild("orb", ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    public void setAngles(EnergyOrbEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.root.traverse().forEach(ModelPart::resetTransform);
        this.updateAnimation(
                entity.animationState,
                EnergyOrbAnimations.IDLE,
                entity.age + ageInTicks,
                1.0F
        );
    }

    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, int color) {
        this.root.render(matrices, vertexConsumer, light, overlay, color);
    }

    public ModelPart getPart() {
        return this.root;
    }
}
