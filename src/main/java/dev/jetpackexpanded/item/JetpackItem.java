package dev.jetpackexpanded.item;

import dev.jetpackexpanded.registry.JetpackTier;
import dev.jetpackexpanded.util.JetpackUtils;
import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.function.Consumer;

public class JetpackItem extends Item {

    private final JetpackTier tier;

    public JetpackItem(JetpackTier tier, Settings settings) {
        super(settings);
        this.tier = tier;
    }

    public JetpackTier getTier() {
        return tier;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, TooltipDisplayComponent display,
                              Consumer<Text> tooltip, TooltipType type) {
        long energy = tier.isCreative() ? tier.maxEnergy() : JetpackUtils.getEnergy(stack);
        long max    = tier.maxEnergy();

        tooltip.accept(Text.literal("⚡ " + formatFE(energy) + " / " + formatFE(max) + " FE")
            .formatted(Formatting.AQUA));

        boolean engine = JetpackUtils.isEngineOn(stack);
        boolean hover  = JetpackUtils.isHovering(stack);

        tooltip.accept(Text.literal("Engine: ").formatted(Formatting.GRAY)
            .append(Text.literal(engine ? "ON" : "OFF").formatted(engine ? Formatting.GREEN : Formatting.RED)));

        tooltip.accept(Text.literal("Hover:  ").formatted(Formatting.GRAY)
            .append(Text.literal(hover ? "ON" : "OFF").formatted(hover ? Formatting.GREEN : Formatting.RED)));

        if (tier.isCreative()) {
            tooltip.accept(Text.literal("∞ Creative Mode").formatted(Formatting.LIGHT_PURPLE));
        }
    }

    private static String formatFE(long fe) {
        if (fe == Long.MAX_VALUE / 2) return "∞";
        if (fe >= 1_000_000) return String.format("%.1fM", fe / 1_000_000.0);
        if (fe >= 1_000)     return String.format("%.1fK", fe / 1_000.0);
        return String.valueOf(fe);
    }
}
