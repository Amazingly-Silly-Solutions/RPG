package dev.beangal.assrpg.mixin;

import dev.beangal.assrpg.registry.AssRPGCardinalComponents;
import it.unimi.dsi.fastutil.longs.Long2BooleanMap;
import it.unimi.dsi.fastutil.longs.Long2BooleanOpenHashMap;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerExplosion;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

// DevLog: Optimizing this - Byte
@Mixin(ServerExplosion.class)
public class ServerExplosionMixin {
    @Shadow @Final private ServerLevel level;

    @Inject(method = "interactWithBlocks", at = @At("HEAD"))
    private void filterBlocksBeforeDestruction(List<BlockPos> list, CallbackInfo ci) {
        if (list.isEmpty()) return;

        // cache exists only for this explosion, cache prevents checking same chunk repetitively.
        Long2BooleanMap chunkcache = new Long2BooleanOpenHashMap();

        list.removeIf(pos -> {
            // bitwise op for fast chunk loc lookups
            int chunkX = pos.getX() >> 4;
            int chunkZ = pos.getZ() >> 4;
            
            // combing chunk pos to be a single long for better mem efficiency. 
            long chunkKey = ChunkPos.asLong(chunkX, chunkZ);

            // fetches from cache if existent & if not then just check the chunk
            return chunkcache.computeIfAbsent(chunkKey, key -> {
                ChunkAccess chunk = this.level.getChunkSource().getChunk(chunkX, chunkZ, false);

                if (chunk == null) {
                    return false;
                }
                
                return AssRPGCardinalComponents.PROTECTED.get(chunk).get();
            });
        });
    }
}
