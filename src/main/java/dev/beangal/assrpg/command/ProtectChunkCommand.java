package dev.beangal.assrpg.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.beangal.assrpg.registry.AssRPGCardinalComponents;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

public class ProtectChunkCommand {
    public static int single(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();

        if (!source.isPlayer()) {
            source.sendFailure(Component.literal("Only players may execute this command!"));
            return 0;
        }

        BlockPos pos = BlockPosArgument.getLoadedBlockPos(context, "pos");
        boolean protect = BoolArgumentType.getBool(context, "protected");

        AssRPGCardinalComponents.PROTECTED.get(source.getLevel().getChunk(pos)).set(protect);

        context.getSource().sendSuccess(() -> Component.literal("Set chunk protected status."), false);
        return 1;
    }

    public static int range(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();

        if (!source.isPlayer()) {
            source.sendFailure(Component.literal("Only players may execute this command!"));
            return 0;
        }

        BlockPos pos1 = BlockPosArgument.getLoadedBlockPos(context, "pos1");
        BlockPos pos2 = BlockPosArgument.getLoadedBlockPos(context, "pos2");
        boolean protect = BoolArgumentType.getBool(context, "protected");

        int x1 = pos1.getX() / 16;
        int z1 = pos1.getZ() / 16;
        int x2 = pos2.getX() / 16;
        int z2 = pos2.getZ() / 16;

        for (int x = x1; x <= x2; x++) {
            for (int z = z1; z <= z2; z++) {
                AssRPGCardinalComponents.PROTECTED.get(source.getLevel().getChunk(x, z)).set(protect);
            }
        }

        context.getSource().sendSuccess(() -> Component.literal("Set chunk range protected status."), false);
        return 1;
    }
}
