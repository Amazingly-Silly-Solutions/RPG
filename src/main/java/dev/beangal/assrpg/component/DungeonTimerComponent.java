package dev.beangal.assrpg.component;

import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import org.ladysnake.cca.api.v3.component.Component;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public class DungeonTimerComponent implements Component, ServerTickingComponent {
    private int ticks = 0;
    private boolean isInsidePortal = false;

    @SuppressWarnings("unused")
    public DungeonTimerComponent(Player player) {
    }

    public int getTicks() { return this.ticks; }
    public void setTicks(int ticks) { this.ticks = ticks; }
    public void setInsidePortal(boolean inside) { this.isInsidePortal = inside; }

    @Override
    public void serverTick() {
        if (this.isInsidePortal) {
            ticks++;
        } else {
            ticks = 0;
        }

        this.isInsidePortal = false;
    }

    @Override
    public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {}

    @Override
    public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {}
}
