package dev.beangal.assrpg;

import dev.beangal.assrpg.component.CoinsComponent;
import dev.beangal.assrpg.component.DungeonTimerComponent;
import dev.beangal.assrpg.component.ProtectedComponent;
import dev.beangal.assrpg.registry.AssRPGCardinalComponents;
import org.ladysnake.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.chunk.ChunkComponentInitializer;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

@SuppressWarnings("unused")
public class AssRPGComponents implements ChunkComponentInitializer, EntityComponentInitializer {
    @Override
    public void registerChunkComponentFactories(ChunkComponentFactoryRegistry registry) {
        registry.register(AssRPGCardinalComponents.PROTECTED, ProtectedComponent::new);
    }

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(AssRPGCardinalComponents.DUNGEON_TIMER, DungeonTimerComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
        registry.registerForPlayers(AssRPGCardinalComponents.COINS, CoinsComponent::new, RespawnCopyStrategy.LOSSLESS_ONLY);
    }
}
