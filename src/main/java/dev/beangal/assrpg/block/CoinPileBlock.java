package dev.beangal.assrpg.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.TickTask;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class CoinPileBlock extends FallingBlock {
    public static final int MAX_SIZE = 5;
    private static final VoxelShape SHAPE = Shapes.box(0.1, 0.0, 0.1, 0.9, 1.0, 0.9);
    public static final IntegerProperty COINS = IntegerProperty.create("coins", 1, MAX_SIZE);
    public static final MapCodec<CoinPileBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(propertiesCodec()).apply(instance, CoinPileBlock::new)
    );

    public CoinPileBlock(Properties properties) {
        super(properties);

        registerDefaultState(defaultBlockState().setValue(COINS, 1));
    }

    @Override
    protected @NotNull MapCodec<? extends FallingBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(COINS);
    }

    @Override
    protected @NotNull VoxelShape getOcclusionShape(BlockState blockState) {
        return SHAPE;
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    protected @NotNull VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    protected @NotNull RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        BlockPos belowPos = blockPos.below();
        BlockState belowState = serverLevel.getBlockState(belowPos);
        int currentCoins = blockState.getValue(COINS);

        if (belowState.is(this)) {
            int belowCoins = belowState.getValue(COINS);

            if (belowCoins < MAX_SIZE) {
                int spaceLeft = MAX_SIZE - belowCoins;
                int transfer = Math.min(spaceLeft, currentCoins);

                int newBelowCoins = belowCoins + transfer;
                int newCurrentCoins = currentCoins - transfer;

                serverLevel.setBlockAndUpdate(belowPos, belowState.setValue(COINS, newBelowCoins));

                if (newCurrentCoins > 0) {
                    serverLevel.setBlockAndUpdate(blockPos, blockState.setValue(COINS, newCurrentCoins));
                    serverLevel.scheduleTick(blockPos, this, 2);
                } else {
                    serverLevel.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
                }
            }
        }

        super.tick(blockState, serverLevel, blockPos, randomSource);
    }

    @Override
    public @NotNull BlockState playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player) {
        int currentCoins = blockState.getValue(COINS);

        if (currentCoins > 1) {
            BlockState newState = blockState.setValue(COINS, currentCoins - 1);
            level.setBlock(blockPos, newState, Block.UPDATE_ALL);
            level.levelEvent(player, LevelEvent.PARTICLES_DESTROY_BLOCK, blockPos, Block.getId(blockState));

            if (level instanceof ServerLevel serverLevel) {
                serverLevel.getServer().schedule(new TickTask(serverLevel.getServer().getTickCount(), () -> {
                    serverLevel.setBlock(blockPos, newState, Block.UPDATE_ALL);
                }));
            }

            return newState;
        } else {
            return super.playerWillDestroy(level, blockPos, blockState, player);
        }
    }
}
