package dev.beangal.assrpg.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.beangal.assrpg.registry.AssRPGCardinalComponents;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class CoinsCommand {
    public static LiteralArgumentBuilder<CommandSourceStack> buildBranch() {
        return Commands.literal("coins")
                        .then(Commands.literal("get")
                                .then(Commands.argument("player", EntityArgument.player())
                                        .executes(CoinsCommand::get)))
                .then(Commands.literal("set")
                        .then(Commands.argument("player", EntityArgument.player())
                                .then(Commands.argument("amount", IntegerArgumentType.integer(0, 2147483647))
                                        .executes(CoinsCommand::set))))
                .then(Commands.literal("add")
                        .then(Commands.argument("player", EntityArgument.player())
                                .then(Commands.argument("amount", IntegerArgumentType.integer(0, 2147483647))
                                        .executes(CoinsCommand::add))))
                .then(Commands.literal("remove")
                        .then(Commands.argument("player", EntityArgument.player())
                                .then(Commands.argument("amount", IntegerArgumentType.integer(0, 2147483647))
                                        .executes(CoinsCommand::remove))));
    }

    public static int get(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = EntityArgument.getPlayer(context, "player");

        context.getSource().sendSuccess(() -> Component.literal(player.getName().getString() + " has " + AssRPGCardinalComponents.COINS.get(player).get()), false);
        return 1;
    }

    public static int set(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = EntityArgument.getPlayer(context, "player");
        int amount = IntegerArgumentType.getInteger(context, "amount");

        AssRPGCardinalComponents.COINS.get(player).set(amount);

        context.getSource().sendSuccess(() -> Component.literal(player.getName().getString() + " now has " + amount + " coins."), false);
        return 1;
    }

    public static int add(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = EntityArgument.getPlayer(context, "player");
        int amount = IntegerArgumentType.getInteger(context, "amount");

        context.getSource().sendSuccess(() -> Component.literal(player.getName().getString() + " now has " + AssRPGCardinalComponents.COINS.get(player).add(amount) + " coins."), false);
        return 1;
    }

    public static int remove(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        Player player = EntityArgument.getPlayer(context, "player");
        int amount = IntegerArgumentType.getInteger(context, "amount");

        context.getSource().sendSuccess(() -> Component.literal(player.getName().getString() + " now has " + AssRPGCardinalComponents.COINS.get(player).add(-amount) + " coins."), false);
        return 1;
    }
}
