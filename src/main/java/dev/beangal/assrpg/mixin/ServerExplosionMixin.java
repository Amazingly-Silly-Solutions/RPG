package dev.beangal.assrpg.mixin;

import dev.beangal.assrpg.registry.AssRPGCardinalComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerExplosion;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ServerExplosion.class)
public class ServerExplosionMixin {
    @Shadow @Final private ServerLevel level;

    @Inject(method = "interactWithBlocks", at = @At("HEAD"))
    private void filterBlocksBeforeDestruction(List<BlockPos> list, CallbackInfo ci) {
        list.removeIf(pos -> {
            ChunkAccess chunk = this.level.getChunk(pos);
            return AssRPGCardinalComponents.PROTECTED.get(chunk).get();
        });
    }
}
