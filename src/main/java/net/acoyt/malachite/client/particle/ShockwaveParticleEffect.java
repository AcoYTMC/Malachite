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

public record ShockwaveParticleEffect(int color, float size, float pitch, float yaw) implements ParticleEffect {
    public static final MapCodec<ShockwaveParticleEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            PortingUtils.RGB.fieldOf("color").forGetter(ShockwaveParticleEffect::color),
            Codec.FLOAT.fieldOf("size").forGetter(ShockwaveParticleEffect::size),
            Codec.FLOAT.fieldOf("pitch").orElse(0.0f).forGetter(ShockwaveParticleEffect::pitch),
            Codec.FLOAT.fieldOf("yaw").orElse(0.0f).forGetter(ShockwaveParticleEffect::yaw)
    ).apply(instance, ShockwaveParticleEffect::new));

    public static final PacketCodec<RegistryByteBuf, ShockwaveParticleEffect> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER,
            ShockwaveParticleEffect::color,
            PacketCodecs.FLOAT,
            ShockwaveParticleEffect::size,
            PacketCodecs.FLOAT,
            ShockwaveParticleEffect::pitch,
            PacketCodecs.FLOAT,
            ShockwaveParticleEffect::yaw,
            ShockwaveParticleEffect::new
    );

    @Override
    public ParticleType<?> getType() {
        return MalachiteParticles.SHOCKWAVE;
    }
}
