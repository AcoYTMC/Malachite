package net.acoyt.malachite.impl.index;

import com.mojang.serialization.Codec;
import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.component.MalachiteComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface MalachiteDataComponents {
    ComponentType<MalachiteComponent> MALACHITE = create("malachite", MalachiteComponent.CODEC, MalachiteComponent.PACKET_CODEC);
    ComponentType<Float> BEAM_DAMAGE = create("beam_damage", Codec.FLOAT, PacketCodecs.FLOAT);

    static <T> ComponentType<T> create(String id, Codec<T> codec, PacketCodec<? super RegistryByteBuf, T> packetCodec) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Malachite.id(id), ComponentType.<T>builder()
                .codec(codec)
                .packetCodec(packetCodec)
                .build());
    }

    static void init() {}
}
