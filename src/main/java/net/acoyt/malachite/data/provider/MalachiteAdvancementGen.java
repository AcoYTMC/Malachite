package net.acoyt.malachite.data.provider;

import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.index.MalachiteCriterions;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementEntry;
import net.minecraft.advancement.AdvancementFrame;
import net.minecraft.advancement.AdvancementRequirements;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.ItemCriterion;
import net.minecraft.advancement.criterion.RecipeCraftedCriterion;
import net.minecraft.advancement.criterion.TickCriterion;
import net.minecraft.loot.condition.LocationCheckLootCondition;
import net.minecraft.predicate.BlockPredicate;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import static net.acoyt.malachite.impl.index.MalachiteBlocks.MALACHITE_PYLON;
import static net.acoyt.malachite.impl.index.MalachiteItems.MALACHITE_LONGSWORD;

@SuppressWarnings("removal")
public class MalachiteAdvancementGen extends FabricAdvancementProvider {
    public static final Map<Identifier, AdvancementEntry> entries = new HashMap<>();

    public MalachiteAdvancementGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(output, registryLookup);
    }

    public void generateAdvancement(RegistryWrapper.WrapperLookup registries, Consumer<AdvancementEntry> consumer) {
        // Electroplating
        AdvancementEntry electroplating = Advancement.Builder.createUntelemetered()
                .parent(Identifier.ofVanilla("adventure/root"))
                .display(
                        MALACHITE_LONGSWORD,
                        Text.translatable("advancements.malachite.electroplating.title"),
                        Text.translatable("advancements.malachite.electroplating.description"),
                        null,
                        AdvancementFrame.GOAL,
                        true,
                        true,
                        false
                ).requirements(AdvancementRequirements.allOf(List.of("electroplating")))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.AND)
                .criterion("electroplating", RecipeCraftedCriterion.Conditions.create(Malachite.id("malachite_longsword")))
                .build(Malachite.id("electroplating"));

        consumer.accept(electroplating);
        entries.put(Malachite.id("electroplating"), electroplating);

        // Doping
        AdvancementEntry doping = Advancement.Builder.createUntelemetered()
                .parent(entries.get(Malachite.id("electroplating")))
                .display(
                        MALACHITE_PYLON,
                        Text.translatable("advancements.malachite.doping.title"),
                        Text.translatable("advancements.malachite.doping.description"),
                        null,
                        AdvancementFrame.GOAL,
                        true,
                        true,
                        false
                ).requirements(AdvancementRequirements.allOf(List.of("doping")))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.AND)
                .criterion("doping", MalachiteCriterions.DOPING.create(new TickCriterion.Conditions(Optional.empty())))
                .build(Malachite.id("doping"));

        consumer.accept(doping);
        entries.put(Malachite.id("doping"), doping);

        // Piezoelectricity
        AdvancementEntry piezoelectricity = Advancement.Builder.createUntelemetered()
                .parent(entries.get(Malachite.id("electroplating")))
                .display(
                        MALACHITE_PYLON,
                        Text.translatable("advancements.malachite.piezoelectricity.title"),
                        Text.translatable("advancements.malachite.piezoelectricity.description"),
                        null,
                        AdvancementFrame.GOAL,
                        true,
                        true,
                        false
                ).requirements(AdvancementRequirements.allOf(List.of("piezoelectricity")))
                .criteriaMerger(AdvancementRequirements.CriterionMerger.AND)
                .criterion("piezoelectricity", Criteria.PLACED_BLOCK.create(
                        new ItemCriterion.Conditions(
                                Optional.empty(),
                                Optional.of(LootContextPredicate.create(
                                        new LocationCheckLootCondition(
                                                Optional.of(
                                                        new LocationPredicate.Builder()
                                                                .block(BlockPredicate.Builder.create()
                                                                        .blocks(MALACHITE_PYLON)
                                                                ).build()),
                                                BlockPos.ORIGIN
                                        )
                                ))
                        )
                )).build(Malachite.id("piezoelectricity"));

        consumer.accept(piezoelectricity);
        entries.put(Malachite.id("piezoelectricity"), piezoelectricity);
    }
}
