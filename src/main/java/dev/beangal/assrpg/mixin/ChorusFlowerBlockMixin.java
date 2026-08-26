package dev.beangal.assrpg.mixin;

import dev.beangal.assrpg.AssRPGUtils;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ChorusFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChorusFlowerBlock.class)
public class ChorusFlowerBlockMixin {
    @Inject(method = "onProjectileHit", at = @At("HEAD"), cancellable = true)
    private void preventBreakInProtectedChunks(Level level, BlockState blockState, BlockHitResult blockHitResult, Projectile projectile, CallbackInfo ci) {
        if (AssRPGUtils.isChunkProtected(level.getChunk(blockHitResult.getBlockPos()))) {
            ci.cancel();
        }
    }
}
