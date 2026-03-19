package net.acoyt.malachite.data.provider.tag;

import net.acoyt.malachite.impl.index.MalachiteEntities;
import net.acoyt.malachite.impl.index.tag.MalachiteEntityTypeTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalEntityTypeTags;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class MalachiteEntityTypeTagGen extends FabricTagProvider.EntityTypeTagProvider {
    public MalachiteEntityTypeTagGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    public void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        this.getOrCreateTagBuilder(MalachiteEntityTypeTags.BEAM_HITTABLE)
                .addOptionalTag(ConventionalEntityTypeTags.BOATS)
                .addOptionalTag(ConventionalEntityTypeTags.MINECARTS)
                .add(EntityType.END_CRYSTAL)
                .setReplace(false);

        this.getOrCreateTagBuilder(MalachiteEntityTypeTags.DRAGLESS)
                .add(MalachiteEntities.ENERGY_BEAM)
                .add(MalachiteEntities.ENERGY_ORB)
                .setReplace(false);
    }
}
