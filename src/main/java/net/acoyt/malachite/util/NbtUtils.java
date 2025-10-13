package net.acoyt.malachite.util;

import net.minecraft.nbt.NbtCompound;

public class NbtUtils {
    public static boolean getOrDefault(NbtCompound compound, String key, boolean defaultValue) {
        return compound.contains(key) ? compound.getBoolean(key) : defaultValue;
    }

    public static int getOrDefault(NbtCompound compound, String key, int defaultValue) {
        return compound.contains(key) ? compound.getInt(key) : defaultValue;
    }

    public static double getOrDefault(NbtCompound compound, String key, double defaultValue) {
        return compound.contains(key) ? compound.getDouble(key) : defaultValue;
    }

    public static float getOrDefault(NbtCompound compound, String key, float defaultValue) {
        return compound.contains(key) ? compound.getFloat(key) : defaultValue;
    }

    public static String getOrDefault(NbtCompound compound, String key, String defaultValue) {
        return compound.contains(key) ? compound.getString(key) : defaultValue;
    }
}
