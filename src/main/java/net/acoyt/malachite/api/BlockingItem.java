package net.acoyt.malachite.api;

import net.acoyt.malachite.index.MalachiteSounds;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;

public interface BlockingItem {
    void absorbDamage(PlayerEntity player, DamageSource source, ItemStack stack, float amount);

    default SoundEvent blockSound() {
        return MalachiteSounds.LONGSWORD_BLOCK;
    }
}
