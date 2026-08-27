package dev.beangal.assrpg;

import dev.beangal.assrpg.registry.AssRPGBlockEntities;
import dev.beangal.assrpg.registry.AssRPGBlocks;
import dev.beangal.assrpg.registry.AssRPGCreativeTabs;
import dev.beangal.assrpg.registry.AssRPGItems;
import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.ResourceLocation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssRPG implements ModInitializer {
	public static final String MOD_ID = "assrpg";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
		AssRPGEvents.initialize();
		AssRPGBlocks.initialize();
		AssRPGBlockEntities.initialize();
		AssRPGCreativeTabs.initialize();
		AssRPGItems.initialize();
	}

	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
	}
}
