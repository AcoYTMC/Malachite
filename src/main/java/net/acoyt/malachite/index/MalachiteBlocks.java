package net.acoyt.malachite.index;

import net.acoyt.acornlib.impl.item.TranslationBlockItem;
import net.acoyt.malachite.Malachite;
import net.acoyt.malachite.block.ChiseledSeraphiteBlock;
import net.acoyt.malachite.block.MalachitePylonBlock;
import net.minecraft.block.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.Function;

public interface MalachiteBlocks {
    Block MALACHITE_PYLON = createWithItem("malachite_pylon", MalachitePylonBlock::new, AbstractBlock.Settings.copy(Blocks.TUFF_WALL));

    Block SERAPHITE = createWithItem("seraphite", Block::new, AbstractBlock.Settings.copy(Blocks.TUFF));
    Block SERAPHITE_SLAB = createWithItem("seraphite_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.TUFF_SLAB));
    Block SERAPHITE_STAIRS = createWithItem("seraphite_stairs", settings -> new StairsBlock(SERAPHITE.getDefaultState(), settings), AbstractBlock.Settings.copy(Blocks.TUFF_STAIRS));
    Block SERAPHITE_WALL = createWithItem("seraphite_wall", WallBlock::new, AbstractBlock.Settings.copy(Blocks.TUFF_WALL));

    Block POLISHED_SERAPHITE = createWithItem("polished_seraphite", Block::new, AbstractBlock.Settings.copy(Blocks.POLISHED_TUFF));
    Block POLISHED_SERAPHITE_SLAB = createWithItem("polished_seraphite_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.POLISHED_TUFF_SLAB));
    Block POLISHED_SERAPHITE_STAIRS = createWithItem("polished_seraphite_stairs", settings -> new StairsBlock(POLISHED_SERAPHITE.getDefaultState(), settings), AbstractBlock.Settings.copy(Blocks.POLISHED_TUFF_STAIRS));
    Block POLISHED_SERAPHITE_WALL = createWithItem("polished_seraphite_wall", WallBlock::new, AbstractBlock.Settings.copy(Blocks.POLISHED_TUFF_WALL));

    Block CHISELED_SERAPHITE = createWithItem("chiseled_seraphite", ChiseledSeraphiteBlock::new, AbstractBlock.Settings.copy(Blocks.CHISELED_TUFF));

    Block SERAPHITE_BRICKS = createWithItem("seraphite_bricks", Block::new, AbstractBlock.Settings.copy(Blocks.TUFF_BRICKS));
    Block SERAPHITE_BRICK_SLAB = createWithItem("seraphite_brick_slab", SlabBlock::new, AbstractBlock.Settings.copy(Blocks.TUFF_BRICK_SLAB));
    Block SERAPHITE_BRICK_STAIRS = createWithItem("seraphite_brick_stairs", settings -> new StairsBlock(SERAPHITE_BRICKS.getDefaultState(), settings), AbstractBlock.Settings.copy(Blocks.TUFF_BRICK_STAIRS));
    Block SERAPHITE_BRICK_WALL = createWithItem("seraphite_brick_wall", WallBlock::new, AbstractBlock.Settings.copy(Blocks.TUFF_BRICK_WALL));

    static Block create(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        Block block = factory.apply(settings);
        return Registry.register(Registries.BLOCK, Malachite.id(name), block);
    }

    static Block createWithItem(String name, Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings) {
        Block block = create(name, factory, settings);
        MalachiteItems.create(name, itemSettings -> new TranslationBlockItem(block, itemSettings), new Item.Settings());
        return block;
    }

    static void init() {
        //
    }

    static void clientInit() {
        //
    }
}
