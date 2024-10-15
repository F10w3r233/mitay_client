package com.flower.mitayclient.GUI.Screen;

import com.flower.mitayclient.GUI.Button.MenuButton.MenuButton;
import com.flower.mitayclient.GUI.Button.WallpaperButton.WallpaperButton;
import com.flower.mitayclient.util.ModIdentifier;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ParentElement;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class WallpaperScreen extends Screen implements ParentElement
{
    public static int wallpaperIndex = 3;
    public static boolean bl1 = true;
    public static boolean bl2 = false;
    public static final Text title = Text.literal("wallpaper");
    public WallpaperScreen()
    {
        super(title);
    }
    public static final Identifier Side = new ModIdentifier("textures/gui/sprites/screen/wallpaper_side.png");
    public static final Identifier Background = new ModIdentifier("textures/gui/white_background.png");

    //mc
    public static final Identifier Background1 = new ModIdentifier("textures/gui/background.png");
    public static final Identifier Background2 = new ModIdentifier("textures/gui/background2.png");
    public static final Identifier Background3 = new ModIdentifier("textures/gui/background3.png");
    public static final Identifier Background4 = new ModIdentifier("textures/gui/background4.png");
    public static final Identifier Background5 = new ModIdentifier("textures/gui/background5.png");
    public static final Identifier Background6 = new ModIdentifier("textures/gui/background6.png");
    public static final Identifier Background7 = new ModIdentifier("textures/gui/background7.png");
    public static final Identifier Background8 = new ModIdentifier("textures/gui/background8.png");

    //anime
    public static final Identifier Ani1 = new ModIdentifier("textures/gui/wallpaper/anime/1.png");
    public static final Identifier Ani2 = new ModIdentifier("textures/gui/wallpaper/anime/2.png");
    public static final Identifier Ani3 = new ModIdentifier("textures/gui/wallpaper/anime/3.png");
    public static final Identifier Ani4 = new ModIdentifier("textures/gui/wallpaper/anime/4.png");
    public static final Identifier Ani5 = new ModIdentifier("textures/gui/wallpaper/anime/5.png");
    public static final Identifier Ani6 = new ModIdentifier("textures/gui/wallpaper/anime/6.png");



    MenuButton mc = MenuButton.builder(Text.literal("MC"), button ->
    {
        if(!bl1)
        {
            bl1 = true;
            bl2 = false;
            init();
        }
    }).dimensions(8,50,162,27).build();
    MenuButton anime = MenuButton.builder(Text.literal("动漫"), button ->
    {
        if(!bl2)
        {
            bl1 = false;
            bl2 = true;
            init();
        }
    }).dimensions(8,50+27+15,162,27).build();


    WallpaperButton mc1 = WallpaperButton.builder(Text.literal(""), button ->
    {
        wallpaperIndex = 1;
    }).wallpaper(Background1).dimensions(200,50,100,50).build();
    WallpaperButton mc2 = WallpaperButton.builder(Text.literal(""), button ->
    {
        wallpaperIndex = 2;
    }).wallpaper(Background2).dimensions(350,50,100,50).build();
    WallpaperButton mc3 = WallpaperButton.builder(Text.literal(""), button ->
    {
        wallpaperIndex = 3;
    }).wallpaper(Background3).dimensions(500,50,100,50).build();
    WallpaperButton mc4 = WallpaperButton.builder(Text.literal(""), button ->
    {
        wallpaperIndex = 4;
    }).wallpaper(Background4).dimensions(650,50,100,50).build();
    WallpaperButton mc5 = WallpaperButton.builder(Text.literal(""), button ->
    {
        wallpaperIndex = 5;
    }).wallpaper(Background5).dimensions(800,50,100,50).build();
    WallpaperButton mc6 = WallpaperButton.builder(Text.literal(""), button ->
    {
        wallpaperIndex = 6;
    }).wallpaper(Background6).dimensions(200,140,100,50).build();
    WallpaperButton mc7 = WallpaperButton.builder(Text.literal(""), button ->
    {
        wallpaperIndex = 7;
    }).wallpaper(Background7).dimensions(350,140,100,50).build();
    WallpaperButton mc8 = WallpaperButton.builder(Text.literal(""), button ->
    {
        wallpaperIndex = 8;
    }).wallpaper(Background8).dimensions(500,140,100,50).build();


    WallpaperButton anime1 = WallpaperButton.builder(Text.literal(""), button ->
    {
        wallpaperIndex = 1;
    }).wallpaper(Ani1).dimensions(200,50,100,50).build();
    WallpaperButton anime2 = WallpaperButton.builder(Text.literal(""), button ->
    {
        wallpaperIndex = 2;
    }).wallpaper(Ani2).dimensions(350,50,100,50).build();
    WallpaperButton anime3 = WallpaperButton.builder(Text.literal(""), button ->
    {
        wallpaperIndex = 3;
    }).wallpaper(Ani3).dimensions(500,50,100,50).build();
    WallpaperButton anime4 = WallpaperButton.builder(Text.literal(""), button ->
    {
        wallpaperIndex = 4;
    }).wallpaper(Ani4).dimensions(650,50,100,50).build();
    WallpaperButton anime5 = WallpaperButton.builder(Text.literal(""), button ->
    {
        wallpaperIndex = 5;
    }).wallpaper(Ani5).dimensions(800,50,100,50).build();
    WallpaperButton anime6 = WallpaperButton.builder(Text.literal(""), button ->
    {
        wallpaperIndex = 6;
    }).wallpaper(Ani6).dimensions(200,140,100,50).build();
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta)
    {

        context.drawTexture(Background, 0,0,0,0,this.client.getWindow().getScaledWidth(), this.client.getWindow().getScaledHeight());
        context.drawTexture(Side, 0,0,0,0,180, this.client.getWindow().getScaledHeight(), 180 , this.client.getWindow().getScaledHeight());
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    protected void init()
    {
        RenderSystem.enableBlend();
        addDrawableChild(mc);
        addDrawableChild(anime);

        if(bl1 && !bl2)
        {
            remove(anime1);
            remove(anime2);
            remove(anime3);
            remove(anime4);
            remove(anime5);
            remove(anime6);

            addDrawableChild(mc1);
            addDrawableChild(mc2);
            addDrawableChild(mc3);
            addDrawableChild(mc4);
            addDrawableChild(mc5);
            addDrawableChild(mc6);
            addDrawableChild(mc7);
            addDrawableChild(mc8);
        }

        if(!bl1 && bl2)
        {
            remove(mc1);
            remove(mc2);
            remove(mc3);
            remove(mc4);
            remove(mc5);
            remove(mc6);
            remove(mc7);
            remove(mc8);

            addDrawableChild(anime1);
            addDrawableChild(anime2);
            addDrawableChild(anime3);
            addDrawableChild(anime4);
            addDrawableChild(anime5);
            addDrawableChild(anime6);
        }
        super.init();
    }
}
