package net.acoyt.malachite.impl.entity;

import net.acoyt.malachite.impl.cca.entity.ChargedComponent;
import net.acoyt.malachite.impl.client.particle.BlastParticleEffect;
import net.acoyt.malachite.impl.index.MalachiteEffects;
import net.acoyt.malachite.impl.index.MalachiteEntities;
import net.acoyt.malachite.impl.index.MalachiteItems;
import net.acoyt.malachite.impl.index.MalachiteParticles;
import net.acoyt.malachite.impl.index.data.MalachiteDamageTypes;
import net.acoyt.malachite.impl.util.EnergyExplosionBehavior;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static net.acoyt.malachite.impl.util.Util.isOwnedBy;

public class EnergyOrbEntity extends PersistentProjectileEntity {
    public final AnimationState animationState = new AnimationState();

    public EnergyOrbEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
        this.animationState.start(0);
    }

    public EnergyOrbEntity(World world, LivingEntity owner, @Nullable ItemStack shotFrom) {
        super(MalachiteEntities.ENERGY_ORB, owner, world, MalachiteItems.MALACHITE.getDefaultStack(), shotFrom);
        setPosition(owner.getX(), owner.getEyeY() - 0.6, owner.getZ());
        this.ignoreCameraFrustum = true;
        this.animationState.start(0);
    }

    public void tick() {
        super.tick();

        World world = this.getWorld();
        Vec3d pos = this.getPos();

        if (world.getRandom().nextInt(3) == 0 && world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(
                    MalachiteParticles.SPARK,
                    pos.x,
                    pos.y,
                    pos.z,
                    6,
                    0.3, 0.3, 0.3,
                    0.1
            );
        }

        if (this.age > 120) { // 6s
            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(
                        new BlastParticleEffect(ColorHelper.Argb.withAlpha(125, 0x53efac), 5.0F, 0.0F, true),
                        pos.x,
                        pos.y,
                        pos.z,
                        0, 0, 0 ,0,
                        0.1
                );
            }

            Box box = this.getBoundingBox().expand(4); // 4 Block radius

            for (LivingEntity living : world.getEntitiesByClass(LivingEntity.class, box, LivingEntity::isAlive)) {
                living.setVelocity(pos.subtract(living.getPos()).multiply(-0.8));
                living.velocityModified = true;

                living.damage(MalachiteDamageTypes.create(world, MalachiteDamageTypes.OVERCHARGED), (float) (4 / (living.squaredDistanceTo(this.getPos()) / 8)));
                living.addStatusEffect(new StatusEffectInstance(MalachiteEffects.OVERCHARGED, 600));
            }

            this.discard();
        }

        for (ProjectileEntity projectile : world.getEntitiesByClass(ProjectileEntity.class, this.getBoundingBox().expand(1.4), p -> p != this)) {
            world.createExplosion(
                    this,
                    MalachiteDamageTypes.create(world, MalachiteDamageTypes.OVERCHARGED),
                    new EnergyExplosionBehavior(),
                    pos.x, pos.y, pos.z,
                    2.0F,
                    false,
                    World.ExplosionSourceType.NONE,
                    true,
                    new BlastParticleEffect(ColorHelper.Argb.withAlpha(125, 0x53efac), 8.0F, 0.0F, true),
                    new BlastParticleEffect(ColorHelper.Argb.withAlpha(125, 0x53efac), 8.0F, 0.0F, true),
                    SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE
            );

            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(
                        new BlastParticleEffect(ColorHelper.Argb.withAlpha(125, 0x53efac), 8.0F, 0.0F, true),
                        pos.x,
                        pos.y,
                        pos.z,
                        0, 0, 0 ,0,
                        0.1
                );
            }

            Box box = this.getBoundingBox().expand(6); // 6 Block radius

            for (LivingEntity living : world.getEntitiesByClass(LivingEntity.class, box, living -> living.isAlive() && !isOwnedBy(living, this.getOwner()))) {
                living.setVelocity(pos.subtract(living.getPos()).multiply(-1.2));
                living.velocityModified = true;
                living.fallDistance = 0.0f;

                living.damage(MalachiteDamageTypes.create(world, MalachiteDamageTypes.OVERCHARGED), (float) (5 / (living.squaredDistanceTo(this.getPos()) / 5)));
                living.addStatusEffect(new StatusEffectInstance(MalachiteEffects.OVERCHARGED, 600, 1));

                if (living instanceof PlayerEntity player) {
                    ChargedComponent.KEY.get(player).setChargedTicks(120);
                }
            }

            if (projectile instanceof PersistentProjectileEntity persistentProjectile && persistentProjectile.getItemStack().getMaxCount() > 1) {
                projectile.discard();
            }

            this.discard();

            break;
        }
    }

    public boolean canHit() {
        return false;
    }

    public boolean isNoClip() {
        return true;
    }

    public SoundEvent getHitSound() {
        return SoundEvents.BLOCK_RESPAWN_ANCHOR_SET_SPAWN;
    }

    public boolean tryPickup(PlayerEntity player) {
        return false;
    }

    public ItemStack getDefaultItemStack() {
        return MalachiteItems.MALACHITE.getDefaultStack();
    }

    public void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
    }

    public boolean hasNoGravity() {
        return true;
    }
}
