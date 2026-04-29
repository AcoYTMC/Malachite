package net.acoyt.malachite.impl.index;

import net.acoyt.malachite.impl.Malachite;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.advancement.criterion.TickCriterion;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public interface MalachiteCriterions {
    TickCriterion DOPING = register("doping", new TickCriterion());

    static <T extends Criterion<?>> T register(String name, T criterion) {
        return Registry.register(Registries.CRITERION, Malachite.id(name), criterion);
    }

    static void init() {}
}
