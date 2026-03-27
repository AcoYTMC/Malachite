package net.acoyt.malachite.impl.index;

import net.acoyt.malachite.impl.networking.s2c.EnergyBeamSoundPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

public interface MalachiteNetworking {
    static void registerTypes() {
        PayloadTypeRegistry.playS2C().register(EnergyBeamSoundPayload.ID, EnergyBeamSoundPayload.CODEC);
    }

    static void registerC2SPackets() {
        //
    }

    static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(EnergyBeamSoundPayload.ID, new EnergyBeamSoundPayload.Receiver());
    }
}
