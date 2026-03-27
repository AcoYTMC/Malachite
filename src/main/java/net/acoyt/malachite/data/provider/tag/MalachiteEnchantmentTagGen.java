package net.acoyt.malachite.data.provider.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.EnchantmentTags;

import java.util.concurrent.CompletableFuture;

import static net.acoyt.malachite.impl.index.data.MalachiteEnchantments.*;

public class MalachiteEnchantmentTagGen extends FabricTagProvider.EnchantmentTagProvider {
    public MalachiteEnchantmentTagGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    public void configure(RegistryWrapper.WrapperLookup registries) {
        this.getOrCreateTagBuilder(EnchantmentTags.NON_TREASURE)
                .add(SHOCKWAVE, VOLTAGE, MAGNETIC, DISRUPT)
                .setReplace(false);
    }
}
