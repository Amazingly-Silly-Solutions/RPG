package dev.beangal.assrpg.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.beangal.assrpg.AssRPG;
import dev.beangal.assrpg.blockentity.DungeonEntranceBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class DungeonEntranceRenderer implements BlockEntityRenderer<DungeonEntranceBlockEntity> {
    private final BlockRenderDispatcher blockRenderer;
    private final ModelResourceLocation blockModelLocation;

    public DungeonEntranceRenderer(BlockEntityRendererProvider.Context context) {
        this.blockRenderer = context.getBlockRenderDispatcher();
        this.blockModelLocation = new ModelResourceLocation(
                AssRPG.id("dungeon_entrance"),
                ""
        );
    }

    @Override
    public void render(DungeonEntranceBlockEntity blockEntity, float delta, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        Player player = Minecraft.getInstance().player;

        if (player == null) {
            return;
        }

        ItemStack mainHand = player.getMainHandItem();
        ItemStack offHand = player.getOffhandItem();
        Item barrierItem = blockEntity.getBlockState().getBlock().asItem();

        if (mainHand.is(barrierItem) || offHand.is(barrierItem)) {
            poseStack.pushPose();
            VertexConsumer buffer = multiBufferSource.getBuffer(RenderType.cutout());
            BakedModel bakedModel = Minecraft.getInstance().getModelManager().getModel(this.blockModelLocation);
            BlockState blockState = blockEntity.getBlockState();

            this.blockRenderer.getModelRenderer().tesselateBlock(
                    blockEntity.getLevel(),
                    bakedModel,
                    blockState,
                    blockEntity.getBlockPos(),
                    poseStack,
                    buffer,
                    false,
                    net.minecraft.util.RandomSource.create(),
                    blockState.getSeed(blockEntity.getBlockPos()),
                    packedOverlay
            );

            poseStack.popPose();
        }
    }
}
