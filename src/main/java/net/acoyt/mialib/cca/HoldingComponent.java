package net.acoyt.mialib.cca;

import net.acoyt.malachite.util.NbtUtils;
import net.acoyt.mialib.Mialib;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class HoldingComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<HoldingComponent> KEY = ComponentRegistry.getOrCreate(Mialib.id("holding"), HoldingComponent.class);
    private final PlayerEntity player;
    private boolean attacking = false;
    private boolean using = false;
    private int tickAttacking = 0;
    private int tickUsing = 0;

    public HoldingComponent(PlayerEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(this.player);
    }

    public boolean isAttacking() {
        return this.attacking;
    }

    public boolean isUsing() {
        return this.using;
    }

    public int getAttackTicks() {
        return this.tickAttacking;
    }

    public int getUsageTicks() {
        return this.tickUsing;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
        this.sync();
    }

    public void setUsing(boolean using) {
        this.using = using;
        this.sync();
    }

    @Override
    public void tick() {
        if (this.attacking) {
            this.tickAttacking++;
        } else {
            this.tickAttacking = 0;
        }
        if (this.using) {
            this.tickUsing++;
        } else {
            this.tickUsing = 0;
        }
    }

    @Override
    public boolean isRequiredOnClient() {
        return false;
    }

    @Override
    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.attacking = NbtUtils.getOrDefault(nbt, "attacking", false);
        this.using = NbtUtils.getOrDefault(nbt, "using", false);
        this.tickAttacking = NbtUtils.getOrDefault(nbt, "tickAttacking", 0);
        this.tickUsing = NbtUtils.getOrDefault(nbt, "tickUsing", 0);
    }

    @Override
    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbt.putBoolean("attacking", this.attacking);
        nbt.putBoolean("using", this.using);
        nbt.putInt("tickAttacking", this.tickAttacking);
        nbt.putInt("tickUsing", this.tickUsing);
    }
}
