package dev.beangal.assrpg.client;

import dev.beangal.assrpg.client.renderer.DungeonEntranceRenderer;
import dev.beangal.assrpg.registry.AssRPGBlockEntities;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;

public class AssRPGBlockEntityRenderers {
    public static void initialize() {
        BlockEntityRenderers.register(
                AssRPGBlockEntities.DUNGEON_ENTERANCE_BLOCK_ENTITY,
                DungeonEntranceRenderer::new
        );
    }
}
