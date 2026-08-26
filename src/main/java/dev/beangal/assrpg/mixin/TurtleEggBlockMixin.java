package dev.beangal.assrpg.mixin;

import dev.beangal.assrpg.AssRPGUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TurtleEggBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TurtleEggBlock.class)
public class TurtleEggBlockMixin {
    @Inject(method = "stepOn", at = @At("HEAD"), cancellable = true)
    private void preventStepInProtectedChunks(Level level, BlockPos blockPos, BlockState blockState, Entity entity, CallbackInfo ci) {
        if (AssRPGUtils.isChunkProtected(level.getChunk(blockPos))) {
            ci.cancel();
        }
    }

    @Inject(method = "fallOn", at = @At("HEAD"), cancellable = true)
    private void preventFallInProtectedChunks(Level level, BlockState blockState, BlockPos blockPos, Entity entity, float f, CallbackInfo ci) {
        if (AssRPGUtils.isChunkProtected(level.getChunk(blockPos))) {
            ci.cancel();
        }
    }
}
