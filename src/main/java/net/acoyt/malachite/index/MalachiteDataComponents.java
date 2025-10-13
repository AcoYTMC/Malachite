package net.acoyt.malachite.index;

import net.acoyt.malachite.Malachite;
import net.acoyt.malachite.component.MalachiteComponent;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.UnaryOperator;

public interface MalachiteDataComponents {
    ComponentType<MalachiteComponent> MALACHITE = create("malachite", builder -> builder.codec(MalachiteComponent.CODEC));
    //ComponentType<List<StatusEffectInstance>> EFFECTS = create("effects", builder -> builder.codec(StatusEffectInstance.CODEC.listOf()));

    static <T> ComponentType<T> create(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Malachite.id(name), (builderOperator.apply(ComponentType.builder()).build()));
    }

    static void init() {
        //
    }
}
