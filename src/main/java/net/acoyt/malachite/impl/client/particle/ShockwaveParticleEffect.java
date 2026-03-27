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

public record ShockwaveParticleEffect(int color, float size) implements ParticleEffect {
    public static final MapCodec<ShockwaveParticleEffect> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            PortingUtils.RGB.fieldOf("color").forGetter(ShockwaveParticleEffect::color),
            Codec.FLOAT.fieldOf("size").forGetter(ShockwaveParticleEffect::size)
    ).apply(instance, ShockwaveParticleEffect::new));

    public static final PacketCodec<RegistryByteBuf, ShockwaveParticleEffect> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, ShockwaveParticleEffect::color,
            PacketCodecs.FLOAT, ShockwaveParticleEffect::size,
            ShockwaveParticleEffect::new
    );

    public ParticleType<?> getType() {
        return MalachiteParticles.SHOCKWAVE;
    }
}
