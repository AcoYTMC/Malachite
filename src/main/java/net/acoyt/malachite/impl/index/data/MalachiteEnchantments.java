package net.acoyt.malachite.impl.index.data;

import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.index.MalachiteEnchantmentEffects;
import net.acoyt.malachite.impl.index.tag.MalachiteItemTags;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public interface MalachiteEnchantments {
    RegistryKey<Enchantment> SHOCKWAVE = create("shockwave");
    RegistryKey<Enchantment> VOLTAGE = create("voltage");
    RegistryKey<Enchantment> MAGNETIC = create("magnetic");
    RegistryKey<Enchantment> DISRUPT = create("disrupt");

    private static RegistryKey<Enchantment> create(String id) {
        return RegistryKey.of(RegistryKeys.ENCHANTMENT, Malachite.id(id));
    }

    static void bootstrap(Registerable<Enchantment> registerable) {
        RegistryEntryLookup<Item> itemLookup = registerable.getRegistryLookup(RegistryKeys.ITEM);

        registerable.register(SHOCKWAVE, Enchantment.builder(Enchantment.definition(
                                itemLookup.getOrThrow(MalachiteItemTags.LONGSWORDS),
                                2,
                                1,
                                Enchantment.leveledCost(5, 0),
                                Enchantment.leveledCost(17, 0),
                                7,
                                AttributeModifierSlot.MAINHAND
                        ))
                        .addNonListEffect(MalachiteEnchantmentEffects.SHOCKWAVE, 1.2F)
                        .build(SHOCKWAVE.getValue())
        );

        registerable.register(VOLTAGE, Enchantment.builder(Enchantment.definition(
                                itemLookup.getOrThrow(MalachiteItemTags.MALACHITE_WEAPONRY),
                                2,
                                1,
                                Enchantment.leveledCost(5, 0),
                                Enchantment.leveledCost(17, 0),
                                7,
                                AttributeModifierSlot.MAINHAND
                        ))
                        .addEffect(MalachiteEnchantmentEffects.VOLTAGE)
                        .build(VOLTAGE.getValue())
        );

        registerable.register(MAGNETIC, Enchantment.builder(Enchantment.definition(
                                itemLookup.getOrThrow(MalachiteItemTags.DAGGERS),
                                2,
                                1,
                                Enchantment.leveledCost(5, 0),
                                Enchantment.leveledCost(17, 0),
                                7,
                                AttributeModifierSlot.MAINHAND
                        ))
                        .addEffect(MalachiteEnchantmentEffects.MAGNETIC)
                        .build(MAGNETIC.getValue())
        );

        registerable.register(DISRUPT, Enchantment.builder(Enchantment.definition(
                                itemLookup.getOrThrow(MalachiteItemTags.LONGSWORDS),
                                2,
                                1,
                                Enchantment.leveledCost(5, 0),
                                Enchantment.leveledCost(17, 0),
                                7,
                                AttributeModifierSlot.MAINHAND
                        ))
                        .addEffect(MalachiteEnchantmentEffects.DISRUPT)
                        .build(DISRUPT.getValue())
        );
    }
}
