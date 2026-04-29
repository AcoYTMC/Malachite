package net.acoyt.malachite.impl.index;

import net.acoyt.acornlib.api.registrants.BlockEntityTypeRegistrant;
import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.block.entity.PylonBlockEntity;
import net.acoyt.malachite.impl.block.entity.SeraphiteBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;

@SuppressWarnings("deprecation")
public interface MalachiteBlockEntities {
    BlockEntityTypeRegistrant BLOCK_ENTITIES = new BlockEntityTypeRegistrant(Malachite.MOD_ID);

    BlockEntityType<PylonBlockEntity> PYLON = BLOCK_ENTITIES.register("pylon", FabricBlockEntityTypeBuilder
            .create(PylonBlockEntity::new, MalachiteBlocks.MALACHITE_PYLON));

    BlockEntityType<SeraphiteBlockEntity> SERAPHITE = BLOCK_ENTITIES.register("seraphite", FabricBlockEntityTypeBuilder
            .create(SeraphiteBlockEntity::new, MalachiteBlocks.CHISELED_SERAPHITE));

    static void init() {}

    static void clientInit() {}
}
