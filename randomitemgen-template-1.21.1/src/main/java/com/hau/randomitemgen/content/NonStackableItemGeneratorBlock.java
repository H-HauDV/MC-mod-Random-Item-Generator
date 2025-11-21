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

public class NonStackableItemGeneratorBlock extends BaseEntityBlock {

    public static final MapCodec<NonStackableItemGeneratorBlock> CODEC = BlockBehaviour
            .simpleCodec(NonStackableItemGeneratorBlock::new);

    public NonStackableItemGeneratorBlock(Properties props) {
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
        // Must return the Non-Stackable entity
        return new NonStackableItemGeneratorBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            Level level, BlockState state, BlockEntityType<T> type) {

        // Check for NON_STACKABLE BlockEntityType
        return !level.isClientSide && type == ModBlockEntities.NON_STACKABLE_ITEM_GENERATOR_TYPE.get()
                // Cast to the correct BlockEntity type
                ? (lvl, pos, st, be) -> NonStackableItemGeneratorBlockEntity.tick(lvl, pos, st,
                        (NonStackableItemGeneratorBlockEntity) be)
                : null;
    }
}