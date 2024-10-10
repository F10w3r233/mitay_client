package com.flower.mitayclient.mixin;

import com.flower.mitayclient.GUI.Screen.PlaceListScreen;
import com.flower.mitayclient.util.PlayerDataHandler;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//import com.rooxchicken.modtut.Event.HandleData;
//import com.rooxchicken.modtut.client.ModtutorialClient;

import net.minecraft.client.network.message.MessageHandler;
import net.minecraft.text.Text;

@Mixin(MessageHandler.class)
public class MessageHandlerMixin
{
    @Shadow @Final private MinecraftClient client;

    @Inject(method = "onGameMessage(Lnet/minecraft/text/Text;Z)V", at = @At("HEAD"), cancellable = true)
//    @Inject(method = "onGameMessage", at = @At("HEAD"), cancellable = true)
    public void onGameMessage(Text message, boolean overlay, CallbackInfo ci)
    {
        String content = message.getString();
        System.out.println(content);

        String[] contents;
        int ping;
        String entitlement;

        String symbol;


        String[] playerNames;
        String playerNameStr;

        if(content.startsWith("{untitled}"))
        {
            ci.cancel();
            contents = content.split("_");
            symbol = contents[1];
            if(symbol.equals("status")) //玩家状态
            {
                PlayerDataHandler.parsePlayerStatusInfo(Integer.parseInt(contents[2]), contents[3]);
            }else if(symbol.equals("tools")) //玩家工具
            {
                if(contents[2].equals("left") && contents[3].equals("mending"))
                {
                    PlayerDataHandler.parseToolInfo("left_true");
                } else if(contents[2].equals("left") && contents[3].equals("none"))  PlayerDataHandler.parseToolInfo("left_false");

                if(contents[2].equals("right") && contents[3].equals("mending"))
                {
                    PlayerDataHandler.parseToolInfo("right_true");
                } else if(contents[2].equals("right") && contents[3].equals("none"))  PlayerDataHandler.parseToolInfo("right_false");
            }else if(symbol.equals("onMending")) //是否正在经验修补
            {
                if(contents[2].equals("left"))
                {
                    if(contents[3].equals("true"))
                    {
                        PlayerDataHandler.parseMendingInfo("left_true");
                    }else if(contents[3].equals("false")) PlayerDataHandler.parseMendingInfo("left_false");

                }else if(contents[2].equals("right"))
                {
                    if(contents[3].equals("true"))
                    {
                        PlayerDataHandler.parseMendingInfo("right_true");
                    }else if(contents[3].equals("false")) PlayerDataHandler.parseMendingInfo("right_false");
                }
            }else if(symbol.equals("playerNames")) //玩家列表
            {
                playerNameStr = content.replace("{untitled}_playerNames_", "");
                playerNames = playerNameStr.split("_");
                PlayerDataHandler.parsePlayerNames(playerNames);
            }
        }



        if(content.startsWith("[untitled]"))
        {
            PlaceListScreen.sendChatCommand("verifymod");
        }
    }
}
