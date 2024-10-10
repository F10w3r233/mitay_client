package com.flower.mitayclient.mixin;

import com.flower.mitayclient.util.ModIdentifier;
import com.flower.mitayclient.util.Locations;
import com.flower.mitayclient.util.PlayerDataHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.PlayerSkinDrawer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.option.AttackIndicator;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.StatusEffectSpriteManager;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;


@Mixin(InGameHud.class)
public abstract class InGameHudMixin {


    @Shadow public abstract TextRenderer getTextRenderer();

    @Shadow public abstract PlayerListHud getPlayerListHud();

    @Shadow @Final private PlayerListHud playerListHud;
    MinecraftClient client = MinecraftClient.getInstance();



    //物品栏
    private static final Identifier HOTBAR_TEXTURE = new ModIdentifier("textures/gui/sprites/hud/hotbar3.png");
    private static final Identifier HOTBAR_SELECTION_TEXTURE = new ModIdentifier("textures/gui/sprites/hud/hotbar_selection.png");
    private static final Identifier HOTBAR_SELECTION_TEXTURE_LEFT = new ModIdentifier("textures/gui/sprites/hud/hotbar_selection_left.png");
    private static final Identifier HOTBAR_SELECTION_TEXTURE_RIGHT = new ModIdentifier("textures/gui/sprites/hud/hotbar_selection_right.png");




    private static final Identifier UP = new ModIdentifier("textures/gui/sprites/hud/rect/up.png");
    private static final Identifier RECT = new ModIdentifier("textures/gui/sprites/hud/rect/rect.png");
    private static final Identifier DOWN = new ModIdentifier("textures/gui/sprites/hud/rect/down.png");






    //药水效果
    private static final Identifier EFFECT_BAR = new ModIdentifier("textures/gui/sprites/hud/effect_bar_2x.png");
    private static final Identifier EFFECT_BAR_BENEFIT = new ModIdentifier("textures/gui/sprites/hud/effect_bar_benefit_2x.png");
    private static final Identifier EFFECT_BAR_BAD = new ModIdentifier("textures/gui/sprites/hud/effect_bar_bad_2x.png");





    //状态栏
    private static final Identifier INFO_BAR = new ModIdentifier("textures/gui/sprites/hud/info_bar_2x.png");
    private static final Identifier END = new ModIdentifier("textures/gui/sprites/hud/ender_eye.png");
    private static final Identifier NETHER = new ModIdentifier("textures/gui/sprites/hud/blaze_powder.png");
    private static final Identifier OVERWORLD = new ModIdentifier("textures/gui/sprites/hud/grass.png");
    private static final Identifier CREATIVE_WORLD = new ModIdentifier("textures/gui/sprites/hud/redstone.png");




    //目标玩家
    private static final Identifier STATUS_BAR = new ModIdentifier("textures/gui/sprites/hud/status_bar_small_4.png");



    private static final Identifier TOOL_BAR = new ModIdentifier("textures/gui/sprites/hud/tool_bar.png");
    private static final Identifier TOOL_BAR_MENDING = new ModIdentifier("textures/gui/sprites/hud/tool_bar_mending.png");




    //药水
    @Inject(method = "renderStatusEffectOverlay", at = @At("HEAD"), cancellable = true)
    private void renderEffect(DrawContext context, CallbackInfo ci)
    {
        ci.cancel();

    }
    int x1 = 0;
    int x2 = 0;
    int x3 = 0;
    int x4 = 0;
    int x5 = 0;
    int x6 = 0;

    int originalSize;

