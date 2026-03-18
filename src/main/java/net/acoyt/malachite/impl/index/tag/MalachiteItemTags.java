package net.acoyt.malachite.impl.index.tag;

import net.acoyt.malachite.impl.Malachite;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public interface MalachiteItemTags {
    TagKey<Item> LONGSWORDS = create("longswords");
    TagKey<Item> DAGGERS = create("daggers");
    TagKey<Item> MALACHITE_WEAPONRY = create("malachite_weaponry");

    private static TagKey<Item> create(String id) {
        return TagKey.of(RegistryKeys.ITEM, Malachite.id(id));
    }
}
