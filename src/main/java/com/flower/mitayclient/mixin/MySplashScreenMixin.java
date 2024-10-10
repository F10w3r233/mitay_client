package com.flower.mitayclient.mixin;

import com.flower.mitayclient.util.ModIdentifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SplashOverlay.class)
public class MySplashScreenMixin
{
    MinecraftClient client = MinecraftClient.getInstance();
    private static final Identifier LOGO = new ModIdentifier("textures/gui/icon4x.png");
    private static final Identifier RED = new ModIdentifier("textures/gui/red.png");

    @Inject(at = @At("RETURN"), method = "render")
    public void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci)
    {
//        if(client.getWindow().getScaledHeight() > 400)
//        {
//            context.drawTexture(RED, 0,0,0,0,2000,400,2000,400);
//            context.drawTexture(LOGO ,(client.getWindow().getScaledWidth()/2)-420,0,0,0,840,404,840,404);
//
//        }else if(client.getWindow().getScaledHeight() < 400)
//        {
//            context.drawTexture(RED, 0,0,0,0,2000,180,2000,180);
//            context.drawTexture(LOGO ,(client.getWindow().getScaledWidth()/2)-210,0,0,0,420,202,420,202);
//
//        }

    }
}
