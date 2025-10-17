package net.acoyt.malachite;

import com.mojang.logging.LogUtils;
import eu.midnightdust.lib.config.MidnightConfig;
import net.acoyt.acornlib.api.ALib;
import net.acoyt.acornlib.api.ALibRegistries;
import net.acoyt.malachite.compat.MalachiteConfig;
import net.acoyt.malachite.index.*;
import net.acoyt.malachite.networking.AttackingPayload;
import net.acoyt.malachite.networking.UsingPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;

public class Malachite implements ModInitializer {
	public static final String MOD_ID = "malachite";
	public static final Logger LOGGER = LogUtils.getLogger();

	public void onInitialize() {
        ALibRegistries.init(MOD_ID);
        ALib.registerModMenu(MOD_ID, 0xFF38624b);
        MidnightConfig.init(MOD_ID, MalachiteConfig.class);

        MalachiteBlockEntities.init();
        MalachiteBlocks.init();
        MalachiteDataComponents.init();
        MalachiteEffects.init();
        MalachiteEntities.init();
        MalachiteItemGroup.init();
        MalachiteItems.init();
        MalachiteSounds.init();

        // Networking
        PayloadTypeRegistry.playC2S().register(AttackingPayload.ID, AttackingPayload.CODEC);
        PayloadTypeRegistry.playC2S().register(UsingPayload.ID, UsingPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(AttackingPayload.ID, new AttackingPayload.Receiver());
        ServerPlayNetworking.registerGlobalReceiver(UsingPayload.ID, new UsingPayload.Receiver());
	}

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
