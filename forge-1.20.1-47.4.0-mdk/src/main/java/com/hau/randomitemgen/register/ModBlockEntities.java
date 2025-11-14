package com.hau.randomitemgen.register;

import com.hau.randomitemgen.RandomItemGen;
import com.hau.randomitemgen.content.RandomItemGeneratorBlockEntity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, RandomItemGen.MODID);

    public static final RegistryObject<BlockEntityType<RandomItemGeneratorBlockEntity>> RANDOM_ITEM_GENERATOR =
            BLOCK_ENTITIES.register("random_item_generator",
                    () -> BlockEntityType.Builder.of(
                            RandomItemGeneratorBlockEntity::new,
                            ModBlocks.RANDOM_ITEM_GENERATOR.get()
                    ).build(null));

    public static void register() {
        BLOCK_ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
