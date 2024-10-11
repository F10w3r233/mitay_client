package com.flower.mitayclient.GUI.Screen;

import com.flower.mitayclient.GUI.Button.TeleportPlayer.TeleportPlayerButton;
import com.flower.mitayclient.networking.ModMessage;
import com.flower.mitayclient.util.ModIdentifier;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.PlayerSkinDrawer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class TeleportPlayerScreen extends Screen
{

    private static final Identifier MATRIX_LARGE = new ModIdentifier("textures/gui/sprites/screen/matrix_large.png");


    MinecraftClient client = MinecraftClient.getInstance();
    public TeleportPlayerScreen()
    {
        super(Text.literal("Teleport_Player"));
    }



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
        final int oriY = (scaledHeight-292)/2+10;
        final int gap = 19;
        int curIndex = 0;
        int initX = (scaledWidth-382)/2+10;
//        int initY = (scaledHeight-292)/2+10;
        //检测玩家数量变动


        if(originalPlayerNumber != players.size())
        {
            for(TeleportPlayerButton button : buttonList)
                super.remove(button);
            buttonList.clear();
            init();
            for(PlayerListEntry player : players)
            {
                curIndex++;

                if (player.getProfile().getName().equals(this.client.getCameraEntity().getNameForScoreboard()))
                {
                    RenderSystem.enableBlend();
                    buttonList.add(new TeleportPlayerButton.Builder(Text.literal(""), button ->
                    {
                        PlaceListScreen.sendChatCommand("tpplus overworld 13 94 8");
                    }).player(player)
                            .dimensions(initX, getCurrentY(oriY, gap, curIndex), 210, 30).build());

                } else
                {
                    RenderSystem.enableBlend();
                    buttonList.add(new TeleportPlayerButton.Builder(Text.literal(""), button ->
                    {
                        PlaceListScreen.sendChatCommand("tpw " + player.getProfile().getName());
                    }).player(player)
                            .dimensions(initX, getCurrentY(oriY, gap, curIndex), 210, 30).build());

                }
                if(curIndex == players.size())
                    init();
                if(curIndex > 6)
                {
                    initX = (scaledWidth-382)/2+10 + 210 + 10;
                }else initX = (scaledWidth-382)/2+10;
            }
        }


        RenderSystem.enableBlend();
        context.drawTexture(MATRIX_LARGE, (scaledWidth-382)/2-14,(scaledHeight-292)/2-14,0,0,382,292,382,292);









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
