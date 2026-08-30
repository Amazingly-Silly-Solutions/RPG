package dev.beangal.assrpg.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import dev.beangal.assrpg.registry.AssRPGBlocks;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.level.Level;

public class DungeonEntranceCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> buildBranch() {
        return Commands.literal("dungeonentrance")
                .then(Commands.argument("target_pos", BlockPosArgument.blockPos())
                        .then(Commands.argument("target_dimension", DimensionArgument.dimension())
                                .then(Commands.argument("time", IntegerArgumentType.integer(0, 300))
                                    .executes(DungeonEntranceCommand::execute))));
    }

    private static int execute(CommandContext<CommandSourceStack> context) {
        CommandSourceStack source = context.getSource();

        if (!(source.getEntity() instanceof ServerPlayer player)) {
            source.sendFailure(Component.literal("§cThis command must be executed by a player."));
            return 0;
        }

        try {
            BlockPos targetPos = BlockPosArgument.getLoadedBlockPos(context, "target_pos");
            ServerLevel targetDimWorld = DimensionArgument.getDimension(context, "target_dimension");
            int time = IntegerArgumentType.getInteger(context, "time");
            ResourceKey<Level> targetDimKey = targetDimWorld.dimension();

            ItemStack entranceStack = new ItemStack(AssRPGBlocks.DUNGEON_ENTRANCE.asItem());

            CompoundTag blockEntityData = new CompoundTag();
            blockEntityData.putInt("target_x", targetPos.getX());
            blockEntityData.putInt("target_y", targetPos.getY());
            blockEntityData.putInt("target_z", targetPos.getZ());
            blockEntityData.putString("target_dimension", targetDimKey.location().toString());
            blockEntityData.putInt("time", time);
            blockEntityData.putString("id", "assrpg:dungeon_entrance");

            CustomData blockEntityComponent = CustomData.of(blockEntityData);
            entranceStack.set(DataComponents.BLOCK_ENTITY_DATA, blockEntityComponent);
            entranceStack.set(DataComponents.ITEM_NAME, Component.literal("Configured Dungeon Entrance"));
            entranceStack.set(DataComponents.LORE, ItemLore.EMPTY
                            .withLineAdded(Component.literal("Target: " + targetPos.toShortString()))
                            .withLineAdded(Component.literal("Dimension: " + targetDimKey.location())));

            player.addItem(entranceStack);
            source.sendSuccess(() -> Component.literal("Gave configured portal."), true);

            return 1;
        } catch (Exception e) {
            source.sendFailure(Component.literal("§cAn error occurred building portal item: " + e.getMessage()));
            return 0;
        }
    }
}
