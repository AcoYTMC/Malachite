package net.acoyt.malachite.impl.cca;

import net.acoyt.malachite.impl.cca.entity.ChargedComponent;
import net.acoyt.malachite.impl.cca.entity.NearbyPylonComponent;
import net.minecraft.entity.LivingEntity;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class MalachiteComponents implements EntityComponentInitializer {
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(LivingEntity.class, NearbyPylonComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(NearbyPylonComponent::new);
        registry.beginRegistration(LivingEntity.class, ChargedComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(ChargedComponent::new);
    }
}
