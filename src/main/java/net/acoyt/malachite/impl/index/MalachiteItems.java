package net.acoyt.malachite.impl.index;

import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.item.MalachiteDaggerItem;
import net.acoyt.malachite.impl.item.MalachiteGreataxeItem;
import net.acoyt.malachite.impl.item.MalachiteLongswordItem;
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
            .attributeModifiers(MalachiteLongswordItem.createAttributeModifiers(MalachiteToolMaterials.MALACHITE, 4.0F, -2.8F, 0.5F)));

    Item MALACHITE_DAGGER = create("malachite_dagger", MalachiteDaggerItem::new, new Item.Settings()
            .attributeModifiers(SwordItem.createAttributeModifiers(MalachiteToolMaterials.MALACHITE, 1, -2.2F)));

    Item MALACHITE_GREATAXE = create("malachite_greataxe", MalachiteGreataxeItem::new, new Item.Settings()
            .attributeModifiers(MalachiteGreataxeItem.createAttributeModifiers(MalachiteToolMaterials.MALACHITE, 4.0F, -2.9F, 0.75F)));

    static Item create(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = factory.apply(settings);
        if (item instanceof BlockItem blockItem) {
            blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return Registry.register(Registries.ITEM, Malachite.id(name), item);
    }

    static void init() {
        modifyItemNameColor(MALACHITE, 0xFF38624b);
        modifyItemNameColor(MALACHITE_LONGSWORD, 0xFF38624b);
        modifyItemNameColor(MALACHITE_DAGGER, 0xFF38624b);
        modifyItemNameColor(MALACHITE_GREATAXE, 0xFF38624b);
    }

    static void clientInit() {
        //
    }
}
