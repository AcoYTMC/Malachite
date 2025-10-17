package net.acoyt.malachite.mixin.mialib;

import net.acoyt.malachite.cca.HoldingComponent;
import net.acoyt.malachite.util.interfaces.MPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity implements MPlayerEntity {
    public PlayerEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public boolean malachite$holdingAttack() {
        return HoldingComponent.KEY.get(this).isAttacking();
    }

    @Override
    public void malachite$setHoldingAttack(boolean attackHeld) {
        HoldingComponent.KEY.get(this).setAttacking(attackHeld);
    }

    @Override
    public int malachite$getHoldingAttackTime() {
        return HoldingComponent.KEY.get(this).getAttackTicks();
    }

    @Override
    public boolean malachite$holdingUse() {
        return HoldingComponent.KEY.get(this).isUsing();
    }

    @Override
    public void malachite$setHoldingUse(boolean useHeld) {
        HoldingComponent.KEY.get(this).setUsing(useHeld);
    }

    @Override
    public int malachite$getHoldingUseTime() {
        return HoldingComponent.KEY.get(this).getUsageTicks();
    }
}