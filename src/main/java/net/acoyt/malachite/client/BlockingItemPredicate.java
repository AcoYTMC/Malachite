package net.acoyt.malachite.client;

import net.acoyt.malachite.Malachite;
import net.acoyt.malachite.index.MalachiteItems;
import net.minecraft.client.item.ModelPredicateProviderRegistry;

public class BlockingItemPredicate {
    public static void registerItemPredicate() {
        ModelPredicateProviderRegistry.register(Malachite.id("blocking"), (stack, world, entity, seed) ->
                entity != null && entity.getActiveItem() != null && entity.getActiveItem().isOf(MalachiteItems.MALACHITE_LONGSWORD) ? 1.0F : 0.0F);
    }
}
