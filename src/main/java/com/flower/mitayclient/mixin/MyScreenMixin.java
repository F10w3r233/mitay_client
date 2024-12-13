package com.flower.mitayclient.mixin;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.Selectable;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static net.minecraft.client.gui.screen.Screen.OPTIONS_BACKGROUND_TEXTURE;

@Mixin(Screen.class)
public abstract class MyScreenMixin
{
    @Shadow @Final protected Text title;
    @Shadow @Final private List<Drawable> drawables;


    @Shadow protected abstract <T extends Element & Selectable> T addSelectableChild(T child);

    @Shadow public abstract Text getTitle();

    @Shadow public int width;

    @Shadow public int height;

    @Inject(at = @At("HEAD"), method = "renderInGameBackground",cancellable = true)
    public void noBackground(DrawContext context, CallbackInfo ci)
    {
        ci.cancel();
        if(!title.contains(Text.literal("place")) && !title.contains(Text.literal("Teleport_Player")) && !title.contains(Text.literal("PlayerInfo")))
        {
            context.fillGradient(0, 0, 10000, 10000, -1072689136, -804253680);
        }
    }

    @Inject(at = @At("HEAD"), method = "renderBackgroundTexture",cancellable = true)
    private void noDirt(DrawContext context, CallbackInfo ci)
    {
        ci.cancel();
        if(!title.contains(Text.literal("wallpaper")) && !title.contains(Text.literal("About")) && !title.contains(Text.literal("统计信息")))
        {
            context.setShaderColor(0.25F, 0.25F, 0.25F, 1.0F);
            context.drawTexture(OPTIONS_BACKGROUND_TEXTURE, 0, 0, 0, 0.0F, 0.0F, width, height, 32, 32);
            context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }




    @Inject(at = @At("RETURN"), method = "addDrawableChild",cancellable = true)
    public <T extends Element & Drawable & Selectable> void soutMessage(T drawableElement, CallbackInfoReturnable<T> cir)
    {

    }
}
