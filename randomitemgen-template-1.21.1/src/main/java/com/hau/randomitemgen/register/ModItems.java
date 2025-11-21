package com.hau.randomitemgen.register;

import com.hau.randomitemgen.RandomItemGen;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;

import java.util.function.Supplier;

public class ModItems {

        public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM,
                        RandomItemGen.MODID);

        public static final Supplier<Item> STACKABLE_ITEM_GENERATOR = ITEMS.register("stackable_item_generator",
                        () -> new BlockItem(ModBlocks.STACKABLE_ITEM_GENERATOR.get(),
                                        new Item.Properties()));

        public static final Supplier<Item> NON_STACKABLE_ITEM_GENERATOR = ITEMS.register("non_stackable_item_generator",
                        () -> new BlockItem(ModBlocks.NON_STACKABLE_ITEM_GENERATOR.get(),
                                        new Item.Properties()));

        public static final Supplier<Item> RANDOM_MOD_GENERATOR = ITEMS.register("random_mod_generator",
                        () -> new BlockItem(ModBlocks.RANDOM_MOD_GENERATOR.get(),
                                        new Item.Properties()));

        public static void register(IEventBus eventBus) {
                ITEMS.register(eventBus);
        }
}