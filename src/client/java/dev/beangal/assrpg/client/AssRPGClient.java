package dev.beangal.assrpg.client;

import dev.beangal.assrpg.registry.AssRPGBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;

public class AssRPGClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		AssRPGClientEvents.initialize();
		AssRPGBlockEntityRenderers.initialize();

		BlockRenderLayerMap.INSTANCE.putBlock(AssRPGBlocks.COIN_PILE, RenderType.cutout());
	}
}