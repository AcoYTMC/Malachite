package net.acoyt.malachite.impl.client;

import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.component.MalachiteComponent;
import net.acoyt.malachite.impl.index.MalachiteDataComponents;
import net.minecraft.client.item.ModelPredicateProviderRegistry;

public class ChargedItemPredicate {
    public static void registerItemPredicate() {
        ModelPredicateProviderRegistry.register(Malachite.id("charged"), (stack, world, entity, seed) ->
                stack.contains(MalachiteDataComponents.MALACHITE) && MalachiteComponent.fullyCharged(stack) ? 1.0F : 0.0F);
    }
}
