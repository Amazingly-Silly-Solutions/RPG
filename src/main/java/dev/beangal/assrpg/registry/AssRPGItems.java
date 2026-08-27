package dev.beangal.assrpg.registry;

import dev.beangal.assrpg.AssRPG;
import dev.beangal.assrpg.item.CoinItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class AssRPGItems {
    public static final Item COIN = register(CoinItem::new, new Item.Properties(), "coin");

    public static <T extends Item> T register(Function< Item.Properties, T> constructor, Item.Properties properties, String name) {
        ResourceLocation id = AssRPG.id(name);

        return Registry.register(BuiltInRegistries.ITEM, id, constructor.apply(properties.setId(ResourceKey.create(Registries.ITEM, id))));
    }

    public static void initialize() {}
}
