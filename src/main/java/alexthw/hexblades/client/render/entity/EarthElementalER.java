package alexthw.hexblades.client.render.entity;

import alexthw.hexblades.client.render.models.EarthElementalModel;
import alexthw.hexblades.common.entity.EarthElementalEntity;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class EarthElementalER extends GeoEntityRenderer<EarthElementalEntity> {
    public EarthElementalER(EntityRendererProvider.Context renderManager) {
        super(renderManager, new EarthElementalModel());
    }

}
