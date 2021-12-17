package alexthw.hexblades.client.render.tile;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.common.blocks.tile_entities.SwordStandTileEntity;
import alexthw.hexblades.common.items.IHexblade;
import alexthw.hexblades.common.items.dulls.HammerDull;
import alexthw.hexblades.common.items.deprecated.EarthHammer1;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Vector3f;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;


public class SwordStandRenderer extends GeoBlockRenderer<SwordStandTileEntity> {

    public SwordStandRenderer(BlockEntityRendererProvider.Context rendererDispatcherIn) {
        super(rendererDispatcherIn, new AnimatedGeoModel<>() {
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
    public void render(BlockEntity tileEntity, float partialTicks, PoseStack matrixStack, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {
        super.render(tileEntity, partialTicks, matrixStack, bufferIn, combinedLightIn, combinedOverlayIn);
        SwordStandTileEntity tileEntityIn = (SwordStandTileEntity) tileEntity;
        Minecraft mc = Minecraft.getInstance();
        ItemRenderer ir = mc.getItemRenderer();
        ItemStack iStack = tileEntityIn.stack;
        if (!iStack.isEmpty() && mc.level != null) {
            matrixStack.pushPose();
            Item item = iStack.getItem();
            if ((item instanceof EarthHammer1) || (item instanceof HammerDull)) {
                matrixStack.translate(0.5D, 0.3D, 0.5D);
                //matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90.0F));
            } else if (item instanceof SwordItem || item instanceof IHexblade) {
                matrixStack.translate(0.6D, 0.5D, 0.5D);
                matrixStack.mulPose(Vector3f.ZP.rotationDegrees(45.0F));
            }
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(2.0F * ((float) (mc.level.getGameTime() % 360L) + partialTicks)));
            ir.renderStatic(tileEntityIn.stack, ItemTransforms.TransformType.GROUND, combinedLightIn, combinedOverlayIn, matrixStack, bufferIn,0);
            matrixStack.popPose();
        }
    }

}
