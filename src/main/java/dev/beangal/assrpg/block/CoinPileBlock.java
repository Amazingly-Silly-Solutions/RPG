package dev.beangal.assrpg.block;

import dev.beangal.assrpg.registry.AssRPGCardinalComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

public class CoinPileBlock extends Block {
    public CoinPileBlock(Properties properties) {
        super(properties);
    }
    private static final VoxelShape SHAPE = Shapes.block();
    private static final VoxelShape COLLISION = Shapes.empty();

    @Override
    protected @NotNull VoxelShape getOcclusionShape(BlockState blockState) {
        return COLLISION;
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return SHAPE;
    }

    @Override
    protected @NotNull VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return COLLISION;
    }

    @Override
    protected @NotNull RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.MODEL;
    }

    @Override
    protected void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (!level.isClientSide() && entity instanceof ServerPlayer player) {
            AssRPGCardinalComponents.COINS.get(player).add(level.getRandom().nextInt(5) + 8);
            level.setBlockAndUpdate(blockPos, Blocks.AIR.defaultBlockState());
        }
    }
}
