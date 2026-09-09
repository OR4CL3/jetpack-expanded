package dev.jetpackexpanded.item;

import dev.jetpackexpanded.registry.JetpackTier;
import dev.jetpackexpanded.util.JetpackUtils;
import net.minecraft.client.item.TooltipType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class JetpackItem extends ArmorItem {

    private final JetpackTier tier;

    public JetpackItem(RegistryEntry<ArmorMaterial> material, JetpackTier tier, Settings settings) {
        super(material, Type.CHESTPLATE, settings);
        this.tier = tier;
    }

    public JetpackTier getTier() {
        return tier;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        long energy = tier.isCreative() ? tier.maxEnergy() : JetpackUtils.getEnergy(stack);
        long max    = tier.maxEnergy();

        // Barra de energia
        tooltip.add(Text.literal("⚡ " + formatFE(energy) + " / " + formatFE(max) + " FE")
            .formatted(Formatting.AQUA));

        // Status
        boolean engine = JetpackUtils.isEngineOn(stack);
        boolean hover  = JetpackUtils.isHovering(stack);

        tooltip.add(Text.literal("Engine: ").formatted(Formatting.GRAY)
            .append(Text.literal(engine ? "ON" : "OFF").formatted(engine ? Formatting.GREEN : Formatting.RED)));

        tooltip.add(Text.literal("Hover:  ").formatted(Formatting.GRAY)
            .append(Text.literal(hover ? "ON" : "OFF").formatted(hover ? Formatting.GREEN : Formatting.RED)));

        if (tier.isCreative()) {
            tooltip.add(Text.literal("∞ Creative Mode").formatted(Formatting.LIGHT_PURPLE));
        }
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    // ── Helpers ──────────────────────────────────────────────────────────────

    private static String formatFE(long fe) {
        if (fe == Long.MAX_VALUE / 2) return "∞";
        if (fe >= 1_000_000) return String.format("%.1fM", fe / 1_000_000.0);
        if (fe >= 1_000)     return String.format("%.1fK", fe / 1_000.0);
        return String.valueOf(fe);
    }
}
