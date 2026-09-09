package dev.jetpackexpanded.network;

import dev.jetpackexpanded.item.JetpackItem;
import dev.jetpackexpanded.network.payload.ToggleEnginePayload;
import dev.jetpackexpanded.network.payload.ToggleHoverPayload;
import dev.jetpackexpanded.util.JetpackUtils;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public final class JetpackNetwork {

    private JetpackNetwork() {}

    /** Registra os tipos de payload (roda em ambos os lados). */
    public static void registerPayloads() {
        PayloadTypeRegistry.playC2S().register(ToggleEnginePayload.ID, ToggleEnginePayload.CODEC);
        PayloadTypeRegistry.playC2S().register(ToggleHoverPayload.ID,  ToggleHoverPayload.CODEC);
    }

    /** Registra os handlers server-side. Chamado apenas no entrypoint main. */
    public static void registerServerHandlers() {
        ServerPlayNetworking.registerGlobalReceiver(ToggleEnginePayload.ID, (payload, ctx) ->
            ctx.server().execute(() -> {
                ItemStack chest = ctx.player().getEquippedStack(EquipmentSlot.CHEST);
                if (chest.getItem() instanceof JetpackItem) {
                    JetpackUtils.setEngineOn(chest, !JetpackUtils.isEngineOn(chest));
                }
            })
        );

        ServerPlayNetworking.registerGlobalReceiver(ToggleHoverPayload.ID, (payload, ctx) ->
            ctx.server().execute(() -> {
                ItemStack chest = ctx.player().getEquippedStack(EquipmentSlot.CHEST);
                if (chest.getItem() instanceof JetpackItem) {
                    JetpackUtils.setHovering(chest, !JetpackUtils.isHovering(chest));
                }
            })
        );
    }
}
