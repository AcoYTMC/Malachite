package net.acoyt.malachite;

import net.acoyt.malachite.datagen.MalachiteLangGenerator;
import net.acoyt.malachite.datagen.MalachiteModelGenerator;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class MalachiteDatagen implements DataGeneratorEntrypoint {
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
        FabricDataGenerator.Pack pack = dataGenerator.createPack();

        pack.addProvider(MalachiteLangGenerator::new);
        pack.addProvider(MalachiteModelGenerator::new);
    }
}
