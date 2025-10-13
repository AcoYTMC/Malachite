package net.acoyt.malachite.index;

import net.acoyt.malachite.Malachite;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

public interface MalachiteBlockEntities {
    Map<BlockEntityType<?>, Identifier> BLOCK_ENTITIES = new LinkedHashMap<>();

    //BlockEntityType<TestBlockEntity> TEST = create("test", BlockEntityType.Builder
    //        .create(TestBlockEntity::new, MalachiteBlocks.TEST)
    //        .build());

    private static <T extends BlockEntity> BlockEntityType<T> create(String name, BlockEntityType<T> blockEntity) {
        BLOCK_ENTITIES.put(blockEntity, Malachite.id(name));
        return blockEntity;
    }

    static void init() {
        BLOCK_ENTITIES.keySet().forEach(blockEntity -> {
            Registry.register(Registries.BLOCK_ENTITY_TYPE, BLOCK_ENTITIES.get(blockEntity), blockEntity);
        });
    }

    static void clientInit() {
        //
    }
}
