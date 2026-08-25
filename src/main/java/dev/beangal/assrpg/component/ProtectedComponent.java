package dev.beangal.assrpg.component;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class ProtectedComponent implements BoolComponent, AutoSyncedComponent {
    private boolean value = false;
    private final ChunkAccess chunk;

    public ProtectedComponent(ChunkAccess chunk) {
        this.chunk = chunk;
    }

    @Override
    public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
        this.value = tag.getBoolean("assrpg:protected");
    }

    @Override
    public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
        tag.putBoolean("assrpg:protected", this.value);
    }

    @Override
    public boolean get() {
        return this.value;
    }

    @Override
    public void set(boolean val) {
        this.value = val;
        this.chunk.markUnsaved();
    }
}
