package com.hau.randomitemgen.register;

import com.hau.randomitemgen.RandomItemGen;
import com.hau.randomitemgen.content.StackableItemGeneratorBlock;
import com.hau.randomitemgen.content.NonStackableItemGeneratorBlock;
import com.hau.randomitemgen.content.RandomModGeneratorBlock;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;

import java.util.function.Supplier;

public class ModBlocks {

        // FIX: Use Registries.BLOCK
        public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK,
                        RandomItemGen.MODID);

        public static final Supplier<Block> STACKABLE_ITEM_GENERATOR = BLOCKS.register("stackable_item_generator",
                        () -> new StackableItemGeneratorBlock(
                                        BlockBehaviour.Properties.of()
                                                        .strength(1.5f).sound(SoundType.STONE)));

        public static final Supplier<Block> NON_STACKABLE_ITEM_GENERATOR = BLOCKS.register(
                        "non_stackable_item_generator",
                        () -> new NonStackableItemGeneratorBlock(
                                        BlockBehaviour.Properties.of()
                                                        .strength(1.5f).sound(SoundType.STONE)));

        public static final Supplier<Block> RANDOM_MOD_GENERATOR = BLOCKS.register("random_mod_generator",
                        () -> new RandomModGeneratorBlock(
                                        BlockBehaviour.Properties.of()
                                                        .strength(1.5f).sound(SoundType.STONE)));

        public static void register(IEventBus eventBus) {
                BLOCKS.register(eventBus);
        }
}
