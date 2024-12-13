package com.flower.mitayclient.GUI.Screen;

import com.flower.mitayclient.GUI.Button.PlaceListButton.Large.PlaceListButton;
import com.flower.mitayclient.util.ModIdentifier;
import com.flower.mitayclient.GUI.Button.PlaceListButton.Small.SmallButtonWidget;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screen.Screen;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.text.*;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class PlaceListScreen extends Screen implements ParentElement {


    static DrawContext context2 = new DrawContext(MinecraftClient.getInstance(), MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers());
    MinecraftClient client = MinecraftClient.getInstance();




    private static final Identifier MATRIX_LARGE = new ModIdentifier("textures/gui/sprites/screen/matrix_large.png");
    private static final Identifier MATRIX_SIDE = new ModIdentifier("textures/gui/sprites/screen/matrix_side.png");
    private static final Identifier SELECTED = new ModIdentifier("textures/gui/sprites/screen/selected.png");


    private static final Identifier PLACES = new ModIdentifier("textures/gui/sprites/hud/places/places.png");




    double initX = 0;
    double initY = 0;
    double posX;
    double posY;
    double x;
    double y;

    boolean canInit = false;
    boolean bl1 = false;
    boolean bl2 = false;
    boolean bl3;
    boolean bl4;

    boolean flag1;
    boolean flag2;

    public static final Text title = Text.literal("place");
    public PlaceListScreen()
    {
        super(title);
    }



    public boolean isMouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY)
    {
        if(super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY))
        {
            return true;
        }else if(this.mouseClicked(mouseX, mouseY, button) && this.isMouseOverElement(x,x+60,y,y+60,mouseX,mouseY))
        {
            posX += deltaX;
            this.x = posX + this.initX;
            posY += deltaY;
            this.y = posY + this.initY;
            return true;
        }else return false;
    }

    public boolean isMouseOverElement(double x1, double x2, double y1, double y2, double mouseX, double mouseY)
    {
        if(mouseX > x1 && mouseX < x2 && mouseY > y1 && mouseY < y2)
        {
            return true;
        }else return false;
    }

    public static void sendChatCommand(String msg)
    {
        MinecraftClient client = MinecraftClient.getInstance();
        ClientPlayNetworkHandler handler = client.getNetworkHandler();

        if(handler == null)
            return;
        handler.sendChatCommand(msg);
    }












    //主世界
    PlaceListButton exchange_button = PlaceListButton.builder(Text.literal("村民交易所"), button ->
    {
        sendChatCommand("tpplus overworld 116 72 119");
        client.setScreen(null);

    }).icon("exchange").dimensions((this.client.getWindow().getScaledWidth()-382)/2+7+115+5+2,(this.client.getWindow().getScaledHeight()-292)/2+10, 210, 30).build();
    PlaceListButton home_button = PlaceListButton.builder(Text.literal("大本营"), button ->
    {
        sendChatCommand("tpplus overworld 13 94 8");
        client.setScreen(null);

    }).icon("home").dimensions((this.client.getWindow().getScaledWidth()-382)/2+7+115+5+2,(this.client.getWindow().getScaledHeight()-292)/2+10+30, 210, 30).build();
    PlaceListButton mob_tower_main_button = PlaceListButton.builder(Text.literal("沼泽刷怪塔"), button ->
    {
        flag1 = true;
        bl1 = false;
        init();
    }).icon("mob_main").dimensions((this.client.getWindow().getScaledWidth()-382)/2+7+115+5+2,(this.client.getWindow().getScaledHeight()-292)/2+10+30+30, 210, 30).build();
    PlaceListButton iron_farm_button = PlaceListButton.builder(Text.literal("刷铁机"), button ->
    {
        sendChatCommand("tpplus overworld 184 69 95");
        client.setScreen(null);
    }).icon("iron").dimensions((this.client.getWindow().getScaledWidth()-382)/2+7+115+5+2,(this.client.getWindow().getScaledHeight()-292)/2+10+30+30+30, 210, 30).build();


    PlaceListButton mob_tower_resouce_button = PlaceListButton.builder(Text.literal("资源点"), button ->
    {
        sendChatCommand("tpplus overworld -514 65 -445");
        client.setScreen(null);
    }).icon("mob_resource").dimensions((this.client.getWindow().getScaledWidth()-382)/2+7+115+5+2,(this.client.getWindow().getScaledHeight()-292)/2+10, 210, 30).build();
    PlaceListButton mob_tower_afk_button = PlaceListButton.builder(Text.literal("挂机点"), button ->
    {
        sendChatCommand("tpplus overworld -545.5 184 -429.5");
        client.setScreen(null);
    }).icon("mob_afk").dimensions((this.client.getWindow().getScaledWidth()-382)/2+7+115+5+2,(this.client.getWindow().getScaledHeight()-292)/2+10+30, 210, 30).build();




    //末地

    PlaceListButton end_portal_button = PlaceListButton.builder(Text.literal("主世界末地传送门"), button ->
    {
        sendChatCommand("tpplus overworld 724.4 -37 -1716.5");
        client.setScreen(null);

    }).icon("end_portal").dimensions((this.client.getWindow().getScaledWidth()-382)/2+7+115+5+2,(this.client.getWindow().getScaledHeight()-292)/2+10, 210, 30).build();
    PlaceListButton end_mainland_button = PlaceListButton.builder(Text.literal("末地主岛"), button ->
    {
        sendChatCommand("tpplus end 5 64 0");
        client.setScreen(null);

    }).icon("end_mainland").dimensions((this.client.getWindow().getScaledWidth()-382)/2+7+115+5+2,(this.client.getWindow().getScaledHeight()-292)/2+10+30, 210, 30).build();
    PlaceListButton ender_man_farm_button = PlaceListButton.builder(Text.literal("小黑塔"), button ->
    {
        sendChatCommand("tpplus end -192.9 1.2 20.5");
        client.setScreen(null);

    }).icon("ender_man_farm").dimensions((this.client.getWindow().getScaledWidth()-382)/2+7+115+5+2,(this.client.getWindow().getScaledHeight()-292)/2+10+30+30, 210, 30).build();



    //创造世界
    PlaceListButton creative_world_button = PlaceListButton.builder(Text.literal("创造世界"), button ->
    {
        sendChatCommand("goto");
        client.setScreen(null);

    }).icon("creative").dimensions((this.client.getWindow().getScaledWidth()-382)/2+7+115+5+2,(this.client.getWindow().getScaledHeight()-292)/2+10, 210, 30).build();






    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta)
    {
        RenderSystem.enableBlend();
        context2.drawTexture(MATRIX_LARGE, (this.client.getWindow().getScaledWidth()-382)/2-14,(this.client.getWindow().getScaledHeight()-292)/2-14,0,0,382,292,382,292);
        context2.drawTexture(MATRIX_SIDE, (this.client.getWindow().getScaledWidth()-382)/2,(this.client.getWindow().getScaledHeight()-292)/2,0,0,115,263,115,263);
//        {
//            init();
//            canInit = false;
//            System.out.println(canInit);
//        }

        context.drawTexture(PLACES, (this.client.getWindow().getScaledWidth()-382)/2+40, (this.client.getWindow().getScaledHeight()-292)/2+8, 0,0,26,13,26,13);
        if(canInit)
        {
            init();
            canInit = false;
        }

        if(bl1)
        {
            context2.drawTexture(SELECTED, (this.client.getWindow().getScaledWidth()-382)/2+1, (this.client.getWindow().getScaledHeight()-292)/2+25+6, 0,0,3,7,3,7);
        }else if(bl2)
        {
            context2.drawTexture(SELECTED, (this.client.getWindow().getScaledWidth()-382)/2+1, (this.client.getWindow().getScaledHeight()-292)/2+25+22+6, 0,0,3,7,3,7);
        }else if(bl3)
        {
            context2.drawTexture(SELECTED, (this.client.getWindow().getScaledWidth()-382)/2+1, (this.client.getWindow().getScaledHeight()-292)/2+25+22+22+6, 0,0,3,7,3,7);
        }else if(bl4)
        {
            context2.drawTexture(SELECTED, (this.client.getWindow().getScaledWidth()-382)/2+1, (this.client.getWindow().getScaledHeight()-292)/2+25+22+22+22+6, 0,0,3,7,3,7);
        }


        super.render(context, mouseX, mouseY, delta);
    }


    @Override
    public void init()
    {
        if(flag1 == true)
        {
            remove(home_button);
            remove(exchange_button);
            remove(mob_tower_main_button);
            remove(iron_farm_button);


            addDrawableChild(mob_tower_resouce_button);
            addDrawableChild(mob_tower_afk_button);

            flag1 = false;
        }else
        {
            remove(mob_tower_resouce_button);
            remove(mob_tower_afk_button);
        }




        addDrawableChild(SmallButtonWidget.builder(Text.literal("主世界"), button ->
        {

            bl1 = true;
            bl2 = false;
            bl3 = false;
            bl4 = false;
            init();
        }).icon("overworld").dimensions((this.client.getWindow().getScaledWidth()-382)/2+7, (this.client.getWindow().getScaledHeight()-292)/2+25, 100, 22).build());


        addDrawableChild(SmallButtonWidget.builder(Text.literal("地狱"), button ->
        {

            bl1 = false;
            bl2 = true;
            bl3 = false;
            bl4 = false;
            init();
        }).icon("nether").dimensions((this.client.getWindow().getScaledWidth()-382)/2+7, (this.client.getWindow().getScaledHeight()-292)/2+25+22, 100, 22).build());

        addDrawableChild(SmallButtonWidget.builder(Text.literal("末地"), button ->
        {

            bl1 = false;
            bl2 = false;
            bl3 = true;
            bl4 = false;
            init();
        }).icon("end").dimensions((this.client.getWindow().getScaledWidth()-382)/2+7, (this.client.getWindow().getScaledHeight()-292)/2+25+22+22, 100, 22).build());

        addDrawableChild(SmallButtonWidget.builder(Text.literal("创造世界"), button ->
        {

            bl1 = false;
            bl2 = false;
            bl3 = false;
            bl4 = true;
            init();
        }).icon("creative").dimensions((this.client.getWindow().getScaledWidth()-382)/2+7, (this.client.getWindow().getScaledHeight()-292)/2+25+22+22+22, 100, 22).build());







        if(bl1)         //主世界
        {
            remove(ender_man_farm_button);
            remove(creative_world_button);
            remove(mob_tower_main_button);
            remove(end_portal_button);
            remove(end_mainland_button);

            addDrawableChild(exchange_button);
            addDrawableChild(home_button);
            addDrawableChild(mob_tower_main_button);
            addDrawableChild(iron_farm_button);

        }else if(bl2)   //地狱
        {
            remove(exchange_button);
            remove(home_button);
            remove(ender_man_farm_button);
            remove(creative_world_button);
            remove(mob_tower_main_button);
            remove(end_portal_button);
            remove(end_mainland_button);
            remove(iron_farm_button);


        }else if(bl3)   //末地
        {
            remove(exchange_button);
            remove(home_button);
            remove(creative_world_button);
            remove(mob_tower_main_button);
            remove(iron_farm_button);

            addDrawableChild(end_mainland_button);
            addDrawableChild(end_portal_button);
            addDrawableChild(ender_man_farm_button);
        } else if(bl4)  //创造世界
        {
            remove(exchange_button);
            remove(home_button);
            remove(ender_man_farm_button);
            remove(mob_tower_main_button);
            remove(end_portal_button);
            remove(end_mainland_button);
            remove(iron_farm_button);

            addDrawableChild(creative_world_button);
        }
    }
}
