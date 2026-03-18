package net.acoyt.malachite.data;

import net.acoyt.malachite.data.provider.MalachiteAdvancementGen;
import net.acoyt.malachite.data.provider.MalachiteBlockLootTableGen;
import net.acoyt.malachite.data.provider.MalachiteDynamicRegistryGen;
import net.acoyt.malachite.data.provider.lang.MalachiteLangGen;
import net.acoyt.malachite.data.provider.lang.MalachiteLolCatLangGen;
import net.acoyt.malachite.data.provider.resources.MalachiteModelGen;
import net.acoyt.malachite.data.provider.tag.MalachiteBlockTagGen;
import net.acoyt.malachite.data.provider.tag.MalachiteDamageTypeTagGen;
import net.acoyt.malachite.data.provider.tag.MalachiteEnchantmentTagGen;
import net.acoyt.malachite.data.provider.tag.MalachiteItemTagGen;
import net.acoyt.malachite.impl.index.data.MalachiteDamageTypes;
import net.acoyt.malachite.impl.index.data.MalachiteEnchantments;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class MalachiteDatagen implements DataGeneratorEntrypoint {
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
        FabricDataGenerator.Pack pack = dataGenerator.createPack();

        pack.addProvider(MalachiteLangGen::new);
        pack.addProvider(MalachiteLolCatLangGen::new);

        pack.addProvider(MalachiteModelGen::new);

        pack.addProvider(MalachiteBlockTagGen::new);
        pack.addProvider(MalachiteDamageTypeTagGen::new);
        pack.addProvider(MalachiteEnchantmentTagGen::new);
        pack.addProvider(MalachiteItemTagGen::new);

        pack.addProvider(MalachiteAdvancementGen::new);
        pack.addProvider(MalachiteBlockLootTableGen::new);

        pack.addProvider(MalachiteDynamicRegistryGen::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder builder) {
        builder.addRegistry(RegistryKeys.ENCHANTMENT, MalachiteEnchantments::bootstrap);
        builder.addRegistry(RegistryKeys.DAMAGE_TYPE, MalachiteDamageTypes::bootstrap);
    }
}
