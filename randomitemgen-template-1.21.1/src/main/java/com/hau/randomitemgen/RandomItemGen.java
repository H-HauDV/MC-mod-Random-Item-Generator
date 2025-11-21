package com.hau.randomitemgen;

import com.hau.randomitemgen.datagen.ModRecipeProvider;
import com.hau.randomitemgen.register.ModBlocks;
import com.hau.randomitemgen.register.ModItems;
import com.hau.randomitemgen.register.ModBlockEntities;

import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;

// NEW IMPORTS for Data Generation
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;

@Mod(RandomItemGen.MODID)
public class RandomItemGen {
    public static final String MODID = "randomitemgen";

    public RandomItemGen(IEventBus modEventBus) {
        ModBlocks.register(modEventBus);
        ModItems.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        modEventBus.register(this);

        // 2. NEW: Subscribe the gatherData method to the mod event bus
        modEventBus.addListener(this::gatherData);
    }

    @SubscribeEvent
    public void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
            event.accept(ModItems.STACKABLE_ITEM_GENERATOR.get());
            event.accept(ModItems.NON_STACKABLE_ITEM_GENERATOR.get());
            event.accept(ModItems.RANDOM_MOD_GENERATOR.get());
        }
    }

    // 3. NEW: Data Generation Event Handler
    private void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        // Register the ModRecipeProvider. Only run on the server-side datagen task.
        if (event.includeServer()) {
            generator.addProvider(true, new ModRecipeProvider(packOutput, lookupProvider));
        }
    }
}