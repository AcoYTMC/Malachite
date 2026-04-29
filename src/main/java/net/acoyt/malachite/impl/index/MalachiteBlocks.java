package net.acoyt.malachite.impl.index;

import net.acoyt.acornlib.api.registrants.BlockRegistrant;
import net.acoyt.malachite.data.provider.MalachiteBlockLootTableGen;
import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.block.*;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.sound.BlockSoundGroup;

import java.util.function.Function;

@SuppressWarnings("deprecation")
public interface MalachiteBlocks {
    BlockRegistrant BLOCKS = new BlockRegistrant(Malachite.MOD_ID);

    Block MALACHITE_PYLON = registerWithDrop("malachite_pylon", MalachitePylonBlock::new, Settings.copy(Blocks.IRON_BLOCK).ticksRandomly());

    Block SERAPHITE_CHAIN = registerWithDrop("seraphite_chain", ChainBlock::new, Settings.copy(Blocks.CHAIN).sounds(BlockSoundGroup.TUFF));
    Block SERAPHITE_LANTERN = registerWithDrop("seraphite_lantern", SeraphiteLanternBlock::new, Settings.copy(Blocks.LANTERN).sounds(BlockSoundGroup.TUFF));

    Block SERAPHITE = registerWithDrop("seraphite", Block::new, Settings.copy(Blocks.TUFF));
    Block SERAPHITE_SLAB = registerWithDrop("seraphite_slab", SlabBlock::new, Settings.copy(Blocks.TUFF_SLAB));
    Block SERAPHITE_STAIRS = registerWithDrop("seraphite_stairs", settings -> new StairsBlock(SERAPHITE.getDefaultState(), settings), Settings.copy(Blocks.TUFF_STAIRS));
    Block SERAPHITE_WALL = registerWithDrop("seraphite_wall", WallBlock::new, Settings.copy(Blocks.TUFF_WALL));

    Block POLISHED_SERAPHITE = registerWithDrop("polished_seraphite", Block::new, Settings.copy(Blocks.POLISHED_TUFF));
    Block POLISHED_SERAPHITE_SLAB = registerWithDrop("polished_seraphite_slab", SlabBlock::new, Settings.copy(Blocks.POLISHED_TUFF_SLAB));
    Block POLISHED_SERAPHITE_STAIRS = registerWithDrop("polished_seraphite_stairs", settings -> new StairsBlock(POLISHED_SERAPHITE.getDefaultState(), settings), Settings.copy(Blocks.POLISHED_TUFF_STAIRS));
    Block POLISHED_SERAPHITE_WALL = registerWithDrop("polished_seraphite_wall", WallBlock::new, Settings.copy(Blocks.POLISHED_TUFF_WALL));

    Block CHISELED_SERAPHITE = registerWithDrop("chiseled_seraphite", ChiseledSeraphiteBlock::new, Settings.copy(Blocks.CHISELED_TUFF));

    Block SERAPHITE_BRICKS = BLOCKS.registerWithItem("seraphite_bricks", Block::new, Settings.copy(Blocks.TUFF_BRICKS));
    Block SERAPHITE_BRICK_SLAB = registerWithDrop("seraphite_brick_slab", SlabBlock::new, Settings.copy(Blocks.TUFF_BRICK_SLAB));
    Block SERAPHITE_BRICK_STAIRS = registerWithDrop("seraphite_brick_stairs", settings -> new StairsBlock(SERAPHITE_BRICKS.getDefaultState(), settings), Settings.copy(Blocks.TUFF_BRICK_STAIRS));
    Block SERAPHITE_BRICK_WALL = registerWithDrop("seraphite_brick_wall", WallBlock::new, Settings.copy(Blocks.TUFF_BRICK_WALL));

    Block BUDDING_COPPER = registerWithDrop("budding_copper", BuddingCopperBlock::new, Settings.copy(Blocks.OXIDIZED_COPPER).ticksRandomly());

    Block MALACHITE_CLUSTER = BLOCKS.registerWithItem("malachite_cluster", settings -> new MalachiteClusterBlock(7.0F, 3.0F, settings), Settings.create().mapColor(MapColor.EMERALD_GREEN).solid().nonOpaque().sounds(BlockSoundGroup.BASALT).strength(1.5F).luminance(state -> 5).pistonBehavior(PistonBehavior.DESTROY));
    Block LARGE_MALACHITE_BUD = BLOCKS.registerWithItem("large_malachite_bud", settings -> new MalachiteClusterBlock(5.0F, 3.0F, settings), Settings.copyShallow(MALACHITE_CLUSTER).sounds(BlockSoundGroup.BASALT).luminance(state -> 4));
    Block MEDIUM_MALACHITE_BUD = BLOCKS.registerWithItem("medium_malachite_bud", settings -> new MalachiteClusterBlock(4.0F, 3.0F, settings), Settings.copyShallow(MALACHITE_CLUSTER).sounds(BlockSoundGroup.BASALT).luminance(state -> 2));
    Block SMALL_MALACHITE_BUD = BLOCKS.registerWithItem("small_malachite_bud", settings -> new MalachiteClusterBlock(3.0F, 4.0F, settings), Settings.copyShallow(MALACHITE_CLUSTER).sounds(BlockSoundGroup.BASALT).luminance(state -> 1));

    static Block registerWithDrop(String name, Function<Settings, Block> factory, Settings settings) {
        Block block = BLOCKS.registerWithItem(name, factory, settings);
        MalachiteBlockLootTableGen.BLOCKS.add(block);
        return block;
    }

    static void init() {}

    static void clientInit() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                MALACHITE_CLUSTER,
                LARGE_MALACHITE_BUD,
                MEDIUM_MALACHITE_BUD,
                SMALL_MALACHITE_BUD,
                SERAPHITE_CHAIN,
                SERAPHITE_LANTERN
        );
    }
}
