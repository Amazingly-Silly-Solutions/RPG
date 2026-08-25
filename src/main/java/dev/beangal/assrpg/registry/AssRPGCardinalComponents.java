package dev.beangal.assrpg.registry;

import dev.beangal.assrpg.AssRPG;
import dev.beangal.assrpg.component.ProtectedComponent;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;

public class AssRPGCardinalComponents {
    public static final ComponentKey<ProtectedComponent> PROTECTED = ComponentRegistry.getOrCreate(AssRPG.id("protected"), ProtectedComponent.class);
}
