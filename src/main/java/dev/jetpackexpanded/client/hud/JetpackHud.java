package dev.jetpackexpanded.client.hud;

import dev.jetpackexpanded.item.JetpackItem;
import dev.jetpackexpanded.registry.JetpackTier;
import dev.jetpackexpanded.util.JetpackUtils;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public final class JetpackHud {

    // Posição do HUD (canto inferior esquerdo)
    private static final int X  = 10;
    private static final int GAP = 2;

    // Cores
    private static final int COLOR_BG      = 0xAA1A1A1A;
    private static final int COLOR_ENERGY  = 0xFF00AAFF;
    private static final int COLOR_EMPTY   = 0xFF333333;
    private static final int COLOR_ENGINE_ON  = 0xFF00FF44;
    private static final int COLOR_ENGINE_OFF = 0xFFFF3333;
    private static final int COLOR_HOVER_ON   = 0xFF44CCFF;
    private static final int COLOR_HOVER_OFF  = 0xFF555555;

    public static void register() {
        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> render(drawContext));
    }

    private static void render(DrawContext ctx) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.options.hudHidden) return;

        ItemStack chest = client.player.getEquippedStack(EquipmentSlot.CHEST);
        if (!(chest.getItem() instanceof JetpackItem jp)) return;

        JetpackTier tier    = jp.getTier();
        long energy         = tier.isCreative() ? tier.maxEnergy() : JetpackUtils.getEnergy(chest);
        long maxEnergy      = tier.maxEnergy();
        boolean engineOn    = JetpackUtils.isEngineOn(chest);
        boolean hoverOn     = JetpackUtils.isHovering(chest);

        int screenH = client.getWindow().getScaledHeight();
        int y       = screenH - 60;

        // ── Barra de energia (60x8) ──────────────────────────────────────────
        float pct      = tier.isCreative() ? 1.0f : (float) energy / maxEnergy;
        int barW       = 60;
        int filledW    = (int) (barW * pct);

        ctx.fill(X - 1, y - 1, X + barW + 1, y + 9, COLOR_BG);
        ctx.fill(X, y, X + barW, y + 8, COLOR_EMPTY);
        if (filledW > 0) ctx.fill(X, y, X + filledW, y + 8, COLOR_ENERGY);

        // ── Label de energia ─────────────────────────────────────────────────
        String label = tier.isCreative() ? "∞ FE" : formatFE(energy) + " FE";
        ctx.drawTextWithShadow(client.textRenderer, label, X, y - 10, 0xFFFFFFFF);

        // ── Indicadores de status (Engine / Hover) ───────────────────────────
        int statusY = y + 11;
        ctx.fill(X - 1, statusY - 1, X + 27, statusY + 7, COLOR_BG);
        ctx.drawTextWithShadow(client.textRenderer, "E",
            X, statusY, engineOn ? COLOR_ENGINE_ON : COLOR_ENGINE_OFF);
        ctx.drawTextWithShadow(client.textRenderer, " H",
            X + 8, statusY, hoverOn ? COLOR_HOVER_ON : COLOR_HOVER_OFF);
    }

    private static String formatFE(long fe) {
        if (fe >= 1_000_000) return String.format("%.1fM", fe / 1_000_000.0);
        if (fe >= 1_000)     return String.format("%.1fK", fe / 1_000.0);
        return String.valueOf(fe);
    }
}
