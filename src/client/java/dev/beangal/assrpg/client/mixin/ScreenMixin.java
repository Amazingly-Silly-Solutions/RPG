package dev.beangal.assrpg.client.mixin;

import dev.beangal.assrpg.AssRPG;
import dev.beangal.assrpg.registry.AssRPGCardinalComponents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ScreenMixin {
    @Unique
    private static final ResourceLocation COIN = AssRPG.id("textures/item/coin.png");

    @Inject(method = "render", at = @At("TAIL"))
    private void onRender(GuiGraphics guiGraphics, int i, int j, float f, CallbackInfo ci) {
        Minecraft client = Minecraft.getInstance();

        if (client.player == null) { return; }

        int iconSize = client.font.lineHeight - 1;

        guiGraphics.blit(
                RenderType::guiTextured,
                COIN,
                10,
                10,
                0f,
                0f,
                iconSize,
                iconSize,
                iconSize,
                iconSize
        );
        guiGraphics.drawString(
                client.font, // font
                Integer.toString(AssRPGCardinalComponents.COINS.get(client.player).get()), // text
                iconSize + 12, // X
                10, // Y
                0xFFFFFFFF, // color
                true // shadow
        );
    }
}
