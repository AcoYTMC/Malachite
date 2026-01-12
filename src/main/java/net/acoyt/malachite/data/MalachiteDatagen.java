package net.acoyt.malachite.data;

import net.acoyt.malachite.data.provider.MalachiteBlockLootTableGen;
import net.acoyt.malachite.data.provider.resources.MalachiteLangGen;
import net.acoyt.malachite.data.provider.resources.MalachiteModelGen;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class MalachiteDatagen implements DataGeneratorEntrypoint {
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
        FabricDataGenerator.Pack pack = dataGenerator.createPack();

        pack.addProvider(MalachiteLangGen::new);
        pack.addProvider(MalachiteModelGen::new);

        pack.addProvider(MalachiteBlockLootTableGen::new);
    }
}
