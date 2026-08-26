package dev.beangal.assrpg.client;

import net.fabricmc.api.ClientModInitializer;

public class AssRPGClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		AssRPGClientEvents.initialize();
		AssRPGBlockEntityRenderers.initialize();
	}
}