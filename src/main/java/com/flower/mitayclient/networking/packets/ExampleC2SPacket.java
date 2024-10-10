package com.flower.mitayclient.networking.packets;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalEnchantmentTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

public class ExampleC2SPacket
{
    public static void receive(MinecraftServer server , ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender)
    {
        //Everything here happens only on the server

        EntityType.COW.spawn((ServerWorld) player.getWorld(), player.getBlockPos(), SpawnReason.TRIGGERED);
        player.teleport(1,1,1);
        player.sendMessage(Text.literal("1"));
    }
}
