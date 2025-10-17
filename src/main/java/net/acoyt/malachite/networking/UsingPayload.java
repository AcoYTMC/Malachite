package net.acoyt.malachite.networking;

import net.acoyt.mialib.Mialib;
import net.acoyt.mialib.util.interfaces.MPlayerEntity;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import org.jetbrains.annotations.NotNull;

public record UsingPayload(boolean using) implements CustomPayload {
    public static final Id<UsingPayload> ID = new Id<>(Mialib.id("using"));
    public static final PacketCodec<PacketByteBuf, UsingPayload> CODEC = PacketCodec.tuple(PacketCodecs.BOOL, UsingPayload::using, UsingPayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public static class Receiver implements ServerPlayNetworking.PlayPayloadHandler<UsingPayload> {
        @Override
        public void receive(@NotNull UsingPayload payload, ServerPlayNetworking.@NotNull Context context) {
            ((MPlayerEntity)context.player()).mialib$setHoldingUse(payload.using());
        }
    }
}