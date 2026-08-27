package dev.beangal.assrpg.component;

import dev.beangal.assrpg.registry.AssRPGCardinalComponents;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;

public class CoinsComponent implements IntComponent, AutoSyncedComponent {
    private int value;
    private final Player player;

    @SuppressWarnings("unused")
    public CoinsComponent(Player player) {
        this.value = 0;
        this.player = player;
    }

    @Override
    public void readFromNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
        this.value = tag.getInt("assrpg:coins");
    }

    @Override
    public void writeToNbt(CompoundTag tag, HolderLookup.Provider registryLookup) {
        tag.putInt("assrpg:coins", this.value);
    }

    @Override
    public int get() {
        return this.value;
    }

    @Override
    public void set(int val) {
        this.value = val;
        this.sync();
    }

    @Override
    public int add(int amount) {
        this.value += amount;
        this.sync();
        return this.value;
    }

    private void sync() {
        // Only sync if we are processing on a real logical server
        if (this.player != null && !this.player.level().isClientSide()) {
            AssRPGCardinalComponents.COINS.sync(this.player);
        }
    }
}
