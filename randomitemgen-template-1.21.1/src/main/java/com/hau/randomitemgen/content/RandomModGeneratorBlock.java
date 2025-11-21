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

public class RandomModGeneratorBlock extends BaseEntityBlock {

    public static final MapCodec<RandomModGeneratorBlock> CODEC = BlockBehaviour
            .simpleCodec(RandomModGeneratorBlock::new);

    public RandomModGeneratorBlock(Properties props) {
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
        return new RandomModGeneratorBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            Level level, BlockState state, BlockEntityType<T> type) {

        return !level.isClientSide && type == ModBlockEntities.RANDOM_MOD_GENERATOR_TYPE.get()
                ? (lvl, pos, st, be) -> RandomModGeneratorBlockEntity.tick(lvl, pos, st,
                        (RandomModGeneratorBlockEntity) be)
                : null;
    }
}