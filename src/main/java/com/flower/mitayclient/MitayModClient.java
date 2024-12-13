package com.flower.mitayclient;

import com.flower.mitayclient.networking.ModMessage;
import com.flower.mitayclient.util.FontDrawer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.font.TextRenderer;

import java.io.IOException;

public class MitayModClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        ModMessage.registerS2CPackets();


        final TextRenderer[] tr = new TextRenderer[1];
        //run this event when the world is finished loading
        final boolean[] worldLoaded = {false};
        ClientTickEvents.END_CLIENT_TICK.register(
                client -> {
                    if (client.world != null && !worldLoaded[0] && client.getResourceManager() != null)
                    {
                        try
                        {

                            //getTr() is in FontDrawer class
                            tr[0] = FontDrawer.getTr();
                        } catch (IOException e)
                        {
                            throw new RuntimeException(e);
                        }
                        worldLoaded[0] = true;
                    }
                }
        );

        HudRenderCallback.EVENT.register((ctx, delta) -> {
//            if (tr[0] != null)
//                ctx.drawText(tr[0], "Hello 你好！《》!", 40, 40, 0x00FF00, false);
        });
    }
}
