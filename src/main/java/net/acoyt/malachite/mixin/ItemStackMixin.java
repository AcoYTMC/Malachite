package net.acoyt.malachite.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.acoyt.malachite.impl.index.tag.MalachiteItemTags;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow public abstract boolean isIn(TagKey<Item> tag);

    @ModifyReturnValue(method = "getDamage", at = @At("RETURN"))
    private int noDamage(int original) {
        if (this.isIn(MalachiteItemTags.MALACHITE_WEAPONRY)) {
            return 0;
        }

        return original;
    }
}
