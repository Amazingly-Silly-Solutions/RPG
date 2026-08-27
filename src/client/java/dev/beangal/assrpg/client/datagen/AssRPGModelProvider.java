package dev.beangal.assrpg.client.datagen;

import dev.beangal.assrpg.AssRPG;
import dev.beangal.assrpg.block.CoinPileBlock;
import dev.beangal.assrpg.registry.AssRPGBlocks;
import dev.beangal.assrpg.registry.AssRPGItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class AssRPGModelProvider extends FabricModelProvider {
    public AssRPGModelProvider(FabricDataOutput output) {
        super(output);
    }

    public void generateItemTexturedBlockModel(BlockModelGenerators blockStateModelGenerator, Block block) {
        ResourceLocation id = BuiltInRegistries.BLOCK.getKey(block);
        TextureMapping textureMapping = new TextureMapping().put(TextureSlot.ALL, id.withPrefix("item/"));
        ModelTemplates.CUBE_ALL.create(id, textureMapping, blockStateModelGenerator.modelOutput);

        blockStateModelGenerator.blockStateOutput.accept(
                MultiVariantGenerator.multiVariant(
                        block,
                        Variant.variant().with(VariantProperties.MODEL, id)
                )
        );
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        generateItemTexturedBlockModel(blockStateModelGenerator, AssRPGBlocks.DUNGEON_ENTRANCE);
        generateItemTexturedBlockModel(blockStateModelGenerator, AssRPGBlocks.INVISIBLE_SUPPORT);
        generateCoinPile(blockStateModelGenerator);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateFlatItem(AssRPGBlocks.DUNGEON_ENTRANCE.asItem(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(AssRPGBlocks.INVISIBLE_SUPPORT.asItem(), ModelTemplates.FLAT_ITEM);

        itemModelGenerator.generateFlatItem(AssRPGItems.COIN, ModelTemplates.FLAT_ITEM);
        itemModelGenerator.itemModelOutput.accept(
                AssRPGBlocks.COIN_PILE.asItem(),
                ItemModelUtils.plainModel(AssRPG.id("block/coin_pile_3"))
        );
    }

    private void generateCoinPile(BlockModelGenerators blockStateModelGenerator) {
        PropertyDispatch.C1<Integer> variantMap = PropertyDispatch.property(CoinPileBlock.COINS);

        for (int i = 1; i <= 5; i++) {
            variantMap.select(i, Variant.variant()
                    .with(VariantProperties.MODEL, AssRPG.id("block/coin_pile_" + i)));
        }

        blockStateModelGenerator.blockStateOutput.accept(MultiVariantGenerator.multiVariant(AssRPGBlocks.COIN_PILE).with(variantMap));
    }
}
