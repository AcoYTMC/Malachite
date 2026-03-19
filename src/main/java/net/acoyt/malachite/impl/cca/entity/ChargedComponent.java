package net.acoyt.malachite.impl.cca.entity;

import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.index.MalachiteParticles;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class ChargedComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<ChargedComponent> KEY = ComponentRegistry.getOrCreate(Malachite.id("charged"), ChargedComponent.class);
    private final PlayerEntity player;
    private int chargedTicks = 0;

    public ChargedComponent(PlayerEntity player) {
        this.player = player;
    }

    public void sync() {
        KEY.sync(this.player);
    }

    public void tick() {
        if (this.chargedTicks > 0) {
            this.chargedTicks--;
            if (this.chargedTicks == 0) {
                this.sync();
            }
        }

        if (this.player.isOnGround() && this.chargedTicks > 20) {
            this.chargedTicks = 20;
            this.sync();
        }
    }

    public void serverTick() {
        this.tick();

        if (this.chargedTicks > 0) {
            this.player.fallDistance = 0.0F;

            if (this.player.getWorld() instanceof ServerWorld serverWorld && serverWorld.getRandom().nextInt(5) == 0) {
                serverWorld.spawnParticles(
                        MalachiteParticles.SPARK,
                        this.player.getPos().x,
                        this.player.getPos().y,
                        this.player.getPos().z,
                        3,
                        0.3, 0.4, 0.3,
                        0.1
                );
            }
        }
    }

    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        this.chargedTicks = nbt.getInt("ChargedTicks");
    }

    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        nbt.putInt("ChargedTicks", this.chargedTicks);
    }

    public int getChargedTicks() {
        return this.chargedTicks;
    }

    public void setChargedTicks(int chargedTicks) {
        this.chargedTicks = chargedTicks;
        this.sync();
    }
}
