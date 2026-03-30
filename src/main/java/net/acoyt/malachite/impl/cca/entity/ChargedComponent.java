package net.acoyt.malachite.impl.cca.entity;

import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.index.MalachiteParticles;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class ChargedComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<ChargedComponent> KEY = ComponentRegistry.getOrCreate(Malachite.id("charged"), ChargedComponent.class);
    private final LivingEntity living;
    private int chargedTicks = 0;
    private int magnetisedTicks = 0;

    public ChargedComponent(LivingEntity living) {
        this.living = living;
    }

    public void sync() {
        KEY.sync(this.living);
    }

    public void tick() {
        if (this.chargedTicks > 0) {
            this.chargedTicks--;
            if (this.chargedTicks == 0) {
                this.sync();
            }
        }

        if (this.magnetisedTicks > 0) {
            this.magnetisedTicks--;
            if (this.magnetisedTicks == 0) {
                this.sync();
            }
        }

        if (this.living.isOnGround() && this.chargedTicks > 20) {
            this.chargedTicks = 20;
            this.sync();
        }

        if ((this.living.isOnGround() || this.living.fallDistance == 0.0F) && this.magnetisedTicks > 0) {
            this.magnetisedTicks = 0;
            this.sync();
        }
    }

    public void serverTick() {
        this.tick();

        if (this.chargedTicks > 0) {
            this.living.fallDistance = 0.0F;

            if (this.living.getWorld() instanceof ServerWorld serverWorld && serverWorld.getRandom().nextInt(5) == 0) {
                serverWorld.spawnParticles(
                        MalachiteParticles.SPARK,
                        this.living.getPos().x,
                        this.living.getPos().y,
                        this.living.getPos().z,
                        3,
                        0.3, 0.4, 0.3,
                        0.1
                );
            }
        }
    }

    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        this.chargedTicks = nbt.getInt("ChargedTicks");
        this.magnetisedTicks = nbt.getInt("MagnetisedTicks");
    }

    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        nbt.putInt("ChargedTicks", this.chargedTicks);
        nbt.putInt("MagnetisedTicks", this.magnetisedTicks);
    }

    public int getChargedTicks() {
        return this.chargedTicks;
    }

    public void setChargedTicks(int chargedTicks) {
        this.chargedTicks = chargedTicks;
        this.sync();
    }

    public int getMagnetisedTicks() {
        return this.magnetisedTicks;
    }

    public void setMagnetisedTicks(int magnetisedTicks) {
        this.magnetisedTicks = magnetisedTicks;
        this.sync();
    }
}
