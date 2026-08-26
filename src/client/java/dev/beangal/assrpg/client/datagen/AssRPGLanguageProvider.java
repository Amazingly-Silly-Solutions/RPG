package dev.beangal.assrpg.client.datagen;

import dev.beangal.assrpg.registry.AssRPGBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class AssRPGLanguageProvider extends FabricLanguageProvider {

    public AssRPGLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add("assrpg.message.protected", "§cThis chunk is protected!");

        translationBuilder.add(AssRPGBlocks.DUNGEON_ENTRANCE, "Dungeon Entrance");
        translationBuilder.add(AssRPGBlocks.DUNGEON_ENTRANCE.asItem(), "Dungeon Entrance");
    }
}
