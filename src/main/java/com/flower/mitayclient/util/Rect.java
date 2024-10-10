package com.flower.mitayclient.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class Rect
{
    DrawContext context = new DrawContext(MinecraftClient.getInstance(), MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers());
    Identifier texture;
    public Rect(DrawContext context, Identifier texture)
    {
        this.context = context;
        this.texture = texture;
    }

    public void pop(int x, int y, int width, int height, boolean draggable)
    {
        int m = 0;
        if (m < x)
        {
            m++;
        }
        context.drawTexture(texture, 0+m, y, 0,0,width, height, width, height);
    }
}
