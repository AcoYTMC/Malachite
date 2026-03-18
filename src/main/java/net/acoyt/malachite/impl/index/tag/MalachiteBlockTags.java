package net.acoyt.malachite.impl.index.tag;

import net.acoyt.malachite.impl.Malachite;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public interface MalachiteBlockTags {
    TagKey<Block> SERAPHITE = keyOf("seraphite");

    private static TagKey<Block> keyOf(String id) {
        return TagKey.of(RegistryKeys.BLOCK, Malachite.id(id));
    }
}
