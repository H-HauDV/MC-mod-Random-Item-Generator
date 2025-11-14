package com.hau.randomitemgen.register;

import com.hau.randomitemgen.RandomItemGen;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, RandomItemGen.MODID);

    public static final RegistryObject<Item> RANDOM_ITEM_GENERATOR =
            ITEMS.register("random_item_generator",
                    () -> new BlockItem(ModBlocks.RANDOM_ITEM_GENERATOR.get(),
                            new Item.Properties()));

    public static void register() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
