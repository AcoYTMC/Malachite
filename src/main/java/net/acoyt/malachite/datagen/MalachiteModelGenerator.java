package net.acoyt.malachite.datagen;

import net.acoyt.malachite.index.MalachiteItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.BlockStateModelGenerator.BlockTexturePool;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

import static net.acoyt.malachite.index.MalachiteBlocks.*;

public class MalachiteModelGenerator extends FabricModelProvider {
    public MalachiteModelGenerator(FabricDataOutput output) {
        super(output);
    }

    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        BlockTexturePool seraphitePool = generator.registerCubeAllModelTexturePool(SERAPHITE)
                .slab(SERAPHITE_SLAB)
                .stairs(SERAPHITE_STAIRS)
                .wall(SERAPHITE_WALL);

        BlockTexturePool polishedSeraphitePool = generator.registerCubeAllModelTexturePool(POLISHED_SERAPHITE)
                .slab(POLISHED_SERAPHITE_SLAB)
                .stairs(POLISHED_SERAPHITE_STAIRS)
                .wall(POLISHED_SERAPHITE_WALL);

        BlockTexturePool seraphiteBricksPool = generator.registerCubeAllModelTexturePool(SERAPHITE_BRICKS)
                .slab(SERAPHITE_BRICK_SLAB)
                .stairs(SERAPHITE_BRICK_STAIRS)
                .wall(SERAPHITE_BRICK_WALL);
    }

    public void generateItemModels(ItemModelGenerator generator) {
        generator.register(MalachiteItems.MALACHITE, Models.GENERATED);
    }
}
