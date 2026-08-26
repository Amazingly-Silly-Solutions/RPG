package dev.beangal.assrpg.client;

import dev.beangal.assrpg.client.datagen.AssRPGLanguageProvider;
import dev.beangal.assrpg.client.datagen.AssRPGModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class AssRPGDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(AssRPGModelProvider::new);
		pack.addProvider(AssRPGLanguageProvider::new);
	}
}
