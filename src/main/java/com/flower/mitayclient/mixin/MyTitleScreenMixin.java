package com.flower.mitayclient.mixin;

//import com.flower.mitayclient.screen.CustomTitleScreen;
import com.flower.mitayclient.GUI.Button.Accessibility.AccessibilityButton;
import com.flower.mitayclient.GUI.Button.MenuButton.MenuButton;
import com.flower.mitayclient.GUI.Screen.AboutScreen;
import com.flower.mitayclient.GUI.Screen.WallpaperScreen;
import com.flower.mitayclient.util.ModIdentifier;
import com.flower.mitayclient.util.SnowAnimation;
import com.mojang.authlib.minecraft.BanDetails;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.*;
import net.minecraft.client.gui.screen.option.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screen.option.LanguageOptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.option.ServerList;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

import static com.flower.mitayclient.GUI.Screen.WallpaperScreen.*;

@Mixin(TitleScreen.class)
public abstract class MyTitleScreenMixin extends Screen
{
    private long backgroundFadeStart;

    @Shadow protected abstract void initWidgetsNormal(int y, int spacingY);

    @Shadow protected abstract void switchToRealms();

    private static final Identifier BACKGROUND_TEXTURES = new ModIdentifier("textures/gui/background3.png");
    private static final Identifier[] SNOWS =
            {
                    new ModIdentifier("textures/gui/sprites/hud/snow/white_snow.png"),
                    new ModIdentifier("textures/gui/sprites/hud/snow/red_snow.png"),
                    new ModIdentifier("textures/gui/sprites/hud/snow/yellow_snow.png")
            };

    private static final Identifier MITAY_LOGO = new ModIdentifier("textures/gui/logo.png");

    private static final CubeMapRenderer PANORAMA_CUBE_MAP = new CubeMapRenderer(new Identifier("textures/gui/title/background/panorama"));

    private static final Text COPYRIGHT = Text.translatable("title.credits");
    private final boolean doBackgroundFade;
    private final RotatingCubeMapRenderer backgroundRenderer = new RotatingCubeMapRenderer(PANORAMA_CUBE_MAP);
    protected MyTitleScreenMixin(Text title, boolean doBackgroundFade) {
        super(title);
        this.doBackgroundFade = doBackgroundFade;
    }
    ServerList serverList = new ServerList(MinecraftClient.getInstance());
    ServerInfo server = new ServerInfo("name", "mitay.playmc.fun", ServerInfo.ServerType.LAN);

    public void connect() {
        this.connect(new ServerInfo("name", "mitay.playmc.fun", ServerInfo.ServerType.LAN));
    }
    public void connect(ServerInfo entry)
    {
        ConnectScreen.connect(this, this.client, ServerAddress.parse(entry.address), entry, false);
    }






