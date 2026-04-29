package net.acoyt.malachite.impl.index;

import com.mojang.serialization.Codec;
import net.acoyt.acornlib.api.registrants.ComponentTypeRegistrant;
import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.util.Util;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Unit;

public interface MalachiteEnchantmentEffects {
    ComponentTypeRegistrant COMPONENTS = new ComponentTypeRegistrant(Malachite.MOD_ID);

    ComponentType<Float> SHOCKWAVE = COMPONENTS.register("shockwave", Codec.FLOAT, PacketCodecs.FLOAT);
    ComponentType<Unit> VOLTAGE = COMPONENTS.register("voltage", Unit.CODEC, Util.UNIT_PACKET_CODEC);
    ComponentType<Unit> MAGNETIC = COMPONENTS.register("magnetic", Unit.CODEC, Util.UNIT_PACKET_CODEC);
    ComponentType<Unit> DISRUPT = COMPONENTS.register("disrupt", Unit.CODEC, Util.UNIT_PACKET_CODEC);

    static void init() {}
}
