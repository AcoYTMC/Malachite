package net.acoyt.malachite.block;

import com.mojang.serialization.MapCodec;
import net.acoyt.malachite.block.entity.SeraphiteBlockEntity;
import net.acoyt.malachite.index.MalachiteBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ChiseledSeraphiteBlock extends BlockWithEntity {
    public static final MapCodec<ChiseledSeraphiteBlock> CODEC = createCodec(ChiseledSeraphiteBlock::new);
    public static final BooleanProperty ACTIVE = BooleanProperty.of("active");

    public ChiseledSeraphiteBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(ACTIVE, false));
    }

    @Override
    public MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, MalachiteBlockEntities.SERAPHITE, SeraphiteBlockEntity::tick);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SeraphiteBlockEntity(pos, state);
    }

    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }
}
