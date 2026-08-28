package dev.beangal.assrpg.client.datagen;

import dev.beangal.assrpg.registry.AssRPGBlocks;
import dev.beangal.assrpg.registry.AssRPGItems;
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
        translationBuilder.add("itemGroup.assrpg.blocks_group", "AssRPG Blocks");
        translationBuilder.add("itemGroup.assrpg.items_group", "AssRPG Items");
        translationBuilder.add("lore.assrpg.coin", "§7Right click to redeem");

        translationBuilder.add(AssRPGBlocks.DUNGEON_ENTRANCE, "Dungeon Entrance");
        translationBuilder.add(AssRPGBlocks.DUNGEON_ENTRANCE.asItem(), "Dungeon Entrance");
        translationBuilder.add(AssRPGBlocks.INVISIBLE_SUPPORT, "Invisible Support");
        translationBuilder.add(AssRPGBlocks.INVISIBLE_SUPPORT.asItem(), "Invisible Support");
        translationBuilder.add(AssRPGBlocks.COIN_PILE, "Coin Pile");
        translationBuilder.add(AssRPGBlocks.COIN_PILE.asItem(), "Coin Pile");
        translationBuilder.add(AssRPGBlocks.GLINT_LAMP, "Glint Lamp");
        translationBuilder.add(AssRPGBlocks.GLINT_LAMP.asItem(), "Glint Lamp");

        translationBuilder.add(AssRPGItems.COIN, "Coin");
    }
}
