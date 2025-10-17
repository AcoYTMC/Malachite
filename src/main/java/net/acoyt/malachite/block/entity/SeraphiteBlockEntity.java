package net.acoyt.malachite.block.entity;

import net.acoyt.malachite.block.ChiseledSeraphiteBlock;
import net.acoyt.malachite.block.MalachitePylonBlock;
import net.acoyt.malachite.index.MalachiteBlockEntities;
import net.acoyt.malachite.util.NbtUtils;
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
        for (int x = -5; x < 5; x++) {
            for (int y = -5; y < 5; y++) {
                for (int z = -5; z < 5; z++) {
                    BlockState pylonState = world.getBlockState(new BlockPos(x, y, z));
                    if (pylonState.contains(MalachitePylonBlock.CHARGE) && state.contains(ChiseledSeraphiteBlock.ACTIVE)) {
                        int charge = pylonState.get(MalachitePylonBlock.CHARGE);
                        boolean charged = state.get(ChiseledSeraphiteBlock.ACTIVE);
                        if (charge == 4 && !charged) {
                            world.setBlockState(pos, state.with(ChiseledSeraphiteBlock.ACTIVE, true));
                        } else if (charged) {
                            world.setBlockState(pos, state.with(ChiseledSeraphiteBlock.ACTIVE, false));
                        }
                    }
                }
            }
        }

        //boolean charged = state.get(ChiseledSeraphiteBlock.ACTIVE);
        //if (!charged && seraphite.pylonNearby) {
        //    world.setBlockState(pos, state.with(ChiseledSeraphiteBlock.ACTIVE, true));
        //} else if (charged && !seraphite.pylonNearby) {
        //    world.setBlockState(pos, state.with(ChiseledSeraphiteBlock.ACTIVE, false));
        //}
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putBoolean("pylonNearby", this.pylonNearby);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.pylonNearby = NbtUtils.getOrDefault(nbt, "pylonNearby", false);
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
