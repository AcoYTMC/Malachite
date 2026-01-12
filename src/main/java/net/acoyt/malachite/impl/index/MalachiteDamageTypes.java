package net.acoyt.malachite.impl.index;

import net.acoyt.malachite.impl.Malachite;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public interface MalachiteDamageTypes {
    RegistryKey<DamageType> DAGGER = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Malachite.id("dagger"));
    RegistryKey<DamageType> OVERCHARGED = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Malachite.id("overcharged"));

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
