package com.flower.mitayclient.networking;

import com.flower.mitayclient.util.ModIdentifier;
import com.flower.mitayclient.networking.packets.ExampleC2SPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;

public class ModMessage
{
    public static final Identifier DR = new ModIdentifier("textures/icon.png");

    public static void registerC2SPackets()
    {
        ServerPlayNetworking.registerGlobalReceiver(DR, ExampleC2SPacket::receive);
    }

    public static void registerS2CPackets()
    {

    }
}
