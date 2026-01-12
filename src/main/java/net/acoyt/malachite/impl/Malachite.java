package net.acoyt.malachite.impl;

import com.mojang.logging.LogUtils;
import eu.midnightdust.lib.config.MidnightConfig;
import net.acoyt.acornlib.api.ALib;
import net.acoyt.acornlib.api.ALibRegistries;
import net.acoyt.malachite.compat.MalachiteConfig;
import net.acoyt.malachite.impl.client.particle.BlastParticleEffect;
import net.acoyt.malachite.impl.client.particle.ShockwaveParticleEffect;
import net.acoyt.malachite.impl.event.MakeBuddingCopperEvent;
import net.acoyt.malachite.impl.event.UpdateSeraphiteEvent;
import net.acoyt.malachite.impl.index.*;
import net.acoyt.malachite.impl.networking.client.PlayEnergyBeamTravelSoundPayload;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
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
        MalachiteCriterions.init();
        MalachiteDataComponents.init();
        MalachiteEffects.init();
        MalachiteEnchantments.init();
        MalachiteEntities.init();
        MalachiteItemGroup.init();
        MalachiteItems.init();
        MalachiteParticles.init();
        MalachitePotions.init();
        MalachiteSounds.init();

        // Resource Packs
        FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(container -> {
            ResourceManagerHelper.registerBuiltinResourcePack(id("old_seraphite"), container, Text.literal("Old Seraphite"), ResourcePackActivationType.NORMAL);
        });

        // Networking
        PayloadTypeRegistry.playS2C().register(PlayEnergyBeamTravelSoundPayload.ID, PlayEnergyBeamTravelSoundPayload.CODEC);

        // Events
        UseBlockCallback.EVENT.register(new MakeBuddingCopperEvent());
        PlayerBlockBreakEvents.AFTER.register(new UpdateSeraphiteEvent());
	}

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

    public static void spawnShockwave(LivingEntity living, float size) {
        spawnShockwave(living, 0x53efac, size);
    }

    public static void spawnShockwave(LivingEntity living, int color, float size) {
        spawnShockwave(living, color, size, Vec3d.ZERO);
    }

    public static void spawnShockwave(LivingEntity living, int color, float size, Vec3d offset) {
        spawnShockwave(living.getWorld(), living.getPos(), color, size, offset);
    }

    public static void spawnShockwave(World world, Vec3d pos, float size, Vec3d offset) {
        spawnShockwave(world, pos, 0x53efac, size, offset);
    }

    public static void spawnShockwave(World world, Vec3d pos, int color, float size, Vec3d offset) {
        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(
                    new ShockwaveParticleEffect(color, size),
                    pos.x + offset.x,
                    pos.y + offset.y,
                    pos.z + offset.z,
                    0,
                    0,
                    0,
                    0,
                    0.1
            );
        }
    }

    public static void spawnBlast(World world, float yaw, int color, float size, Vec3d pos) {
        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(
                    new BlastParticleEffect(color, size, yaw),
                    pos.x,
                    pos.y,
                    pos.z,
                    0, 0, 0 ,0,
                    0.1
            );
        }
    }

    public static void spawnBlast(Entity entity, int color, float size, Vec3d offset) {
        float yaw = MathHelper.wrapDegrees(entity.getYaw());
        float rotation;
        if ((yaw < -45.0F && yaw > -135.0F) || (yaw < 135.0F && yaw > 45.0F)) {
            rotation = 0.0F;
        } else {
            rotation = 90.0F;
        }

        Vec3d pos = entity.getPos();
        if (entity.getWorld() instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(
                    new BlastParticleEffect(color, size, rotation),
                    pos.x + offset.x,
                    pos.y + offset.y,
                    pos.z + offset.z,
                    0,
                    0,
                    0,
                    0,
                    0.1
            );
        }
    }
}
