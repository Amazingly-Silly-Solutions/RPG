package dev.beangal.assrpg.mixin;


import dev.beangal.assrpg.registry.AssRPGCardinalComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Level.class)
public class LevelMixin {
    @Inject(method = "mayInteract", at = @At("HEAD"), cancellable = true)
    private void preventPlayerInteract(Player player, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (player.isCreative()) {
            return;
        }

        Level level = (Level) (Object) this;
        ChunkAccess chunk = level.getChunk(pos);

        if (AssRPGCardinalComponents.PROTECTED.get(chunk).get()) {
            cir.setReturnValue(false);
        }
    }
}
