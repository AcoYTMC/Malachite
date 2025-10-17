package net.acoyt.malachite.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;

public class ChiseledSeraphiteBlock extends Block {
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");

    public ChiseledSeraphiteBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(ACTIVE, false));
    }

    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }
}
