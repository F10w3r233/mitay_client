package com.flower.mitayclient.mixin;

import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin
{
    @Inject(method = "getWindowTitle", at = @At("HEAD"), cancellable = true)
    private void getWindowTitle(CallbackInfoReturnable<String> cir)
    {
        cir.setReturnValue("Mitay Minecraft Client 1.20.4 | By 13anx!aF10w3r");
    }


    @Inject(method = "openChatScreen", at = @At("HEAD"), cancellable = true)
    private void getWindowTitle(String text, CallbackInfo ci)
    {
//        ci.cancel();
    }
}
