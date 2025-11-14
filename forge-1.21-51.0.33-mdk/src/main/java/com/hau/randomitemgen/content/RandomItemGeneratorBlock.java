package com.hau.randomitemgen.content;

import java.util.Properties;

import java.util.Properties;

package com.hau.randomitemgen.content;

import java.util.Properties;

import com.hau.randomitemgen.register.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class RandomItemGeneratorBlock extends BaseEntityBlock {

    public static final Codec<RandomItemGeneratorBlock> CODEC = BlockBehaviour.simpleCodec(RandomItemGeneratorBlock::new);

    public RandomItemGeneratorBlock(Properties props) {
        super(props);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return MapCodec.unit(() -> this);
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

        return !level.isClientSide && type == ModBlockEntities.RANDOM_ITEM_GENERATOR_TYPE.get() 
                ? (lvl, pos, st, be) -> RandomItemGeneratorBlockEntity.tick(lvl, pos, st, (RandomItemGeneratorBlockEntity) be)
                : null;
    }
}