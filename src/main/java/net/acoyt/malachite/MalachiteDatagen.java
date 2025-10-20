package net.acoyt.malachite;

import net.acoyt.malachite.datagen.MalachiteBlockLootTableGen;
import net.acoyt.malachite.datagen.MalachiteLangGen;
import net.acoyt.malachite.datagen.MalachiteModelGen;
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
