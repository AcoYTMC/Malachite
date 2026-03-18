package net.acoyt.malachite.impl.index;

import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.effect.StatusEffectBase;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public interface MalachiteEffects {
    RegistryEntry<StatusEffect> OVERCHARGED = create("overcharged", new StatusEffectBase(StatusEffectCategory.NEUTRAL, 0xFF53efac));

    private static RegistryEntry<StatusEffect> create(String name, StatusEffect effect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Malachite.id(name), effect);
    }

    static void init() {}
}
