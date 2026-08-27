package dev.beangal.assrpg.item;

import dev.beangal.assrpg.registry.AssRPGCardinalComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CoinItem extends Item {
    public CoinItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> list, TooltipFlag tooltipFlag) {
        list.add(Component.translatable("lore.assrpg.coin"));
        super.appendHoverText(itemStack, tooltipContext, list, tooltipFlag);
    }

    @Override
    public @NotNull InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
        ItemStack stack = player.getItemInHand(interactionHand);
        AssRPGCardinalComponents.COINS.get(player).add(stack.getCount());
        player.setItemInHand(interactionHand, ItemStack.EMPTY);
        return InteractionResult.SUCCESS;
    }
}
