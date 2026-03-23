package net.acoyt.malachite.impl.entity;

import net.acoyt.acornlib.api.util.NetworkingUtils;
import net.acoyt.malachite.impl.index.MalachiteEffects;
import net.acoyt.malachite.impl.index.MalachiteEntities;
import net.acoyt.malachite.impl.index.MalachiteItems;
import net.acoyt.malachite.impl.index.MalachiteParticles;
import net.acoyt.malachite.impl.index.data.MalachiteDamageTypes;
import net.acoyt.malachite.impl.index.tag.MalachiteEntityTypeTags;
import net.acoyt.malachite.impl.networking.s2c.PlayEnergyBeamTravelSoundPayload;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Ownable;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class EnergyBeamEntity extends PersistentProjectileEntity {
    public static final int distancePerTick = 24;

    public int getMaxTicks() {
        return MathHelper.floor(512F / (distancePerTick * (this.isVoltage() ? 1.6F : 1.0F)));
    }

    public static final TrackedData<Boolean> VOLTAGE = DataTracker.registerData(EnergyBeamEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Float> DAMAGE = DataTracker.registerData(EnergyBeamEntity.class, TrackedDataHandlerRegistry.FLOAT);
    public static final TrackedData<Float> FORCED_PITCH = DataTracker.registerData(EnergyBeamEntity.class, TrackedDataHandlerRegistry.FLOAT);
    public static final TrackedData<Float> FORCED_YAW = DataTracker.registerData(EnergyBeamEntity.class, TrackedDataHandlerRegistry.FLOAT);

    public int distanceTraveled = 0, ticksExisted = 0;

    private final Set<Entity> hitEntities = new HashSet<>();

    public EnergyBeamEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
        this.ignoreCameraFrustum = true;
    }

    public EnergyBeamEntity(World world, LivingEntity owner, @Nullable ItemStack shotFrom) {
        super(MalachiteEntities.ENERGY_BEAM, owner, world, MalachiteItems.MALACHITE.getDefaultStack(), shotFrom);
        setPosition(owner.getX(), owner.getEyeY() - 0.3, owner.getZ());
        this.ignoreCameraFrustum = true;
    }

    public ItemStack getDefaultItemStack() {
        return MalachiteItems.MALACHITE.getDefaultStack();
    }

    public boolean hasNoGravity() {
        return true;
    }

    public void tick() {
        super.tick();

        World world = this.getWorld();
        Entity owner = this.getOwner();
        Vec3d pos = this.getPos();

        if (!world.isClient && this.ticksExisted == 0 && world.getServer() != null) {
            //NetworkingUtils.sendForAllPlayers(world.getServer(), new PlayEnergyBeamTravelSoundPayload(this.getId()));
        }

        world.getOtherEntities(owner, this.getBoundingBox().expand(0.5), EntityPredicates.EXCEPT_SPECTATOR.and(entity -> this.canEntityBeHit(owner, entity))).forEach(entity -> {
            if (!world.isClient) {
                entity.damage(MalachiteDamageTypes.create(world, MalachiteDamageTypes.OVERCHARGED, this, owner), (float) this.getDamage());

                if (entity instanceof LivingEntity living) living.addStatusEffect(new StatusEffectInstance(MalachiteEffects.OVERCHARGED, 120));
                if (entity.getWorld() instanceof ServerWorld serverWorld) {
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

                this.hitEntities.add(entity);
                if (this.getOwner() instanceof ServerPlayerEntity player && entity instanceof LivingEntity living) {
                    Criteria.KILLED_BY_CROSSBOW.trigger(player, Set.of(living));
                }
            }
        });

        if (this.ticksExisted > this.getMaxTicks()) {
            if (this.isVoltage() && owner instanceof LivingEntity living) {
                if (world instanceof ServerWorld serverWorld) {
                    living.teleportTo(new TeleportTarget(
                            serverWorld,
                            pos,
                            living.getVelocity(),
                            living.getYaw(),
                            living.getPitch(),
                            TeleportTarget.NO_OP
                    ));
                }
            }

            this.discard();
        }

        this.ticksExisted++;
    }

    public void onEntityHit(EntityHitResult entityHitResult) {
        //
    }

    public void onBlockHit(BlockHitResult blockHitResult) {
        if (this.isVoltage() && this.getOwner() instanceof LivingEntity living) {
            if (this.getWorld() instanceof ServerWorld serverWorld) {
                living.teleportTo(new TeleportTarget(
                        serverWorld,
                        this.getPos(),
                        living.getVelocity(),
                        living.getYaw(),
                        living.getPitch(),
                        TeleportTarget.NO_OP
                ));
            }
        }

        this.discard();
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setVoltage(nbt.getBoolean("Voltage"));
        this.setDamage(nbt.getFloat("Damage"));
        this.dataTracker.set(FORCED_PITCH, nbt.getFloat("ForcedPitch"));
        this.dataTracker.set(FORCED_YAW, nbt.getFloat("ForcedYaw"));
        this.distanceTraveled = nbt.getInt("DistanceTraveled");
        this.ticksExisted = nbt.getInt("TicksExisted");
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("Voltage", this.isVoltage());
        nbt.putFloat("Damage", (float) this.getDamage());
        nbt.putFloat("ForcedPitch", this.getPitch());
        nbt.putFloat("ForcedYaw", this.getYaw());
        nbt.putInt("DistanceTraveled", this.distanceTraveled);
        nbt.putInt("TicksExisted", this.ticksExisted);
    }

    public void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(VOLTAGE, false);
        builder.add(DAMAGE, 0F);
        builder.add(FORCED_PITCH, 0F);
        builder.add(FORCED_YAW, 0F);
    }

    public float getPitch() {
        return this.dataTracker.get(FORCED_PITCH);
    }

    public float getYaw() {
        return this.dataTracker.get(FORCED_YAW);
    }

    public void setVoltage(boolean voltage) {
        this.dataTracker.set(VOLTAGE, voltage);
    }

    public boolean isVoltage() {
        return this.dataTracker.get(VOLTAGE);
    }

    public void setDamage(double damage) {
        this.dataTracker.set(DAMAGE, (float) damage);
    }

    public double getDamage() {
        return this.dataTracker.get(DAMAGE);
    }

    private boolean canEntityBeHit(Entity owner, Entity entity) {
        if ((entity instanceof LivingEntity living && !living.isInCreativeMode() && !living.isSpectator()) || entity.getType().isIn(MalachiteEntityTypeTags.BEAM_HITTABLE)) {
            return !this.hitEntities.contains(entity) && entity.isAlive() && EnergyBeamEntity.shouldHurt(owner, entity);
        }

        return false;
    }

    public static boolean shouldHurt(Entity attacker, Entity hitEntity) {
        if (attacker == null || hitEntity == null) return true;
        if (attacker == hitEntity || attacker.getVehicle() == hitEntity) return false;

        if (hitEntity instanceof PlayerEntity hitPlayer && attacker instanceof PlayerEntity attackingPlayer) {
            return attackingPlayer.shouldDamagePlayer(hitPlayer);
        } else if (hitEntity instanceof Ownable ownable) {
            return shouldHurt(attacker, ownable.getOwner());
        }

        return true;
    }
}
