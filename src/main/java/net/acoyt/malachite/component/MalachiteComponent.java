package net.acoyt.malachite.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.acoyt.malachite.index.MalachiteDataComponents;
import net.minecraft.item.ItemStack;

public record MalachiteComponent(int charge, int maxCharge) {
    public static final MalachiteComponent LONGSWORD = new MalachiteComponent(0, 6);
    public static final MalachiteComponent DAGGER = new MalachiteComponent(0, 3);

    public static final Codec<MalachiteComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf("charge", 0).forGetter(MalachiteComponent::charge),
            Codec.INT.optionalFieldOf("maxCharge", 6).forGetter(MalachiteComponent::maxCharge)
    ).apply(instance, MalachiteComponent::new));

    public MalachiteComponent withCharge(int charge) {
        return new MalachiteComponent(charge, this.maxCharge);
    }

    public MalachiteComponent addCharge(int charge) {
        return new MalachiteComponent(this.charge + charge, this.maxCharge);
    }

    public MalachiteComponent withMaxCharge(int maxCharge) {
        return new MalachiteComponent(this.charge, maxCharge);
    }

    public static boolean fullyCharged(ItemStack stack) {
        MalachiteComponent component = getOrDefault(stack);
        return component.charge >= component.maxCharge;
    }

    public static MalachiteComponent getOrDefault(ItemStack stack) {
        return stack.getOrDefault(MalachiteDataComponents.MALACHITE, LONGSWORD);
    }
}
