package net.acoyt.malachite.index;

import net.acoyt.malachite.Malachite;
import net.acoyt.malachite.item.MalachiteDaggerItem;
import net.acoyt.malachite.item.MalachiteLongswordItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.Function;

import static net.acoyt.acornlib.api.util.ItemUtils.modifyItemNameColor;

public interface MalachiteItems {
    Item MALACHITE = create("malachite", Item::new, new Item.Settings());

    Item MALACHITE_LONGSWORD = create("malachite_longsword", MalachiteLongswordItem::new, new Item.Settings()
            .attributeModifiers(MalachiteLongswordItem.createAttributeModifiers(MalachiteToolMaterials.LONGSWORD, 4.0f, -2.8f, 0.5f)));

    Item MALACHITE_DAGGER = create("malachite_dagger", MalachiteDaggerItem::new, new Item.Settings()
            .attributeModifiers(SwordItem.createAttributeModifiers(MalachiteToolMaterials.DAGGER, 2, -2.3f)));

    static Item create(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = factory.apply(settings);
        if (item instanceof BlockItem blockItem) {
            blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return Registry.register(Registries.ITEM, Malachite.id(name), item);
    }

    static void init() {
        modifyItemNameColor(MALACHITE, 0x38624b);
        modifyItemNameColor(MALACHITE_LONGSWORD, 0x38624b);
        modifyItemNameColor(MALACHITE_DAGGER, 0x38624b);
    }

    static void clientInit() {
        //
    }
}
