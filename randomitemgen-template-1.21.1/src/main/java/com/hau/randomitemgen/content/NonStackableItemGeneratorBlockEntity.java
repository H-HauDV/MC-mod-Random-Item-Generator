package com.hau.randomitemgen.content;

import com.hau.randomitemgen.register.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.ChestBlock;

import java.util.ArrayList;
import java.util.List;

public class NonStackableItemGeneratorBlockEntity extends BlockEntity {

    private int tickCounter = 0;
    private static List<Item> ALL_ITEMS = null;

    public NonStackableItemGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.NON_STACKABLE_ITEM_GENERATOR_TYPE.get(), pos, state);
    }

    private static boolean tryInsertOneItem(Container container, ItemStack stackToInsert) {
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack slot = container.getItem(i);

            // Only check for empty slots, as non-stackable items cannot merge/grow stacks
            if (slot.isEmpty()) {
                container.setItem(i, stackToInsert.copy());
                container.setChanged();
                return true;
            }
        }
        return false;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, NonStackableItemGeneratorBlockEntity be) {
        if (level.isClientSide)
            return;
        if (!level.hasNeighborSignal(pos))
            return;

        be.tickCounter++;

        if (be.tickCounter >= 20) {
            be.tickCounter = 0;
            BlockPos abovePos = pos.above();
            BlockState aboveState = level.getBlockState(abovePos);
            Container container;

            if (aboveState.getBlock() instanceof ChestBlock) {
                container = ChestBlock.getContainer((ChestBlock) aboveState.getBlock(), aboveState, level, abovePos, true);
            } else {
                BlockEntity aboveEntity = level.getBlockEntity(abovePos);
                container = (aboveEntity instanceof Container) ? (Container) aboveEntity : null;
            }

            if (container == null)
                return;

            if (ALL_ITEMS == null) {
                // Filter: Keep items that do NOT stack (max stack size == 1)
                ALL_ITEMS = new ArrayList<>(BuiltInRegistries.ITEM.stream()
                        .filter(item -> new ItemStack(item).getMaxStackSize() == 1)
                        .toList());
            }

            if (ALL_ITEMS.isEmpty())
                return;

            // Primary Item Generation
            Item randomNonStackableItem = ALL_ITEMS.get(level.random.nextInt(ALL_ITEMS.size()));
            ItemStack stack = new ItemStack(randomNonStackableItem, 1);

            // Attempt to insert the item
            tryInsertOneItem(container, stack);
            // Note: Since these items don't stack, the secondary duplication logic
            // is not needed/effective, so we can stop here.
        }
    }

}