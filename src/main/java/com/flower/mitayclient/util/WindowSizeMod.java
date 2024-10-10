//package com.flower.mitayclient.util;
//
//import net.fabricmc.api.ModInitializer;
//import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
//import net.minecraft.client.MinecraftClient;
//import net.minecraft.client.util.Window;
//
//public class WindowSizeMod implements ModInitializer {
//    @Override
//    public void onInitialize() {
//        ClientTickEvents.START_CLIENT_TICK.register(client ->
//        {
//            if (client.player == null) return;
//            // 在玩家加载完毕后，第一次tick时调整窗口大小
//            Window window = MinecraftClient.getInstance().getWindow();
//            window.setWindowedSize(1024, 768);
//
//        });
//    }
//}