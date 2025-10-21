package net.acoyt.malachite.entity;

import net.acoyt.malachite.index.*;
import net.acoyt.malachite.networking.client.PlayEnergyBeamTravelSoundPayload;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
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
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public class EnergyBeamEntity extends PersistentProjectileEntity {
    public static final int distancePerTick = 24;

    public static int getMaxTicks() {
        return MathHelper.floor(256F / distancePerTick);
    }

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
        super(MalachiteEntities.ENERGY_BEAM, owner, world, ItemStack.EMPTY, shotFrom);
        setPosition(owner.getX(), owner.getEyeY() - 0.3, owner.getZ());
        this.ignoreCameraFrustum = true;
    }

    public ItemStack getDefaultItemStack() {
        return MalachiteItems.MALACHITE.getDefaultStack();
    }

    public void tick() {
        if (!getWorld().isClient && ticksExisted == 0 && getWorld().getServer() != null) PlayerLookup.all(getWorld().getServer()).forEach(foundPlayer -> PlayEnergyBeamTravelSoundPayload.send(foundPlayer, this));
        if (isCritical()) setCritical(false);
        
        setVelocity(Vec3d.ZERO);
        for (int i = 0; i < distancePerTick; i++) {
            float min = Math.min(distanceTraveled, ticksExisted);
            if (min > 0 && min == distanceTraveled) {
                discard();
            }
            Vec3d start = getPos().add(getRotationVector().multiply(distanceTraveled + (ticksExisted > 0 ? -1 : 0))), end = start.add(getRotationVector());
            BlockHitResult hitResult = getWorld().raycast(new RaycastContext(start, end, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                if (!getWorld().isClient) {
                    getWorld().emitGameEvent(GameEvent.PROJECTILE_LAND, hitResult.getPos(), GameEvent.Emitter.of(this));
                }

                break;
            } else {
                Entity owner = getOwner();
                getWorld().getOtherEntities(owner, Box.from(hitResult.getPos()).expand(0.5), EntityPredicates.EXCEPT_SPECTATOR.and(entity -> canEntityBeHit(owner, entity))).forEach(entity -> {
                    if (!getWorld().isClient) {
                        double damage = getDamage();
                        if (entity instanceof LivingEntity living) {
                            damage *= living.getMaxHealth() / 20F;
                        }
                        damage *= getDamageMultiplier(distanceTraveled);
                        damage = Math.min(50, damage);
                        entity.damage(MalachiteDamageTypes.create(getWorld(), MalachiteDamageTypes.OVERCHARGED, this, owner), (float) damage);
                        if (entity instanceof LivingEntity living) living.addStatusEffect(new StatusEffectInstance(MalachiteEffects.OVERCHARGED, 20));
                        if (entity.getWorld() instanceof ServerWorld serverWorld) serverWorld.spawnParticles(MalachiteParticles.SPARK, this.getPos().x, this.getPos().y, this.getPos().z, 6, 0.3, 0.3, 0.3, 0.1);
                        hitEntities.add(entity);
                        if (getOwner() instanceof ServerPlayerEntity player && entity instanceof LivingEntity living) {
                            Criteria.KILLED_BY_CROSSBOW.trigger(player, Set.of(living));
                        }
                    }
                });
            }
            
            distanceTraveled++;
        }
        
        if (ticksExisted > getMaxTicks()) discard();
        
        ticksExisted++;
    }

    public void onEntityHit(EntityHitResult entityHitResult) {
        //
    }

    public void onBlockHit(BlockHitResult blockHitResult) {
        //
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        setDamage(nbt.getFloat("Damage"));
        dataTracker.set(FORCED_PITCH, nbt.getFloat("ForcedPitch"));
        dataTracker.set(FORCED_YAW, nbt.getFloat("ForcedYaw"));
        distanceTraveled = nbt.getInt("DistanceTraveled");
        ticksExisted = nbt.getInt("TicksExisted");
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putFloat("Damage", (float) getDamage());
        nbt.putFloat("ForcedPitch", getPitch());
        nbt.putFloat("ForcedYaw", getYaw());
        nbt.putInt("DistanceTraveled", distanceTraveled);
        nbt.putInt("TicksExisted", ticksExisted);
    }

    public void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(DAMAGE, 0F);
        builder.add(FORCED_PITCH, 0F);
        builder.add(FORCED_YAW, 0F);
    }

    public float getPitch() {
        return dataTracker.get(FORCED_PITCH);
    }

    public float getYaw() {
        return dataTracker.get(FORCED_YAW);
    }

    public void setDamage(double damage) {
        dataTracker.set(DAMAGE, (float) damage);
    }

    public double getDamage() {
        return dataTracker.get(DAMAGE);
    }

    public float getDamageMultiplier(int distanceTraveled) {
        if (distanceTraveled < 8) {
            return MathHelper.lerp(distanceTraveled / 8F, 0.25F, 1);
        }
        return Math.min(2, MathHelper.lerp((distanceTraveled - 8) / 200F, 1F, 2F));
    }

    private boolean canEntityBeHit(Entity owner, Entity entity) {
        if ((entity instanceof LivingEntity living && !living.isInCreativeMode() && !living.isSpectator()) || entity.getType().isIn(MalachiteTags.BEAM_HITTABLE)) {
            return !hitEntities.contains(entity) && entity.isAlive() && EnergyBeamEntity.shouldHurt(owner, entity);
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