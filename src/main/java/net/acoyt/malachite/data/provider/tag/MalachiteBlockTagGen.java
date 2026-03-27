package net.acoyt.malachite.data.provider.tag;

import net.acoyt.malachite.impl.index.tag.MalachiteBlockTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

import static net.acoyt.malachite.impl.index.MalachiteBlocks.*;

public class MalachiteBlockTagGen extends FabricTagProvider.BlockTagProvider {
    public MalachiteBlockTagGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    public void configure(RegistryWrapper.WrapperLookup registries) {
        this.getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .forceAddTag(MalachiteBlockTags.SERAPHITE)
                .add(
                        BUDDING_COPPER,
                        MALACHITE_PYLON,
                        MALACHITE_CLUSTER,
                        SMALL_MALACHITE_BUD,
                        MEDIUM_MALACHITE_BUD,
                        LARGE_MALACHITE_BUD
                )
                .setReplace(false);

        this.getOrCreateTagBuilder(MalachiteBlockTags.SERAPHITE)
                .add(
                        SERAPHITE_CHAIN,
                        SERAPHITE_LANTERN,
                        CHISELED_SERAPHITE,
                        POLISHED_SERAPHITE,
                        POLISHED_SERAPHITE_SLAB,
                        POLISHED_SERAPHITE_STAIRS,
                        POLISHED_SERAPHITE_WALL,
                        SERAPHITE,
                        SERAPHITE_BRICK_SLAB,
                        SERAPHITE_BRICK_STAIRS,
                        SERAPHITE_BRICK_WALL,
                        SERAPHITE_BRICKS,
                        SERAPHITE_SLAB,
                        SERAPHITE_STAIRS,
                        SERAPHITE_WALL
                )
                .setReplace(false);
    }
}
