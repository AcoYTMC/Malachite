package net.acoyt.malachite.impl.index;

import net.acoyt.acornlib.api.registrants.ItemRegistrant;
import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.item.MalachiteDaggerItem;
import net.acoyt.malachite.impl.item.MalachiteGreataxeItem;
import net.acoyt.malachite.impl.item.MalachiteLongswordItem;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;

import static net.acoyt.acornlib.api.util.ItemUtils.modifyItemNameColor;

public interface MalachiteItems {
    ItemRegistrant ITEMS = new ItemRegistrant(Malachite.MOD_ID);

    Item MALACHITE = ITEMS.register("malachite", Item::new, new Item.Settings());

    Item MALACHITE_LONGSWORD = ITEMS.register("malachite_longsword", MalachiteLongswordItem::new, new Item.Settings()
            .attributeModifiers(MalachiteLongswordItem.createAttributeModifiers(MalachiteToolMaterials.MALACHITE, 4.0F, -2.8F, 0.5F)));

    Item MALACHITE_DAGGER = ITEMS.register("malachite_dagger", MalachiteDaggerItem::new, new Item.Settings()
            .attributeModifiers(SwordItem.createAttributeModifiers(MalachiteToolMaterials.MALACHITE, 1, -2.2F)));

    Item MALACHITE_GREATAXE = ITEMS.register("malachite_greataxe", MalachiteGreataxeItem::new, new Item.Settings()
            .attributeModifiers(MalachiteGreataxeItem.createAttributeModifiers(MalachiteToolMaterials.MALACHITE, 4.0F, -2.9F, 0.75F)));

    static void init() {
        modifyItemNameColor(MALACHITE, 0xFF38624b);
        modifyItemNameColor(MALACHITE_LONGSWORD, 0xFF38624b);
        modifyItemNameColor(MALACHITE_DAGGER, 0xFF38624b);
        modifyItemNameColor(MALACHITE_GREATAXE, 0xFF38624b);
    }

    static void clientInit() {}
}
