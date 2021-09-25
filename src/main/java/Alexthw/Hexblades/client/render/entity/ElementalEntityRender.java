package Alexthw.Hexblades.client.render.entity;

import Alexthw.Hexblades.client.render.models.BaseElementalModel;
import Alexthw.Hexblades.common.entity.BaseElementalEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ElementalEntityRender extends GeoEntityRenderer<BaseElementalEntity> {


    public ElementalEntityRender(EntityRendererManager renderManager) {
        super(renderManager, new BaseElementalModel() {
            @Override
            public ResourceLocation getModelLocation(BaseElementalEntity baseElementalEntity) {
                return super.getModelLocation(baseElementalEntity);
            }
        });
    }

    @Override
    public ResourceLocation getEntityTexture(BaseElementalEntity entity) {
        return null;
    }

}
