package net.acoyt.malachite.util.interfaces;

@SuppressWarnings("unused")
public interface MPlayerEntity {
    default boolean malachite$holdingAttack() {
        return false;
    }

    default void malachite$setHoldingAttack(boolean attackHeld) {}

    default int malachite$getHoldingAttackTime() {
        return 0;
    }

    default boolean malachite$holdingUse() {
        return false;
    }

    default void malachite$setHoldingUse(boolean useHeld) {}

    default int malachite$getHoldingUseTime() {
        return 0;
    }
}