package net.acoyt.malachite.impl.networking.client;

import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.client.sound.EnergyBeamTravelSoundInstance;
import net.acoyt.malachite.impl.entity.EnergyBeamEntity;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;

public record PlayEnergyBeamTravelSoundPayload(int entityId) implements CustomPayload {
    public static final Id<PlayEnergyBeamTravelSoundPayload> ID = new Id<>(Malachite.id("play_energy_beam_travel_sound"));
    public static final PacketCodec<RegistryByteBuf, PlayEnergyBeamTravelSoundPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, PlayEnergyBeamTravelSoundPayload::entityId,
            PlayEnergyBeamTravelSoundPayload::new
    );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static void send(ServerPlayerEntity player, Entity entity) {
        ServerPlayNetworking.send(player, new PlayEnergyBeamTravelSoundPayload(entity.getId()));
    }

    public static class Receiver implements ClientPlayNetworking.PlayPayloadHandler<PlayEnergyBeamTravelSoundPayload> {
        @Override
        public void receive(PlayEnergyBeamTravelSoundPayload payload, ClientPlayNetworking.Context context) {
            Entity entity = context.player().getWorld().getEntityById(payload.entityId());
            if (entity instanceof EnergyBeamEntity energyBeam) {
                context.client().getSoundManager().play(new EnergyBeamTravelSoundInstance(energyBeam));
            }
        }
    }
}
