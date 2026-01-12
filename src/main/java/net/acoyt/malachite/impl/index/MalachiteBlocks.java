package net.acoyt.malachite.impl.index;

import net.acoyt.acornlib.impl.item.TranslationBlockItem;
import net.acoyt.malachite.data.provider.MalachiteBlockLootTableGen;
import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.block.BuddingCopperBlock;
import net.acoyt.malachite.impl.block.ChiseledSeraphiteBlock;
import net.acoyt.malachite.impl.block.MalachiteClusterBlock;
import net.acoyt.malachite.impl.block.MalachitePylonBlock;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;

import java.util.function.Function;

@SuppressWarnings("deprecation")
public interface MalachiteBlocks {
    Block MALACHITE_PYLON = createWithItem("malachite_pylon", MalachitePylonBlock::new, Settings.copy(Blocks.IRON_BLOCK).ticksRandomly(), true);

    Block SERAPHITE = createWithItem("seraphite", Block::new, Settings.copy(Blocks.TUFF));
    Block SERAPHITE_SLAB = createWithItem("seraphite_slab", SlabBlock::new, Settings.copy(Blocks.TUFF_SLAB), true);
    Block SERAPHITE_STAIRS = createWithItem("seraphite_stairs", settings -> new StairsBlock(SERAPHITE.getDefaultState(), settings), Settings.copy(Blocks.TUFF_STAIRS), true);
    Block SERAPHITE_WALL = createWithItem("seraphite_wall", WallBlock::new, Settings.copy(Blocks.TUFF_WALL), true);

    Block POLISHED_SERAPHITE = createWithItem("polished_seraphite", Block::new, Settings.copy(Blocks.POLISHED_TUFF), true);
    Block POLISHED_SERAPHITE_SLAB = createWithItem("polished_seraphite_slab", SlabBlock::new, Settings.copy(Blocks.POLISHED_TUFF_SLAB), true);
    Block POLISHED_SERAPHITE_STAIRS = createWithItem("polished_seraphite_stairs", settings -> new StairsBlock(POLISHED_SERAPHITE.getDefaultState(), settings), Settings.copy(Blocks.POLISHED_TUFF_STAIRS), true);
    Block POLISHED_SERAPHITE_WALL = createWithItem("polished_seraphite_wall", WallBlock::new, Settings.copy(Blocks.POLISHED_TUFF_WALL), true);

    Block CHISELED_SERAPHITE = createWithItem("chiseled_seraphite", ChiseledSeraphiteBlock::new, Settings.copy(Blocks.CHISELED_TUFF), true);

    Block SERAPHITE_BRICKS = createWithItem("seraphite_bricks", Block::new, Settings.copy(Blocks.TUFF_BRICKS));
    Block SERAPHITE_BRICK_SLAB = createWithItem("seraphite_brick_slab", SlabBlock::new, Settings.copy(Blocks.TUFF_BRICK_SLAB), true);
    Block SERAPHITE_BRICK_STAIRS = createWithItem("seraphite_brick_stairs", settings -> new StairsBlock(SERAPHITE_BRICKS.getDefaultState(), settings), Settings.copy(Blocks.TUFF_BRICK_STAIRS), true);
    Block SERAPHITE_BRICK_WALL = createWithItem("seraphite_brick_wall", WallBlock::new, Settings.copy(Blocks.TUFF_BRICK_WALL), true);

    Block BUDDING_COPPER = createWithItem("budding_copper", BuddingCopperBlock::new, Settings.copy(Blocks.OXIDIZED_COPPER).ticksRandomly(), true);

    Block MALACHITE_CLUSTER = createWithItem("malachite_cluster", settings -> new MalachiteClusterBlock(7.0F, 3.0F, settings), Settings.create().mapColor(MapColor.EMERALD_GREEN).solid().nonOpaque().sounds(BlockSoundGroup.BASALT).strength(1.5F).luminance(state -> 5).pistonBehavior(PistonBehavior.DESTROY));
    Block LARGE_MALACHITE_BUD = createWithItem("large_malachite_bud", settings -> new MalachiteClusterBlock(5.0F, 3.0F, settings), Settings.copyShallow(MALACHITE_CLUSTER).sounds(BlockSoundGroup.BASALT).luminance(state -> 4));
    Block MEDIUM_MALACHITE_BUD = createWithItem("medium_malachite_bud", settings -> new MalachiteClusterBlock(4.0F, 3.0F, settings), Settings.copyShallow(MALACHITE_CLUSTER).sounds(BlockSoundGroup.BASALT).luminance(state -> 2));
    Block SMALL_MALACHITE_BUD = createWithItem("small_malachite_bud", settings -> new MalachiteClusterBlock(3.0F, 4.0F, settings), Settings.copyShallow(MALACHITE_CLUSTER).sounds(BlockSoundGroup.BASALT).luminance(state -> 1));

    static Block create(String name, Function<Settings, Block> factory, Settings settings) {
        Block block = factory.apply(settings);
        return Registry.register(Registries.BLOCK, Malachite.id(name), block);
    }

    static Block createWithItem(String name, Function<Settings, Block> factory, Settings settings, boolean regDrop) {
        Block block = create(name, factory, settings);
        MalachiteItems.create(name, itemSettings -> new TranslationBlockItem(block, itemSettings), new Item.Settings());
        if (regDrop) MalachiteBlockLootTableGen.BLOCKS.add(block);
        return block;
    }

    static Block createWithItem(String name, Function<Settings, Block> factory, Settings settings) {
        return createWithItem(name, factory, settings, false);
    }

    static void init() {
        //
    }

    static void clientInit() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                MALACHITE_CLUSTER,
                LARGE_MALACHITE_BUD,
                MEDIUM_MALACHITE_BUD,
                SMALL_MALACHITE_BUD
        );
    }
}
