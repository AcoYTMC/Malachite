package net.acoyt.malachite.index;

import net.acoyt.malachite.Malachite;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;

import java.util.LinkedList;
import java.util.List;

public interface MalachiteSounds {
    List<SoundEvent> SOUNDS = new LinkedList<>();

    SoundEvent DAGGER_HITS = create("entity.dagger.hits");
    SoundEvent DAGGER_THROWN = create("entity.dagger.thrown");

    SoundEvent LONGSWORD_BLOCKS = create("item.longsword.blocks");

    private static SoundEvent create(String name) {
        SoundEvent soundEvent = SoundEvent.of(Malachite.id(name));
        SOUNDS.add(soundEvent);
        return soundEvent;
    }

    static void init() {
        SOUNDS.forEach(soundEvent -> Registry.register(Registries.SOUND_EVENT, soundEvent.getId(), soundEvent));
    }
}
