package com.flower.mitayclient.GUI.Button;

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
public abstract class SmallPressableWidget extends ClickableWidget
{

//    private static final ButtonTextures TEXTURES = new ButtonTextures(
//            new ModIdentifier("textures/gui/sprites/screen/button_small.png"), new ModIdentifier("textures/gui/sprites/screen/button_large.png"), new ModIdentifier("textures/gui/sprites/screen/button_small_pressed.png")
//    );




    private static final Identifier END = new ModIdentifier("textures/gui/sprites/hud/ender_eye.png");
    private static final Identifier NETHER = new ModIdentifier("textures/gui/sprites/hud/blaze_powder.png");
    private static final Identifier OVERWORLD = new ModIdentifier("textures/gui/sprites/hud/grass.png");
    private static final Identifier CREATIVE_WORLD = new ModIdentifier("textures/gui/sprites/hud/redstone.png");




    private static final Identifier BUTTON = new ModIdentifier("textures/gui/sprites/screen/button_small.png");
    private static final Identifier BUTTON_FOCUS = new ModIdentifier("textures/gui/sprites/screen/button_small_hovered.png");




    String iconName;

    public SmallPressableWidget(int i, int j, int k, int l, Text text, String iconName)
    {
        super(i, j, k, l, text);
        this.iconName = iconName;
    }
    public abstract void onPress();

    public static Identifier getIdentifier(String iconName)
    {
        if(iconName == null)
        {
            return null;
        }
        return switch (iconName) {
            case "end" -> END;
            case "overworld" -> OVERWORLD;
            case "nether" -> NETHER;
            case "creative" -> CREATIVE_WORLD;
            default -> null;
        };
    }




    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        context.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();



        if(!this.isHovered())
        {
            context.drawTexture(BUTTON, this.getX(), this.getY(), 0, 0,100, 22,100,22);
            if(getIdentifier(iconName) != null)
            {
                context.drawTexture(getIdentifier(iconName), this.getX()+8, this.getY()+4, 0, 0,14, 14,14,14);
            }
        }else
        {
            context.drawTexture(BUTTON_FOCUS, this.getX(), this.getY(), 0, 0,100, 22,100,22);
            if(getIdentifier(iconName) != null)
            {
                context.drawTexture(getIdentifier(iconName), this.getX()+8, this.getY()+4, 0, 0,14, 14,14,14);
            }
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