    DrawContext context2 = new DrawContext(MinecraftClient.getInstance(), MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers());
    @Inject(method = "render", at = @At("RETURN"), cancellable = true)
    private void render(DrawContext context, float tickDelta, CallbackInfo ci)
    {
        RenderSystem.enableBlend();
        int fps = MinecraftClient.getInstance().getCurrentFps();
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, "Mitay Minecraft Survival Server Client Dev1.0", 2, 2, 111111);
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, "FPS : " + fps, 2, 10, 1111111);
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, "Player : " + MinecraftClient.getInstance().getCameraEntity().getNameForScoreboard(), 2, 18, 1111111);





        Collection<StatusEffectInstance> collection = this.client.player.getStatusEffects();
        if (!collection.isEmpty())
        {
            if (this.client.currentScreen instanceof AbstractInventoryScreen abstractInventoryScreen &&
                    abstractInventoryScreen.hideStatusEffectHud())
            {
                return;
            }

            RenderSystem.enableBlend();
            int i = 0;
            int gap = 40;
            StatusEffectSpriteManager statusEffectSpriteManager = this.client.getStatusEffectSpriteManager();
//            List<Runnable> list = Lists.<Runnable>newArrayListWithExpectedSize(collection.size());


            for(StatusEffectInstance statusEffectInstance : collection)
            {
                RenderSystem.enableBlend();
                if(collection.size() != originalSize)
                {
                    x1 = -83;
                    x2 = -79;
                    x3 = -76;
                    x4 = -50;
                    x5 = -90;
                }

                if(x1 < 12)
                {
                    x1++;
                }
                if(x2 < 17)
                {
                    x2++;
                }
                if(x3 < 20)
                {
                    x3++;
                }
                if(x4 < 46)
                {
                    x4++;
                }
                if(x5 < 85)
                {
                    x5++;
                }


                StatusEffect statusEffect = statusEffectInstance.getEffectType();
                Sprite sprite = statusEffectSpriteManager.getSprite(statusEffect);

                //绘制效果sprite
                i++;
                int n = gap*i;
                context2.drawTexture(EFFECT_BAR, x1 , n-8, 0, 0, 90, 34, 90, 34);
                if(!statusEffect.isBeneficial())
                {
                    context2.drawTexture(EFFECT_BAR_BAD, x2, n-3, 0,0,24,24,24,24);
                }else if (statusEffectInstance.isAmbient())
                {
                    context2.drawTexture(EFFECT_BAR_BENEFIT, x2, n-3, 0,0,24,24,24,24);
                }
                float f = 1.0F;
//                if (statusEffectInstance.isDurationBelow(200))
//                {
//                    int m = statusEffectInstance.getDuration();
//                    int a = 10 - m / 20;
//                    f = MathHelper.clamp((float)m / 10.0F / 5.0F * 0.5F, 0.0F, 0.5F)
//                            + MathHelper.cos((float)m * (float) Math.PI / 5.0F) * MathHelper.clamp((float)a / 10.0F * 0.25F, 0.0F, 0.25F);
//                }


//                float g = f;
//                context.setShaderColor(1.0F, 1.0F, 1.0F, g);
                context.drawSprite(x3, n, 0, 18, 18, sprite);
//                context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

                //计时
                int totalDuration = statusEffectInstance.getDuration()/20;
                int min;
                String minStr;
                if(totalDuration <= 0)
                {
                    min = 0;
                    minStr = "0" + min;
                }else {
                    min = totalDuration / 60;
                    if(min < 10)
                    {
                        minStr = "0" + min;
                    }else minStr = String.valueOf(min);
                }
                int second;
                String secondStr;
                if(totalDuration < 60)
                {
                    second = totalDuration;
                    if(second < 10)
                    {
                        secondStr = "0" + second;
                    }else secondStr = String.valueOf(second);
                }else {
                    second = totalDuration % 60;
                    if(second < 10)
                    {
                        secondStr = "0" + second;
                    }else secondStr = String.valueOf(second);
                }





                //effectName && 计时display
                String effectLevel;
                switch (statusEffectInstance.getAmplifier())
                {
//                    case 0 : effectLevel = "";break;
                    case 1 : effectLevel = "II";break;
                    case 2 : effectLevel = "III";break;
                    case 3 : effectLevel = "IV";break;
                    case 4 : effectLevel = "V";break;
                    case 5 : effectLevel = "VI";break;
                    default: effectLevel = "";
                }
                context.drawTextWithShadow(client.textRenderer,  "§l" + statusEffect.getName().getString() + "" + effectLevel, x4, n-1, statusEffect.getColor());

                if(statusEffectInstance.isInfinite())
                {
                    context.drawTextWithShadow(client.textRenderer, "∞", x4, n+11, 111111);
                }else if(min == 0 && second <= 10)
                {
                    context.drawText(client.textRenderer, minStr + ":" + secondStr, x4, n+11, 12400439, true);
                } else context.drawText(client.textRenderer, minStr + ":" + secondStr, x4, n+11, 111111, true);

            }

        }
        originalSize = collection.size();
    }

    @Nullable
    private PlayerEntity getCameraPlayer()
    {
        return this.client.getCameraEntity() instanceof PlayerEntity playerEntity ? playerEntity : null;
    }

    private static final Identifier HOTBAR_OFFHAND_LEFT_TEXTURE = new Identifier("hud/hotbar_offhand_left");
    private static final Identifier HOTBAR_OFFHAND_RIGHT_TEXTURE = new Identifier("hud/hotbar_offhand_right");
    private static final Identifier HOTBAR_ATTACK_INDICATOR_BACKGROUND_TEXTURE = new Identifier("hud/hotbar_attack_indicator_background");
    private static final Identifier HOTBAR_ATTACK_INDICATOR_PROGRESS_TEXTURE = new Identifier("hud/hotbar_attack_indicator_progress");


    int color;
    int playerNameColor = 14474460;
    int playerPingColor = 14474460;
    int playerLeftToolDamageColor = 14474460;
    int playerRightToolDamageColor = 14474460;
    int playerLeftToolNameColor = 14474460;
    int playerRightToolNameColor = 14474460;

    String leftOnMending;
    String rightOnMending;

    Identifier pingIcon;

    int y = 0;
    int l = 0;
    int e = 0;
    int o = 0;
    int o2 = 0;
    int o3 = 0;
    int o4 = 0;

    int p1 = -300;
    String orginalWorld;
    String orginalPlace;
    PlayerListEntry targetPlayer;

    int originalPlayerNumber;
    int playerNumber;

        @Inject(method = "renderHotbar", at = @At("HEAD"), cancellable = true)
    public void overwriteHotbarRender(float tickDelta, DrawContext context, CallbackInfo ci)
    {
        ci.cancel();


















        PlayerEntity playerEntity = this.getCameraPlayer();


        if (playerEntity != null)
        {
            ItemStack itemStack = playerEntity.getOffHandStack();
            Arm arm = playerEntity.getMainArm().getOpposite();
            int i = client.getWindow().getScaledWidth() / 2;
            int j = 182;
            int k = 91;
            context.getMatrices().push();
            context.getMatrices().translate(0.0F, 0.0F, -90.0F);
            context.drawTexture(HOTBAR_TEXTURE, i - 92, client.getWindow().getScaledHeight() - y, 0,0,184, 24,184,24); //////dsaaaaaaaadsaaaaaaaaa
            if(playerEntity.getInventory().selectedSlot == 0)
            {
                context.drawTexture(HOTBAR_SELECTION_TEXTURE_LEFT, i - 92 + playerEntity.getInventory().selectedSlot * 20, client.getWindow().getScaledHeight() - y, 0,0,24, 23,24,23);
            }else if(playerEntity.getInventory().selectedSlot == 8)
            {
                context.drawTexture(HOTBAR_SELECTION_TEXTURE_RIGHT, i - 92 + playerEntity.getInventory().selectedSlot * 20, client.getWindow().getScaledHeight() - y, 0,0,24, 23,24,23);
            }else
            {
                context.drawTexture(HOTBAR_SELECTION_TEXTURE, i - 92 + playerEntity.getInventory().selectedSlot * 20, client.getWindow().getScaledHeight() - y, 0, 0, 24, 23, 24, 23);
            }

            //动画
            if(y<23)
            {
                y += 4;
            }
            if(l<300)
            {
                l ++;
            }

//            context.drawTexture(EFFECT_BAR_HIGH, 0, MinecraftClient.getInstance().getWindow().getScaledHeight()-10-y, 0,0,90,34,90,34);
//            Rect rect = new Rect(context, EFFECT_BAR_HIGH);
//            rect.pop(300,40,90,34,false);
            if (!itemStack.isEmpty()) {
                if (arm == Arm.LEFT) {
                    context.drawGuiTexture(HOTBAR_OFFHAND_LEFT_TEXTURE, i - 91 - 29, client.getWindow().getScaledHeight() - 23, 29, 24);
                } else {
                    context.drawGuiTexture(HOTBAR_OFFHAND_RIGHT_TEXTURE, i + 91, client.getWindow().getScaledHeight() - 23, 29, 24);
                }
            }

            context.getMatrices().pop();
            int l = 1;

            for (int m = 0; m < 9; m++) {
                int n = i - 90 + m * 20 + 2;
                int o = client.getWindow().getScaledHeight() - 16 - 3;
                this.renderHotbarItem(context, n, o-1, tickDelta, playerEntity, playerEntity.getInventory().main.get(m), l++);
            }

            if (!itemStack.isEmpty()) {
                int m = client.getWindow().getScaledHeight() - 16 - 3;
                if (arm == Arm.LEFT) {
                    this.renderHotbarItem(context, i - 91 - 26, m, tickDelta, playerEntity, itemStack, l++);
                } else {
                    this.renderHotbarItem(context, i + 91 + 10, m, tickDelta, playerEntity, itemStack, l++);
                }
            }

            RenderSystem.enableBlend();
            if (this.client.options.getAttackIndicator().getValue() == AttackIndicator.HOTBAR)
            {
                float f = this.client.player.getAttackCooldownProgress(0.0F);
                if (f < 1.0F)
                {
                    int n = client.getWindow().getScaledHeight() - 20;
                    int o = i + 91 + 6;
                    if (arm == Arm.RIGHT)
                    {
                        o = i - 91 - 22;
                    }

                    int p = (int) (f * 19.0F);
                    context.drawGuiTexture(HOTBAR_ATTACK_INDICATOR_BACKGROUND_TEXTURE, o, n, 18, 18);
                    context.drawGuiTexture(HOTBAR_ATTACK_INDICATOR_PROGRESS_TEXTURE, 18, 18, 0, 18 - p, o, n + 18 - p, 18, p);
                }
            }

        }



        //获取正在经验修补状态
        if(PlayerDataHandler.leftOnMending != null)
        {
            switch (PlayerDataHandler.leftOnMending)
            {
                case "true" -> this.leftOnMending = "true";
                case "false" -> this.leftOnMending = "false";
            }
        }
        if(PlayerDataHandler.rightOnMending != null)
        {
            switch (PlayerDataHandler.rightOnMending)
            {
                case "true" -> this.rightOnMending = "true";
                case "false" -> this.rightOnMending = "false";
            }
        }


        //获取玩家名颜色
        if(PlayerDataHandler.entitlement != null)
        {
            switch (PlayerDataHandler.entitlement)
            {
                case "vip" -> playerNameColor = 5569364;
                case "mvp" -> playerNameColor = 5569531;
                case "mvppp" -> playerNameColor = 16492544;
                case "owner" -> playerNameColor = 12400439;
                case "hentai" -> playerNameColor = 16777045;
                case "none" -> playerNameColor = 14474460;
            }
        }


        //获取玩家延迟颜色
        if(PlayerDataHandler.entitlement != null)
        {
            int ping = PlayerDataHandler.ping;
            if(ping <= 150)
            {
                playerPingColor = 111111;
                pingIcon = new ModIdentifier("textures/gui/sprites/hud/network/ping_5.png");
            }else if(ping > 150 && ping <= 300)
            {
                playerPingColor = 111111;
                pingIcon = new ModIdentifier("textures/gui/sprites/hud/network/ping_4.png");
            }else if(ping > 300 && ping <= 600)
            {
                playerPingColor = 16777045;
                pingIcon = new ModIdentifier("textures/gui/sprites/hud/network/ping_3.png");
            }else if(ping > 600 && ping < 1000)
            {
                playerPingColor = 10824234;
                pingIcon = new ModIdentifier("textures/gui/sprites/hud/network/ping_2.png");
            }else if(ping > 1000)
            {
                playerPingColor = 10824234;
                pingIcon = new ModIdentifier("textures/gui/sprites/hud/network/ping_1.png");
            }
        }

        //获取玩家工具耐久值颜色
//        if(PlayerDataHandler.rightHasMending != null)
//        {
//            switch (PlayerDataHandler.rightHasMending)
//            {
//                case "true" -> playerRightToolDamageColor = 1111111;
//                case "false" -> playerRightToolDamageColor = 14474460;
//            }
//        }
//
//        if(PlayerDataHandler.leftHasMending != null)
//        {
//            switch (PlayerDataHandler.leftHasMending)
//            {
//                case "true" -> playerLeftToolDamageColor = 1111111;
//                case "false" -> playerLeftToolDamageColor = 14474460;
//            }
//        }






            //顶部玩家信息栏

        String worldName = getCameraPlayer().getEntityWorld().getDimension().effects().getPath();
                //世界名
        switch (worldName)
        {
            case "overworld" : worldName = "主世界"; color = 111111;break;
            case "the_nether" : worldName = "地狱"; color = 12400439;break;
            case "the_end" : worldName = "末地";color = 8981897;break;
        }
        if(worldName.equals("主世界") && getCameraPlayer().isCreative())
        {
            worldName = "创造世界";
        }


                //玩家坐标 获取地点名
        double placeX = getCameraPlayer().getX();
        double placeZ = getCameraPlayer().getZ();
        String place = Locations.getPlace(placeX,placeZ, worldName);




        ClientPlayNetworkHandler clientPlayNetworkHandler = this.client.player.networkHandler;
        PlayerListEntry playerListEntry = clientPlayNetworkHandler.getPlayerListEntry(getCameraPlayer().getUuid());
        if(e < 400)   //延迟动画
        {
            e++;
        }else
        {
            if(orginalWorld != worldName || place != orginalPlace) //地点变化 载入动画重播放
            {
                o = -34;
                o2 = -24;
                o3 = -14;
            }

            if(o < 10)
            {
                o++;
            }
            if(o2<18)
                o2++;
            if(o3<28)
                o3++;
            int x = (client.getWindow().getScaledWidth()-160)/2;


            //顶部信息栏渲染
            context2.setShaderColor(1,1,1,0.7F);
            context2.drawTexture(INFO_BAR, x, o, 0, 0,160, 34, 160, 34);
            context2.setShaderColor(1,1,1,1F);


            if(playerListEntry != null)
            {
                //玩家头像渲染
                PlayerSkinDrawer.draw(context, playerListEntry.getSkinTextures(), x+8+4+4+4+4, o2, 18);
            }


            //玩家名长度处理
            String playerName = getCameraPlayer().getNameForScoreboard();
            int nameLength = 0;
            int pingLength = 0;
            if(playerName.length() <= 7)
            {
                nameLength = playerName.length() * 6 + 4;
                pingLength = nameLength + 5 + 8;
            }else if (playerName.length() > 7 && playerName.length() <= 9)
            {
                nameLength = playerName.length() * 5 + 10;
                pingLength = nameLength + 5 + 8;
            }else if(playerName.length() > 9)
            {
                nameLength = playerName.length() * 6 - 5;
                pingLength = nameLength + 5 + 8;
            }

            //玩家名渲染
            context.drawText(client.textRenderer, playerName, x+8+9+8+8+4+4+4+4+4+2, o2, playerNameColor, true);

            //延迟图标渲染
            if(pingIcon != null)
            {
                context.drawTexture(pingIcon, x+8+9+8+8+4+4+4+4+4+2+nameLength, o2, 0,0,10,8,10,8);

                //延迟渲染
                try
                {
                    context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, String.valueOf(PlayerDataHandler.ping), x+8+9+8+8+4+4+4+4+4+2+pingLength, o2, playerPingColor  );
                }catch (NullPointerException e)
                {
                    e.printStackTrace();
                }
            }



            //世界图标渲染
            if(worldName.equals("末地"))
            {
                context2.drawTexture(END, x+8+9+8+8+4+4+4+4+4+2, o3, 0, 0,9, 9, 9, 9);
            }else if (worldName.equals("地狱"))
            {
                context2.drawTexture(NETHER, x+8+9+8+8+4+4+4+4+4+2, o3-1, 0, 0,9, 9, 9, 9);
            }else if (worldName.equals("主世界"))
            {
                context2.drawTexture(OVERWORLD, x+8+9+8+8+4+4+4+4+4+2, o3-1, 0, 0,9, 9, 9, 9);
            }else if (worldName.equals("创造世界"))
            {
                context2.drawTexture(CREATIVE_WORLD, x+8+9+8+8+4+4+4+4+4+2, o3-1, 0, 0,9, 9, 9, 9);
            }
            if(place != null)
            {
                //地点渲染
                context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, place, x+8+9+8+8+9+2+4+4+4+4+4+2+worldName.getBytes().length*5, o3, color);
            }
            //世界名渲染
            context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, worldName, x+8+9+8+8+9+2+4+4+4+4+4+2, o3, color);
        }





        orginalPlace = place;
        orginalWorld = worldName;



               // 目标玩家血量条
        RenderSystem.enableBlend();
        if(client.targetedEntity != null)
        {
            if(client.targetedEntity.isPlayer())
                context2.drawTexture(STATUS_BAR, 15, client.getWindow().getScaledHeight()-107, 0, 0,120, 40, 120, 40);
            targetPlayer = client.getNetworkHandler().getPlayerListEntry(client.targetedEntity.getUuid());
            if(client.targetedEntity.isPlayer() && client.targetedEntity != null && targetPlayer != null)
            {
                //玩家头像渲染
                PlayerSkinDrawer.draw(context, targetPlayer.getSkinTextures(), 25, client.getWindow().getScaledHeight()-100, 24);
                PlayerEntity player = (PlayerEntity) client.targetedEntity;
                //玩家名渲染
                context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, String.valueOf(player.getNameForScoreboard()), 60, client.getWindow().getScaledHeight()-97, playerNameColor);
                //玩家血量渲染
                context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, "血量:" + (int) player.getHealth(), 60, client.getWindow().getScaledHeight()-87, color);
            }


        }







        List<ItemStack> itemStacks = (ArrayList<ItemStack>) getCameraPlayer().getHandItems();

        //主副手物品
        ItemStack leftHandItem = itemStacks.get(1);
        ItemStack rightHandItem = itemStacks.get(0);

        //主副手物品颜色处理
        if (leftHandItem.hasEnchantments())
        {
            playerLeftToolNameColor = 16492544;
        }else playerLeftToolNameColor = 14474460;

        if(rightHandItem.hasEnchantments())
        {
            playerRightToolNameColor = 16492544;
        }else playerRightToolNameColor = 14474460;




        //耐久颜色 （是否有经验修补）
        if(leftHandItem.getEnchantments().contains(Enchantments.MENDING))
        {
            playerLeftToolDamageColor = 1111111;
        }else playerLeftToolDamageColor = 14474460;

        if(rightHandItem.getEnchantments().contains(Enchantments.MENDING))
        {
            playerRightToolDamageColor = 1111111;

        }else playerRightToolDamageColor = 14474460;






        if(leftHandItem.isDamageable())//左手
        {
            RenderSystem.enableBlend();

            if(k2 > client.getWindow().getScaledWidth()-130)
                k2-=2;

            //白框
            if(this.leftOnMending != null)
            {
                if(this.leftOnMending.equals("true"))
                {
                    context.drawTexture(TOOL_BAR_MENDING, k2, client.getWindow().getScaledHeight()-100  , 0,0,100,35,100,35);
                }else context.drawTexture(TOOL_BAR, k2, client.getWindow().getScaledHeight()-100  , 0,0,100,35,100,35);
            }else context.drawTexture(TOOL_BAR, k2, client.getWindow().getScaledHeight()-100  , 0,0,100,35,100,35);


            context.drawItem(leftHandItem, k2 + 10, client.getWindow().getScaledHeight()-100+9);
            //物品名
            context.drawText(getTextRenderer(), leftHandItem.getName().getString(), k2+10+24, client.getWindow().getScaledHeight()-100+8, playerLeftToolNameColor, true);
            //耐久
            context.drawText(client.textRenderer, leftHandItem.getMaxDamage() - leftHandItem.getDamage() + "/" + leftHandItem.getMaxDamage() , k2+10+24, client.getWindow().getScaledHeight()-100+18, playerLeftToolDamageColor, true);
        }else k2 = client.getWindow().getScaledWidth();

        if(rightHandItem.isDamageable())//右手
        {
            RenderSystem.enableBlend();
            if(k1 > client.getWindow().getScaledWidth()-130)
                k1-=2;

            //白框
            if(this.rightOnMending != null)
            {
                if(this.rightOnMending.equals("true"))
                {
                    context.drawTexture(TOOL_BAR_MENDING, k1, client.getWindow().getScaledHeight()-60  , 0,0,100,35,100,35);
                } else context.drawTexture(TOOL_BAR, k1, client.getWindow().getScaledHeight()-60  , 0,0,100,35,100,35);
            }else context.drawTexture(TOOL_BAR, k1, client.getWindow().getScaledHeight()-60  , 0,0,100,35,100,35);

            //物品
            context.drawItem(rightHandItem, k1 + 10, client.getWindow().getScaledHeight()-60+9);

            //物品名
            context.drawText(getTextRenderer(), rightHandItem.getName().getString(), k1+10+24, client.getWindow().getScaledHeight()-60+8, playerRightToolNameColor, true);
            //耐久
            context.drawText(client.textRenderer, rightHandItem.getMaxDamage() - rightHandItem.getDamage() + "/" + rightHandItem.getMaxDamage() , k1+10+24, client.getWindow().getScaledHeight()-60+18, playerRightToolDamageColor, true);
        }else k1 = client.getWindow().getScaledWidth();









        int i = 1;
        int index = 0;
        int oriY = 15;
        int curY;
        int gap = 3;




        Collection<PlayerListEntry> players = client.getNetworkHandler().getPlayerList();


        RenderSystem.enableBlend();

