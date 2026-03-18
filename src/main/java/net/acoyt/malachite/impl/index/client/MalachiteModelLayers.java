package net.acoyt.malachite.impl.index.client;

import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.client.entity.model.EnergyBeamEntityModel;
import net.acoyt.malachite.impl.client.entity.model.EnergyOrbEntityModel;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;

public interface MalachiteModelLayers {
    EntityModelLayer ENERGY_BEAM = create("energy_beam");
    EntityModelLayer ENERGY_ORB = create("energy_orb");

    private static EntityModelLayer create(String id) {
        return new EntityModelLayer(Malachite.id(id), "main");
    }

    static void clientInit() {
        EntityModelLayerRegistry.registerModelLayer(ENERGY_BEAM, EnergyBeamEntityModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ENERGY_ORB, EnergyOrbEntityModel::getTexturedModelData);
    }
}
