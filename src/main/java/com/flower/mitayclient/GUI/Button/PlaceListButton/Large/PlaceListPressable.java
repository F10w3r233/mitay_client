package com.flower.mitayclient.GUI.Button.PlaceListButton.Large;

import com.flower.mitayclient.util.ModIdentifier;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ButtonTextures;
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
public abstract class PlaceListPressable extends ClickableWidget {


        //end
    private static final Identifier ENDER_MAN_FARM = new ModIdentifier("textures/gui/sprites/hud/places/ender_pearl.png");


        //overworld
    private static final Identifier EXCHANGE = new ModIdentifier("textures/gui/sprites/hud/places/emerald.png");
    private static final Identifier HOME = new ModIdentifier("textures/gui/sprites/hud/places/birch_planks.png");


    private static final Identifier MOB_TOWER_MAIN = new ModIdentifier("textures/gui/sprites/hud/places/slime_ball.png");
    private static final Identifier MOB_TOWER_RESOURCE = new ModIdentifier("textures/gui/sprites/hud/places/chest.png");
    private static final Identifier MOB_TOWER_AFK = new ModIdentifier("textures/gui/sprites/hud/places/afk.png");

    private static final Identifier IRON_FARM = new ModIdentifier("textures/gui/sprites/hud/places/iron_ingot.png");



    private static final Identifier END_PORTAL = new ModIdentifier("textures/gui/sprites/hud/places/end_portal_frame.png");
    private static final Identifier END_MAINLAND = new ModIdentifier("textures/gui/sprites/hud/places/end_stone.png");


        //creative world
    private static final Identifier CREATIVE_WORLD = new ModIdentifier("textures/gui/sprites/hud/places/redstone.png");




    private static final Identifier BUTTON = new ModIdentifier("textures/gui/sprites/screen/button_large.png");
    private static final Identifier BUTTON_FOCUS = new ModIdentifier("textures/gui/sprites/screen/button_large_hovered.png");




    String iconName;

    public PlaceListPressable(int i, int j, int k, int l, Text text, String iconName)
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
            case "ender_man_farm" -> ENDER_MAN_FARM;
            case "exchange" -> EXCHANGE;
            case "home" -> HOME;
            case "creative" -> CREATIVE_WORLD;
            case "mob_main" -> MOB_TOWER_MAIN;
            case "mob_resource" -> MOB_TOWER_RESOURCE;
            case "mob_afk" -> MOB_TOWER_AFK;
            case "end_mainland" -> END_MAINLAND;
            case "end_portal" -> END_PORTAL;
            case "iron" -> IRON_FARM;

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
            context.drawTexture(BUTTON, this.getX(), this.getY(), 0, 0,210, 30,210,30);
            if(getIdentifier(iconName) != null)
            {
                context.drawTexture(getIdentifier(iconName), this.getX()+10, this.getY()+5, 0, 0,20, 20,20,20);
            }
        }else
        {
            context.drawTexture(BUTTON_FOCUS, this.getX(), this.getY(), 0, 0,210, 30,210,30);
            if(getIdentifier(iconName) != null)
            {
                context.drawTexture(getIdentifier(iconName), this.getX()+10, this.getY()+5, 0, 0,20, 20,20,20);
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

