package alexthw.hexblades.client.render.tile;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.common.blocks.tile_entities.SwordStandTileEntity;
import alexthw.hexblades.common.items.IHexblade;
import alexthw.hexblades.common.items.dulls.Hammer_dull;
import alexthw.hexblades.common.items.tier1.EarthHammer1;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;


public class SwordStandRenderer extends GeoBlockRenderer<SwordStandTileEntity> {

    public SwordStandRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn, new AnimatedGeoModel<SwordStandTileEntity>() {
            @Override
            public ResourceLocation getModelLocation(SwordStandTileEntity object) {
                return new ResourceLocation(Hexblades.MODID, "geo/" + "sword_stand.geo.json");
            }

            @Override
            public ResourceLocation getTextureLocation(SwordStandTileEntity object) {
                return new ResourceLocation(Hexblades.MODID, "textures/custom_models/" + "sword_stand.png");
            }

            @Override
            public ResourceLocation getAnimationFileLocation(SwordStandTileEntity animatable) {
                return new ResourceLocation(Hexblades.MODID, "animations/animation.hexblades.sword_stand.json");
            }
        });
    }

    @Override
    public void render(TileEntity tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        super.render(tileEntity, partialTicks, matrixStack, bufferIn, combinedLightIn, combinedOverlayIn);
        SwordStandTileEntity tileEntityIn = (SwordStandTileEntity) tileEntity;
        Minecraft mc = Minecraft.getInstance();
        ItemRenderer ir = mc.getItemRenderer();
        ItemStack iStack = tileEntityIn.stack;
        if (!iStack.isEmpty() && mc.level != null) {
            matrixStack.pushPose();
            Item item = iStack.getItem();
            if ((item instanceof EarthHammer1) || (item instanceof Hammer_dull)) {
                matrixStack.translate(0.5D, 0.3D, 0.5D);
                //matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90.0F));
            } else if (item instanceof SwordItem || item instanceof IHexblade) {
                matrixStack.translate(0.6D, 0.5D, 0.5D);
                matrixStack.mulPose(Vector3f.ZP.rotationDegrees(45.0F));
            }
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(2.0F * ((float) (mc.level.getGameTime() % 360L) + partialTicks)));
            ir.renderStatic(tileEntityIn.stack, ItemCameraTransforms.TransformType.GROUND, combinedLightIn, combinedOverlayIn, matrixStack, bufferIn);
            matrixStack.popPose();
        }
    }

}
