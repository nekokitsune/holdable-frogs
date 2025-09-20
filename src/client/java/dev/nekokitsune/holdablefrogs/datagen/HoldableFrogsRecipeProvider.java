package dev.nekokitsune.holdablefrogs.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import dev.nekokitsune.holdablefrogs.item.ModItems;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class HoldableFrogsRecipeProvider extends FabricRecipeProvider {
    public HoldableFrogsRecipeProvider(FabricDataOutput output,
                                       CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter recipeExporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, ModItems.FROG_HOLDER)
                .pattern(" is")
                .pattern(" is")
                .pattern("i  ")
                .input('i', Items.IRON_INGOT)
                .input('s', Items.STRING)
                .group("multi_bench")
                .criterion(FabricRecipeProvider.hasItem(ModItems.FROG_HOLDER), FabricRecipeProvider.conditionsFromItem(ModItems.FROG_HOLDER))
                .offerTo(recipeExporter);
    }
}
