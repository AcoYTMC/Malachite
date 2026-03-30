package net.acoyt.malachite.data.provider.tag;

import net.acoyt.malachite.impl.index.tag.MalachiteItemTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

import static net.acoyt.malachite.impl.index.MalachiteItems.*;

public class MalachiteItemTagGen extends FabricTagProvider.ItemTagProvider {
    public MalachiteItemTagGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    public void configure(RegistryWrapper.WrapperLookup registries) {
        this.getOrCreateTagBuilder(ItemTags.SWORD_ENCHANTABLE)
                .add(MALACHITE_LONGSWORD, MALACHITE_DAGGER)
                .setReplace(false);

        this.getOrCreateTagBuilder(ItemTags.AXES)
                .add(MALACHITE_GREATAXE)
                .setReplace(false);

        this.getOrCreateTagBuilder(ItemTags.TRIM_MATERIALS)
                .add(MALACHITE)
                .setReplace(false);

        this.getOrCreateTagBuilder(MalachiteItemTags.LONGSWORDS)
                .add(MALACHITE_LONGSWORD)
                .setReplace(false);

        this.getOrCreateTagBuilder(MalachiteItemTags.DAGGERS)
                .add(MALACHITE_DAGGER)
                .setReplace(false);

        this.getOrCreateTagBuilder(MalachiteItemTags.MAGNETIC_ENCHANTABLE)
                .addOptionalTag(MalachiteItemTags.DAGGERS)
                .add(MALACHITE_GREATAXE)
                .setReplace(false);

        this.getOrCreateTagBuilder(MalachiteItemTags.MALACHITE_WEAPONRY)
                .add(MALACHITE_LONGSWORD, MALACHITE_DAGGER, MALACHITE_GREATAXE)
                .setReplace(false);
    }
}
