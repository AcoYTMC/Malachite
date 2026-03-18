package net.acoyt.malachite.impl.index.data;

import net.acoyt.malachite.impl.Malachite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageScaling;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public interface MalachiteDamageTypes {
    RegistryKey<DamageType> DAGGER = create("dagger");
    RegistryKey<DamageType> OVERCHARGED = create("overcharged");

    private static RegistryKey<DamageType> create(String id) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Malachite.id(id));
    }

    static void bootstrap(Registerable<DamageType> registerable) {
        registerable.register(DAGGER, new DamageType("dagger", DamageScaling.NEVER, 0.1F));
        registerable.register(OVERCHARGED, new DamageType("overcharged", DamageScaling.NEVER, 0.1F));
    }

    static DamageSource create(World world, RegistryKey<DamageType> key) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
    }

    static DamageSource create(World world, RegistryKey<DamageType> key, @Nullable Entity attacker) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key), attacker);
    }

    static DamageSource create(World world, RegistryKey<DamageType> key, @Nullable Entity source, @Nullable Entity attacker) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key), source, attacker);
    }
}
