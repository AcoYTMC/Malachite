package net.acoyt.malachite.block;

import net.acoyt.malachite.index.MalachiteBlocks;
import net.minecraft.block.AmethystClusterBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;

public class BuddingCopperBlock extends Block {
    public BuddingCopperBlock(Settings settings) {
        super(settings);
    }

    public void randomTick(BlockState blockState, ServerWorld world, BlockPos pos, Random random) {
        if (random.nextInt(5) == 0) {
            BlockState state = world.getBlockState(pos.up());
            Block block = null;
            if (canGrowIn(state)) {
                block = MalachiteBlocks.SMALL_MALACHITE_BUD;
            } else if (state.isOf(MalachiteBlocks.SMALL_MALACHITE_BUD)) {
                block = MalachiteBlocks.MEDIUM_MALACHITE_BUD;
            } else if (state.isOf(MalachiteBlocks.MEDIUM_MALACHITE_BUD)) {
                block = MalachiteBlocks.LARGE_MALACHITE_BUD;
            } else if (state.isOf(MalachiteBlocks.LARGE_MALACHITE_BUD)) {
                block = MalachiteBlocks.MALACHITE_CLUSTER;
            }

            if (block != null) {
                BlockState newState = block.getDefaultState()
                        .with(MalachiteClusterBlock.FACING, Direction.UP)
                        .with(AmethystClusterBlock.WATERLOGGED, blockState.getFluidState().getFluid() == Fluids.WATER);
                world.setBlockState(pos.up(), newState);
            }
        }
    }

    public static boolean canGrowIn(BlockState state) {
        return state.isAir() || state.isOf(Blocks.WATER) && state.getFluidState().getLevel() == 8;
    }
}
