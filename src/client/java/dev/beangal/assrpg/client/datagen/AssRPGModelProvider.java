package dev.beangal.assrpg.client.datagen;

import dev.beangal.assrpg.registry.AssRPGBlocks;
import dev.beangal.assrpg.registry.AssRPGItems;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
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

        blockStateModelGenerator.createTrivialBlock(AssRPGBlocks.COIN_PILE, TexturedModel.CUBE);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        itemModelGenerator.generateFlatItem(AssRPGBlocks.DUNGEON_ENTRANCE.asItem(), ModelTemplates.FLAT_ITEM);
        itemModelGenerator.generateFlatItem(AssRPGBlocks.INVISIBLE_SUPPORT.asItem(), ModelTemplates.FLAT_ITEM);

        itemModelGenerator.generateFlatItem(AssRPGItems.COIN, ModelTemplates.FLAT_ITEM);
    }
}
