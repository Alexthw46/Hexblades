package Alexthw.Hexblades.client.render.entity;

import Alexthw.Hexblades.client.render.models.EarthElementalModel;
import Alexthw.Hexblades.common.entity.EarthElementalEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EarthElementalER extends GeoEntityRenderer<EarthElementalEntity> {
    public EarthElementalER(EntityRendererManager rendererManager) {
        super(rendererManager, new EarthElementalModel());
    }

    @Override
    public ResourceLocation getEntityTexture(EarthElementalEntity entity) {
        return getTextureLocation(entity);
    }

}
