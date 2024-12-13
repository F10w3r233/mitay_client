package com.flower.mitayclient.GUI.Screen;

import com.flower.mitayclient.util.ModIdentifier;
import com.google.common.collect.Ordering;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ParentElement;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.*;

public class PlayerInfoScreen extends Screen implements ParentElement
{
    public PlayerInfoScreen()
    {
        super(Text.literal("PlayerInfo"));
    }

    private PlayerEntity getCameraPlayer()
    {
        return this.client.getCameraEntity() instanceof PlayerEntity playerEntity ? playerEntity : null;
    }
    public void drawItemEnchantments(ItemStack itemStack, DrawContext context, int x, int y)
    {
        if (itemStack.isEmpty())
        {
            return;
        }
        for(Map.Entry<Enchantment, Integer> enchantment :EnchantmentHelper.get(itemStack).entrySet())
        {
            context.drawTextWithShadow(textRenderer,enchantment.getKey().getName(enchantment.getValue()), x, y, 111111);
            y += 18;
        }
    }
    private static final Identifier MATRIX_LARGE = new ModIdentifier("textures/gui/sprites/screen/player_info_matrix.png");
    MinecraftClient client = MinecraftClient.getInstance();
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta)
    {
        RenderSystem.enableBlend();
        context.drawTexture(MATRIX_LARGE, (this.client.getWindow().getScaledWidth()-1264/2)/2-14,(this.client.getWindow().getScaledHeight()-292)/2-14,0,0,1264/2,292,1264/2,292);
        PlayerEntity player = getCameraPlayer();
        int index = 0;


        Iterator<ItemStack> listIterator = player.getArmorItems().iterator();
//        List<String> IDs = new ArrayList<>();
        List<ItemStack> list2 = new ArrayList<>();
        while(listIterator.hasNext())
        {
            list2.add(listIterator.next());
        }

//        for(ItemStack itemStack : list2)
//        {
//            for (Map.Entry<Enchantment, Integer> enchantment : EnchantmentHelper.get(itemStack).entrySet())
//            {
//                Text enchantmentID = enchantment.getKey().getName(enchantment.getValue());
//                IDs.add(enchantmentID.getString());
//            }
//        }
        Collections.reverse(list2);
        for(ItemStack armor : list2)
        {
            index += 80;
            context.drawItem(armor, 110 + index,140);

            if(armor.hasEnchantments())
            {
//                int i = 1;
                int y = 160;
                for (Map.Entry<Enchantment, Integer> enchantment : EnchantmentHelper.get(armor).entrySet())
                {
                    Text enchantmentID = enchantment.getKey().getName(enchantment.getValue());
                    context.drawText(textRenderer, enchantmentID, 100 + index - enchantmentID.getString().length()/10 ,y,111111, true);
                    y += 18;
                }
//                drawItemEnchantments(armor, context, 100 + index, 160);
            }
        }


        super.render(context, mouseX, mouseY, delta);
    }
}
