package net.acoyt.malachite.impl.client.particle;

import net.acoyt.acornlib.api.util.PortingUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.AnimatedParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class ShockwaveParticle extends AnimatedParticle {
    private final Quaternionf quaternion;
    private final Vector3f color;

    public ShockwaveParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider, ShockwaveParticleEffect particleEffect) {
        super(world, x, y, z, spriteProvider, 0.0f);

        this.gravityStrength = 0.0F;

        this.scale = particleEffect.size();
        this.maxAge = 24;

        this.velocityX = 0.0F;
        this.velocityY = 0.0F;
        this.velocityZ = 0.0F;

        this.quaternion = new Quaternionf(0.0F, 90.0F, 90.0F, 1.0F);

        this.setSpriteForAge(spriteProvider);

        this.color = PortingUtils.toVector(particleEffect.color());
    }

    public void method_60373(VertexConsumer buffer, Camera camera, Quaternionf quaternionf, float ticks) {
        this.setColor(this.color.x, this.color.y, this.color.z);
        Vec3d vec3 = camera.getPos();
        float x = (float) (MathHelper.lerp(ticks, this.prevPosX, this.x) - vec3.getX());
        float y = (float) (MathHelper.lerp(ticks, this.prevPosY, this.y) - vec3.getY());
        float z = (float) (MathHelper.lerp(ticks, this.prevPosZ, this.z) - vec3.getZ());

        Vector3f[] vector3fs = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, 1.0F, 0.0F), new Vector3f(1.0F, 1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F)};
        // Additional vertices for underside faces
        Vector3f[] vector3fsBottom = new Vector3f[]{new Vector3f(-1.0F, -1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F), new Vector3f(1.0F, -1.0F, 0.0F), new Vector3f(-1.0F, -1.0F, 0.0F)};

        float f4 = this.getSize(ticks);

        for (int i = 0; i < 4; ++i) {
            Vector3f vector3f = vector3fs[i];
            vector3f.rotate(this.quaternion);
            vector3f.mul(f4);
            //slightly higher to avoid z-fighting with bottom particle and sometimes ground-level blocks as well
            vector3f.add(x, y + 0.01f, z);

            // Create additional vertices for underside faces
            Vector3f vector3fBottom = vector3fsBottom[i];
            vector3fBottom.rotate(this.quaternion);
            vector3fBottom.mul(f4);
            vector3fBottom.add(x, y, z);
        }

        float f7 = this.getMinU();
        float f8 = this.getMaxU();
        float f5 = this.getMinV();
        float f6 = this.getMaxV();
        int light = this.getBrightness(ticks);

        // Render the top faces
        buffer.vertex(vector3fs[0].x(), vector3fs[0].y(), vector3fs[0].z()).texture(f8, f6).color(this.red, this.green, this.blue, this.alpha).light(light);
        buffer.vertex(vector3fs[1].x(), vector3fs[1].y(), vector3fs[1].z()).texture(f8, f5).color(this.red, this.green, this.blue, this.alpha).light(light);
        buffer.vertex(vector3fs[2].x(), vector3fs[2].y(), vector3fs[2].z()).texture(f7, f5).color(this.red, this.green, this.blue, this.alpha).light(light);
        buffer.vertex(vector3fs[3].x(), vector3fs[3].y(), vector3fs[3].z()).texture(f7, f6).color(this.red, this.green, this.blue, this.alpha).light(light);

        // Render the underside faces
        buffer.vertex(vector3fs[3].x(), vector3fs[3].y(), vector3fs[3].z()).texture(f7, f6).color(this.red, this.green, this.blue, this.alpha).light(light);
        buffer.vertex(vector3fs[2].x(), vector3fs[2].y(), vector3fs[2].z()).texture(f7, f5).color(this.red, this.green, this.blue, this.alpha).light(light);
        buffer.vertex(vector3fs[1].x(), vector3fs[1].y(), vector3fs[1].z()).texture(f8, f5).color(this.red, this.green, this.blue, this.alpha).light(light);
        buffer.vertex(vector3fs[0].x(), vector3fs[0].y(), vector3fs[0].z()).texture(f8, f6).color(this.red, this.green, this.blue, this.alpha).light(light);
    }

    public int getBrightness(float tint) {
        return 240;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<ShockwaveParticleEffect> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Nullable
        public Particle createParticle(ShockwaveParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new ShockwaveParticle(world, x, y, z, this.spriteProvider, parameters);
        }
    }
}
