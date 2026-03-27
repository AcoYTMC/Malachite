package net.acoyt.malachite.impl.event;

import net.acoyt.malachite.impl.index.MalachiteBlocks;
import net.acoyt.malachite.impl.index.MalachiteCriterions;
import net.acoyt.malachite.impl.index.MalachiteParticles;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class MakeBuddingCopperEvent implements UseBlockCallback {
    public ActionResult interact(PlayerEntity user, World world, Hand hand, BlockHitResult hitResult) {
        ItemStack stack = user.getStackInHand(hand);
        if (stack.isOf(Items.ECHO_SHARD) && user.isSneaking() && world.getBlockState(hitResult.getBlockPos()).isOf(Blocks.OXIDIZED_COPPER)) {
            world.setBlockState(hitResult.getBlockPos(), MalachiteBlocks.BUDDING_COPPER.getDefaultState());
            Vec3d pos = hitResult.getPos();
            Random random = world.getRandom();
            double d = pos.getX() + random.nextDouble() - 0.5;
            double e = pos.getY() + 0.25;
            double f = pos.getZ() + random.nextDouble() - 0.5;

            for (int i = 0; i < 4; i++) {
                world.addParticle(ParticleTypes.LAVA, pos.getX() + random.nextDouble() - 0.5, pos.getY() + 1, pos.getZ() + random.nextDouble() - 0.5, 0.0, 0.0, 0.0);
            }

            for (int i = 0; i < 7; i++) {
                world.addParticle(MalachiteParticles.SPARK, pos.getX() + random.nextDouble() - 0.5, pos.getY() + random.nextDouble() + 0.25, pos.getZ() + random.nextDouble() - 0.5, 0.0, 0.0, 0.0);
            }

            user.swingHand(hand, !world.isClient);
            stack.decrementUnlessCreative(1, user);

            world.playSound(null, d, e, f, SoundEvents.BLOCK_CHORUS_FLOWER_GROW, SoundCategory.BLOCKS, 3.0F, (float) (1.0F + user.getRandom().nextGaussian() / 10.0F));
            world.playSound(null, d, e, f, SoundEvents.BLOCK_BASALT_BREAK, SoundCategory.BLOCKS, 0.45F, (float) (1.0F + user.getRandom().nextGaussian() / 10.0F));
            world.playSound(null, d, e, f, SoundEvents.ITEM_SHIELD_BREAK, SoundCategory.BLOCKS, 0.45F, (float) (1.0F + user.getRandom().nextGaussian() / 10.0F));

            if (user instanceof ServerPlayerEntity serverPlayer) MalachiteCriterions.DOPING.trigger(serverPlayer);
        }

        return ActionResult.PASS;
    }
}
