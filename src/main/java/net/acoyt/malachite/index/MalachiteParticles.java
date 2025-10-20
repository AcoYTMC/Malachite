package net.acoyt.malachite.index;

import net.acoyt.malachite.Malachite;
import net.acoyt.malachite.client.particle.ShockwaveParticle;
import net.acoyt.malachite.client.particle.ShockwaveParticleEffect;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.GlowParticle;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface MalachiteParticles {
    ParticleType<ShockwaveParticleEffect> SHOCKWAVE = FabricParticleTypes.complex(true, ShockwaveParticleEffect.CODEC, ShockwaveParticleEffect.PACKET_CODEC);
    SimpleParticleType SPARK = FabricParticleTypes.simple(true);

    private static void create(String name, ParticleType<?> particle) {
        Registry.register(Registries.PARTICLE_TYPE, Malachite.id(name), particle);
    }

    static void init() {
        create("shockwave", SHOCKWAVE);
        create("spark", SPARK);
    }

    static void clientInit() {
        ParticleFactoryRegistry.getInstance().register(SHOCKWAVE, ShockwaveParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(SPARK, GlowParticle.ElectricSparkFactory::new);
    }
}
