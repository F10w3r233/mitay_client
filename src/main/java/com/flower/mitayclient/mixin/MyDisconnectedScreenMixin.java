package com.flower.mitayclient.mixin;

import com.flower.mitayclient.util.ModIdentifier;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DisconnectedScreen.class)
public class MyDisconnectedScreenMixin
{
    private static final Identifier BACKGROUND_TEXTURES = new ModIdentifier("textures/gui/background2.png");
    @Inject(at = @At("RETURN"), method = "init")
    public void init(CallbackInfo ci)
    {

    }
}
