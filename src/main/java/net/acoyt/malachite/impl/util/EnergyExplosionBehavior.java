package net.acoyt.malachite.impl.util;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;

import java.util.Optional;

public class EnergyExplosionBehavior extends ExplosionBehavior {
    public Optional<Float> getBlastResistance(Explosion explosion, BlockView world, BlockPos pos, BlockState blockState, FluidState fluidState) {
        return Optional.empty();
    }

    public boolean canDestroyBlock(Explosion explosion, BlockView world, BlockPos pos, BlockState state, float power) {
        return false;
    }

    public float getKnockbackModifier(Entity entity) {
        return 3.0f;
    }

    public float calculateDamage(Explosion explosion, Entity entity) {
        return 0.0f;
    }
}
