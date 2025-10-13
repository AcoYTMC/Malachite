package net.acoyt.malachite;

import net.acoyt.malachite.client.BlockingItemPredicate;
import net.acoyt.malachite.client.ChargedItemPredicate;
import net.acoyt.malachite.index.MalachiteBlockEntities;
import net.acoyt.malachite.index.MalachiteBlocks;
import net.acoyt.malachite.index.MalachiteEntities;
import net.acoyt.malachite.index.MalachiteItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class MalachiteClient implements ClientModInitializer {
    public void onInitializeClient() {
        BlockingItemPredicate.registerItemPredicate();
        ChargedItemPredicate.registerItemPredicate();

        MalachiteBlockEntities.clientInit();
        MalachiteBlocks.clientInit();
        MalachiteEntities.clientInit();
        MalachiteItems.clientInit();
    }
}
