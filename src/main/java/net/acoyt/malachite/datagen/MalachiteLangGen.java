package net.acoyt.malachite.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

import static net.acoyt.malachite.index.MalachiteBlocks.*;
import static net.acoyt.malachite.index.MalachiteEffects.OVERCHARGED;
import static net.acoyt.malachite.index.MalachiteItems.*;

public class MalachiteLangGen extends FabricLanguageProvider {
    public MalachiteLangGen(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder builder) {
        // Item Groups
        builder.add("itemGroup.malachite", "Malachite");

        // Items
        builder.add(MALACHITE, "Malachite");
        builder.add(MALACHITE_DAGGER, "Malachite Dagger");
        builder.add(MALACHITE_LONGSWORD, "Malachite Longsword");
        builder.add("item.malachite.tooltip.charged", "Charged");

        // Blocks
        builder.add(MALACHITE_PYLON, "Malachite Pylon");
        builder.add(SERAPHITE, "Seraphite");
        builder.add(SERAPHITE_SLAB, "Seraphite Slab");
        builder.add(SERAPHITE_STAIRS, "Seraphite Stairs");
        builder.add(SERAPHITE_WALL, "Seraphite Wall");
        builder.add(POLISHED_SERAPHITE, "Polished Seraphite");
        builder.add(POLISHED_SERAPHITE_SLAB, "Polished Seraphite Slab");
        builder.add(POLISHED_SERAPHITE_STAIRS, "Polished Seraphite Stairs");
        builder.add(POLISHED_SERAPHITE_WALL, "Polished Seraphite Wall");
        builder.add(CHISELED_SERAPHITE, "Chiseled Seraphite");
        builder.add(SERAPHITE_BRICKS, "Seraphite Bricks");
        builder.add(SERAPHITE_BRICK_SLAB, "Seraphite Brick Slab");
        builder.add(SERAPHITE_BRICK_STAIRS, "Seraphite Brick Stairs");
        builder.add(SERAPHITE_BRICK_WALL, "Seraphite Brick Wall");

        builder.add(MALACHITE_CLUSTER, "Malachite Cluster");
        builder.add(LARGE_MALACHITE_BUD, "Large Malachite Bud");
        builder.add(MEDIUM_MALACHITE_BUD, "Medium Malachite Bud");
        builder.add(SMALL_MALACHITE_BUD, "Small Malachite Bud");

        builder.add(BUDDING_COPPER, "Budding Copper");

        // Enchantments
        builder.add("enchantment.malachite.shockwave", "Shockwave");
        builder.add("enchantment.malachite.shockwave.desc", "Allows the Malachite Longsword to create a shockwave, launching nearby entities back.");

        // Damage Types
        builder.add("death.attack.dagger", "%1$s was pierced");
        builder.add("death.attack.dagger.item", "%1$s was pierced by %2$s using %3$s");
        builder.add("death.attack.dagger.player", "%1$s was pierced by %2$s");

        builder.add("death.attack.overcharged", "%1$s was overcharged");
        builder.add("death.attack.overcharged.item", "%1$s was overcharged by %2$s using %3$s");
        builder.add("death.attack.overcharged.player", "%1$s was overcharged by %2$s");

        // Status Effects
        builder.add(OVERCHARGED.value(), "Overcharged");

        // Potions
        //builder.add();

        // Subtitles
        builder.add("subtitles.malachite.entity.dagger.hit", "Dagger Hits");
        builder.add("subtitles.malachite.entity.dagger.throw", "Dagger Throws");
        builder.add("subtitles.malachite.item.longsword.block", "Longsword Blocks");
        builder.add("subtitles.malachite.entity.energy_beam.shoot", "Energy Beam Shoots");
    }
}
