package dev.beangal.assrpg.registry;

import dev.beangal.assrpg.AssRPG;
import dev.beangal.assrpg.block.DungeonEntranceBlock;
import dev.beangal.assrpg.block.InvisibleSupportBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

@SuppressWarnings("unused")
public class AssRPGBlocks {
    public static final Block DUNGEON_ENTRANCE = register(DungeonEntranceBlock::new, BlockBehaviour.Properties.of().sound(SoundType.GLASS).strength(-1f, 30000f), "dungeon_entrance", true);
    public static final Block INVISIBLE_SUPPORT = register(InvisibleSupportBlock::new, BlockBehaviour.Properties.of().sound(SoundType.GLASS).strength(0f, 1f), "invisible_support", true);

    @SuppressWarnings("unchecked")
    public static <T extends Block> T register(Function<BlockBehaviour.Properties, T> constructor, BlockBehaviour.Properties properties, String name, boolean shouldRegisterItem) {
        ResourceLocation id = AssRPG.id(name);
        Block block = constructor.apply(properties.setId(ResourceKey.create(Registries.BLOCK, id)));

        if (shouldRegisterItem) {
            BlockItem blockItem = new BlockItem(block, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id)));
            Registry.register(BuiltInRegistries.ITEM, id, blockItem);
        }

        return (T) Registry.register(BuiltInRegistries.BLOCK, id, block);
    }

    public static void initialize() {}
}
