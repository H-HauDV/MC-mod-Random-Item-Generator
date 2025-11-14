package com.hau.randomitemgen;

import com.hau.randomitemgen.register.ModBlocks;
import com.hau.randomitemgen.register.ModItems;
import com.hau.randomitemgen.register.ModBlockEntities;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraft.world.item.CreativeModeTabs;

@Mod(RandomItemGen.MODID)
public class RandomItemGen {
    public static final String MODID = "randomitemgen";

    public RandomItemGen() {
        ModBlocks.register();
        ModItems.register();
        ModBlockEntities.register();
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
    }

    @SubscribeEvent
    public void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
            event.accept(ModItems.RANDOM_ITEM_GENERATOR.get());
        }
    }
}