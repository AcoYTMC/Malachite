package net.acoyt.malachite.impl.index;

import net.acoyt.acornlib.api.registrants.PotionRegistrant;
import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.event.RegisterPotionsEvent;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.registry.entry.RegistryEntry;

public interface MalachitePotions {
    PotionRegistrant POTIONS = new PotionRegistrant(Malachite.MOD_ID);

    RegistryEntry<Potion> OVERCHARGED = POTIONS.registerRef("overcharged",
            new Potion("overcharged", new StatusEffectInstance(MalachiteEffects.OVERCHARGED, 1200, 0))
    );

    static void init() {
        FabricBrewingRecipeRegistryBuilder.BUILD.register(new RegisterPotionsEvent());
    }
}
