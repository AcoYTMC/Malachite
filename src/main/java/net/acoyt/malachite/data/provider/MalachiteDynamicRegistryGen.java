package net.acoyt.malachite.data.provider;

import net.acoyt.malachite.impl.Malachite;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class MalachiteDynamicRegistryGen extends FabricDynamicRegistryProvider {
    public MalachiteDynamicRegistryGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    public void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        entries.addAll(registries.getWrapperOrThrow(RegistryKeys.ENCHANTMENT));
        entries.addAll(registries.getWrapperOrThrow(RegistryKeys.DAMAGE_TYPE));
    }

    public String getName() {
        return Malachite.MOD_ID + "_dynamic";
    }
}
