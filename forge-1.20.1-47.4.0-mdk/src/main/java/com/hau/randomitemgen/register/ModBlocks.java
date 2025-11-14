package com.hau.randomitemgen.register;

import com.hau.randomitemgen.RandomItemGen;
import com.hau.randomitemgen.content.RandomItemGeneratorBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, RandomItemGen.MODID);

    public static final RegistryObject<Block> RANDOM_ITEM_GENERATOR =
            BLOCKS.register("random_item_generator", 
    () -> new RandomItemGeneratorBlock(Block.Properties.of().strength(1.5f).sound(SoundType.STONE)));

    public static void register() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}