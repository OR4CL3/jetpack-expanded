package dev.jetpackexpanded.registry;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static dev.jetpackexpanded.JetpackExpanded.MOD_ID;

public final class ModCreativeTabs {

    public static final ItemGroup JETPACK_EXPANDED = Registry.register(
        Registries.ITEM_GROUP,
        Identifier.of(MOD_ID, "jetpack_expanded"),
        FabricItemGroup.builder()
            .icon(() -> new ItemStack(ModItems.JETPACK_MK3))
            .displayName(Text.translatable("itemGroup.jetpackexpanded.jetpack_expanded"))
            .entries((ctx, entries) -> {
                entries.add(ModItems.JETPACK_MK1);
                entries.add(ModItems.JETPACK_MK2);
                entries.add(ModItems.JETPACK_MK3);
                entries.add(ModItems.JETPACK_MK4);
                entries.add(ModItems.JETPACK_CREATIVE);
            })
            .build()
    );

    public static void init() { /* trigger static */ }
}
