package alexthw.hexblades.client.render.tile;

import alexthw.hexblades.common.blocks.tile_entities.EverfullUrnTileEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;

public class Urn_Renderer<T extends BlockEntity> implements BlockEntityRenderer<EverfullUrnTileEntity> {

    public Urn_Renderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
    }

    @Override
    public void render(EverfullUrnTileEntity tileEntityIn, float partialTicks, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {

    }
}
