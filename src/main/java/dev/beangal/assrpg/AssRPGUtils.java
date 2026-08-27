package dev.beangal.assrpg;

import dev.beangal.assrpg.registry.AssRPGCardinalComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;

import static dev.beangal.assrpg.AssRPG.LOGGER;

public class AssRPGUtils {
    public static boolean isChunkProtected(ChunkAccess chunk) {
        if (chunk == null) {
            return false;
        }

        return AssRPGCardinalComponents.PROTECTED.get(chunk).get();
    }

    public static boolean isChunkProtected(BlockPos pos, Level level) {
        return isChunkProtected(level.getChunkSource().getChunk(SectionPos.blockToSectionCoord(pos.getX()), SectionPos.blockToSectionCoord(pos.getZ()), false));
    }

    public static boolean nextToProtectedChunk(BlockPos pos, Level level) {
        if (isChunkProtected(pos, level)) { LOGGER.info("prot"); return false; }

        int[][] directions = {
                {1, 0},  // East
                {-1, 0}, // West
                {0, 1},  // South
                {0, -1}  // North
        };

        for (int[] direction : directions) {
            if (isChunkProtected(pos.immutable().offset(direction[0], 0, direction[1]), level)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isEnteringProtectedChunk(BlockPos sourcePos, BlockPos destinationPos, Level level) {
        return nextToProtectedChunk(sourcePos, level) && isChunkProtected(destinationPos, level);
    }

    public static boolean isExitingProtectedChunk(BlockPos sourcePos, BlockPos destinationPos, Level level) {
        return nextToProtectedChunk(destinationPos, level) && isChunkProtected(sourcePos, level);
    }

    public static boolean isCrossingProtectionBoundary(BlockPos sourcePos, BlockPos destinationPos, Level level) {
        return isEnteringProtectedChunk(sourcePos, destinationPos, level) || isExitingProtectedChunk(sourcePos, destinationPos, level);
    }
}
