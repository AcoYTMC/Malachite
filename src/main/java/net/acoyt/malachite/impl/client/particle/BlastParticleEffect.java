package net.acoyt.malachite.impl.client.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.acoyt.acornlib.api.util.PortingUtils;
import net.acoyt.malachite.impl.index.MalachiteParticles;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

public record BlastParticleEffect(int color, float size, float rotation, boolean forceBillboard) implements ParticleEffect {
    public static final MapCodec<BlastParticleEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            PortingUtils.RGB.fieldOf("color").forGetter(BlastParticleEffect::color),
            Codec.FLOAT.fieldOf("size").forGetter(BlastParticleEffect::size),
            Codec.FLOAT.fieldOf("rotation").orElse(0.0f).forGetter(BlastParticleEffect::rotation),
            Codec.BOOL.fieldOf("forceBillboard").orElse(false).forGetter(BlastParticleEffect::forceBillboard)
    ).apply(instance, BlastParticleEffect::new));

    public static final PacketCodec<RegistryByteBuf, BlastParticleEffect> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, BlastParticleEffect::color,
            PacketCodecs.FLOAT, BlastParticleEffect::size,
            PacketCodecs.FLOAT, BlastParticleEffect::rotation,
            PacketCodecs.BOOL, BlastParticleEffect::forceBillboard,
            BlastParticleEffect::new
    );

    public BlastParticleEffect(int color, float size, float rotation) {
        this(color, size, rotation, false);
    }

    @Override
    public ParticleType<?> getType() {
        return MalachiteParticles.BLAST;
    }
}
