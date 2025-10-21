package net.acoyt.malachite.client.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.acoyt.acornlib.api.util.PortingUtils;
import net.acoyt.malachite.index.MalachiteParticles;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

public record BlastParticleEffect(int color, float size, float rotation) implements ParticleEffect {
    public static final MapCodec<BlastParticleEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            PortingUtils.RGB.fieldOf("color").forGetter(BlastParticleEffect::color),
            Codec.FLOAT.fieldOf("size").forGetter(BlastParticleEffect::size),
            Codec.FLOAT.fieldOf("rotation").orElse(0.0f).forGetter(BlastParticleEffect::rotation)
    ).apply(instance, BlastParticleEffect::new));

    public static final PacketCodec<RegistryByteBuf, BlastParticleEffect> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER,
            BlastParticleEffect::color,
            PacketCodecs.FLOAT,
            BlastParticleEffect::size,
            PacketCodecs.FLOAT,
            BlastParticleEffect::rotation,
            BlastParticleEffect::new
    );

    @Override
    public ParticleType<?> getType() {
        return MalachiteParticles.BLAST;
    }
}
