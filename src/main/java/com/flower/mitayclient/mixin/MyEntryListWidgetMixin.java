package com.flower.mitayclient.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.EntryListWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.transformer.meta.MixinInner;

@Mixin(EntryListWidget.class)
public abstract class MyEntryListWidgetMixin extends ClickableWidget
{
    public MyEntryListWidgetMixin(int x, int y, int width, int height, Text message)
    {
        super(x, y, width, height, message);
    }

    @Shadow protected abstract void renderList(DrawContext context, int mouseX, int mouseY, float delta);

    @Shadow protected abstract void enableScissor(DrawContext context);

    @Shadow private boolean renderHeader;

    @Shadow public abstract int getRowLeft();

    @Shadow public abstract double getScrollAmount();

    @Shadow protected abstract void renderHeader(DrawContext context, int x, int y);

    @Shadow protected abstract void renderDecorations(DrawContext context, int mouseX, int mouseY);

    @Shadow @Final private static Identifier SCROLLER_TEXTURE;

    @Shadow protected abstract int getScrollbarPositionX();

    @Shadow public abstract int getMaxScroll();

    @Shadow protected abstract int getMaxPosition();

    @Shadow protected int headerHeight;

    @Shadow @Final protected int itemHeight;

    @Shadow protected abstract void setRenderHeader(boolean renderHeader, int headerHeight);

    @Inject(at = @At(value = "HEAD", target = "Lnet/minecraft/client/gui/DrawContext;drawTexture()V"), method = "renderWidget", cancellable = true)
    public void noDirt(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci)
    {
        ci.cancel();


        this.enableScissor(context);
        if (this.renderHeader)
        {
            int i = this.getRowLeft();
            int j = this.getY() + 4 - (int)this.getScrollAmount();
            renderHeader(context, i , j);
        }
        renderList(context, mouseX, mouseY, delta);

        context.disableScissor();
        int i = this.getMaxScroll();
        if (i > 0) {
            int j = this.getScrollbarPositionX();
            int k = (int)((float)(this.height * this.height) / (float)this.getMaxPosition());
            k = MathHelper.clamp(k, 32, this.height - 8);
            int l = (int)this.getScrollAmount() * (this.height - k) / i + this.getY();
            if (l < this.getY()) {
                l = this.getY();
            }

            context.fill(j, this.getY(), j + 6, this.getBottom(), -16777216);
            context.drawGuiTexture(SCROLLER_TEXTURE, j, l, 6, k);
        }

        this.renderDecorations(context, mouseX, mouseY);
    }

//    @Inject(at = @At("HEAD"), method = "getRowTop", cancellable = true)
//    public void heightMixin(int index, CallbackInfoReturnable<Integer> cir)
//    {
//        setRenderHeader(renderHeader, 160);
//        cir.setReturnValue(this.getY() + 4 - (int)this.getScrollAmount() + index * this.itemHeight + this.headerHeight);
//    }



}
