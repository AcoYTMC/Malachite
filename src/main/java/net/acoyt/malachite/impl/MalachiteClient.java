package net.acoyt.malachite.impl;

import net.acoyt.malachite.impl.index.*;
import net.acoyt.malachite.impl.networking.client.PlayEnergyBeamTravelSoundPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

@Environment(EnvType.CLIENT)
public class MalachiteClient implements ClientModInitializer {
    public void onInitializeClient() {
        MalachiteBlockEntities.clientInit();
        MalachiteBlocks.clientInit();
        MalachiteEntities.clientInit();
        MalachiteItems.clientInit();
        MalachiteModelLayers.clientInit();
        MalachiteParticles.clientInit();

        // Networking
        ClientPlayNetworking.registerGlobalReceiver(PlayEnergyBeamTravelSoundPayload.ID, new PlayEnergyBeamTravelSoundPayload.Receiver());
    }
}
