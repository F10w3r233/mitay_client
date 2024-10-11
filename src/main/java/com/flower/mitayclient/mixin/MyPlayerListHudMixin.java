package com.flower.mitayclient.mixin;

import com.flower.mitayclient.util.ModIdentifier;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.PlayerSkinDrawer;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.scoreboard.*;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(PlayerListHud.class)
@Environment(EnvType.CLIENT)
public class MyPlayerListHudMixin
{
    private static final Comparator<PlayerListEntry> ENTRY_ORDERING = Comparator.comparingInt(entry -> entry.getGameMode() == GameMode.SPECTATOR ? 1 : 0);
    MinecraftClient client = MinecraftClient.getInstance();
    public List<PlayerListEntry> collectPlayerEntries()
    {
        return this.client.player.networkHandler.getListedPlayerListEntries().stream().sorted(ENTRY_ORDERING).limit(80L).toList();
    }

    private static final Identifier UP = new ModIdentifier("textures/gui/sprites/hud/rect/up.png");
    private static final Identifier RECT = new ModIdentifier("textures/gui/sprites/hud/rect/rect.png");
    private static final Identifier DOWN = new ModIdentifier("textures/gui/sprites/hud/rect/down.png");

    public Text getPlayerName(PlayerListEntry entry) {
        return entry.getDisplayName() != null
                ? this.applyGameModeFormatting(entry, entry.getDisplayName().copy())
                : this.applyGameModeFormatting(entry, Team.decorateName(entry.getScoreboardTeam(), Text.literal(entry.getProfile().getName())));
    }

    private Text applyGameModeFormatting(PlayerListEntry entry, MutableText name) {
        return entry.getGameMode() == GameMode.SPECTATOR ? name.formatted(Formatting.ITALIC) : name;
    }


    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    public void render(DrawContext context, int scaledWindowWidth, Scoreboard scoreboard, ScoreboardObjective objective, CallbackInfo ci)
    {
        ci.cancel();


        RenderSystem.enableBlend();
        int i = 1;
        int index = 0;
        int oriY = 15;
        int curY;
        int gap = 3;
        Collection<PlayerListEntry> players = client.getNetworkHandler().getPlayerList();
        if (this.client.options.playerListKey.isPressed())
        {
            context.setShaderColor(1,1,1,0.7F);
            //玩家列表 白框
            int playerNumber = players.size();
            int rectHeight;
            context.drawTexture(UP, 700,5, 0,0,291/2, 11/2,291/2,11/2);
            if(playerNumber == 1)
            {
                context.drawTexture(RECT, 700,5+11/2, 0,0,291/2, 35/2,291/2,35/2);
                context.drawTexture(DOWN, 700,5     +35/2   +11/2, 0,0,291/2, 11/2,291/2,11/2);
            }else
            {
                rectHeight = (playerNumber-1) * 47/2;
                context.drawTexture(RECT, 700,5+11/2, 0,0,291/2, 35/2 + rectHeight,291/2,35/2 + rectHeight);
                context.drawTexture(DOWN, 700,5     +35/2  + rectHeight       +11/2, 0,0,291/2, 11/2,291/2,11/2);
            }
            context.setShaderColor(1,1,1,1F);

            //头像 名字显示
            if(players != null)
            {
                for(PlayerListEntry player : players)
                {
                    i++;
                    index++;
                    curY = oriY + 20 * (index-1) + gap * (index-1);

                    if(player.getDisplayName() == null)
                    {
                        if(player.getProfile().getName() != null)
                        {
                            context.drawTextWithShadow(client.textRenderer, player.getProfile().getName(), 732, curY, 14474460);
                            if(player.getSkinTextures() != null)
                                PlayerSkinDrawer.draw(context, player.getSkinTextures(), 708, curY-5, 16);
//                            context.drawTextWithShadow(client.textRenderer, String.valueOf(player.getLatency()), 800, curY, 14474460);

                        }
                    }else
                    {
                        if(player.getSkinTextures() != null)
                            PlayerSkinDrawer.draw(context, player.getSkinTextures(), 708, curY-5, 16);
                        context.drawTextWithShadow(client.textRenderer, player.getDisplayName(), 732, curY, 14474460);
                    }
                }
            }
        }

    }
}
