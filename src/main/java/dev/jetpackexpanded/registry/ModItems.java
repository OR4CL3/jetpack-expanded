package dev.jetpackexpanded.registry;

import dev.jetpackexpanded.item.JetpackItem;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import java.util.function.Function;

import static dev.jetpackexpanded.JetpackExpanded.MOD_ID;

public final class ModItems {

    public static final JetpackItem JETPACK_MK1 = register("jetpack_mk1",
        settings -> new JetpackItem(JetpackTier.MK1, settings));

    public static final JetpackItem JETPACK_MK2 = register("jetpack_mk2",
        settings -> new JetpackItem(JetpackTier.MK2, settings));

    public static final JetpackItem JETPACK_MK3 = register("jetpack_mk3",
        settings -> new JetpackItem(JetpackTier.MK3, settings));

    public static final JetpackItem JETPACK_MK4 = register("jetpack_mk4",
        settings -> new JetpackItem(JetpackTier.MK4, settings));

    public static final JetpackItem JETPACK_CREATIVE = register("jetpack_creative",
        settings -> new JetpackItem(JetpackTier.CREATIVE, settings));

    /** Chamado no entrypoint main para forçar init dos estáticos. */
    public static void init() { /* trigger static fields */ }

    private static <T extends Item> T register(String name, Function<Item.Settings, T> factory) {
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(MOD_ID, name));
        Item.Settings settings = new Item.Settings()
            .registryKey(key)
            .maxCount(1)
            .equippableUnswappable(EquipmentSlot.CHEST);
        return Registry.register(Registries.ITEM, key, factory.apply(settings));
    }
}
