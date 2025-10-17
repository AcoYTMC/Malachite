package net.acoyt.malachite.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

import static net.acoyt.malachite.index.MalachiteBlocks.*;
import static net.acoyt.malachite.index.MalachiteEffects.OVERCHARGED;
import static net.acoyt.malachite.index.MalachiteItems.*;

public class MalachiteLangGenerator extends FabricLanguageProvider {
    public MalachiteLangGenerator(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder builder) {
        builder.add("itemGroup.malachite", "Malachite");

        builder.add(MALACHITE, "Malachite");
        builder.add(MALACHITE_DAGGER, "Malachite Dagger");
        builder.add(MALACHITE_LONGSWORD, "Malachite Longsword");
        builder.add("item.malachite.tooltip.charged", "Charged");

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

        builder.add("death.attack.dagger", "%1$s was pierced");
        builder.add("death.attack.dagger.item", "%1$s was pierced by %2$s using %3$s");
        builder.add("death.attack.dagger.player", "%1$s was pierced by %2$s");

        builder.add(OVERCHARGED.value(), "Overcharged");
    }
}
