package net.acoyt.malachite.impl.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class SeraphiteLanternBlock extends LanternBlock {
    public static final VoxelShape STANDING_SHAPE = createCuboidShape(4.0, 0.0, 4.0, 12.0, 11.0, 12.0);
    public static final VoxelShape HANGING_SHAPE = createCuboidShape(4.0, 0.0, 4.0, 12.0, 11.0, 12.0);

    public SeraphiteLanternBlock(Settings settings) {
        super(settings);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(HANGING) ? HANGING_SHAPE : STANDING_SHAPE;
    }
}
