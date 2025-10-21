package net.acoyt.malachite.block.entity;

import net.acoyt.malachite.Malachite;
import net.acoyt.malachite.block.MalachitePylonBlock;
import net.acoyt.malachite.cca.NearbyPylonComponent;
import net.acoyt.malachite.index.MalachiteBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PylonBlockEntity extends BlockEntity {
    public int fallback = 0;

    public PylonBlockEntity(BlockPos pos, BlockState state) {
        super(MalachiteBlockEntities.PYLON, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, @NotNull PylonBlockEntity pylon) {
        Box box = new Box(pos).expand(19); // 20 Block radius

        for (LivingEntity living : world.getEntitiesByClass(LivingEntity.class, box, LivingEntity::isAlive)) {
            NearbyPylonComponent component = NearbyPylonComponent.KEY.get(living);
            if (!component.isNearby() && world.getBlockState(pos).get(MalachitePylonBlock.CHARGE) != 4)
                component.setNearby(true); // If the component is not marked as nearby, set it to nearby, that simple :P

            if (!MalachitePylonBlock.isPowered(world, pos)) {
                if (pylon.fallback == 0 && world.getTime() - living.lastDamageTime == 1L) { // If was recently attacked, and fallback time is 0, update block state to increment charge :D
                    if (state.contains(MalachitePylonBlock.CHARGE)) {
                        int i = state.get(MalachitePylonBlock.CHARGE);
                        if (i < 4) {
                            world.setBlockState(pos, state.with(MalachitePylonBlock.CHARGE, i + 1));
                        }
                    }

                    pylon.fallback = 15;
                    world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
                    pylon.markDirty();
                }
            } else if (state.contains(MalachitePylonBlock.CHARGE) && state.get(MalachitePylonBlock.CHARGE) == 4) {
                world.setBlockState(pos, state.with(MalachitePylonBlock.CHARGE, 0));

                Vec3d vec3d = pos.toCenterPos();
                boolean bl = living.getPos().y < vec3d.y - 1; // if living is below the Pylon

                living.setVelocity(vec3d.subtract(living.getPos()).multiply(-3, 0, -3).add(0, bl ? -1.6 : 1.6, 0));
                living.velocityModified = true;

                Malachite.spawnShockwave(world, vec3d, 8.0f, new Vec3d(0, 0.5, 0));
            }
        }

        if (pylon.fallback > 0) {
            pylon.fallback--;
            if (pylon.fallback == 0) {
                world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
                pylon.markDirty();
            }
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putInt("fallback", this.fallback);
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.fallback = nbt.getInt("fallback");
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
