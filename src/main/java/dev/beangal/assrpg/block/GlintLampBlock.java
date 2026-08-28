package dev.beangal.assrpg.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.redstone.Orientation;
import org.jetbrains.annotations.Nullable;

public class GlintLampBlock extends Block {
    public static final BooleanProperty LIT = BooleanProperty.create("lit");

    public GlintLampBlock(Properties properties) {
        super(properties);

        registerDefaultState(defaultBlockState().setValue(LIT, true));
    }

    @Override
    protected void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, @Nullable Orientation orientation, boolean bl) {
        if (level.isClientSide()) { return; }

        boolean lit = blockState.getValue(LIT);
        if (lit != level.hasNeighborSignal(blockPos)) {
            level.setBlock(blockPos, blockState.setValue(LIT, !lit), Block.UPDATE_CLIENTS);
        }
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        return this.defaultBlockState().setValue(LIT, blockPlaceContext.getLevel().hasNeighborSignal(blockPlaceContext.getClickedPos()));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }
}
