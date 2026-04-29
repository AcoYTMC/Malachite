package net.acoyt.malachite.impl.index;

import com.mojang.serialization.Codec;
import net.acoyt.acornlib.api.registrants.ComponentTypeRegistrant;
import net.acoyt.malachite.impl.Malachite;
import net.acoyt.malachite.impl.component.MalachiteComponent;
import net.acoyt.malachite.impl.util.Util;
import net.minecraft.component.ComponentType;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.math.Vec3d;

public interface MalachiteDataComponents {
    ComponentTypeRegistrant COMPONENTS = new ComponentTypeRegistrant(Malachite.MOD_ID);

    ComponentType<MalachiteComponent> MALACHITE = COMPONENTS.register("malachite", MalachiteComponent.CODEC, MalachiteComponent.PACKET_CODEC);
    ComponentType<Float> BEAM_DAMAGE = COMPONENTS.register("beam_damage", Codec.FLOAT, PacketCodecs.FLOAT);
    ComponentType<Vec3d> POSITION = COMPONENTS.register("position", Vec3d.CODEC, Util.VEC3D_PACKET_CODEC);

    static void init() {}
}
