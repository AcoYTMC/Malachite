package net.acoyt.malachite.mixin;

import net.acoyt.malachite.index.MalachiteItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract boolean isOf(Item item);

    @Inject(method = "getDamage", at = @At("HEAD"), cancellable = true)
    private void noDamage(CallbackInfoReturnable<Integer> cir) {
        if (this.isOf(MalachiteItems.MALACHITE_LONGSWORD)) {
            cir.setReturnValue(0);
        }
    }
}
