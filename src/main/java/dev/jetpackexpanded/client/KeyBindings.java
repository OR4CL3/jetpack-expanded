package dev.jetpackexpanded.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import static dev.jetpackexpanded.JetpackExpanded.MOD_ID;

public final class KeyBindings {

    private static final KeyBinding.Category CATEGORY =
        KeyBinding.Category.create(Identifier.of(MOD_ID, "general"));

    public static final KeyBinding TOGGLE_ENGINE = KeyBindingHelper.registerKeyBinding(
        new KeyBinding(
            "key.jetpackexpanded.toggle_engine",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_V,           // default: V
            CATEGORY
        )
    );

    public static final KeyBinding TOGGLE_HOVER = KeyBindingHelper.registerKeyBinding(
        new KeyBinding(
            "key.jetpackexpanded.toggle_hover",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_G,           // default: G
            CATEGORY
        )
    );

    public static void init() { /* trigger static */ }
}
