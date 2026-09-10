package dev.jetpackexpanded.registry;

import dev.jetpackexpanded.item.JetpackItem;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static dev.jetpackexpanded.JetpackExpanded.MOD_ID;

public final class ModItems {

    // ── Jetpacks ─────────────────────────────────────────────────────────────

    public static final JetpackItem JETPACK_MK1 = register("jetpack_mk1",
        new JetpackItem(material("mk1", 4), JetpackTier.MK1, new Item.Settings().maxCount(1)));

    public static final JetpackItem JETPACK_MK2 = register("jetpack_mk2",
        new JetpackItem(material("mk2", 5), JetpackTier.MK2, new Item.Settings().maxCount(1)));

    public static final JetpackItem JETPACK_MK3 = register("jetpack_mk3",
        new JetpackItem(material("mk3", 6), JetpackTier.MK3, new Item.Settings().maxCount(1)));

    public static final JetpackItem JETPACK_MK4 = register("jetpack_mk4",
        new JetpackItem(material("mk4", 8), JetpackTier.MK4, new Item.Settings().maxCount(1)));

    public static final JetpackItem JETPACK_CREATIVE = register("jetpack_creative",
        new JetpackItem(material("creative", 20), JetpackTier.CREATIVE, new Item.Settings().maxCount(1)));

    // ── Init ─────────────────────────────────────────────────────────────────

    // ponytail: TeamReborn energy removido do MVP — add quando o maven estiver ok
    // Energia funciona normalmente via JetpackUtils (NBT direto)

    /** Chamado no entrypoint main para garantir que os estáticos sejam inicializados. */
    public static void init() { /* trigger static fields */ }

    // ── Helpers privados ─────────────────────────────────────────────────────

    private static <T extends Item> T register(String name, T item) {
        return Registry.register(Registries.ITEM, Identifier.of(MOD_ID, name), item);
    }

    private static RegistryEntry<ArmorMaterial> material(String name, int chestDefense) {
        Map<ArmorItem.Type, Integer> defense = new EnumMap<>(ArmorItem.Type.class);
        for (ArmorItem.Type t : ArmorItem.Type.values()) {
            defense.put(t, t == ArmorItem.Type.CHESTPLATE ? chestDefense : 0);
        }
        return Registry.registerReference(
            Registries.ARMOR_MATERIAL,
            Identifier.of(MOD_ID, name),
            new ArmorMaterial(
                defense,
                0,
                SoundEvents.ITEM_ARMOR_EQUIP_IRON,
                () -> Ingredient.EMPTY,
                List.of(new ArmorMaterial.Layer(Identifier.of(MOD_ID, name))),
                0.0f, 0.0f
            )
        );
    }
}
