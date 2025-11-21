package com.hau.randomitemgen.register;

import com.hau.randomitemgen.RandomItemGen;
import com.hau.randomitemgen.content.NonStackableItemGeneratorBlockEntity;
import com.hau.randomitemgen.content.RandomModGeneratorBlockEntity;
import com.hau.randomitemgen.content.StackableItemGeneratorBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.bus.api.IEventBus;

public class ModBlockEntities {

        public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister
                        .create(Registries.BLOCK_ENTITY_TYPE, RandomItemGen.MODID);

        public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<StackableItemGeneratorBlockEntity>> STACKABLE_ITEM_GENERATOR_TYPE = BLOCK_ENTITY_TYPES
                        .register("stackable_item_generator",
                                        () -> BlockEntityType.Builder.of(StackableItemGeneratorBlockEntity::new,
                                                        ModBlocks.STACKABLE_ITEM_GENERATOR.get()).build(null));

        public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<NonStackableItemGeneratorBlockEntity>> NON_STACKABLE_ITEM_GENERATOR_TYPE = BLOCK_ENTITY_TYPES
                        .register("non_stackable_item_generator",
                                        () -> BlockEntityType.Builder.of(NonStackableItemGeneratorBlockEntity::new,
                                                        ModBlocks.NON_STACKABLE_ITEM_GENERATOR.get()).build(null));
        
        public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RandomModGeneratorBlockEntity>> RANDOM_MOD_GENERATOR_TYPE = BLOCK_ENTITY_TYPES
            .register("random_mod_generator",
                    () -> BlockEntityType.Builder.of(RandomModGeneratorBlockEntity::new,
                            ModBlocks.RANDOM_MOD_GENERATOR.get()).build(null));

        public static void register(IEventBus eventBus) {
                BLOCK_ENTITY_TYPES.register(eventBus);
        }
}