package dev.beangal.assrpg;

import dev.beangal.assrpg.registry.AssRPGCardinalComponents;
import net.minecraft.world.level.chunk.ChunkAccess;

public class AssRPGUtils {
    public static boolean isChunkProtected(ChunkAccess chunk) {
        return AssRPGCardinalComponents.PROTECTED.get(chunk).get();
    }
}
