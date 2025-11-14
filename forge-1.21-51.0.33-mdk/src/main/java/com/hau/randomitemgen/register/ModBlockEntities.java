package com.hau.randomitemgen.register;

import com.hau.randomitemgen.RandomItemGen;
import com.hau.randomitemgen.content.RandomItemGeneratorBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.bus.api.IEventBus;

import net.minecraft.core.registries.BuiltInRegistries;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, RandomItemGen.MODID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RandomItemGeneratorBlockEntity>> RANDOM_ITEM_GENERATOR_TYPE =
            BLOCK_ENTITY_TYPES.register("random_item_generator",
                    () -> BlockEntityType.Builder.of(RandomItemGeneratorBlockEntity::new,
                            ModBlocks.RANDOM_ITEM_GENERATOR.get()).build(null));
    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}