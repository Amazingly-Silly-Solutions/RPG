package dev.beangal.assrpg.mixin;

import dev.beangal.assrpg.AssRPGUtils;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DecoratedPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(DecoratedPotBlock.class)
public class DecoratedPotBlockMixin {
    @Inject(method = "getDrops", at = @At("HEAD"), cancellable = true)
    protected void preventSherdDrops(BlockState blockState, LootParams.Builder builder, CallbackInfoReturnable<List<ItemStack>> cir) {
        cir.setReturnValue(List.of());
    }

    @Inject(method = "onProjectileHit", at = @At("HEAD"), cancellable = true)
    private void preventBreakInProtectedChunks(Level level, BlockState blockState, BlockHitResult blockHitResult, Projectile projectile, CallbackInfo ci) {
        if (AssRPGUtils.isChunkProtected(level.getChunk(blockHitResult.getBlockPos()))) {
            ci.cancel();
        }
    }
}
