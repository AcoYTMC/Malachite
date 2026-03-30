package net.acoyt.malachite.impl.item;

import net.acoyt.acornlib.api.item.ModelVaryingItem;
import net.acoyt.acornlib.api.util.MiscUtils;
import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.cca.entity.ChargedComponent;
import net.acoyt.malachite.impl.component.MalachiteComponent;
import net.acoyt.malachite.impl.index.MalachiteDataComponents;
import net.acoyt.malachite.impl.index.MalachiteEffects;
import net.acoyt.malachite.impl.index.MalachiteEnchantmentEffects;
import net.acoyt.malachite.impl.index.MalachiteToolMaterials;
import net.acoyt.malachite.impl.util.Util;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class MalachiteGreataxeItem extends AxeItem implements ModelVaryingItem {
    public MalachiteGreataxeItem(Settings settings) {
        super(MalachiteToolMaterials.MALACHITE, settings
                .component(MalachiteDataComponents.MALACHITE, MalachiteComponent.DEFAULT));
    }

    public static AttributeModifiersComponent createAttributeModifiers(ToolMaterial material, float baseAttackDamage, float attackSpeed, float attackReach) {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.GENERIC_ATTACK_DAMAGE,
                        new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, baseAttackDamage + material.getAttackDamage(), EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                ).add(
                        EntityAttributes.GENERIC_ATTACK_SPEED,
                        new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                ).add(
                        EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
                        new EntityAttributeModifier(Malachite.id("attack_reach"), attackReach, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }

    public float getBonusAttackDamage(Entity target, float baseAttackDamage, DamageSource source) {
        if (source.getAttacker() instanceof LivingEntity living && living.hasStatusEffect(MalachiteEffects.OVERCHARGED)) {
            return 2.0F * living.getStatusEffect(MalachiteEffects.OVERCHARGED).getAmplifier() + 1;
        }

        return super.getBonusAttackDamage(target, baseAttackDamage, source);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (MalachiteComponent.fullyCharged(stack) && !user.getItemCooldownManager().isCoolingDown(stack.getItem())) {
            if (EnchantmentHelper.hasAnyEnchantmentsWith(stack, MalachiteEnchantmentEffects.MAGNETIC)) {
                stack.set(MalachiteDataComponents.MALACHITE, MalachiteComponent.getOrDefault(stack).withCharge(0));
                Box box = user.getBoundingBox().expand(4, 10, 4).offset(0, 10, 0);

                for (LivingEntity living : world.getEntitiesByClass(LivingEntity.class, box, EntityPredicates.EXCEPT_SPECTATOR)) {
                    living.addVelocity(0, -1.2, 0);
                    living.velocityModified = true;

                    ChargedComponent.KEY.get(living).setMagnetisedTicks(120); // 6s

                    Util.spawnShockwave(living, living.getScale());
                }

                user.getItemCooldownManager().set(stack.getItem(), 80);

                return TypedActionResult.success(user.getStackInHand(hand));
            } else if (EnchantmentHelper.hasAnyEnchantmentsWith(stack, MalachiteEnchantmentEffects.VOLTAGE)) {
                if (!user.getItemCooldownManager().isCoolingDown(stack.getItem())) {
                    if (!user.isSneaking()) {
                        stack.set(MalachiteDataComponents.POSITION, user.getPos());

                        user.getItemCooldownManager().set(stack.getItem(), 20);

                        return TypedActionResult.success(user.getStackInHand(hand));
                    } else if (stack.contains(MalachiteDataComponents.POSITION)) {
                        Vec3d pos = stack.getOrDefault(MalachiteDataComponents.POSITION, user.getPos());
                        if (world instanceof ServerWorld serverWorld) {
                            user.teleportTo(new TeleportTarget(serverWorld, pos, Vec3d.ZERO, user.getYaw(), user.getPitch(), TeleportTarget.NO_OP));
                        }

                        user.getItemCooldownManager().set(stack.getItem(), 40);
                        stack.set(MalachiteDataComponents.MALACHITE, MalachiteComponent.getOrDefault(stack).withCharge(0));

                        return TypedActionResult.success(user.getStackInHand(hand));
                    }
                }
            } else {
                stack.set(MalachiteDataComponents.MALACHITE, MalachiteComponent.getOrDefault(stack).withCharge(0));
                user.addStatusEffect(new StatusEffectInstance(MalachiteEffects.OVERCHARGED, 300, 0));
                user.getItemCooldownManager().set(stack.getItem(), 100);

                return TypedActionResult.success(user.getStackInHand(hand));
            }
        }

        return TypedActionResult.fail(user.getStackInHand(hand));
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!MalachiteComponent.fullyCharged(stack)) {
            MalachiteComponent component = MalachiteComponent.getOrDefault(stack);
            stack.set(MalachiteDataComponents.MALACHITE, component.addCharge(1));
        }

        return super.postHit(stack, target, attacker);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (MalachiteComponent.fullyCharged(stack)) {
            tooltip.add(Text.translatable("item.malachite.tooltip.charged").withColor(0xFF53efac));
        }

        if (stack.contains(MalachiteDataComponents.POSITION)) {
            tooltip.add(Text.translatable("item.malachite.tooltip.position", stack.getOrDefault(MalachiteDataComponents.POSITION, Vec3d.ZERO)).withColor(0xFF53efac));
        }
    }

    public boolean isItemBarVisible(ItemStack stack) {
        return MalachiteComponent.getOrDefault(stack).charge() > 0;
    }

    public int getItemBarColor(ItemStack stack) {
        return 0xFF43bd86;
    }

    public int getItemBarStep(ItemStack stack) {
        MalachiteComponent component = MalachiteComponent.getOrDefault(stack);
        return Math.round((float)component.charge() / component.maxCharge() * 13);
    }

    public Identifier getModel(ModelTransformationMode renderMode, ItemStack stack, @Nullable LivingEntity entity) {
        if (MalachiteComponent.fullyCharged(stack)) {
            return MiscUtils.isGui(renderMode) ? Malachite.id("malachite_greataxe_charged") : Malachite.id("malachite_greataxe_charged_in_hand");
        } else {
            return MiscUtils.isGui(renderMode) ? Malachite.id("malachite_greataxe") : Malachite.id("malachite_greataxe_in_hand");
        }
    }

    public List<Identifier> getModelsToLoad() {
        return Arrays.asList(
                Malachite.id("malachite_greataxe"),
                Malachite.id("malachite_greataxe_in_hand"),
                Malachite.id("malachite_greataxe_charged"),
                Malachite.id("malachite_greataxe_charged_in_hand")
        );
    }
}
