package net.acoyt.malachite.cca;

import net.minecraft.entity.LivingEntity;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class MalachiteComponents implements EntityComponentInitializer {
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(LivingEntity.class, NearbyPylonComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(NearbyPylonComponent::new);
    }
}
