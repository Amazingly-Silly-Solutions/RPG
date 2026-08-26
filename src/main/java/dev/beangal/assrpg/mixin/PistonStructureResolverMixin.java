package dev.beangal.assrpg.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static dev.beangal.assrpg.AssRPGUtils.isCrossingProtectionBoundary;

@Mixin(PistonStructureResolver.class)
public class PistonStructureResolverMixin {
    @Shadow
    @Final
    private Level level;

    @Shadow
    @Final
    private List<BlockPos> toPush;

    @Shadow
    @Final
    private Direction pushDirection;

    @Shadow
    @Final
    private BlockPos pistonPos;

    @Inject(method = "resolve", at = @At("TAIL"), cancellable = true)
    public void protectAgainstPush(CallbackInfoReturnable<Boolean> cir) {
        if (isCrossingProtectionBoundary(this.pistonPos, (this.pistonPos.relative(this.pushDirection)), this.level)) {
            cir.setReturnValue(false);
        }

        for (BlockPos sourcePos : this.toPush) {
            BlockPos destinationPos = sourcePos.relative(this.pushDirection);

            if (isCrossingProtectionBoundary(sourcePos, destinationPos, this.level)) {
                cir.setReturnValue(false);
            }
        }

    }
}
