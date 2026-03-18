package net.acoyt.malachite.impl.index;

import net.acoyt.malachite.impl.Malachite;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public interface MalachiteSounds {
    SoundEvent DAGGER_HIT = create("entity.dagger.hit");
    SoundEvent DAGGER_THROW = create("entity.dagger.throw");

    SoundEvent LONGSWORD_BLOCK = create("item.longsword.block");

    SoundEvent ENERGY_BEAM_TRAVEL = create("entity.energy_beam.travel");
    SoundEvent ENERGY_BEAM_SHOOT = create("entity.energy_beam.shoot");

    private static SoundEvent create(String name) {
        Identifier id = Malachite.id(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    static void init() {}
}
