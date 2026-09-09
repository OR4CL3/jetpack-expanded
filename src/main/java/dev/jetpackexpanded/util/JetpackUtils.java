package dev.jetpackexpanded.util;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

/** Helpers para ler/escrever estado do jetpack no DataComponent do ItemStack. */
public final class JetpackUtils {

    private JetpackUtils() {}

    // ── Energy ───────────────────────────────────────────────────────────────

    public static long getEnergy(ItemStack stack) {
        return getNbt(stack).getLong("energy");
    }

    public static void setEnergy(ItemStack stack, long energy) {
        NbtCompound nbt = getNbt(stack);
        nbt.putLong("energy", energy);
        setNbt(stack, nbt);
    }

    /**
     * Drena {@code amount} FE do jetpack.
     * @return true se havia energia suficiente e foi drenada.
     */
    public static boolean drainEnergy(ItemStack stack, long amount) {
        long current = getEnergy(stack);
        if (current < amount) return false;
        setEnergy(stack, current - amount);
        return true;
    }

    // ── Engine / Hover ───────────────────────────────────────────────────────

    public static boolean isEngineOn(ItemStack stack) {
        return getNbt(stack).getBoolean("engine_on");
    }

    public static void setEngineOn(ItemStack stack, boolean on) {
        NbtCompound nbt = getNbt(stack);
        nbt.putBoolean("engine_on", on);
        setNbt(stack, nbt);
    }

    public static boolean isHovering(ItemStack stack) {
        return getNbt(stack).getBoolean("hover_on");
    }

    public static void setHovering(ItemStack stack, boolean on) {
        NbtCompound nbt = getNbt(stack);
        nbt.putBoolean("hover_on", on);
        setNbt(stack, nbt);
    }

    // ── Helpers privados ─────────────────────────────────────────────────────

    private static NbtCompound getNbt(ItemStack stack) {
        return stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt();
    }

    private static void setNbt(ItemStack stack, NbtCompound nbt) {
        stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(nbt));
    }
}
