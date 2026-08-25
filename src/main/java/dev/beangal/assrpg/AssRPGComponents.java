package dev.beangal.assrpg;

import dev.beangal.assrpg.component.ProtectedComponent;
import dev.beangal.assrpg.registry.AssRPGCardinalComponents;
import org.ladysnake.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.chunk.ChunkComponentInitializer;

public class AssRPGComponents implements ChunkComponentInitializer {
    @Override
    public void registerChunkComponentFactories(ChunkComponentFactoryRegistry registry) {
        registry.register(AssRPGCardinalComponents.PROTECTED, ProtectedComponent::new);
    }
}
