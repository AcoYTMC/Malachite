package net.acoyt.malachite.impl.cca.entity;

import net.acoyt.malachite.impl.Malachite;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.CommonTickingComponent;

public class NearbyPylonComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final ComponentKey<NearbyPylonComponent> KEY = ComponentRegistry.getOrCreate(Malachite.id("nearby_pylon"), NearbyPylonComponent.class);
    private final LivingEntity living;
    private int nearbyTicks = 0;
    private boolean nearby = false;

    public NearbyPylonComponent(LivingEntity living) {
        this.living = living;
    }

    public void sync() {
        KEY.sync(this.living);
    }

    public void tick() {
        //if (this.nearby && this.living instanceof PlayerEntity player) player.sendMessage(Text.literal("You are near a Malachite Pylon!").withColor(0xFF38624b), true);

        if (this.nearbyTicks > 0) {
            this.nearbyTicks--;
            if (!this.nearby) {
                this.nearby = true;
                this.sync();
            }

            if (this.nearbyTicks == 0) {
                this.nearby = false;
                this.sync();
            }
        }

        if (this.nearbyTicks == 0 && this.nearby) {
            this.nearby = false;
            this.sync();
        }
    }

    public boolean isNearby() {
        return this.nearby;
    }

    public void setNearby(boolean nearby) {
        this.nearby = nearby;
        this.nearbyTicks = nearby ? 3 : 0;
        this.sync();
    }

    @Override
    public void writeToNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        nbt.putInt("nearbyTicks", this.nearbyTicks);
        nbt.putBoolean("nearby", this.nearby);
    }

    @Override
    public void readFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup wrapperLookup) {
        this.nearbyTicks = nbt.getInt("nearbyTicks");
        this.nearby = nbt.getBoolean("nearby");
    }
}
