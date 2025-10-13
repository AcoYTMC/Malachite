package net.acoyt.mialib;

import net.acoyt.mialib.cca.HoldingComponent;
import net.acoyt.mialib.networking.AttackingPayload;
import net.acoyt.mialib.networking.UsingPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mialib implements ModInitializer, EntityComponentInitializer {
	public static final String MOD_ID = "mialib";
	public static final String MOD_NAME = "MiaLib";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

	public void onInitialize() {
		PayloadTypeRegistry.playC2S().register(AttackingPayload.ID, AttackingPayload.CODEC);
		PayloadTypeRegistry.playC2S().register(UsingPayload.ID, UsingPayload.CODEC);
		ServerPlayNetworking.registerGlobalReceiver(AttackingPayload.ID, new AttackingPayload.Receiver());
		ServerPlayNetworking.registerGlobalReceiver(UsingPayload.ID, new UsingPayload.Receiver());
	}

	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.beginRegistration(PlayerEntity.class, HoldingComponent.KEY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(HoldingComponent::new);
	}

	@NotNull
	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}