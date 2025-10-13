package net.acoyt.malachite.datagen;

import net.acoyt.malachite.index.MalachiteBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.BlockStateModelGenerator.BlockTexturePool;
import net.minecraft.data.client.ItemModelGenerator;

import static net.acoyt.malachite.index.MalachiteBlocks.*;

public class MalachiteModelGenerator extends FabricModelProvider {
    public MalachiteModelGenerator(FabricDataOutput output) {
        super(output);
    }

    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        BlockTexturePool seraphitePool = generator.registerCubeAllModelTexturePool(SERAPHITE);
        seraphitePool.slab(SERAPHITE_SLAB);
        seraphitePool.stairs(SERAPHITE_STAIRS);
        seraphitePool.wall(SERAPHITE_WALL);
    }

    public void generateItemModels(ItemModelGenerator generator) {
        //
    }
}
