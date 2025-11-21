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

        // Recipe for STACKABLE
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.STACKABLE_ITEM_GENERATOR.get())
                .pattern("CCC").pattern("SSS").pattern("CCC")
                .define('C', Items.COBBLESTONE).define('S', Items.STICK)
                .unlockedBy(getHasName(Items.COBBLESTONE), has(Items.COBBLESTONE))
                .save(output);

        // Recipe for UNSTACKABLE
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.NON_STACKABLE_ITEM_GENERATOR.get())
                .pattern("CCC").pattern("III").pattern("CCC")
                .define('C', Items.COBBLESTONE).define('I', Items.IRON_INGOT)
                .unlockedBy(getHasName(Items.COBBLESTONE), has(Items.COBBLESTONE))
                .save(output);
        // Recipe for RANDOM MOD GENERATOR
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.RANDOM_MOD_GENERATOR.get())
                .pattern("CCC").pattern("DDD").pattern("CCC")
                .define('C', Items.COBBLESTONE)
                .define('D', Items.DIAMOND)
                .unlockedBy(getHasName(Items.COBBLESTONE), has(Items.COBBLESTONE))
                .save(output);
    }
}