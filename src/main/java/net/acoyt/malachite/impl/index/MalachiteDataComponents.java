package net.acoyt.malachite.impl.index;

import com.mojang.serialization.Codec;
import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.component.MalachiteComponent;
import net.acoyt.malachite.impl.util.Util;
import net.minecraft.component.ComponentType;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.math.Vec3d;

public interface MalachiteDataComponents {
    ComponentType<MalachiteComponent> MALACHITE = create("malachite", MalachiteComponent.CODEC, MalachiteComponent.PACKET_CODEC);
    ComponentType<Float> BEAM_DAMAGE = create("beam_damage", Codec.FLOAT, PacketCodecs.FLOAT);
    ComponentType<Vec3d> POSITION = create("position", Vec3d.CODEC, Util.VEC3D_PACKET_CODEC);

    static <T> ComponentType<T> create(String id, Codec<T> codec, PacketCodec<? super RegistryByteBuf, T> packetCodec) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Malachite.id(id), ComponentType.<T>builder()
                .codec(codec)
                .packetCodec(packetCodec)
                .build());
    }

    static void init() {}
}
