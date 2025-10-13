package net.acoyt.malachite.index;

import net.acoyt.malachite.Malachite;
import net.acoyt.malachite.client.render.entity.MalachiteDaggerEntityRenderer;
import net.acoyt.malachite.entity.MalachiteDaggerEntity;
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
            ).dimensions(0.5f, 0.5f).maxTrackingRange(64).trackingTickInterval(20)
    );

    private static <T extends Entity> EntityType<T> create(String name, EntityType.Builder<T> builder) {
        return Registry.register(Registries.ENTITY_TYPE, Malachite.id(name), builder.build());
    }

    static void init() {
        //
    }

    static void clientInit() {
        EntityRendererRegistry.register(MALACHITE_DAGGER, MalachiteDaggerEntityRenderer::new);
    }
}
