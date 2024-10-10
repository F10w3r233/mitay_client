package com.flower.mitayclient.GUI.Button.MenuButton;

import com.flower.mitayclient.util.ModIdentifier;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.input.KeyCodes;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;


@Environment(EnvType.CLIENT)
public abstract class MenuPressableWidget extends ClickableWidget {
    private static final Identifier BUTTON = new ModIdentifier("textures/gui/sprites/widget/button_large.png");
    private static final Identifier BUTTON_FOCUS = new ModIdentifier("textures/gui/sprites/widget/button_large_hovered.png");




    public MenuPressableWidget(int i, int j, int k, int l, Text text)
    {
        super(i, j, k, l, text);
    }
    public abstract void onPress();




    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        context.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();



        if(!this.isHovered())
        {
            context.drawTexture(BUTTON, this.getX(), this.getY(), 0, 0,162, 27,162,27);
        }else
        {
            context.drawTexture(BUTTON_FOCUS, this.getX(), this.getY(), 0, 0,162, 27,162,27);
        }

        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = this.active ? 16843008 : 16777215;
        this.drawMessage(context, minecraftClient.textRenderer, i | MathHelper.ceil(this.alpha * 255.0F) << 24);
    }

    public void drawMessage(DrawContext context, TextRenderer textRenderer, int color) {
        this.drawScrollableText(context, textRenderer, 2, color);
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.onPress();
    }


    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!this.active || !this.visible) {
            return false;
        } else if (KeyCodes.isToggle(keyCode)) {
            this.playDownSound(MinecraftClient.getInstance().getSoundManager());
            this.onPress();
            return true;
        } else {
            return false;
        }
    }
}

