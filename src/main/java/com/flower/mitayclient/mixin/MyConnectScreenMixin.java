package com.flower.mitayclient.mixin;

import com.flower.mitayclient.util.ModIdentifier;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ConnectScreen.class)
public class MyConnectScreenMixin extends Screen
{
    @Unique
    private static final Identifier BACKGROUND_TEXTURES = new ModIdentifier("textures/gui/background6.png");

    protected MyConnectScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("RETURN"), method = "render")
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci)
    {
        context.drawTexture(BACKGROUND_TEXTURES, 0,0,0,0,this.width,this.height,this.width,this.height);
    }


    @Inject(at = @At("HEAD"), method = "init", cancellable = true)
    public void init(CallbackInfo ci)
    {
        ci.cancel();

    }
}
