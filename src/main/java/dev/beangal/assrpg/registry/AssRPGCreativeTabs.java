package dev.beangal.assrpg.registry;

import dev.beangal.assrpg.AssRPG;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class AssRPGCreativeTabs {
    public static final ResourceKey<CreativeModeTab> BLOCKS_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), AssRPG.id("blocks_group"));
    public static final CreativeModeTab BLOCKS_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(AssRPGBlocks.INVISIBLE_SUPPORT))
            .title(Component.translatable("itemGroup.assrpg.blocks_group"))
            .build();
    public static final ResourceKey<CreativeModeTab> ITEMS_GROUP_KEY = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), AssRPG.id("items_group"));
    public static final CreativeModeTab ITEMS_GROUP = FabricItemGroup.builder()
            .icon(() -> new ItemStack(AssRPGItems.COIN))
            .title(Component.translatable("itemGroup.assrpg.items_group"))
            .build();

    public static void initialize() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, BLOCKS_GROUP_KEY, BLOCKS_GROUP);
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ITEMS_GROUP_KEY, ITEMS_GROUP);

        ItemGroupEvents.modifyEntriesEvent(BLOCKS_GROUP_KEY).register(itemGroup -> itemGroup.accept(AssRPGBlocks.INVISIBLE_SUPPORT));
        ItemGroupEvents.modifyEntriesEvent(ITEMS_GROUP_KEY).register(itemGroup -> itemGroup.accept(AssRPGItems.COIN));
    }
}
