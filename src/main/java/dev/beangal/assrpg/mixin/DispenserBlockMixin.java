package dev.beangal.assrpg.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.beangal.assrpg.AssRPGUtils.isEnteringProtectedChunk;

@Mixin(DispenserBlock.class)
public class DispenserBlockMixin {
    @Inject(method = "dispenseFrom", at = @At("HEAD"), cancellable = true)
    private void blockProtectedDispensation(ServerLevel serverLevel, BlockState blockState, BlockPos blockPos, CallbackInfo ci) {
        BlockPos destinationPos = blockPos.relative(blockState.getValue(DispenserBlock.FACING));
        DispenserBlockEntity dispenserBlockEntity = serverLevel.getBlockEntity(blockPos, BlockEntityType.DISPENSER).orElse(null);

        if (dispenserBlockEntity != null && isEnteringProtectedChunk(blockPos, destinationPos, serverLevel)) {
            serverLevel.levelEvent(LevelEvent.SOUND_DISPENSER_FAIL, blockPos, 0);
            serverLevel.gameEvent(GameEvent.BLOCK_ACTIVATE, blockPos, GameEvent.Context.of(blockState));
            ci.cancel();
        }
    }
}
