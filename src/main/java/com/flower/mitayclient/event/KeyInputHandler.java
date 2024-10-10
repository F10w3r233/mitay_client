package com.flower.mitayclient.event;

import com.flower.mitayclient.GUI.Screen.PlaceListScreen;
import com.flower.mitayclient.GUI.Screen.TeleportPlayerScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class KeyInputHandler
{
    public static final String KEY_CATEGORY = "key.category.mitayclient.mitay";
    public static final String KEY_OPEN_PLACES_LIST = "key.mitayclient.open_places_list";
    public static final String KEY_OPEN_TELEPORT_LIST = "key.mitayclient.open_teleport_screen";

    public static KeyBinding openPlacesListKey;
    public static KeyBinding openTeleportScreenKey;

    public static void registerKetInputs()
    {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {   //地点列表
            if (openPlacesListKey.wasPressed())
            {
//                client.player.sendMessage(Text.of("Hi"), true);
                MinecraftClient.getInstance().setScreen(new PlaceListScreen());
//                ClientPlayNetworking.send(ModMessage.DR, PacketByteBufs.create());
            }
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {   //tp玩家列表
            if (openTeleportScreenKey.wasPressed())
            {
                MinecraftClient.getInstance().setScreen(new TeleportPlayerScreen());
            }
        });
    }

    public static void register()
    {
        openPlacesListKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_OPEN_PLACES_LIST,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                KEY_CATEGORY
        ));

        openTeleportScreenKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                KEY_OPEN_TELEPORT_LIST,
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                KEY_CATEGORY
        ));

        registerKetInputs();
    }

}

