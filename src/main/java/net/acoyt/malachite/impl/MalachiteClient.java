package net.acoyt.malachite.impl;

import net.acoyt.malachite.impl.index.*;
import net.acoyt.malachite.impl.index.client.MalachiteModelLayers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class MalachiteClient implements ClientModInitializer {
    public void onInitializeClient() {
        /* Initialization */
        MalachiteBlockEntities.clientInit();
        MalachiteBlocks.clientInit();
        MalachiteEntities.clientInit();
        MalachiteItems.clientInit();
        MalachiteModelLayers.clientInit();
        MalachiteParticles.clientInit();

        /* Networking */
        MalachiteNetworking.registerS2CPackets();
    }
}
