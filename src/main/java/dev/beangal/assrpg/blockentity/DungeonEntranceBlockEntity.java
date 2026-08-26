package dev.beangal.assrpg.blockentity;

import dev.beangal.assrpg.registry.AssRPGBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.awt.*;
import java.util.Objects;

public class DungeonEntranceBlockEntity extends BlockEntity {
    public int target_x = 0;
    public int target_y = 0;
    public int target_z = 0;
    public ResourceKey<Level> target_dimension = ResourceKey.create(Registries.DIMENSION, ResourceLocation.withDefaultNamespace("overworld"));

    public DungeonEntranceBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(AssRPGBlockEntities.DUNGEON_ENTERANCE_BLOCK_ENTITY, blockPos, blockState);
    }

    public ResourceKey<Level> getTargetDimension() {
        return target_dimension;
    }

    public BlockPos getTargetPos() {
        return new BlockPos(target_x, target_y, target_z);
    }

    @Override
    protected void loadAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        target_x = compoundTag.getInt("target_x");
        target_y = compoundTag.getInt("target_y");
        target_z = compoundTag.getInt("target_z");
        target_dimension = ResourceKey.create(Registries.DIMENSION, Objects.requireNonNullElse(ResourceLocation.tryParse(compoundTag.getString("target_dimension")), ResourceLocation.withDefaultNamespace("overworld")));

        super.loadAdditional(compoundTag, provider);
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag, HolderLookup.Provider provider) {
        compoundTag.putInt("target_x", target_x);
        compoundTag.putInt("target_y", target_y);
        compoundTag.putInt("target_z", target_z);
        compoundTag.putString("target_dimension", target_dimension.location().toString());

        super.saveAdditional(compoundTag, provider);
    }
}
