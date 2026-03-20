package net.acoyt.malachite.data.provider.lang;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

import static net.acoyt.malachite.impl.index.MalachiteBlocks.*;
import static net.acoyt.malachite.impl.index.MalachiteEffects.OVERCHARGED;
import static net.acoyt.malachite.impl.index.MalachiteItems.*;

public class MalachiteLolCatLangGen extends FabricLanguageProvider {
    public MalachiteLolCatLangGen(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "lol_us", registryLookup);
    }

    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder builder) {
        // Advancements
        builder.add("advancements.malachite.doping.title", "doopy");
        builder.add("advancements.malachite.doping.description", "let it grow");
        builder.add("advancements.malachite.electroplating.title", "shoking");
        builder.add("advancements.malachite.electroplating.description", "make big sord");
        builder.add("advancements.malachite.piezoelectricity.title", "pie zero shockies");
        builder.add("advancements.malachite.piezoelectricity.description", "*shoves you*");

        // Item Groups
        builder.add("itemGroup.malachite", "malcite");

        // Items
        builder.add(MALACHITE, "gren rock");
        builder.add(MALACHITE_DAGGER, "gren rock shank");
        builder.add(MALACHITE_LONGSWORD, "gren rock big sord");
        builder.add("item.malachite.tooltip.charged", "shocker");

        // Blocks
        builder.add(MALACHITE_PYLON, "gren rock pusher");
        builder.add(SERAPHITE_CHAIN, "gren chan");
        builder.add(SERAPHITE_LANTERN, "gren glowy");
        builder.add(SERAPHITE, "gren stone");
        builder.add(SERAPHITE_SLAB, "gren stone slab");
        builder.add(SERAPHITE_STAIRS, "gren stone stars");
        builder.add(SERAPHITE_WALL, "gren stone wal");
        builder.add(POLISHED_SERAPHITE, "fancy gren stone");
        builder.add(POLISHED_SERAPHITE_SLAB, "fancy gren stone slab");
        builder.add(POLISHED_SERAPHITE_STAIRS, "fancy gren stone stars");
        builder.add(POLISHED_SERAPHITE_WALL, "fancy gren stone wal");
        builder.add(CHISELED_SERAPHITE, "chise gren stone");
        builder.add(SERAPHITE_BRICKS, "gren stone briks");
        builder.add(SERAPHITE_BRICK_SLAB, "gren stone brik slab");
        builder.add(SERAPHITE_BRICK_STAIRS, "gren stone brik stars");
        builder.add(SERAPHITE_BRICK_WALL, "gren stone brik wal");

        builder.add(MALACHITE_CLUSTER, "gren clustr");
        builder.add(LARGE_MALACHITE_BUD, "big gren buddy");
        builder.add(MEDIUM_MALACHITE_BUD, "nermal gren buddy");
        builder.add(SMALL_MALACHITE_BUD, "smol gren buddy");

        builder.add(BUDDING_COPPER, "buddy coper");

        // Enchantments
        builder.add("enchantment.malachite.disrupt", "Orb");
        builder.add("enchantment.malachite.disrupt.desc", "Ponder the scary orb");
        builder.add("enchantment.malachite.magnetic", "Attrac");
        builder.add("enchantment.malachite.magnetic.desc", "Me when i pull] you");
        builder.add("enchantment.malachite.shockwave", "Shockwave");
        builder.add("enchantment.malachite.shockwave.desc", "Me when i push you");
        builder.add("enchantment.malachite.voltage", "Voltage");
        builder.add("enchantment.malachite.voltage.desc", "Now u see me now u dont");

        // Damage Types
        builder.add("death.attack.dagger", "%1$s was pierced");
        builder.add("death.attack.dagger.item", "%1$s was pierced by %2$s using %3$s");
        builder.add("death.attack.dagger.player", "%1$s was pierced by %2$s");

        builder.add("death.attack.overcharged", "%1$s was overcharged");
        builder.add("death.attack.overcharged.item", "%1$s was overcharged by %2$s using %3$s");
        builder.add("death.attack.overcharged.player", "%1$s was overcharged by %2$s");

        // Status Effects
        builder.add(OVERCHARGED.value(), "sohcked");

        // Trim Materials
        builder.add("trim_material.malachite.malachite", "gren matrial");

        // Potions
        builder.add("item.minecraft.tipped_arrow.effect.overcharged", "Speshul Shooty Tignh Dat Makez u Shocked");
        builder.add("item.minecraft.potion.effect.overcharged", "Poshun of Shocked");
        builder.add("item.minecraft.splash_potion.effect.overcharged", "Spleshy Poshun of Shocked");
        builder.add("item.minecraft.lingering_potion.effect.overcharged", "Linnerin Poshun of Shocked");

        // Subtitles
        builder.add("subtitles.malachite.entity.dagger.hit", "Dagger Hits");
        builder.add("subtitles.malachite.entity.dagger.throw", "Dagger Throws");
        builder.add("subtitles.malachite.item.longsword.block", "Longsword Blocks");
        builder.add("subtitles.malachite.entity.energy_beam.shoot", "Energy Beam Shoots");

        // Config
        builder.add("malachite.midnightconfig.title", "malcite");
        builder.add("malachite.midnightconfig.billboardBlast", "Billboard Blast Particle");
        builder.add("malachite.midnightconfig.billboardBlast.tooltip", "should i stay or should i go now");
    }
}
