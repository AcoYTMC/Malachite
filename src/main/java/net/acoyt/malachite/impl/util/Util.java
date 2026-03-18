package net.acoyt.malachite.impl.util;

import io.netty.buffer.ByteBuf;
import net.acoyt.malachite.impl.client.particle.BlastParticleEffect;
import net.acoyt.malachite.impl.client.particle.ShockwaveParticleEffect;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Unit;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class Util {
    public static final PacketCodec<ByteBuf, Unit> UNIT_PACKET_CODEC = PacketCodecs.codec(Unit.CODEC);

    public static void spawnShockwave(LivingEntity living, float size) {
        spawnShockwave(living, ColorHelper.Argb.withAlpha(125, 0x53efac), size);
    }

    public static void spawnShockwave(LivingEntity living, int color, float size) {
        spawnShockwave(living, color, size, Vec3d.ZERO);
    }

    public static void spawnShockwave(LivingEntity living, int color, float size, Vec3d offset) {
        spawnShockwave(living.getWorld(), living.getPos(), color, size, offset);
    }

    public static void spawnShockwave(World world, Vec3d pos, float size, Vec3d offset) {
        spawnShockwave(world, pos, ColorHelper.Argb.withAlpha(125, 0x53efac), size, offset);
    }

    public static void spawnShockwave(World world, Vec3d pos, int color, float size, Vec3d offset) {
        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(
                    new ShockwaveParticleEffect(color, size),
                    pos.x + offset.x,
                    pos.y + offset.y,
                    pos.z + offset.z,
                    0, 0, 0, 0,
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
