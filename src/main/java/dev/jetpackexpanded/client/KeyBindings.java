package dev.jetpackexpanded.client;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public final class KeyBindings {

    public static final KeyBinding TOGGLE_ENGINE = KeyBindingHelper.registerKeyBinding(
        new KeyBinding(
            "key.jetpackexpanded.toggle_engine",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_V,           // default: V
            "category.jetpackexpanded"
        )
    );

    public static final KeyBinding TOGGLE_HOVER = KeyBindingHelper.registerKeyBinding(
        new KeyBinding(
            "key.jetpackexpanded.toggle_hover",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_G,           // default: G
            "category.jetpackexpanded"
        )
    );

    public static void init() { /* trigger static */ }
}
