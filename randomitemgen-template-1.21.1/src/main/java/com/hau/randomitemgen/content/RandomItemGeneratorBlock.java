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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import com.mojang.serialization.MapCodec;

public class RandomItemGeneratorBlock extends BaseEntityBlock {

    // ✅ THE DEFINITIVE FINAL FIX: simpleCodec is static on BlockBehaviour, not BlockBehaviour.Properties
    public static final MapCodec<RandomItemGeneratorBlock> CODEC = BlockBehaviour.simpleCodec(RandomItemGeneratorBlock::new);
    
    public RandomItemGeneratorBlock(Properties props) {
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
        return new RandomItemGeneratorBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            Level level, BlockState state, BlockEntityType<T> type) {

        // Note: Assumes ModBlockEntities.RANDOM_ITEM_GENERATOR_TYPE.get() is a BlockEntityType
        return !level.isClientSide && type == ModBlockEntities.RANDOM_ITEM_GENERATOR_TYPE.get() 
                ? (lvl, pos, st, be) -> RandomItemGeneratorBlockEntity.tick(lvl, pos, st, (RandomItemGeneratorBlockEntity) be)
                : null;
    }
}