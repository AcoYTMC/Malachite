package net.acoyt.malachite.item;

import net.acoyt.malachite.Malachite;
import net.acoyt.malachite.api.BlockingItem;
import net.acoyt.malachite.block.MalachitePylonBlock;
import net.acoyt.malachite.component.MalachiteComponent;
import net.acoyt.malachite.entity.EnergyBeamEntity;
import net.acoyt.malachite.index.*;
import net.minecraft.block.BlockState;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.apache.commons.lang3.mutable.MutableFloat;
import xyz.amymialee.mialib.cca.HoldingComponent;

import java.util.List;

public class MalachiteLongswordItem extends SwordItem implements BlockingItem {
    public MalachiteLongswordItem(Settings settings) {
        super(MalachiteToolMaterials.LONGSWORD, settings
                .component(MalachiteDataComponents.MALACHITE, MalachiteComponent.LONGSWORD)
                .component(MalachiteDataComponents.BEAM_DAMAGE, 5.0f)
        );
    }

    public static AttributeModifiersComponent createAttributeModifiers(ToolMaterial material, float baseAttackDamage, float attackSpeed, float attackReach) {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.GENERIC_ATTACK_DAMAGE,
                        new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, baseAttackDamage + material.getAttackDamage(), Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                ).add(
                        EntityAttributes.GENERIC_ATTACK_SPEED,
                        new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed, Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                ).add(
                        EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
                        new EntityAttributeModifier(Malachite.id("attack_reach"), attackReach, Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }

    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player && !player.getItemCooldownManager().isCoolingDown(stack.getItem()) && selected) {
            HoldingComponent holding = HoldingComponent.KEY.get(player);
            MalachiteComponent malachite = stack.getOrDefault(MalachiteDataComponents.MALACHITE, MalachiteComponent.LONGSWORD);
            if (!world.isClient && player.getActiveItem() == stack && malachite.charge() >= malachite.maxCharge() && holding.isAttacking()) {
                if (!player.isCreative()) {
                    stack.set(MalachiteDataComponents.MALACHITE, malachite.withCharge(0));
                } else {
                    player.getItemCooldownManager().set(stack.getItem(), 5);
                }

                if (EnchantmentHelper.hasAnyEnchantmentsWith(stack, MalachiteEnchantments.SHOCKWAVE)) { // Shockwave Logic
                    Vec3d pos = player.getPos();
                    Box box = new Box(pos.x - 1, pos.y - 1, pos.z - 1, pos.x + 1, pos.y + 1, pos.z + 1).expand(7);
                    for (LivingEntity living : world.getEntitiesByClass(LivingEntity.class, box, living -> living.isAlive() && !living.isSpectator())) {
                        if (living.getUuid() != player.getUuid()) {
                            living.setVelocity(pos.subtract(living.getPos()).multiply(-getShockwaveStrength(stack), 0, -getShockwaveStrength(stack)).add(0, living.getPos().y < player.getPos().y - 1 ? -1.6 : 1.6, 0));
                            living.velocityModified = true;

                            living.damage(MalachiteDamageTypes.create(world, MalachiteDamageTypes.OVERCHARGED), 3);
                            living.addStatusEffect(new StatusEffectInstance(MalachiteEffects.OVERCHARGED, living.getRandom().nextBetween(10, 15) * 20));
                        }
                    }

                    Malachite.spawnShockwave(world, pos, 5.0f, new Vec3d(0, 0.5, 0));

                    player.getItemCooldownManager().set(stack.getItem(), 10);
                } else { // Beam Logic
                    world.playSound(null, player.getPos().x, player.getPos().y, player.getPos().z, MalachiteSounds.ENERGY_BEAM_SHOOT, SoundCategory.PLAYERS, 1.0F, (float) (1.0F + player.getRandom().nextGaussian() / 10.0F));

                    float damage = stack.getOrDefault(MalachiteDataComponents.BEAM_DAMAGE, 5.0F);
                    EnergyBeamEntity energyBeam = new EnergyBeamEntity(world, player, stack);
                    energyBeam.setDamage(damage);
                    energyBeam.getDataTracker().set(EnergyBeamEntity.FORCED_PITCH, player.getPitch());
                    energyBeam.getDataTracker().set(EnergyBeamEntity.FORCED_YAW, player.getYaw());
                    world.spawnEntity(energyBeam);

                    player.getItemCooldownManager().set(stack.getItem(), 20);
                }
            }
        }
    }

    public float getShockwaveStrength(ItemStack stack) {
        MutableFloat mutableFloat = new MutableFloat(1.3F);
        if (EnchantmentHelper.hasAnyEnchantmentsWith(stack, MalachiteEnchantments.SHOCKWAVE)) {
            EnchantmentHelper.forEachEnchantment(stack, (enchantment, level) -> {
                if (enchantment.value().effects().contains(MalachiteEnchantments.SHOCKWAVE)) {
                    mutableFloat.setValue(enchantment.value().effects().get(MalachiteEnchantments.SHOCKWAVE));
                }
            });
        }

        return mutableFloat.floatValue();
    }

    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return Integer.MAX_VALUE;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.NONE;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        super.use(world, user, hand);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(user.getStackInHand(hand));
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockState state = context.getWorld().getBlockState(context.getBlockPos());
        PlayerEntity user = context.getPlayer();
        ItemStack stack = context.getStack();
        if (user != null && stack != null) {
            MalachiteComponent component = stack.getOrDefault(MalachiteDataComponents.MALACHITE, MalachiteComponent.LONGSWORD);
            if (state.getBlock() instanceof MalachitePylonBlock && component.charge() < component.maxCharge()) {
                return ActionResult.FAIL;
            }
        }

        return super.useOnBlock(context);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (MalachiteComponent.fullyCharged(stack)) {
            tooltip.add(Text.translatable("item.malachite.tooltip.charged").withColor(0x53efac));
        }
    }

    public void absorbDamage(PlayerEntity player, DamageSource source, ItemStack stack, float amount) {
        if (!MalachiteComponent.fullyCharged(stack)) {
            MalachiteComponent component = MalachiteComponent.getOrDefault(stack);
            stack.set(MalachiteDataComponents.MALACHITE, component.addCharge(1));
        }

        World world = player.getWorld();
        Vec3d pos = player.getPos();

        if (world instanceof ServerWorld serverWorld) {
            serverWorld.spawnParticles(
                    MalachiteParticles.SPARK,
                    pos.x,
                    pos.y,
                    pos.z,
                    55,
                    0.6,
                    1.8,
                    0.6,
                    0.1
            );
        }
    }

    public boolean isItemBarVisible(ItemStack stack) {
        return MalachiteComponent.getOrDefault(stack).charge() > 0;
    }

    public int getItemBarColor(ItemStack stack) {
        return 0x43bd86;
    }

    public int getItemBarStep(ItemStack stack) {
        MalachiteComponent component = MalachiteComponent.getOrDefault(stack);
        return Math.round((float)component.charge() / component.maxCharge() * 13);
    }

    public boolean allowComponentsUpdateAnimation(PlayerEntity player, Hand hand, ItemStack oldStack, ItemStack newStack) {
        return oldStack.getItem() != newStack.getItem();
    }
}
