package net.acoyt.malachite.impl.block;

import com.mojang.serialization.MapCodec;
import net.acoyt.malachite.impl.block.entity.PylonBlockEntity;
import net.acoyt.malachite.impl.component.MalachiteComponent;
import net.acoyt.malachite.impl.index.MalachiteBlockEntities;
import net.acoyt.malachite.impl.index.MalachiteDataComponents;
import net.acoyt.malachite.impl.index.MalachiteParticles;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MalachitePylonBlock extends BlockWithEntity implements Waterloggable {
    public static final MapCodec<MalachitePylonBlock> CODEC = createCodec(MalachitePylonBlock::new);
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final EnumProperty<Direction> FACING = Properties.FACING;
    public static final IntProperty CHARGE = IntProperty.of("charge", 0, 4);
    private static final VoxelShape xAxisShape = createCuboidShape(0.0F, 3.5F, 3.5F, 16.0F, 12.5F, 12.5F);
    private static final VoxelShape yAxisShape = createCuboidShape(3.5F, 0.0F, 3.5F, 12.5F, 16.0F, 12.5F);
    private static final VoxelShape zAxisShape = createCuboidShape(3.5F, 3.5F, 0.0F, 12.5F, 12.5F, 16.0F);

    public MalachitePylonBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false).with(CHARGE, 0));
    }

    public MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    public ItemActionResult onUseWithItem(ItemStack itemStack, BlockState state, World world, BlockPos pos, PlayerEntity user, Hand hand, BlockHitResult hit) {
        MalachiteComponent component = itemStack.getOrDefault(MalachiteDataComponents.MALACHITE, MalachiteComponent.DAGGER);
        if (itemStack.contains(MalachiteDataComponents.MALACHITE) && component.charge() < component.maxCharge() && state.contains(CHARGE) && state.get(CHARGE) == 4) {
            itemStack.set(MalachiteDataComponents.MALACHITE, component.withCharge(component.maxCharge()));

            world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.BLOCKS, 1.0F, (float) (1.0F + user.getRandom().nextGaussian() / 10.0F));

            if (!user.isCreative()) {
                world.setBlockState(pos, state.with(CHARGE, 0));
            }

            return ItemActionResult.SUCCESS;
        }

        return ItemActionResult.FAIL;
    }

    public static boolean isPowered(World world, BlockPos pos) {
        for (Direction direction : Direction.values()) {
            if (world.isEmittingRedstonePower(pos.offset(direction), direction)) {
                return true;
            }
        }

        return false;
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos blockPos, Random random) {
        int charge = world.getBlockState(blockPos).contains(CHARGE) ? world.getBlockState(blockPos).get(CHARGE) : 2;
        int i = charge == 4 ? 1 :
                charge == 3 ? 2 :
                charge == 2 ? 3 :
                charge == 1 ? 4 : 5;

        if (random.nextInt(i) != 0) return;

        Vec3d pos = blockPos.toCenterPos();
        world.addParticle(MalachiteParticles.SPARK, pos.x - 0.5 + random.nextDouble(), pos.y - 0.25 + random.nextDouble(), pos.z - 0.5 + random.nextDouble(), 0.0, 0.0, 0.0);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, MalachiteBlockEntities.PYLON, PylonBlockEntity::tick);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PylonBlockEntity(pos, state);
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)) {
            case NORTH, SOUTH -> zAxisShape;
            case EAST, WEST -> xAxisShape;
            default -> yAxisShape;
        };
    }

    public BlockState getPlacementState(@NotNull ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState().with(WATERLOGGED, fluidState.isOf(Fluids.WATER)).with(FACING, ctx.getSide());
    }

    public void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, FACING, CHARGE);
    }

    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return state;
    }
}
