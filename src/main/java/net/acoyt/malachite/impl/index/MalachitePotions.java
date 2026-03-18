package net.acoyt.malachite.impl.index;

import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.event.RegisterPotionsEvent;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;

public interface MalachitePotions {
    RegistryEntry<Potion> OVERCHARGED = create("overcharged", new Potion(
            "overcharged",
            new StatusEffectInstance(MalachiteEffects.OVERCHARGED, 1200, 0)
    ));

    private static RegistryEntry<Potion> create(String name, Potion potion) {
        return Registry.registerReference(Registries.POTION, Malachite.id(name), potion);
    }

    static void init() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(new RegisterPotionsEvent());
    }
}
