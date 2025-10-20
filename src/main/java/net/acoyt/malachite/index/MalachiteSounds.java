package net.acoyt.malachite.index;

import net.acoyt.malachite.Malachite;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;

import java.util.LinkedList;
import java.util.List;

public interface MalachiteSounds {
    List<SoundEvent> SOUNDS = new LinkedList<>();

    SoundEvent DAGGER_HIT = create("entity.dagger.hit");
    SoundEvent DAGGER_THROW = create("entity.dagger.throw");

    SoundEvent LONGSWORD_BLOCK = create("item.longsword.block");

    SoundEvent ENERGY_BEAM_TRAVEL = create("entity.energy_beam.travel");
    SoundEvent ENERGY_BEAM_SHOOT = create("entity.energy_beam.shoot");

    private static SoundEvent create(String name) {
        SoundEvent soundEvent = SoundEvent.of(Malachite.id(name));
        SOUNDS.add(soundEvent);
        return soundEvent;
    }

    static void init() {
        SOUNDS.forEach(soundEvent -> Registry.register(Registries.SOUND_EVENT, soundEvent.getId(), soundEvent));
    }
}
