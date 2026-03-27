package net.acoyt.malachite.impl.client.sound;

import net.acoyt.malachite.impl.entity.EnergyBeamEntity;
import net.acoyt.malachite.impl.index.MalachiteSounds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class EnergyBeamSoundInstance extends MovingSoundInstance {
    private final EnergyBeamEntity entity;
    private Vec3d previousPos;
    private float pitchModifier = 0;

    public EnergyBeamSoundInstance(EnergyBeamEntity entity) {
        super(MalachiteSounds.ENERGY_BEAM_TRAVEL, entity.getSoundCategory(), entity.getRandom());
        this.entity = entity;
        x = entity.getX();
        y = entity.getY();
        z = entity.getZ();
        repeat = true;
        repeatDelay = 0;
        volume = 8;
    }

    public void tick() {
        if (entity == null || entity.isRemoved()) {
            if (volume > 1) {
                volume = Math.max(1, volume - 4);
            } else {
                volume -= 0.1F;
            }
            if (volume < 0) {
                setDone();
            }
            return;
        }
        Vec3d newPos = entity.getPos().add(entity.getRotationVector().multiply(entity.distanceTraveled));
        x = newPos.getX();
        y = newPos.getY();
        z = newPos.getZ();
        if (previousPos != null) {
            Entity cameraEntity = MinecraftClient.getInstance().getCameraEntity();
            if (cameraEntity != null) {
                Vec3d cameraPos = cameraEntity.getPos();
                double prevDistance = previousPos.distanceTo(cameraPos);
                if (prevDistance < volume * 16) {
                    if (prevDistance > newPos.distanceTo(cameraPos)) {
                        pitchModifier += 1 / 40F;
                    } else {
                        pitchModifier -= 1 / 40F;
                    }
                }
            }
        }
        pitchModifier = MathHelper.clamp(pitchModifier, 0, 0.5F);
        pitch = (float) MathHelper.lerp(entity.getDamage() / 12F, 1.5F, 1) + pitchModifier;
        previousPos = newPos;
    }
}
