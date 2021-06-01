package Alexthw.Hexblades.common.blocks.tile_entities;

import Alexthw.Hexblades.common.items.dulls.Hammer_dull;
import Alexthw.Hexblades.common.items.tier1.EarthHammer1;
import Alexthw.Hexblades.common.items.tier2.EarthHammer2;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
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
        if (!istack.isEmpty()) {
            matrixStackIn.push();
            Item item = istack.getItem();
            if ((item instanceof EarthHammer2) || (item instanceof EarthHammer1) || (item instanceof Hammer_dull)) {
                matrixStackIn.translate(0.5D, 0.5D, 0.5D);
                //matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90.0F));
            } else if (item instanceof SwordItem) {
                matrixStackIn.translate(0.6D, 0.5D, 0.5D);
                matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(45.0F));
            }
            matrixStackIn.rotate(Vector3f.YP.rotationDegrees(2.0F * ((float) (mc.world.getGameTime() % 360L) + partialTicks)));
            ir.renderItem(tileEntityIn.stack, ItemCameraTransforms.TransformType.GROUND, combinedLightIn, combinedOverlayIn, matrixStackIn, bufferIn);
            matrixStackIn.pop();
        }
    }

}
