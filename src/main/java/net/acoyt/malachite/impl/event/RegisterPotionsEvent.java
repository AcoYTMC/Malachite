package net.acoyt.malachite.impl.event;

import net.acoyt.malachite.impl.index.MalachiteItems;
import net.acoyt.malachite.impl.index.MalachitePotions;
import net.fabricmc.fabric.api.registry.FabricBrewingRecipeRegistryBuilder;
import net.minecraft.potion.Potions;
import net.minecraft.recipe.BrewingRecipeRegistry;

public class RegisterPotionsEvent implements FabricBrewingRecipeRegistryBuilder.BuildCallback {
    @Override
    public void build(BrewingRecipeRegistry.Builder builder) {
        builder.registerPotionRecipe(Potions.AWKWARD, MalachiteItems.MALACHITE, MalachitePotions.OVERCHARGED);
    }
}
