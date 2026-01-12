package net.acoyt.malachite.impl.index;

import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.client.particle.BlastParticle;
import net.acoyt.malachite.impl.client.particle.BlastParticleEffect;
import net.acoyt.malachite.impl.client.particle.ShockwaveParticle;
import net.acoyt.malachite.impl.client.particle.ShockwaveParticleEffect;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.GlowParticle;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface MalachiteParticles {
    ParticleType<ShockwaveParticleEffect> SHOCKWAVE = FabricParticleTypes.complex(true, ShockwaveParticleEffect.CODEC, ShockwaveParticleEffect.PACKET_CODEC);
    ParticleType<BlastParticleEffect> BLAST = FabricParticleTypes.complex(true, BlastParticleEffect.CODEC, BlastParticleEffect.PACKET_CODEC);
    SimpleParticleType SPARK = FabricParticleTypes.simple(true);

    private static void create(String name, ParticleType<?> particle) {
        Registry.register(Registries.PARTICLE_TYPE, Malachite.id(name), particle);
    }

    static void init() {
        create("shockwave", SHOCKWAVE);
        create("blast", BLAST);
        create("spark", SPARK);
    }

    static void clientInit() {
        ParticleFactoryRegistry.getInstance().register(SHOCKWAVE, ShockwaveParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(BLAST, BlastParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(SPARK, GlowParticle.ElectricSparkFactory::new);
    }
}
