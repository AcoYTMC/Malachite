package net.acoyt.malachite.impl.index;

import net.acoyt.acornlib.api.registrants.SoundEventRegistrant;
import net.acoyt.malachite.impl.Malachite;
import net.minecraft.sound.SoundEvent;

public interface MalachiteSounds {
    SoundEventRegistrant SOUNDS = new SoundEventRegistrant(Malachite.MOD_ID);

    SoundEvent DAGGER_HIT = SOUNDS.register("entity.dagger.hit");
    SoundEvent DAGGER_THROW = SOUNDS.register("entity.dagger.throw");

    SoundEvent LONGSWORD_BLOCK = SOUNDS.register("item.longsword.block");

    SoundEvent ENERGY_BEAM_TRAVEL = SOUNDS.register("entity.energy_beam.travel");
    SoundEvent ENERGY_BEAM_SHOOT = SOUNDS.register("entity.energy_beam.shoot");

    static void init() {}
}
