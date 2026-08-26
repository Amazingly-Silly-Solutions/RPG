package dev.beangal.assrpg.registry;

import dev.beangal.assrpg.AssRPG;
import dev.beangal.assrpg.blockentity.DungeonEntranceBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class AssRPGBlockEntities {
    public static final BlockEntityType<DungeonEntranceBlockEntity> DUNGEON_ENTERANCE_BLOCK_ENTITY = register("dungeon_entrance", DungeonEntranceBlockEntity::new,
            AssRPGBlocks.DUNGEON_ENTRANCE);


    public static <T extends BlockEntity> BlockEntityType<T> register(String name,
                                                                      FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
                                                                      Block... blocks) {
        ResourceLocation id = AssRPG.id(name);
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }

    public static void initialize() {}
}
