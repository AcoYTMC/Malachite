package net.acoyt.malachite.impl.index;

import com.mojang.serialization.Codec;
import net.acoyt.malachite.impl.Malachite;
import net.minecraft.component.ComponentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

import java.util.function.UnaryOperator;

public interface MalachiteEnchantments {
    RegistryKey<Enchantment> SHOCKWAVE_KEY = RegistryKey.of(RegistryKeys.ENCHANTMENT, Malachite.id("shockwave"));

    ComponentType<Float> SHOCKWAVE = create("shockwave", builder -> builder.codec(Codec.FLOAT));

    static <T> ComponentType<T> create(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.ENCHANTMENT_EFFECT_COMPONENT_TYPE, Malachite.id(name), (builderOperator.apply(ComponentType.builder()).build()));
    }

    static void init() {
        //
    }
}
