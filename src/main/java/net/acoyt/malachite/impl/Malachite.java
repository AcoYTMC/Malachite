package net.acoyt.malachite.impl;

import com.mojang.logging.LogUtils;
import net.acoyt.acornlib.api.ALib;
import net.acoyt.malachite.compat.MalachiteConfig;
import net.acoyt.malachite.impl.event.MakeBuddingCopperEvent;
import net.acoyt.malachite.impl.event.UpdateSeraphiteEvent;
import net.acoyt.malachite.impl.index.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;

public class Malachite implements ModInitializer {
    public static final String MOD_ID = "malachite";
    public static final Logger LOGGER = LogUtils.getLogger();

    public void onInitialize() {
        /* AcornLib */
        ALib.registerModMenu(MOD_ID, 0xFF38624b);
        MalachiteConfig.init(MOD_ID, MalachiteConfig.class);

        /* Initialization */
        MalachiteBlockEntities.init();
        MalachiteBlocks.init();
        MalachiteCriterions.init();
        MalachiteDataComponents.init();
        MalachiteEffects.init();
        MalachiteEnchantmentEffects.init();
        MalachiteEntities.init();
        MalachiteItemGroup.init();
        MalachiteItems.init();
        MalachiteParticles.init();
        MalachitePotions.init();
        MalachiteSounds.init();

        /* Packs */
        registerPack(id("old_seraphite"), Text.literal("Old Seraphite"));

        /* Networking */
        MalachiteNetworking.registerTypes();
        MalachiteNetworking.registerC2SPackets();

        /* Events */
        UseBlockCallback.EVENT.register(new MakeBuddingCopperEvent());
        PlayerBlockBreakEvents.AFTER.register(new UpdateSeraphiteEvent());
    }

    private static void registerPack(Identifier packId, Text packName) {
        FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(container -> {
            ResourceManagerHelper.registerBuiltinResourcePack(packId, container, packName, ResourcePackActivationType.NORMAL);
        });
    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
