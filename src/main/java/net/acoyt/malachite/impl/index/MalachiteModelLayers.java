package net.acoyt.malachite.impl.index;

import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.client.model.entity.EnergyBeamEntityModel;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public interface MalachiteModelLayers {
    EntityModelLayer ENERGY_BEAM = of("energy_beam");

    private static EntityModelLayer of(String id) {
        return new EntityModelLayer(Malachite.id(id), "main");
    }

    static void clientInit() {
        EntityModelLayerRegistry.registerModelLayer(ENERGY_BEAM, EnergyBeamEntityModel::getTexturedModelData);
    }
}
