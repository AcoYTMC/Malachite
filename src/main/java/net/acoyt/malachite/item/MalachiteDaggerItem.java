package net.acoyt.malachite.item;

import net.acoyt.malachite.component.MalachiteComponent;
import net.acoyt.malachite.entity.MalachiteDaggerEntity;
import net.acoyt.malachite.index.MalachiteDataComponents;
import net.acoyt.malachite.index.MalachiteEffects;
import net.acoyt.malachite.index.MalachiteSounds;
import net.acoyt.malachite.index.MalachiteToolMaterials;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class MalachiteDaggerItem extends SwordItem {
    public MalachiteDaggerItem(Settings settings) {
        super(MalachiteToolMaterials.DAGGER, settings
                .component(MalachiteDataComponents.MALACHITE, MalachiteComponent.DAGGER)
        );
    }

    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        MalachiteComponent component = MalachiteComponent.getOrDefault(stack);
        if (!MalachiteComponent.fullyCharged(stack)) {
            stack.set(MalachiteDataComponents.MALACHITE, component.addCharge(component.charge() > component.maxCharge() ? -1 : 1));
        } else {
            float strength = (float)(0.25f * (1.0 - target.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)));

            //target.setVelocity(attacker.getPos().subtract(target.getPos()).multiply(strength * 5.0f).multiply(-1));
            //target.addVelocity(0, 0.6, 0);
            //target.velocityModified = true;

            target.addStatusEffect(new StatusEffectInstance(MalachiteEffects.OVERCHARGED, 600, 1));

            stack.set(MalachiteDataComponents.MALACHITE, component.withCharge(0));
        }

        return true;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!user.getItemCooldownManager().isCoolingDown(stack.getItem())) {
            MalachiteDaggerEntity daggerEntity = new MalachiteDaggerEntity(world, user, stack);
            daggerEntity.setCharged(MalachiteComponent.fullyCharged(stack));
            daggerEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 2.5F * 0.5F, 1.0F);
            if (user.isCreative()) {
                daggerEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
            } else {
                user.getInventory().removeOne(stack);
            }

            world.spawnEntity(daggerEntity);
            world.playSoundFromEntity(null, daggerEntity, MalachiteSounds.DAGGER_THROWN, SoundCategory.PLAYERS, 1.0F, 1.0F);

            user.getItemCooldownManager().set(stack.getItem(), 5);
            return TypedActionResult.success(user.getStackInHand(hand));
        }

        return TypedActionResult.fail(user.getStackInHand(hand));
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (MalachiteComponent.fullyCharged(stack)) {
            tooltip.add(Text.translatable("item.malachite.tooltip.charged").withColor(0x53efac));
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
}
