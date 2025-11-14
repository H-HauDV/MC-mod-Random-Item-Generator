package com.hau.randomitemgen.datagen;

import com.hau.randomitemgen.RandomItemGen;
import com.hau.randomitemgen.register.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.tags.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    
    // Constructor
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.RANDOM_ITEM_GENERATOR.get()) 
            // The 3x3 pattern
            .pattern("CCC")
            .pattern("SSS")
            .pattern("CCC")
            // Define the keys: C is Cobblestone, S is Stick
            .define('C', Items.COBBLESTONE)
            .define('S', Items.STICK)
            // Required: An "unlockedBy" condition, usually a simple ingredient
            .unlockedBy(getHasName(Items.COBBLESTONE), has(Items.COBBLESTONE))
            // Save the recipe
            .save(output);

    }
}