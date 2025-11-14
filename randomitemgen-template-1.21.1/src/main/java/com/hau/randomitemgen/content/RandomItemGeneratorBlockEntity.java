// --- FILE: .\src\main\java\com\hau\randomitemgen\content\RandomItemGeneratorBlockEntity.java ---
package com.hau.randomitemgen.content;

import com.hau.randomitemgen.register.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.properties.ChestType;

import java.util.ArrayList;
import java.util.List;

public class RandomItemGeneratorBlockEntity extends BlockEntity {

    private int tickCounter = 0;
    private static List<Item> ALL_ITEMS = null;

    public RandomItemGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RANDOM_ITEM_GENERATOR.get(), pos, state);
    }
    
    // NEW HELPER METHOD: Attempts to insert one item into the container
    private static boolean tryInsertOneItem(Container container, ItemStack stackToInsert) {
        // Attempt to insert the item into any available slot/stack
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack slot = container.getItem(i);

            // 1. Check for empty slot
            if (slot.isEmpty()) {
                // IMPORTANT: Use copy to ensure we don't accidentally link or mutate the source stack
                container.setItem(i, stackToInsert.copy());
                container.setChanged();
                return true;
            } 
            // 2. Check for matching, non-full stack
            else if (slot.getItem() == stackToInsert.getItem() && slot.getCount() < slot.getMaxStackSize()) {
                slot.grow(1);
                container.setChanged();
                return true;
            }
        }
        return false; // Failed to insert
    }

    public static void tick(Level level, BlockPos pos, BlockState state, RandomItemGeneratorBlockEntity be) {
        if (level.isClientSide) return;
        if (!level.hasNeighborSignal(pos)) return; // Redstone check

        be.tickCounter++;

        if (be.tickCounter >= 20) {
            be.tickCounter = 0;

            // --- DOUBLE CHEST RESOLUTION FIX (from previous step) ---
            BlockPos abovePos = pos.above();
            BlockState aboveState = level.getBlockState(abovePos);
            Container container;

            if (aboveState.getBlock() instanceof ChestBlock) {
                // Resolves to a single or double chest wrapper
                container = ChestBlock.getContainer((ChestBlock) aboveState.getBlock(), aboveState, level, abovePos, true);
            } else {
                BlockEntity aboveEntity = level.getBlockEntity(abovePos);
                container = (aboveEntity instanceof Container) ? (Container) aboveEntity : null;
            }

            if (container == null) return;
            // --- END DOUBLE CHEST FIX ---

            if (ALL_ITEMS == null) {
                ALL_ITEMS = new ArrayList<>(ForgeRegistries.ITEMS.getValues());
            }

            if (ALL_ITEMS.isEmpty()) return;

            // 1. Generate the primary random item
            Item primaryRandomItem = ALL_ITEMS.get(level.random.nextInt(ALL_ITEMS.size()));
            ItemStack primaryStack = new ItemStack(primaryRandomItem, 1);

            // 2. Try to insert the primary item. If successful, we're done.
            if (tryInsertOneItem(container, primaryStack)) {
                return;
            }

            // 3. PRIMARY INSERTION FAILED (Container is "full" for the primary item).
            //    We now search for an existing, non-full item to duplicate/stack with.
            List<Item> nonFullItems = new ArrayList<>();
            for (int i = 0; i < container.getContainerSize(); i++) {
                ItemStack slot = container.getItem(i);
                
                // Collect all item types that are present and not fully stacked
                if (!slot.isEmpty() && slot.getCount() < slot.getMaxStackSize()) {
                    if (!nonFullItems.contains(slot.getItem())) {
                        nonFullItems.add(slot.getItem());
                    }
                }
            }

            // 4. If we found at least one item that can still be stacked
            if (!nonFullItems.isEmpty()) {
                // Select a random item from the list of stackable items currently in the container
                Item itemToDuplicate = nonFullItems.get(level.random.nextInt(nonFullItems.size()));
                ItemStack secondaryStack = new ItemStack(itemToDuplicate, 1);

                // 5. Insert the secondary item. This is guaranteed to succeed since we know a spot exists.
                tryInsertOneItem(container, secondaryStack);
            }
        }
    }
}
