package com.hau.randomitemgen.content;

import com.hau.randomitemgen.register.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
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
import java.util.Map;
import java.util.stream.Collectors;

public class RandomModGeneratorBlockEntity extends BlockEntity {

    private int tickCounter = 0;
    // Map to store item lists, keyed by MOD ID (namespace)
    private static Map<String, List<Item>> MOD_ITEM_CACHE = null;

    public RandomModGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RANDOM_MOD_GENERATOR_TYPE.get(), pos, state);
    }

    /**
     * Tries to insert a single item into the container, handling stacking or finding an empty slot.
     */
    private static boolean tryInsertOneItem(Container container, ItemStack stackToInsert) {
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack slot = container.getItem(i);

            // 1. Try stacking/growing the existing slot
            if (slot.getItem() == stackToInsert.getItem() && slot.getCount() < slot.getMaxStackSize()) {
                slot.grow(1);
                container.setChanged();
                return true;
            }

            // 2. Try placing in an empty slot
            if (slot.isEmpty()) {
                container.setItem(i, stackToInsert.copy());
                container.setChanged();
                return true;
            }
        }
        return false;
    }

    /**
     * Scans the container to find the MOD ID (namespace) of the first non-empty item.
     * @param container The container to scan.
     * @return The mod namespace (e.g., "minecraft", "randomitemgen") or null if empty.
     */
    private static String getTargetModId(Container container) {
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack slot = container.getItem(i);
            if (!slot.isEmpty()) {
                ResourceLocation itemRL = BuiltInRegistries.ITEM.getKey(slot.getItem());
                return itemRL.getNamespace();
            }
        }
        return null; // Container is empty
    }

    /**
     * Initializes the cache of all items, grouped by mod ID.
     */
    private static void initializeModCache() {
        MOD_ITEM_CACHE = BuiltInRegistries.ITEM.stream()
                .filter(item -> !item.builtInRegistryHolder().key().location().getPath().contains("debug") 
                              && !item.builtInRegistryHolder().key().location().getPath().contains("jigsaw")
                              && !item.builtInRegistryHolder().key().location().getPath().contains("structure")) // Basic filtering for junk
                .collect(Collectors.groupingBy(item -> BuiltInRegistries.ITEM.getKey(item).getNamespace()));
    }


    public static void tick(Level level, BlockPos pos, BlockState state, RandomModGeneratorBlockEntity be) {
        if (level.isClientSide || !level.hasNeighborSignal(pos))
            return;

        be.tickCounter++;

        if (be.tickCounter >= 20) {
            be.tickCounter = 0;

            // --- Container Resolution (Standard logic) ---
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
            // --- End Container Resolution ---

            if (MOD_ITEM_CACHE == null) {
                initializeModCache();
            }
            if (MOD_ITEM_CACHE.isEmpty()) return;

            // 1. Determine the target mod ID
            String targetModId = getTargetModId(container);
            
            // If the chest is empty, pick a random mod ID to start
            if (targetModId == null) {
                List<String> modIds = new ArrayList<>(MOD_ITEM_CACHE.keySet());
                if (!modIds.isEmpty()) {
                    targetModId = modIds.get(level.random.nextInt(modIds.size()));
                } else {
                    return; // Should not happen if cache isn't empty
                }
            }

            // 2. Get the list of items for the target mod
            List<Item> modItems = MOD_ITEM_CACHE.get(targetModId);
            if (modItems == null || modItems.isEmpty()) return;
            
            // 3. Generate a random item from that mod
            Item randomItem = modItems.get(level.random.nextInt(modItems.size()));
            ItemStack stack = new ItemStack(randomItem, 1);

            // 4. Attempt to insert the item
            tryInsertOneItem(container, stack);
        }
    }
}