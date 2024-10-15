package com.flower.mitayclient.GUI.Button.WallpaperButton;

import net.minecraft.client.gui.widget.ClickableWidget;



import com.flower.mitayclient.util.ModIdentifier;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.PlayerSkinDrawer;
import net.minecraft.client.gui.screen.ButtonTextures;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.input.KeyCodes;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import static net.fabricmc.fabric.api.client.screen.v1.Screens.getTextRenderer;

@Environment(EnvType.CLIENT)
public abstract class WallpaperPressable extends ClickableWidget {



    private static final Identifier BUTTON = new ModIdentifier("textures/gui/sprites/widget/button_large.png");
    private static final Identifier BUTTON_FOCUS = new ModIdentifier("textures/gui/sprites/widget/button_large_hovered.png");




    Identifier wallpaper;

    public WallpaperPressable(int i, int j, int k, int l, Text text, Identifier wallpaper)
    {
        super(i, j, k, l, text);
        this.wallpaper = wallpaper;
    }
    public abstract void onPress();





    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        context.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();

        if(wallpaper != null)
        {
            context.drawTexture(wallpaper, this.getX(), this.getY(), 0,0,120,60,120,60);
        }

        if(!this.isHovered())
        {

        }else
        {

        }

        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = this.active ? 16843008 : 16777215;
        this.drawMessage(context, client.textRenderer, i | MathHelper.ceil(this.alpha * 255.0F) << 24);
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

