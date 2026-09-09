package dev.jetpackexpanded;

import dev.jetpackexpanded.handler.JetpackHandler;
import dev.jetpackexpanded.network.JetpackNetwork;
import dev.jetpackexpanded.registry.ModCreativeTabs;
import dev.jetpackexpanded.registry.ModItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JetpackExpanded implements ModInitializer {

    public static final String MOD_ID = "jetpackexpanded";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Jetpack Expanded — initializing");

        // Registra itens e creative tab
        ModItems.init();
        ModCreativeTabs.init();

        // Registra energy storages (TeamReborn compatibility)
        ModItems.registerEnergyStorages();

        // Network — payloads e handlers server-side
        JetpackNetwork.registerPayloads();
        JetpackNetwork.registerServerHandlers();

        // Tick de voo — roda no final de cada world tick no servidor
        ServerTickEvents.END_WORLD_TICK.register(JetpackHandler::onWorldTick);

        LOGGER.info("Jetpack Expanded — ready!");
    }
}
