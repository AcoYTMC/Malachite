package net.acoyt.malachite.impl.index;

import com.mojang.serialization.Codec;
import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.component.MalachiteComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.UnaryOperator;

public interface MalachiteDataComponents {
    ComponentType<MalachiteComponent> MALACHITE = create("malachite",
            builder -> builder.codec(MalachiteComponent.CODEC).packetCodec(MalachiteComponent.PACKET_CODEC));
    ComponentType<Float> BEAM_DAMAGE = create("beam_damage",
            builder -> builder.codec(Codec.FLOAT).packetCodec(PacketCodecs.FLOAT));
    //ComponentType<List<StatusEffectInstance>> EFFECTS = create("effects",
    //                                  builder -> builder.codec(StatusEffectInstance.CODEC.listOf()).packetCodec(StatusEffectInstance.PACKET_CODEC.collect(PacketCodecs.toList())));

    static <T> ComponentType<T> create(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Malachite.id(name), (builderOperator.apply(ComponentType.builder()).build()));
    }

    static void init() {
        //
    }
}