//        context.drawTexture(STATUS_BAR, 275, 4, 0,0,133,30,133,30);
//        context.drawTexture(EX, 700, 4, 0,0,336/2,152/2,336/2,152/2);

        //玩家列表 白框














        RenderSystem.disableBlend();
    }


    int k1 = client.getWindow().getScaledWidth()+ 120;
    int k2 = client.getWindow().getScaledWidth()+ 120;
    private void renderHotbarItem(DrawContext context, int x, int y, float tickDelta, PlayerEntity player, ItemStack stack, int seed)
    {
        if (!stack.isEmpty())
        {
            float f = (float) stack.getBobbingAnimationTime() - tickDelta;
            if (f > 0.0F)
            {
                float g = 1.0F + f / 5.0F;
                context.getMatrices().push();
                context.getMatrices().translate((float) (x + 8), (float) (y + 12), 0.0F);
                context.getMatrices().scale(1.0F / g, (g + 1.0F) / 2.0F, 1.0F);
                context.getMatrices().translate((float) (-(x + 8)), (float) (-(y + 12)), 0.0F);
            }

            context.drawItem(player, stack, x, y, seed);
            if (f > 0.0F)
            {
                context.getMatrices().pop();
            }

            context.drawItemInSlot(this.client.textRenderer, stack, x, y + 2);
        }

    }





    @Inject(at = @At("HEAD"), method = "drawTextBackground", cancellable = true)
    public void TextPop(DrawContext context, TextRenderer textRenderer, int yOffset, int width, int color, CallbackInfo ci)
    {
        ci.cancel();
        int i = this.client.options.getTextBackgroundColor(0.0F);
        if (i != 0) {
            int j = -width / 2;
            context.fill(j - 2, yOffset - 2, j + width + 2, yOffset + 11, ColorHelper.Argb.mixColor(i, color));
        }



    }
}
