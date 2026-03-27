package net.acoyt.malachite.impl.block.entity;

import net.acoyt.malachite.impl.block.MalachitePylonBlock;
import net.acoyt.malachite.impl.cca.entity.NearbyPylonComponent;
import net.acoyt.malachite.impl.index.MalachiteBlockEntities;
import net.acoyt.malachite.impl.index.MalachiteEffects;
import net.acoyt.malachite.impl.index.data.MalachiteDamageTypes;
import net.acoyt.malachite.impl.util.Util;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
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
        if (!MalachiteBlockEntities.PYLON.supports(state)) return;

        Box box = new Box(pos).expand(19); // 20 Block radius

        for (LivingEntity living : world.getEntitiesByClass(LivingEntity.class, box, LivingEntity::isAlive)) {
            NearbyPylonComponent component = NearbyPylonComponent.KEY.get(living);
            if (!component.isNearby() && state.getOrEmpty(MalachitePylonBlock.CHARGE).orElse(0) != 4)
                component.setNearby(true); // If the component is not marked as nearby, set it to nearby, that simple :P

            if (!MalachitePylonBlock.isPowered(world, pos)) {
                if (pylon.fallback == 0 && world.getTime() - living.lastDamageTime == 1L) { // If was recently attacked, and fallback time is 0, update block animationState to increment charge :D
                    if (state.contains(MalachitePylonBlock.CHARGE)) {
                        int i = state.get(MalachitePylonBlock.CHARGE);
                        if (i < 4) {
                            world.setBlockState(pos, state.with(MalachitePylonBlock.CHARGE, i + 1));
                        }
                    }

                    pylon.fallback = 15;
                    //world.updateListeners(pos, animationState, animationState, Block.NOTIFY_LISTENERS);
                    pylon.markDirty();
                }
            } else if (state.getOrEmpty(MalachitePylonBlock.CHARGE).orElse(0) == 4) {
                world.setBlockState(pos, state.with(MalachitePylonBlock.CHARGE, 0));

                Vec3d vec3d = pos.toCenterPos();

                Direction direction = state.getOrEmpty(MalachitePylonBlock.FACING).orElse(Direction.UP);
                if (direction == Direction.UP || direction == Direction.DOWN) {
                    boolean bl = living.getPos().y < vec3d.y - 1; // if living is below the Pylon
                    living.setVelocity(vec3d.subtract(living.getPos()).multiply(-3, 0, -3).add(0, bl ? -1.6 : 1.6, 0));

                    Util.spawnShockwave(world, vec3d, 8.0f, new Vec3d(0, 0.5, 0));
                } else if (direction == Direction.EAST || direction == Direction.WEST) {
                    boolean bl = living.getPos().x < vec3d.x + 1;
                    living.setVelocity(vec3d.subtract(living.getPos()).multiply(-3, -3, 0).add(bl ? -1.6 : 1.6, 0, 0));

                    Util.spawnBlast(world, 0.0F, 0xFF53efac, 8.0F, vec3d);
                } else if (direction == Direction.NORTH || direction == Direction.SOUTH) {
                    boolean bl = living.getPos().z < vec3d.z - 1;
                    living.setVelocity(vec3d.subtract(living.getPos()).multiply(0, -3, -3).add(0, 0, bl ? -1.6 : 1.6));

                    Util.spawnBlast(world, 90.0F, 0xFF53efac, 8.0F, vec3d);
                }

                living.velocityModified = true;

                living.damage(MalachiteDamageTypes.create(world, MalachiteDamageTypes.OVERCHARGED), 3);
                living.addStatusEffect(new StatusEffectInstance(MalachiteEffects.OVERCHARGED, living.getRandom().nextBetween(10, 15) * 20));
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

    public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        nbt.putInt("fallback", this.fallback);
    }

    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        this.fallback = nbt.getInt("fallback");
    }

    public @Nullable Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        return createNbt(registries);
    }
}
