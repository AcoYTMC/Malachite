package net.acoyt.malachite;

import net.acoyt.malachite.client.BlockingItemPredicate;
import net.acoyt.malachite.client.ChargedItemPredicate;
import net.acoyt.malachite.index.*;
import net.acoyt.malachite.networking.client.PlayEnergyBeamTravelSoundPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

@Environment(EnvType.CLIENT)
public class MalachiteClient implements ClientModInitializer {
    public void onInitializeClient() {
        BlockingItemPredicate.registerItemPredicate();
        ChargedItemPredicate.registerItemPredicate();

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
