package net.acoyt.malachite.impl.index.tag;

import net.acoyt.malachite.impl.Malachite;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

public interface MalachiteEntityTypeTags {
    TagKey<EntityType<?>> BEAM_HITTABLE = create("beam_hittable");
    TagKey<EntityType<?>> DRAGLESS = create("dragless");

    private static TagKey<EntityType<?>> create(String id) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, Malachite.id(id));
    }
}
