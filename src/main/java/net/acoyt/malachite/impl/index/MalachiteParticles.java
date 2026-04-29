package net.acoyt.malachite.impl.index;

import net.acoyt.acornlib.api.registrants.ParticleTypeRegistrant;
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

public interface MalachiteParticles {
    ParticleTypeRegistrant PARTICLES = new ParticleTypeRegistrant(Malachite.MOD_ID);

    ParticleType<ShockwaveParticleEffect> SHOCKWAVE = PARTICLES.register("shockwave", FabricParticleTypes.complex(true, ShockwaveParticleEffect.CODEC, ShockwaveParticleEffect.PACKET_CODEC));
    ParticleType<BlastParticleEffect> BLAST = PARTICLES.register("blast", FabricParticleTypes.complex(true, BlastParticleEffect.CODEC, BlastParticleEffect.PACKET_CODEC));
    SimpleParticleType SPARK = PARTICLES.register("spark", FabricParticleTypes.simple(true));

    static void init() {}

    static void clientInit() {
        ParticleFactoryRegistry.getInstance().register(SHOCKWAVE, ShockwaveParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(BLAST, BlastParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(SPARK, GlowParticle.ElectricSparkFactory::new);
    }
}
