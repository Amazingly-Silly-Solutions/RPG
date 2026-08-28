package dev.beangal.assrpg.client.datagen;

import dev.beangal.assrpg.registry.AssRPGBlocks;
import dev.beangal.assrpg.registry.AssRPGItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.concurrent.CompletableFuture;

public class AssRPGBlockLootTableProvider extends FabricBlockLootTableProvider {
    public AssRPGBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        this.add(AssRPGBlocks.INVISIBLE_SUPPORT, LootTable.lootTable());
        this.add(AssRPGBlocks.COIN_PILE, LootTable.lootTable()
                .pool(
                        LootPool.lootPool()
                                .setRolls(new ConstantValue(1))
                                .add(
                                        AlternativesEntry.alternatives(
                                                LootItem.lootTableItem(AssRPGBlocks.COIN_PILE).when(this.hasSilkTouch()),
                                                LootItem.lootTableItem(AssRPGItems.COIN)
                                                        .apply(SetItemCountFunction.setCount(
                                                                UniformGenerator.between(4, 8)
                                                        ))
                                        )
                                ).build()
                )
        );

        dropSelf(AssRPGBlocks.GLINT_LAMP);
    }
}
