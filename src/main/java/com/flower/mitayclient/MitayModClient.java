package com.flower.mitayclient;

import com.flower.mitayclient.networking.ModMessage;
import net.fabricmc.api.ClientModInitializer;

public class MitayModClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        ModMessage.registerS2CPackets();
    }
}
