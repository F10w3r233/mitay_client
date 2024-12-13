package com.flower.mitayclient.GUI.Screen;

import com.flower.mitayclient.util.ModIdentifier;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class AboutScreen extends Screen
{

    public AboutScreen()
    {
        super(Text.literal("About"));
    }
    static DrawContext context2 = new DrawContext(MinecraftClient.getInstance(), MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers());



    private static final Identifier MATRIX_LARGE = new ModIdentifier("textures/gui/sprites/screen/matrix_large.png");
    public static final Identifier Background = new ModIdentifier("textures/gui/white_background.png");
    private static final Identifier MITAY_LOGO = new ModIdentifier("textures/gui/sprites/screen/logo.png");
    private static final Identifier MITAY_UNIVERSE = new ModIdentifier("textures/gui/icon4x.png");



    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta)
    {
        RenderSystem.enableBlend();
        context.drawTexture(Background, 0,0,0,0,this.client.getWindow().getScaledWidth(), this.client.getWindow().getScaledHeight());
//        context.drawTexture(MITAY_UNIVERSE,200,80,0,0,260,160,260,160);
        context.drawTexture(MITAY_LOGO,(this.client.getWindow().getScaledWidth()-967/4)/2,0,0,0,967/4,489/4,967/4,489/4);

        super.render(context, mouseX, mouseY, delta);
    }
}
