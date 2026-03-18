package net.acoyt.malachite.impl.event;

import net.acoyt.malachite.impl.block.entity.SeraphiteBlockEntity;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class UpdateSeraphiteEvent implements PlayerBlockBreakEvents.After {
    @Override
    public void afterBlockBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity) {
        for (int x = -5; x < 5; x++) {
            for (int y = -5; y < 5; y++) {
                for (int z = -5; z < 5; z++) {
                    BlockPos blockPos = pos.add(x, y, z);
                    if (world.getBlockEntity(blockPos) instanceof SeraphiteBlockEntity seraphite && seraphite.pylonNearby) {
                        seraphite.pylonNearby = false;
                        world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
                        seraphite.markDirty();
                    }
                }
            }
        }
    }
}
