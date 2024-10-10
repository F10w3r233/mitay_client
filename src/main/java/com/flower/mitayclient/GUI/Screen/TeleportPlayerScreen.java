package com.flower.mitayclient.GUI.Screen;

import com.flower.mitayclient.GUI.Button.TeleportPlayer.TeleportPlayerButton;
import com.flower.mitayclient.util.ModIdentifier;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.PlayerSkinDrawer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TeleportPlayerScreen extends Screen
{

    private static final Identifier MATRIX_LARGE = new ModIdentifier("textures/gui/sprites/screen/matrix_large.png");
    private static final Identifier MATRIX_SIDE = new ModIdentifier("textures/gui/sprites/screen/matrix_side.png");
    private static final Identifier INFO_BAR = new ModIdentifier("textures/gui/sprites/hud/info_bar_2x.png");

    MinecraftClient client = MinecraftClient.getInstance();
    public TeleportPlayerScreen()
    {
        super(Text.literal("Teleport_Player"));
    }


    TeleportPlayerButton button = TeleportPlayerButton.builder(Text.literal(""), button ->
    {
        PlaceListScreen.sendChatCommand("tpw " + MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry("BanxiaFlower").getProfile().getName());
    }).player(MinecraftClient.getInstance().getNetworkHandler().getPlayerListEntry("BanxiaFlower"))
            .dimensions((this.client.getWindow().getScaledWidth()-382)/2+10,(this.client.getWindow().getScaledHeight()-292)/2+10, 210, 30).build();


    //按钮集合
    List<TeleportPlayerButton> buttonList = new ArrayList<>();









    int originalPlayerNumber;

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta)
    {
        //玩家列表
        Collection<PlayerListEntry> players = MinecraftClient.getInstance().getNetworkHandler().getPlayerList();

        //窗口
        int scaledWidth = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int scaledHeight = MinecraftClient.getInstance().getWindow().getScaledHeight();

        //初始高   间隔
        final int oriY = scaledHeight/2-122;
        final int gap = 19;
        int curIndex = 0;
        int oriIndex = 0;
        int initX = (scaledWidth-382)/2+10;
        int initY = (scaledHeight-292)/2+10;
        //检测玩家数量变动


        if(originalPlayerNumber != players.size())
        {
            buttonList.clear();
            for(PlayerListEntry player : players)
            {
                oriIndex = curIndex;
                curIndex++;


                buttonList.add(new TeleportPlayerButton.Builder(Text.literal(""), button ->
                {

                }).player(player)
                        .dimensions(initX,getCurrentY(oriY, gap, curIndex), 210, 30).build());
            }
            init();
        }


        RenderSystem.enableBlend();
        context.drawTexture(MATRIX_LARGE, (scaledWidth-382)/2-14,(scaledHeight-292)/2-14,0,0,382,292,382,292);



//        context.drawTexture(INFO_BAR, (scaledWidth-382)/2+10,(scaledHeight-292)/2+10,0,0,160,34,160,34);

        context.drawTexture(INFO_BAR, (scaledWidth-382)/2+10,(scaledHeight-292)/2+34+15,0,0,160,34,160,34);
        context.drawTexture(INFO_BAR, (scaledWidth-382)/2+160+22,(scaledHeight-292)/2+10,0,0,160,34,160,34);








        int index = 0;
        int curY;

        if(players != null)
        {
            for(PlayerListEntry player : players)
            {
                index++;
                curY = oriY + 20 * (index-1) + gap * (index-1);

                if(player.getDisplayName() == null)
                {
                    if(player.getProfile().getName() != null)
                    {
                        context.drawTextWithShadow(client.textRenderer, player.getProfile().getName(), (this.client.getWindow().getScaledWidth()-382)/2+40, curY, 14474460);
                        if(player.getSkinTextures() != null)
                            PlayerSkinDrawer.draw(context, player.getSkinTextures(), (this.client.getWindow().getScaledWidth()-382)/2+20, curY-5, 16);
//                        context.drawTextWithShadow(client.textRenderer, String.valueOf(player.getLatency()), 800, curY, 14474460);

                    }
                }else
                {
                    if(player.getSkinTextures() != null)
                        PlayerSkinDrawer.draw(context, player.getSkinTextures(), (this.client.getWindow().getScaledWidth()-382)/2+20, curY-5, 16);
                    context.drawTextWithShadow(client.textRenderer, player.getDisplayName(), (this.client.getWindow().getScaledWidth()-382)/2+40, curY, 14474460);
                }
            }
        }




        originalPlayerNumber = players.size();
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    protected void init()
    {
//        addDrawableChild(button);
        for (TeleportPlayerButton button : buttonList)
        {
            addDrawableChild(button);
        }
        
        super.init();
    }

    public int getCurrentY(int oriY, int gap, int index)
    {
        return oriY + 20 * (index-1) + gap * (index-1);
    }
}
