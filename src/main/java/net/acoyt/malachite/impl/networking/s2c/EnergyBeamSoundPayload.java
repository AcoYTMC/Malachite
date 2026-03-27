package net.acoyt.malachite.impl.networking.s2c;

import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.client.sound.EnergyBeamSoundInstance;
import net.acoyt.malachite.impl.entity.EnergyBeamEntity;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;

public record EnergyBeamSoundPayload(int entityId) implements CustomPayload {
    public static final Id<EnergyBeamSoundPayload> ID = new Id<>(Malachite.id("energy_beam_sound"));

    public static final PacketCodec<RegistryByteBuf, EnergyBeamSoundPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, EnergyBeamSoundPayload::entityId,
            EnergyBeamSoundPayload::new
    );

    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<EnergyBeamSoundPayload> {
        public void receive(EnergyBeamSoundPayload payload, ClientPlayNetworking.Context context) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.getSoundManager() == null) return;

            Entity entity = context.player().getWorld().getEntityById(payload.entityId());
            if (entity instanceof EnergyBeamEntity energyBeam) {
                client.getSoundManager().play(new EnergyBeamSoundInstance(energyBeam));
            }
        }
    }
}
