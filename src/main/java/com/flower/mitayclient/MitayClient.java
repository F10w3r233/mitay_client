package com.flower.mitayclient;

import com.flower.mitayclient.event.KeyInputHandler;
import com.flower.mitayclient.networking.ModMessage;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MitayClient implements ModInitializer {

	public final String MOD_ID = "MitayClient";
    public static final Logger LOGGER = LoggerFactory.getLogger("MitayClient");

	@Override
	public void onInitialize()
	{
		KeyInputHandler.register();
		ModMessage.registerC2SPackets();
	}
}