package net.acoyt.malachite.impl.index;

import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.client.entity.render.EnergyBeamEntityRenderer;
import net.acoyt.malachite.impl.client.entity.render.EnergyOrbEntityRenderer;
import net.acoyt.malachite.impl.client.entity.render.MalachiteDaggerEntityRenderer;
import net.acoyt.malachite.impl.entity.EnergyBeamEntity;
import net.acoyt.malachite.impl.entity.EnergyOrbEntity;
import net.acoyt.malachite.impl.entity.MalachiteDaggerEntity;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface MalachiteEntities {
    EntityType<MalachiteDaggerEntity> MALACHITE_DAGGER = create(
            "malachite_dagger",
            EntityType.Builder.<MalachiteDaggerEntity>create(
                    MalachiteDaggerEntity::new,
                    SpawnGroup.MISC
            ).dimensions(0.5f, 0.5f)
                    .maxTrackingRange(64)
                    .trackingTickInterval(20)
    );

    EntityType<EnergyBeamEntity> ENERGY_BEAM = create(
            "energy_beam",
            EntityType.Builder.<EnergyBeamEntity>create(
                    EnergyBeamEntity::new,
                    SpawnGroup.MISC
            ).dimensions(EntityType.ARROW.getWidth(), EntityType.ARROW.getHeight())
                    .maxTrackingRange(64)
    );

    EntityType<EnergyOrbEntity> ENERGY_ORB = create(
            "energy_orb",
            EntityType.Builder.<EnergyOrbEntity>create(
                    EnergyOrbEntity::new,
                    SpawnGroup.MISC
            ).dimensions(0.5F, 0.5F)
                    .maxTrackingRange(64)
                    .eyeHeight(0.25f)
    );

    private static <T extends Entity> EntityType<T> create(String name, EntityType.Builder<T> builder) {
        return Registry.register(Registries.ENTITY_TYPE, Malachite.id(name), builder.build());
    }

    static void init() {}

    static void clientInit() {
        EntityRendererRegistry.register(MALACHITE_DAGGER, MalachiteDaggerEntityRenderer::new);
        EntityRendererRegistry.register(ENERGY_BEAM, EnergyBeamEntityRenderer::new);
        EntityRendererRegistry.register(ENERGY_ORB, EnergyOrbEntityRenderer::new);
    }
}
