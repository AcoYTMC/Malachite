package net.acoyt.malachite.client;

import net.acoyt.malachite.Malachite;
import net.acoyt.malachite.component.MalachiteComponent;
import net.acoyt.malachite.index.MalachiteDataComponents;
import net.minecraft.client.item.ModelPredicateProviderRegistry;

public class ChargedItemPredicate {
    public static void registerItemPredicate() {
        ModelPredicateProviderRegistry.register(Malachite.id("charged"), (stack, world, entity, seed) ->
                stack.contains(MalachiteDataComponents.MALACHITE) && MalachiteComponent.fullyCharged(stack) ? 1.0F : 0.0F);
    }
}
