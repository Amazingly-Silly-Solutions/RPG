package dev.beangal.assrpg.block;

import dev.beangal.assrpg.blockentity.DungeonEntranceBlockEntity;
import dev.beangal.assrpg.component.DungeonTimerComponent;
import dev.beangal.assrpg.registry.AssRPGCardinalComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;

public class DungeonEntranceBlock extends Block implements EntityBlock {
    public DungeonEntranceBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        if (collisionContext instanceof EntityCollisionContext entityContext) {
            Entity entity = entityContext.getEntity();
            if (entity instanceof Player player) {
                if (player.isCreative() || player.isSpectator()) {
                    return Shapes.block();
                }
            }
        }

        return Shapes.empty();
    }

    @Override
    protected @NotNull VoxelShape getCollisionShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        return Shapes.empty();
    }

    @Override
    protected @NotNull RenderShape getRenderShape(BlockState blockState) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new DungeonEntranceBlockEntity(blockPos, blockState);
    }

    @Override
    protected void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (!level.isClientSide() && entity instanceof ServerPlayer player) {
            if (level.getBlockEntity(blockPos) instanceof DungeonEntranceBlockEntity blockEntity) {
                DungeonTimerComponent timer = AssRPGCardinalComponents.DUNGEON_TIMER.get(player);
                timer.setInsidePortal(true);

                if (timer.getTicks() >= 60) {
                    timer.setTicks(0);

                    ServerLevel destWorld = player.server.getLevel(blockEntity.getTargetDimension());
                    BlockPos destPos = blockEntity.getTargetPos();

                    if (destWorld != null) {
                        player.teleportTo(
                                destWorld,
                                destPos.getX(),
                                destPos.getY(),
                                destPos.getZ(),
                                Collections.emptySet(),
                                player.getYRot(),
                                player.getXRot(),
                                true
                        );
                    }
                }
            }
        }
    }
}
