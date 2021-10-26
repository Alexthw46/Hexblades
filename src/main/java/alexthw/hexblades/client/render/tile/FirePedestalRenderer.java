package alexthw.hexblades.client.render.tile;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.common.blocks.tile_entities.FirePedestalTileEntity;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class FirePedestalRenderer extends GeoBlockRenderer<FirePedestalTileEntity> {
    public FirePedestalRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn, new AnimatedGeoModel<FirePedestalTileEntity>() {
            /**
             * This resource location needs to point to a json file of your animation file,
             * i.e. "geckolib:animations/frog_animation.json"
             *
             * @param animatable this
             * @return the animation file location
             */
            @Override
            public ResourceLocation getAnimationFileLocation(FirePedestalTileEntity animatable) {
                return new ResourceLocation(Hexblades.MODID, "animations/animation.hexblades.fire_pedestal.json");
            }

            @Override
            public ResourceLocation getModelLocation(FirePedestalTileEntity object) {
                return new ResourceLocation(Hexblades.MODID, "geo/fire_pedestal.geo.json");
            }

            @Override
            public ResourceLocation getTextureLocation(FirePedestalTileEntity object) {
                return new ResourceLocation(Hexblades.MODID, "textures/custom_models/" + "fire_pedestal.png");
            }
        });
    }
}
