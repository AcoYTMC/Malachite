package net.acoyt.malachite.impl.client;

import net.acoyt.malachite.api.BlockingItem;
import net.acoyt.malachite.impl.Malachite;
import net.minecraft.client.item.ModelPredicateProviderRegistry;

public class BlockingItemPredicate {
    public static void registerItemPredicate() {
        ModelPredicateProviderRegistry.register(Malachite.id("blocking"), (stack, world, entity, seed) ->
                entity != null && entity.getActiveItem() != null && entity.getActiveItem().getItem() instanceof BlockingItem ? 1.0F : 0.0F);
    }
}
