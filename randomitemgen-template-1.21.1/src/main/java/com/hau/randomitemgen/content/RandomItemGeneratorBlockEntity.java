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
import net.minecraft.world.level.block.state.properties.ChestType;

import java.util.ArrayList;
import java.util.List;

public class RandomItemGeneratorBlockEntity extends BlockEntity {

private int tickCounter = 0;
    private static List<Item> ALL_ITEMS = null;

    public RandomItemGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RANDOM_ITEM_GENERATOR_TYPE.get(), pos, state);
    }

    private static boolean tryInsertOneItem(Container container, ItemStack stackToInsert) {
        // ... (Helper method unchanged) ...
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack slot = container.getItem(i);

            if (slot.isEmpty()) {
                container.setItem(i, stackToInsert.copy());
                container.setChanged();
                return true;
            } 
            else if (slot.getItem() == stackToInsert.getItem() && slot.getCount() < slot.getMaxStackSize()) {
                slot.grow(1);
                container.setChanged();
                return true;
            }
        }
        return false;
    }

    public static void tick(Level level, BlockPos pos, BlockState state, RandomItemGeneratorBlockEntity be) {
        if (level.isClientSide) return;
        if (!level.hasNeighborSignal(pos)) return;

        be.tickCounter++;

        if (be.tickCounter >= 20) {
            be.tickCounter = 0;

            // --- DOUBLE CHEST RESOLUTION FIX (from previous step) ---
            BlockPos abovePos = pos.above();
            BlockState aboveState = level.getBlockState(abovePos);
            Container container;

            if (aboveState.getBlock() instanceof ChestBlock) {
                container = ChestBlock.getContainer((ChestBlock) aboveState.getBlock(), aboveState, level, abovePos, true);
            } else {
                BlockEntity aboveEntity = level.getBlockEntity(abovePos);
                container = (aboveEntity instanceof Container) ? (Container) aboveEntity : null;
            }

            if (container == null) return;
            // --- END DOUBLE CHEST FIX ---

            if (ALL_ITEMS == null) {
                ALL_ITEMS = new ArrayList<>(BuiltInRegistries.ITEM.stream().toList());
            }
            
            if (ALL_ITEMS.isEmpty()) return;

            // ... (rest of the tick method unchanged) ...
            Item primaryRandomItem = ALL_ITEMS.get(level.random.nextInt(ALL_ITEMS.size()));
            ItemStack primaryStack = new ItemStack(primaryRandomItem, 1);

            if (tryInsertOneItem(container, primaryStack)) {
                return;
            }

            List<Item> nonFullItems = new ArrayList<>();
            for (int i = 0; i < container.getContainerSize(); i++) {
                ItemStack slot = container.getItem(i);
                
                if (!slot.isEmpty() && slot.getCount() < slot.getMaxStackSize()) {
                    if (!nonFullItems.contains(slot.getItem())) {
                        nonFullItems.add(slot.getItem());
                    }
                }
            }

            if (!nonFullItems.isEmpty()) {
                Item itemToDuplicate = nonFullItems.get(level.random.nextInt(nonFullItems.size()));
                ItemStack secondaryStack = new ItemStack(itemToDuplicate, 1);
                tryInsertOneItem(container, secondaryStack);
            }
        }
    }
}