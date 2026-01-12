package net.acoyt.malachite.impl.block.entity;

import net.acoyt.malachite.impl.block.ChiseledSeraphiteBlock;
import net.acoyt.malachite.impl.block.MalachitePylonBlock;
import net.acoyt.malachite.impl.index.MalachiteBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SeraphiteBlockEntity extends BlockEntity {
    public boolean pylonNearby = false;

    public SeraphiteBlockEntity(BlockPos pos, BlockState state) {
        super(MalachiteBlockEntities.SERAPHITE, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, @NotNull SeraphiteBlockEntity seraphite) {
        if (!seraphite.pylonNearby) {
            label22:
            for (int x = -5; x < 5; x++) {
                for (int y = -5; y < 5; y++) {
                    for (int z = -5; z < 5; z++) {
                        BlockPos blockPos = pos.add(x, y, z);
                        BlockState blockState = world.getBlockState(blockPos);
                        if (blockState.contains(MalachitePylonBlock.CHARGE) && blockState.get(MalachitePylonBlock.CHARGE) == 4) {
                            boolean charged = state.get(ChiseledSeraphiteBlock.ACTIVE);
                            if (!charged) world.setBlockState(pos, state.with(ChiseledSeraphiteBlock.ACTIVE, true));

                            seraphite.pylonNearby = true;
                            world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
                            seraphite.markDirty();

                            break label22;
                        }
                    }
                }
            }
        }

        boolean charged = state.get(ChiseledSeraphiteBlock.ACTIVE);
        if (!charged && seraphite.pylonNearby) {
            world.setBlockState(pos, state.with(ChiseledSeraphiteBlock.ACTIVE, true));
        } else if (charged && !seraphite.pylonNearby) {
            world.setBlockState(pos, state.with(ChiseledSeraphiteBlock.ACTIVE, false));
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putBoolean("pylonNearby", this.pylonNearby);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.pylonNearby = nbt.contains("pylonNearby") && nbt.getBoolean("pylonNearby");
    }

    @Override
    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createNbt(registries);
    }
}
