package net.acoyt.malachite.impl.index;

import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.block.entity.PylonBlockEntity;
import net.acoyt.malachite.impl.block.entity.SeraphiteBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

@SuppressWarnings("deprecation")
public interface MalachiteBlockEntities {
    BlockEntityType<PylonBlockEntity> PYLON = create("pylon", FabricBlockEntityTypeBuilder
            .create(PylonBlockEntity::new, MalachiteBlocks.MALACHITE_PYLON));

    BlockEntityType<SeraphiteBlockEntity> SERAPHITE = create("seraphite", FabricBlockEntityTypeBuilder
            .create(SeraphiteBlockEntity::new, MalachiteBlocks.CHISELED_SERAPHITE));

    //BlockEntityType<TestBlockEntity> TEST = create("test", FabricBlockEntityTypeBuilder
    //        .create(TestBlockEntity::new, MalachiteBlocks.TEST));

    private static <T extends BlockEntity> BlockEntityType<T> create(String name, FabricBlockEntityTypeBuilder<T> builder) {
        return Registry.register(Registries.BLOCK_ENTITY_TYPE, Malachite.id(name), builder.build());
    }

    static void init() {}

    static void clientInit() {}
}
