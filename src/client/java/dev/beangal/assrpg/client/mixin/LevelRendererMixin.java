package dev.beangal.assrpg.client.mixin;

import com.mojang.blaze3d.framegraph.FrameGraphBuilder;
import dev.beangal.assrpg.AssRPG;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {
    @Inject(method = "addCloudsPass", at = @At("HEAD"), cancellable = true)
    private void cancelCloudRender(FrameGraphBuilder frameGraphBuilder, Matrix4f matrix4f, Matrix4f matrix4f2, CloudStatus cloudStatus, Vec3 vec3, float f, int i, float g, CallbackInfo ci) {
        Minecraft client = Minecraft.getInstance();

        if (client.level == null) { return; }
        if (client.level.dimension().location().toString().equals(AssRPG.id("dungeon").toString())) {
            ci.cancel();
        }
    }
}
