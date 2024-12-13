package com.flower.mitayclient.mixin;

import com.flower.mitayclient.GUI.Button.PlaceListButton.Small.SmallButtonWidget;
import com.flower.mitayclient.util.ModIdentifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.StatsScreen;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.Item;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StatsScreen.class)
public abstract class MyStatsScreenMixin extends Screen
{
    MinecraftClient client = MinecraftClient.getInstance();
    Identifier iconFrame = new ModIdentifier("textures/gui/sprites/hud/stat.png");
    @Shadow abstract void renderIcon(DrawContext context, int x, int y, Identifier texture);

    @Shadow public abstract void selectStatList(@Nullable AlwaysSelectedEntryListWidget<?> list);

//    @Shadow private StatsScreen.GeneralStatsListWidget generalStats;
//
//    @Shadow private StatsScreen.ItemStatsListWidget itemStats;
//
//    @Shadow private StatsScreen.EntityStatsListWidget mobStats;

    @Shadow @Final protected Screen parent;

    protected MyStatsScreenMixin(Text title)
    {
        super(title);
    }

    @Inject(at = @At("HEAD"), method = "renderBackground", cancellable = true)
    public void noBackground(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci)
    {
        ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    public void noBackground2(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci)
    {
        ci.cancel();
        super.render(context,mouseX, mouseY, delta);
    }

    @Inject(method = "renderStatItem", at = @At("HEAD"), cancellable = true)
    public void rewriteIcon(DrawContext context, int x, int y, Item item, CallbackInfo ci)
    {
        ci.cancel();
        this.renderIcon(context, x + 1, y + 1, iconFrame);
        context.drawItemWithoutEntity(item.getDefaultStack(), x + 2, y + 2);
    }

    @Inject(method = "renderIcon", at = @At("HEAD"), cancellable = true)
    public void rewriteRenderIcon(DrawContext context, int x, int y, Identifier texture, CallbackInfo ci)
    {
        ci.cancel();
        context.drawTexture(texture, x, y, 0,0, 18, 18,18,18);
    }


    @Inject(method = "createButtons", at = @At("HEAD"), cancellable = true)
    public void button(CallbackInfo ci)
    {
//        this.addDrawableChild(
//                SmallButtonWidget.builder(Text.translatable("stat.generalButton"), button -> this.selectStatList(this.generalStats))
//                        .dimensions(this.width / 2 - 120, this.height - 52, 80, 20).icon(null)
//                        .build()
//        );
//        SmallButtonWidget buttonWidget = this.addDrawableChild(
//                SmallButtonWidget.builder(Text.translatable("stat.itemsButton"), button -> this.selectStatList(this.itemStats))
//                        .dimensions(this.width / 2 - 40, this.height - 52, 80, 20).icon(null)
//                        .build()
//        );
//        SmallButtonWidget buttonWidget2 = this.addDrawableChild(
//                SmallButtonWidget.builder(Text.translatable("stat.mobsButton"), button -> this.selectStatList(this.mobStats))
//                        .dimensions(this.width / 2 + 40, this.height - 52, 80, 20).icon(null)
//                        .build()
//        );
        this.addDrawableChild(
                SmallButtonWidget.builder(ScreenTexts.DONE, button -> this.client.setScreen(this.parent)).icon(null).dimensions(this.width / 2 - 100, this.height - 28, 200, 20).build()
        );
    }
}

@Mixin(targets = "net.minecraft.client.gui.screen.StatsScreen$ItemStatsListWidget")
class MixinSlot
{
//    @ModifyArg(method = "renderHeader", at = @At(value = "INVOKE", target = "Lnet/mincraft/client/gui/screen/StatsScreen;renderIcon()V", index = 3))
//    public Identifier header()
//    {
//
//    }

    @ModifyVariable(method = "renderHeader", at = @At("STORE"), ordinal = 0)
    private Identifier injected(Identifier identifier) {
        return new ModIdentifier("textures/gui/sprites/hud/stat.png");
    }
}

