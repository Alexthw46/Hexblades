package Alexthw.Hexblades.common.blocks.tile_entities;

import Alexthw.Hexblades.common.items.tier1.EarthHammer1;
import Alexthw.Hexblades.common.items.tier2.EarthHammer2;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Vector3f;


public class SwordStandRenderer extends TileEntityRenderer<SwordStandTileEntity> {
    public SwordStandRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(SwordStandTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Minecraft mc = Minecraft.getInstance();
        ItemRenderer ir = mc.getItemRenderer();
        ItemStack istack = tileEntityIn.stack;
        if (istack.isEmpty()) {
            matrixStackIn.push();
            if (!((istack.getItem() instanceof EarthHammer1) || (istack.getItem() instanceof EarthHammer2))) {
                matrixStackIn.translate(0.6D, 0.5D, 0.5D);
                matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(45.0F));
            }
            ir.renderItem(tileEntityIn.stack, ItemCameraTransforms.TransformType.GROUND, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
            matrixStackIn.pop();
        }
    }

}
