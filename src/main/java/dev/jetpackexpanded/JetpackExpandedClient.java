package dev.jetpackexpanded;

import dev.jetpackexpanded.client.KeyBindings;
import dev.jetpackexpanded.client.hud.JetpackHud;
import dev.jetpackexpanded.network.payload.ToggleEnginePayload;
import dev.jetpackexpanded.network.payload.ToggleHoverPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.networking.v1.ClientPlayNetworking;

public class JetpackExpandedClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Key bindings
        KeyBindings.init();

        // HUD
        JetpackHud.register();

        // Input handling — verifica key presses a cada client tick
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            while (KeyBindings.TOGGLE_ENGINE.wasPressed()) {
                ClientPlayNetworking.send(new ToggleEnginePayload());
            }
            while (KeyBindings.TOGGLE_HOVER.wasPressed()) {
                ClientPlayNetworking.send(new ToggleHoverPayload());
            }
        });
    }
}
