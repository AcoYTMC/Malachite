package net.acoyt.malachite.impl.index;

import com.mojang.serialization.Codec;
import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.util.Util;
import net.minecraft.component.ComponentType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Unit;

public interface MalachiteEnchantmentEffects {
    ComponentType<Float> SHOCKWAVE = create("shockwave", Codec.FLOAT, PacketCodecs.FLOAT);
    ComponentType<Unit> VOLTAGE = create("voltage", Unit.CODEC, Util.UNIT_PACKET_CODEC);
    ComponentType<Unit> MAGNETIC = create("magnetic", Unit.CODEC, Util.UNIT_PACKET_CODEC);
    ComponentType<Unit> DISRUPT = create("disrupt", Unit.CODEC, Util.UNIT_PACKET_CODEC);

    static <T> ComponentType<T> create(String id, Codec<T> codec, PacketCodec<? super RegistryByteBuf, T> packetCodec) {
        return Registry.register(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, Malachite.id(id), ComponentType.<T>builder()
                .codec(codec)
                .packetCodec(packetCodec)
                .build());
    }

    static void init() {}
}
