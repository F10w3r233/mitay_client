package com.flower.mitayclient.GUI.Button.TeleportPlayer;


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

/**
 * A pressable widget has a press action. It is pressed when it is clicked. It is
 * also pressed when enter or space keys are pressed when it is selected.
 */
@Environment(EnvType.CLIENT)
public abstract class TeleportPlayerPressable extends ClickableWidget {



    private static final Identifier BUTTON = new ModIdentifier("textures/gui/sprites/widget/button_large.png");
    private static final Identifier BUTTON_FOCUS = new ModIdentifier("textures/gui/sprites/widget/button_large_hovered.png");




    String iconName;
    PlayerListEntry player;

    public TeleportPlayerPressable(int i, int j, int k, int l, Text text, PlayerListEntry player)
    {
        super(i, j, k, l, text);
        this.player = player;
    }
    public abstract void onPress();





    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {
        MinecraftClient client = MinecraftClient.getInstance();
        context.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();



        if(!this.isHovered())
        {
            context.drawTexture(BUTTON, this.getX(), this.getY(), 0, 0,160, 34,160,34);
            if(player != null)
            {
                PlayerSkinDrawer.draw(context, player.getSkinTextures(), getX()+5+5, getY()+5+3,16);
                if(player.getDisplayName() != null)
                    context.drawTextWithShadow(client.textRenderer, player.getDisplayName(), getX()+5+16+5+5+2, getY()+5+7, 14474460);
            }
        }else
        {
            context.drawTexture(BUTTON_FOCUS, this.getX(), this.getY(), 0, 0,160, 34,160,34);
            if(player != null)
            {
                PlayerSkinDrawer.draw(context, player.getSkinTextures(), getX()+5+5, getY()+5+3,16);
                if(player.getDisplayName() != null)
                    context.drawTextWithShadow(client.textRenderer, player.getDisplayName(), getX()+5+16+5+5+2, getY()+5+7, 14474460);
            }
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

