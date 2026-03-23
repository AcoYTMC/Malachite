package net.acoyt.malachite.impl.item;

import net.acoyt.acornlib.api.item.ModelVaryingItem;
import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.block.MalachitePylonBlock;
import net.acoyt.malachite.impl.component.MalachiteComponent;
import net.acoyt.malachite.impl.entity.MalachiteDaggerEntity;
import net.acoyt.malachite.impl.index.*;
import net.acoyt.malachite.impl.util.Util;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SwordItem;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class MalachiteDaggerItem extends SwordItem implements ModelVaryingItem {
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
            target.addStatusEffect(new StatusEffectInstance(MalachiteEffects.OVERCHARGED, 600, 1));

            Util.spawnBlast(attacker, 0xFF53efac, 1.0f, attacker.getRotationVector().multiply(1.2, 0, 1.2).add(0, 1, 0));

            if (!attacker.isInCreativeMode()) stack.set(MalachiteDataComponents.MALACHITE, component.withCharge(0));
        }

        if (EnchantmentHelper.hasAnyEnchantmentsWith(stack, MalachiteEnchantmentEffects.MAGNETIC) && component.charge() == 4) {
            target.setVelocity(attacker.getRotationVector().multiply(-1, 0, -1).add(0, 0.1, 0));
            target.velocityModified = true;
        }

        return true;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (!user.getItemCooldownManager().isCoolingDown(stack.getItem())) {
            MalachiteDaggerEntity daggerEntity = new MalachiteDaggerEntity(world, user, stack);
            daggerEntity.setCharged(MalachiteComponent.fullyCharged(stack));
            daggerEntity.setMagnetic(EnchantmentHelper.hasAnyEnchantmentsWith(stack, MalachiteEnchantmentEffects.MAGNETIC));
            daggerEntity.setVoltage(EnchantmentHelper.hasAnyEnchantmentsWith(stack, MalachiteEnchantmentEffects.VOLTAGE));
            daggerEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 2.5F * 0.5F, 1.0F);
            daggerEntity.setCreative(user.isCreative());
            if (user.isCreative()) {
                daggerEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
            } else {
                user.getInventory().removeOne(stack);
            }

            world.spawnEntity(daggerEntity);
            world.playSoundFromEntity(null, daggerEntity, MalachiteSounds.DAGGER_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);

            user.getItemCooldownManager().set(stack.getItem(), 15);
            return TypedActionResult.success(user.getStackInHand(hand));
        }

        return TypedActionResult.fail(user.getStackInHand(hand));
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockState state = context.getWorld().getBlockState(context.getBlockPos());
        PlayerEntity user = context.getPlayer();
        ItemStack stack = context.getStack();
        if (user != null && stack != null) {
            MalachiteComponent component = stack.getOrDefault(MalachiteDataComponents.MALACHITE, MalachiteComponent.DAGGER);
            if (state.getBlock() instanceof MalachitePylonBlock && component.charge() < component.maxCharge()) {
                return ActionResult.FAIL;
            }
        }

        return super.useOnBlock(context);
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        if (MalachiteComponent.fullyCharged(stack)) {
            tooltip.add(Text.translatable("item.malachite.tooltip.charged").withColor(0xFF53efac));
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

    // Gui Varying
    @Override
    public Identifier getModel(ModelTransformationMode renderMode, ItemStack stack, @Nullable LivingEntity entity) {
        return MalachiteComponent.fullyCharged(stack) ? Malachite.id("malachite_dagger_charged") : Malachite.id("malachite_dagger");
    }

    @Override
    public List<Identifier> getModelsToLoad() {
        return Arrays.asList(
                Malachite.id("malachite_dagger"),
                Malachite.id("malachite_dagger_charged")
        );
    }
}
