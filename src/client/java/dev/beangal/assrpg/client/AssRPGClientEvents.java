package dev.beangal.assrpg.client;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;

import static dev.beangal.assrpg.AssRPGEvents.placingAndAllowed;

public class AssRPGClientEvents {
    public static void initialize() {
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            if (!placingAndAllowed(player, level, hitResult)) {
                Minecraft.getInstance().gui.setOverlayMessage(Component.translatable("assrpg.message.protected"), false);

                return InteractionResult.FAIL;
            }

            return InteractionResult.PASS;
        });
    }
}
