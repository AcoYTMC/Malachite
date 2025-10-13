package net.acoyt.malachite.item;

import net.acoyt.malachite.Malachite;
import net.acoyt.malachite.component.MalachiteComponent;
import net.acoyt.malachite.index.MalachiteDataComponents;
import net.acoyt.malachite.index.MalachiteToolMaterials;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

import java.util.List;

public class MalachiteLongswordItem extends SwordItem {
    public MalachiteLongswordItem(Settings settings) {
        super(MalachiteToolMaterials.LONGSWORD, settings
                .component(MalachiteDataComponents.MALACHITE, MalachiteComponent.LONGSWORD)
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
        return false;
    }
}
