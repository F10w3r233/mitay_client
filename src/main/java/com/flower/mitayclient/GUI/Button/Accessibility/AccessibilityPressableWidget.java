package com.flower.mitayclient.GUI.Button.Accessibility;

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

/**
 * A pressable widget has a press action. It is pressed when it is clicked. It is
 * also pressed when enter or space keys are pressed when it is selected.
 */
@Environment(EnvType.CLIENT)
public abstract class AccessibilityPressableWidget extends ClickableWidget
{


    int size = 15;
    String name;
    private static final Identifier LANGUAGE = new ModIdentifier("textures/gui/sprites/hud/accessibility/language.png");
    private static final Identifier ACCESSIBILITY = new ModIdentifier("textures/gui/sprites/hud/accessibility/accessibility.png");
    private static final Identifier WALLPAPER = new ModIdentifier("textures/gui/sprites/hud/accessibility/wallpaper.png");
    private static final Identifier ABOUT = new ModIdentifier("textures/gui/sprites/hud/accessibility/about.png");
    Identifier identifier;



    String type;

    public AccessibilityPressableWidget(int i, int j, int k, int l, Text text, String type)
    {
        super(i, j, k, l, text);
        this.type = type;
    }
    public abstract void onPress();



    float transparency = 0;

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        context.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();



        switch (type)
        {
            case "language" ->
            {
                identifier = LANGUAGE;
                name = "语言";
            }
            case "accessibility" ->
            {
                identifier = ACCESSIBILITY;
                name = "通用";
            }
            case "wallpaper" ->
            {
                identifier = WALLPAPER;
                name = "壁纸";
            }
            case "about" ->
            {
                identifier = ABOUT;
                name = "关于";
            }
        }

        if(hovered)
        {
            if(size <= 17)
                size++;
            context.drawTexture(identifier, this.getX(), this.getY(), 0, 0, size,size,size,size);
            if(transparency < 1)
            {
                transparency += 0.1f;
            }
            context.setShaderColor(1f,1f,1f,transparency);
            context.drawText(MinecraftClient.getInstance().textRenderer, name, this.getX()+1, this.getY()-11, 16777215,false);
        }else
        {
            if(size >15)
                size--;
            context.drawTexture(identifier, this.getX(), this.getY(), 0, 0, size,size,size,size);
            if(transparency > 0)
            {
                transparency -= 0.1f;
            }
            context.setShaderColor(1f,1f,1f,transparency);
            context.drawText(MinecraftClient.getInstance().textRenderer, name, this.getX()+1, this.getY()-11, 16777215,false);

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
        } else if (KeyCodes.isToggle(keyCode))
        {
            this.playDownSound(MinecraftClient.getInstance().getSoundManager());
            this.onPress();
            return true;
        } else {
            return false;
        }
    }
}

