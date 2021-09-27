package alexthw.hexblades.client.render.entity;

import alexthw.hexblades.client.render.models.EarthElementalModel;
import alexthw.hexblades.common.entity.EarthElementalEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EarthElementalER extends GeoEntityRenderer<EarthElementalEntity> {
    public EarthElementalER(EntityRendererManager rendererManager) {
        super(rendererManager, new EarthElementalModel());
    }

}
