package net.acoyt.malachite.impl.index;

import net.acoyt.acornlib.api.registrants.StatusEffectRegistrant;
import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.effect.StatusEffectBase;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.entry.RegistryEntry;

public interface MalachiteEffects {
    StatusEffectRegistrant EFFECTS = new StatusEffectRegistrant(Malachite.MOD_ID);

    RegistryEntry<StatusEffect> OVERCHARGED = EFFECTS.registerRef("overcharged", new StatusEffectBase(StatusEffectCategory.NEUTRAL, 0xFF53efac));

    static void init() {}
}
