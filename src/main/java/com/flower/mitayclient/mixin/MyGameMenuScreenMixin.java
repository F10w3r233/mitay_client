package com.flower.mitayclient.mixin;


import com.flower.mitayclient.GUI.Button.MenuButton.MenuButton;
import com.flower.mitayclient.GUI.Screen.MyStatScreen;
import com.flower.mitayclient.util.ModIdentifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public class MyGameMenuScreenMixin extends Screen
{
    private static final Identifier BACKGROUND_TEXTURES = new ModIdentifier("textures/gui/background.png");
    private static final Identifier MITAY_UNIVERSE = new ModIdentifier("textures/gui/icon4x.png");
    MinecraftClient client = MinecraftClient.getInstance();

    protected MyGameMenuScreenMixin(Text title) {
        super(title);
    }


    @Inject(at = @At("HEAD"), method = "initWidgets", cancellable = true)
    public void initWidgets(CallbackInfo ci)
    {
        ci.cancel();
        this.addDrawableChild(                                                                                    //自定义按钮
                MenuButton.builder(Text.translatable("退出游戏"), button ->
                {
                    boolean bl = this.client.isInSingleplayer();
                    if(bl)
                    {
                        this.client.world.disconnect();
                        client.disconnect(new MessageScreen(Text.literal("保存世界中...")));
                        client.setScreen(new TitleScreen());
                    }else
                    {
                        client.disconnect();
                        client.setScreen(new TitleScreen());

                    }
                })
                        .dimensions(80, this.client.getWindow().getScaledHeight()/2-40, 150, 20)
                        .build()
        );
        this.addDrawableChild(                                                                                    //自定义按钮
                MenuButton.builder(Text.translatable("选项"), button ->
                    this.client.setScreen(new OptionsScreen(this, this.client.options)))
                        .dimensions(80, this.client.getWindow().getScaledHeight()/2-80, 150, 20)
                        .build()
        );

        this.addDrawableChild(                                                                                    //自定义按钮
                MenuButton.builder(Text.translatable("返回游戏"), button ->
                {
                    this.client.setScreen(null);
                    this.client.mouse.lockCursor();
                })
                        .dimensions(80, this.client.getWindow().getScaledHeight()/2-120, 150, 20)
                        .build()
        );

        this.addDrawableChild(
                MenuButton.builder(Text.literal("统计信息"),button ->
                        this.client.setScreen(new MyStatScreen(this, this.client.player.getStatHandler())))
                        .dimensions(80, this.client.getWindow().getScaledHeight()/2-160,150,20)
                        .build()
        );
    }

    @Inject(at = @At("RETURN"), method = "renderBackground",cancellable = true)
    public void renderMitayLogo(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci)
    {
        ci.cancel();
        //
        context.drawTexture(MITAY_UNIVERSE, this.client.getWindow().getScaledWidth()-420, 20, 0,0,420,202,420,202);
    }
}
