package net.acoyt.malachite;

import com.mojang.logging.LogUtils;
import eu.midnightdust.lib.config.MidnightConfig;
import net.acoyt.acornlib.api.ALib;
import net.acoyt.acornlib.api.ALibRegistries;
import net.acoyt.malachite.compat.MalachiteConfig;
import net.acoyt.malachite.index.*;
import net.fabricmc.api.ModInitializer;
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
	}

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }
}
