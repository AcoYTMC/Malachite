package net.acoyt.malachite.impl.index;

import net.acoyt.acornlib.api.registrants.ItemGroupRegistrant;
import net.acoyt.malachite.impl.Malachite;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;

import static net.acoyt.malachite.impl.index.MalachiteBlocks.*;
import static net.acoyt.malachite.impl.index.MalachiteItems.*;

public interface MalachiteItemGroup {
    ItemGroupRegistrant GROUPS = new ItemGroupRegistrant(Malachite.MOD_ID);

    RegistryKey<ItemGroup> GROUP_KEY = RegistryKey.of(RegistryKeys.ITEM_GROUP, Malachite.id(Malachite.MOD_ID));
    ItemGroup ITEM_GROUP = GROUPS.register(GROUP_KEY.getValue().getPath(), FabricItemGroup.builder()
            .icon(MALACHITE::getDefaultStack)
            .displayName(Text.translatable("itemGroup.malachite").withColor(0xFF38624b))
            .build());

    static void init() {
        ItemGroupEvents.modifyEntriesEvent(GROUP_KEY).register(MalachiteItemGroup::addEntries);
    }

    private static void addEntries(FabricItemGroupEntries itemGroup) {
        itemGroup.add(MALACHITE);
        itemGroup.add(MALACHITE_LONGSWORD);
        itemGroup.add(MALACHITE_DAGGER);
        itemGroup.add(MALACHITE_GREATAXE);
        itemGroup.add(MALACHITE_PYLON);

        itemGroup.add(CHISELED_SERAPHITE);

        itemGroup.add(SERAPHITE);
        itemGroup.add(SERAPHITE_SLAB);
        itemGroup.add(SERAPHITE_STAIRS);
        itemGroup.add(SERAPHITE_WALL);

        itemGroup.add(POLISHED_SERAPHITE);
        itemGroup.add(POLISHED_SERAPHITE_SLAB);
        itemGroup.add(POLISHED_SERAPHITE_STAIRS);
        itemGroup.add(POLISHED_SERAPHITE_WALL);

        itemGroup.add(SERAPHITE_BRICKS);
        itemGroup.add(SERAPHITE_BRICK_SLAB);
        itemGroup.add(SERAPHITE_BRICK_STAIRS);
        itemGroup.add(SERAPHITE_BRICK_WALL);

        itemGroup.add(SERAPHITE_CHAIN);
        itemGroup.add(SERAPHITE_LANTERN);

        itemGroup.add(SMALL_MALACHITE_BUD);
        itemGroup.add(MEDIUM_MALACHITE_BUD);
        itemGroup.add(LARGE_MALACHITE_BUD);
        itemGroup.add(MALACHITE_CLUSTER);

        itemGroup.add(BUDDING_COPPER);
    }
}
