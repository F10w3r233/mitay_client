package com.flower.mitayclient.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalEnchantmentTags;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class ExampleC2SPacket
{
    public static void receive(MinecraftServer server , ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender)
    {
        BlockPos pos = MinecraftClient.getInstance().getServer().getPlayerManager().getPlayer("").getSpawnPointPosition();
        //Everything here happens only on the server
        player.sendMessage(Text.literal("{untitled}_bedPos_x_" + pos.getX() + "_y_" + pos.getY() + "_z_" + pos.getZ()));
    }
}
