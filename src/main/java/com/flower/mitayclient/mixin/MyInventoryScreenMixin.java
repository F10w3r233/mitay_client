package com.flower.mitayclient.mixin;

import com.flower.mitayclient.util.ModIdentifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public class MyInventoryScreenMixin
{
    private static final Identifier INVENTORY = new ModIdentifier("textures/gui/sprites/hud/inventory.png");
    private static final Identifier INVENTORY2 = new ModIdentifier("textures/gui/sprites/hud/inventory2.png");
    int width = MinecraftClient.getInstance().getWindow().getScaledWidth();
    int height = MinecraftClient.getInstance().getWindow().getScaledHeight();
    public int backgroundWidth = 176;
    public int backgroundHeight = 166;
    MinecraftClient client = MinecraftClient.getInstance();
    @Inject(at = @At("HEAD"), method = "drawBackground", cancellable = true)
    public void drawBackground(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci)
    {
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
//        context.drawTexture(BACKGROUND_TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
//        drawEntity(context, i + 26, j + 8, i + 75, j + 78, 30, 0.0625F, this.mouseX, this.mouseY, this.client.player);
//        ci.cancel();

//        RenderSystem.setShaderColor(1,1,1,0.5F);

//        context.drawTexture(INVENTORY2, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
//        InventoryScreen.drawEntity(context, i + 26, j + 8, i + 75, j + 78, 30, 0.0625F, mouseX, mouseY, client.player);




    }
}
