package dev.beangal.assrpg;

import com.mojang.brigadier.arguments.BoolArgumentType;
import dev.beangal.assrpg.command.ProtectChunkCommand;
import dev.beangal.assrpg.registry.AssRPGCardinalComponents;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import static dev.beangal.assrpg.AssRPG.LOGGER;

public class AssRPGEvents {
    public static void initialize() {
        PlayerBlockBreakEvents.BEFORE.register((level, player, blockPos, state, blockEntity) -> {
            boolean allowed = canModifyChunk(level, player, blockPos);

            if (!allowed && player instanceof ServerPlayer serverPlayer) {
                    serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("assrpg.message.protected")));
            }
            return allowed;
        });

        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            if (!placingAndAllowed(player, level, hitResult) && !level.isClientSide()) {
                if (player instanceof ServerPlayer serverPlayer) {
                    serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("assrpg.message.protected")));
                }

                return InteractionResult.FAIL;
            }

            return InteractionResult.PASS;
        });

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            LOGGER.info("Registering commands...");
            dispatcher.register(Commands.literal("protectchunk")
                    .then(Commands.literal("single")
                            .then(Commands.argument("pos", BlockPosArgument.blockPos())
                                    .then(Commands.argument("protected", BoolArgumentType.bool())
                                            .executes(ProtectChunkCommand::single))))
                    .then(Commands.literal("range")
                            .then(Commands.argument("pos1", BlockPosArgument.blockPos())
                                    .then(Commands.argument("pos2", BlockPosArgument.blockPos())
                                            .then(Commands.argument("protected", BoolArgumentType.bool())
                                                    .executes(ProtectChunkCommand::range))))));
        });
    }

    public static boolean placingAndAllowed(Player player, Level level, BlockHitResult hitResult) {
        ItemStack heldItem = player.getItemBySlot(EquipmentSlot.MAINHAND);
        if (heldItem.getItem() instanceof BlockItem || heldItem.getItem() instanceof BucketItem) {
            BlockPos placePos = hitResult.getBlockPos().relative(hitResult.getDirection());
            BlockPos hitPos = hitResult.getBlockPos();
            BlockState state = level.getBlockState(hitPos);

            if (state.canBeReplaced()) {
                placePos = hitPos;
            }

            return canModifyChunk(level, player, placePos);
        }

        return true;
    }

    public static boolean canModifyChunk(Level level, @Nullable Player player, BlockPos blockPos) {
        if (player != null && player.isCreative()) {
            return true;
        }

        ChunkAccess chunk = level.getChunk(blockPos);

        return !AssRPGCardinalComponents.PROTECTED.get(chunk).get();
    }
}