    @Inject(at = @At("HEAD"), method = "initWidgetsNormal", cancellable = true)
    private void addCustomButton(int y, int spacingY, CallbackInfo ci)
    {
        ci.cancel();






        this.addDrawableChild(MenuButton.builder(Text.literal("单人游戏"), mybotton ->                                                        //直连服务器按钮
        {
            this.client.setScreen(new SelectWorldScreen(this));
        }).dimensions(50, 140, 162, 27).build());

        this.addDrawableChild(MenuButton.builder(Text.literal("直连到服务器"), mybotton ->                                                        //直连服务器按钮
        {
            this.connect();
//            client.setScreen(new MultiplayerScreen(this));
        }).dimensions(50, 180, 162, 27).build());
        this.addDrawableChild(                                                                                    //自定义按钮
                MenuButton.builder(Text.translatable("退出游戏"), button ->
                            this.client.scheduleStop()


        ).dimensions(50, 220, 162, 27)
                .build());





//        TextIconButtonWidget textIconButtonWidget = this.addDrawableChild(AccessibilityOnboardingButtons.createLanguageButton(20, (button) -> {
//            this.client.setScreen(new LanguageOptionsScreen(this, this.client.options, this.client.getLanguageManager()));
//        }, true));
//        textIconButtonWidget.setPosition(0, 0);
//
//        TextIconButtonWidget textIconButtonWidget2 = this.addDrawableChild(AccessibilityOnboardingButtons.createAccessibilityButton(20, (button) -> {
//            this.client.setScreen(new AccessibilityOptionsScreen(this, this.client.options));
//        }, true));
//        textIconButtonWidget2.setPosition(20, 20);

        this.addDrawableChild(                                                                                    //自定义按钮
                AccessibilityButton.builder(Text.translatable(""), button ->
                        client.setScreen(new LanguageOptionsScreen(this, this.client.options, this.client.getLanguageManager()))
                        ).dimensions(client.getWindow().getScaledWidth()-19, client.getWindow().getScaledHeight()-19,15,15).type("language")
                        .build());

        this.addDrawableChild(                                                                                    //自定义按钮
                AccessibilityButton.builder(Text.translatable(""), button ->
                                client.setScreen(new AccessibilityOptionsScreen(this, this.client.options))
                        ).dimensions(client.getWindow().getScaledWidth()-41, client.getWindow().getScaledHeight()-18,15,15)
                        .type("accessibility")
                        .build());

        this.addDrawableChild(                                                                                    //自定义按钮
                AccessibilityButton.builder(Text.translatable(""), button ->
                                client.setScreen(new WallpaperScreen())
                        ).dimensions(client.getWindow().getScaledWidth()-62, client.getWindow().getScaledHeight()-19,15,15)
                        .type("wallpaper")
                        .build());

        this.addDrawableChild(                                                                                    //自定义按钮
                AccessibilityButton.builder(Text.translatable(""), button ->
                                client.setScreen(new AboutScreen())
                        ).dimensions(client.getWindow().getScaledWidth()-83, client.getWindow().getScaledHeight()-18,15,15)
                        .type("about")
                        .build());

    }

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    private void rewriteRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci)
    {
        ci.cancel();


        if (this.backgroundFadeStart == 0L && this.doBackgroundFade) {
            this.backgroundFadeStart = Util.getMeasuringTimeMs();
        }

        float f = this.doBackgroundFade ? (float)(Util.getMeasuringTimeMs() - this.backgroundFadeStart) / 1000.0F : 1.0F;
        this.backgroundRenderer.render(delta, MathHelper.clamp(f, 0.0F, 1.0F));
        RenderSystem.enableBlend();
        float g = this.doBackgroundFade ? MathHelper.clamp(f - 1.0F, 0.0F, 1.0F) : 1.0F;
        int i = MathHelper.ceil(g * 255.0F) << 24;
        if ((i & -67108864) != 0)
        {
            String string = "Minecraft " + SharedConstants.getGameVersion().getName();

            if (MinecraftClient.getModStatus().isModded()) {
                string = string +  " | §6MitayMinecraft Server Edition | by 13anx!aF10w3r";
            }

            context.drawTextWithShadow(this.textRenderer, string, 2, this.height - 10, 16777215 | i);

            //绘制背景
            Identifier background = Background3;
            if(bl1 && !bl2)
            {
                switch (wallpaperIndex)
                {
                    case 1 -> background = Background1;
                    case 2 -> background = Background2;
                    case 3 -> background = Background3;
                    case 4 -> background = Background4;
                    case 5 -> background = Background5;
                    case 6 -> background = Background6;
                    case 7 -> background = Background7;
                    case 8 -> background = Background8;
                }
            }
            if(bl2 && !bl1)
            {
                switch (wallpaperIndex)
                {
                    case 1 -> background = Ani1;
                    case 2 -> background = Ani2;
                    case 3 -> background = Ani3;
                    case 4 -> background = Ani4;
                    case 5 -> background = Ani5;
                    case 6 -> background = Ani6;
                }
            }
            context.drawTexture(background, 0,0,0,0,this.width,this.height,this.width,this.height);
            //绘制mitayLogo
            context.drawTexture(MITAY_LOGO, 10,-15,0,0,260,160,260,160);
            MinecraftClient client1 = MinecraftClient.getInstance();
//            PlayerSkinDrawer.draw(context,client1.player.networkHandler.getPlayerListEntry(client.player.getUuid()).getSkinTextures(), 1 , 1 , 16);
//            context.drawTextWithShadow(this.textRenderer, "单人游戏", 81,145, 16843008 | i);
//            context.drawTextWithShadow(this.textRenderer, "直连到：MitayMinecraft", 40,186, 16843008 | i);


//            context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, Text.literal(String.valueOf(server.online)), 2, 28,111111);

            for (Element element : this.children())
            {
                if (element instanceof ClickableWidget) {
                    ((ClickableWidget)element).setAlpha(g);
                }
            }
            super.render(context, mouseX, mouseY, delta);
        }
        SnowAnimation.INSTANCE = new SnowAnimation(new Random());
        SnowAnimation.INSTANCE.tick(this.width, this.height, SNOWS);
    }


    @Inject(at = @At("HEAD"), method = "init", cancellable = true)
    private void rewriteInit(CallbackInfo ci)
    {
        ci.cancel();
        int i = this.textRenderer.getWidth(COPYRIGHT);
        int j = this.width - i - 2;
        int k = 24;
        int l = this.height / 4 + 48;
        this.initWidgetsNormal(l ,24);
    }

    @Nullable
    private Text getMultiplayerDisabledText() {
        if (this.client.isMultiplayerEnabled()) {
            return null;
        } else if (this.client.isUsernameBanned()) {
            return Text.translatable("title.multiplayer.disabled.banned.name");
        } else {
            BanDetails banDetails = this.client.getMultiplayerBanDetails();
            if (banDetails != null) {
                return banDetails.expires() != null
                        ? Text.translatable("title.multiplayer.disabled.banned.temporary")
                        : Text.translatable("title.multiplayer.disabled.banned.permanent");
            } else {
                return Text.translatable("title.multiplayer.disabled");
            }
        }
    }
}
