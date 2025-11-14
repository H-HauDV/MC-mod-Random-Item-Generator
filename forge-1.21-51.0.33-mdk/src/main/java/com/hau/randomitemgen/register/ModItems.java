package com.hau.randomitemgen.register;

import com.hau.randomitemgen.RandomItemGen;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries; 
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;

import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, RandomItemGen.MODID);

    public static final Supplier<Item> RANDOM_ITEM_GENERATOR =
            ITEMS.register("random_item_generator",
                    () -> new BlockItem(ModBlocks.RANDOM_ITEM_GENERATOR.get(),
                            new Item.Properties().arch$tab(CreativeModeTabs.BUILDING_BLOCKS)
                    ));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}