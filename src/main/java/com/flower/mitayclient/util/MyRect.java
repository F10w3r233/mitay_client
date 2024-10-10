package com.flower.mitayclient.util;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class MyRect extends Screen implements Drawable
{
    DrawContext context = new DrawContext(MinecraftClient.getInstance(), MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers());
    Identifier texture;
    Screen screen;
    public MyRect(Screen screen)
    {
        super(Text.literal(""));
        this.screen = screen;
    }
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta)
    {

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
