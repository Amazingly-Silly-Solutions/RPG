package dev.beangal.assrpg.mixin;

import dev.beangal.assrpg.AssRPGUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.monster.EnderMan;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderMan.class)
public class EnderManMixin {
    @Mixin(targets = "net.minecraft.world.entity.monster.EnderMan$EndermanTakeBlockGoal")
    public static class TakeBlockMixin {
        @Shadow
        @Final
        private EnderMan enderman;

        @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
        private void canUse(CallbackInfoReturnable<Boolean> cir) {
            if (AssRPGUtils.isChunkProtected(this.enderman.level().getChunk(BlockPos.containing(this.enderman.getPosition(0f))))) {
                cir.setReturnValue(false);
            }
        }
    }

    @Mixin(targets = "net.minecraft.world.entity.monster.EnderMan$EndermanLeaveBlockGoal")
    public static class LeaveBlockMixin {
        @Shadow
        @Final
        private EnderMan enderman;

        @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
        private void canUse(CallbackInfoReturnable<Boolean> cir) {
            if (AssRPGUtils.isChunkProtected(this.enderman.level().getChunk(BlockPos.containing(this.enderman.getPosition(0f))))) {
                cir.setReturnValue(false);
            }
        }
    }
}
