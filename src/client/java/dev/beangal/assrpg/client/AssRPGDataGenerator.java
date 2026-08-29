package dev.beangal.assrpg.client;

import dev.beangal.assrpg.client.datagen.*;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class AssRPGDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(AssRPGModelProvider::new);
		pack.addProvider(AssRPGLanguageProvider::new);
		pack.addProvider(AssRPGBlockLootTableProvider::new);
		pack.addProvider(AssRPGBlockTagProvider::new);
		pack.addProvider(AssRPGRecipeProvider::new);
	}
}
