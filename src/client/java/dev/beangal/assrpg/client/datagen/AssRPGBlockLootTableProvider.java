package dev.beangal.assrpg.client.datagen;

import dev.beangal.assrpg.registry.AssRPGBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.concurrent.CompletableFuture;

public class AssRPGBlockLootTableProvider extends FabricBlockLootTableProvider {
    public AssRPGBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        this.add(AssRPGBlocks.INVISIBLE_SUPPORT, LootTable.lootTable());

        dropSelf(AssRPGBlocks.COIN_PILE);
    }
}
