package net.acoyt.malachite.index;

import net.acoyt.malachite.Malachite;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public interface MalachiteTags {
    private static TagKey<Block> blockOf(String id) {
        return TagKey.of(RegistryKeys.BLOCK, Malachite.id(id));
    }

    private static TagKey<Item> itemOf(String id) {
        return TagKey.of(RegistryKeys.ITEM, Malachite.id(id));
    }

    private static TagKey<EntityType<?>> entityOf(String id) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, Malachite.id(id));
    }
}
