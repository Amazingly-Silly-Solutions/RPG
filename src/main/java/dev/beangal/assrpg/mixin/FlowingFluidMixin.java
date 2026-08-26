package dev.beangal.assrpg.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static dev.beangal.assrpg.AssRPGUtils.isEnteringProtectedChunk;

@Mixin(FlowingFluid.class)
public abstract class FlowingFluidMixin {
    @Inject(method = "spreadTo", at = @At("HEAD"), cancellable = true)
    private void cancelLiquidSpread(LevelAccessor levelAccessor, BlockPos blockPos, BlockState blockState, Direction direction, FluidState fluidState, CallbackInfo ci) {
        if (!(levelAccessor instanceof ServerLevel serverLevel)) { return; }

        BlockPos prevPos = blockPos.relative(direction.getOpposite());

        if (isEnteringProtectedChunk(prevPos, blockPos, serverLevel)) {
            ci.cancel();
        }
    }
}
