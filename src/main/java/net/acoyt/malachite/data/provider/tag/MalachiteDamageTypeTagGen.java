package net.acoyt.malachite.data.provider.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.DamageTypeTags;

import java.util.concurrent.CompletableFuture;

import static net.acoyt.malachite.impl.index.data.MalachiteDamageTypes.DAGGER;
import static net.acoyt.malachite.impl.index.data.MalachiteDamageTypes.OVERCHARGED;

public class MalachiteDamageTypeTagGen extends FabricTagProvider<DamageType> {
    public MalachiteDamageTypeTagGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, RegistryKeys.DAMAGE_TYPE, completableFuture);
    }

    public void configure(RegistryWrapper.WrapperLookup registries) {
        this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR)
                .add(OVERCHARGED)
                .setReplace(false);

        this.getOrCreateTagBuilder(DamageTypeTags.BYPASSES_SHIELD)
                .add(OVERCHARGED)
                .setReplace(false);

        this.getOrCreateTagBuilder(DamageTypeTags.NO_KNOCKBACK)
                .add(DAGGER, OVERCHARGED)
                .setReplace(false);
    }
}
