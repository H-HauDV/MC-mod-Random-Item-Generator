package com.hau.randomitemgen.content;

import com.hau.randomitemgen.register.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import com.mojang.serialization.MapCodec;

public class StackableItemGeneratorBlock extends BaseEntityBlock {

    public static final MapCodec<StackableItemGeneratorBlock> CODEC = BlockBehaviour
            .simpleCodec(StackableItemGeneratorBlock::new);

    public StackableItemGeneratorBlock(Properties props) {
        super(props);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        // FIX 1: Should return the correct, renamed BlockEntity
        return new StackableItemGeneratorBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            Level level, BlockState state, BlockEntityType<T> type) {

        // FIX 2: Check for the correct BlockEntityType
        return !level.isClientSide && type == ModBlockEntities.STACKABLE_ITEM_GENERATOR_TYPE.get()
                // FIX 3: Cast to the correct BlockEntity type
                ? (lvl, pos, st, be) -> StackableItemGeneratorBlockEntity.tick(lvl, pos, st,
                        (StackableItemGeneratorBlockEntity) be)
                : null;
    }
}