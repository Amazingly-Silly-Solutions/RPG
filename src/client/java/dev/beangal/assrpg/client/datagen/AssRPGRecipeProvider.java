package dev.beangal.assrpg.client.datagen;

import dev.beangal.assrpg.registry.AssRPGBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class AssRPGRecipeProvider extends FabricRecipeProvider {
    public AssRPGRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
        return new RecipeProvider(registryLookup, exporter) {
            @Override
            public void buildRecipes() {
                HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);

                shaped(RecipeCategory.REDSTONE, AssRPGBlocks.GLINT_LAMP, 4)
                        .unlockedBy(getHasName(Items.AMETHYST_SHARD), has(Items.AMETHYST_SHARD))
                        .pattern(" A ")
                        .pattern("AOA")
                        .pattern(" R ")
                        .define('A', Items.AMETHYST_SHARD)
                        .define('O', Items.OBSIDIAN)
                        .define('R', Items.REDSTONE)
                        .save(output);
            }
        };
    }

    @Override
    public @NotNull String getName() {
        return "AssRPGRecipeProvider";
    }
}
