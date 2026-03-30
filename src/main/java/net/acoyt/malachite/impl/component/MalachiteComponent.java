package net.acoyt.malachite.impl.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.acoyt.malachite.impl.index.MalachiteDataComponents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;

public record MalachiteComponent(int charge, int maxCharge) {
    public static final MalachiteComponent DEFAULT = new MalachiteComponent(0, 4);

    public static final Codec<MalachiteComponent> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.optionalFieldOf("charge", 0).forGetter(MalachiteComponent::charge),
            Codec.INT.optionalFieldOf("maxCharge", 4).forGetter(MalachiteComponent::maxCharge)
    ).apply(instance, MalachiteComponent::new));

    public static final PacketCodec<RegistryByteBuf, MalachiteComponent> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER,
            MalachiteComponent::charge,
            PacketCodecs.INTEGER,
            MalachiteComponent::maxCharge,
            MalachiteComponent::new
    );

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
        return stack.getOrDefault(MalachiteDataComponents.MALACHITE, DEFAULT);
    }
}
