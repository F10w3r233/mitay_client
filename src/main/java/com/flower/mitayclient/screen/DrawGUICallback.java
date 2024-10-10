package com.flower.mitayclient.screen;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.gui.DrawContext;

public class DrawGUICallback implements HudRenderCallback
{
    public static int ping;
    public static String entitlement;

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta)
    {

    }
}
