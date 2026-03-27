package net.acoyt.malachite.data.provider;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static net.acoyt.malachite.impl.index.MalachiteBlocks.*;

public class MalachiteBlockLootTableGen extends FabricBlockLootTableProvider {
    public static final List<Block> BLOCKS = new ArrayList<>();

    public MalachiteBlockLootTableGen(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    public void generate() {
        for (Block block : BLOCKS) {
            this.addDrop(block);
        }

        this.addDropWithSilkTouch(SMALL_MALACHITE_BUD);
        this.addDropWithSilkTouch(MEDIUM_MALACHITE_BUD);
        this.addDropWithSilkTouch(LARGE_MALACHITE_BUD);
    }
}
