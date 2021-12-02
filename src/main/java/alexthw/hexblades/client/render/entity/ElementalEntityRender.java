package alexthw.hexblades.client.render.entity;

import alexthw.hexblades.client.render.models.BaseElementalModel;
import alexthw.hexblades.common.entity.BaseElementalEntity;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class ElementalEntityRender extends GeoEntityRenderer<BaseElementalEntity> {


    public ElementalEntityRender(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BaseElementalModel() {
            @Override
            public ResourceLocation getModelLocation(BaseElementalEntity baseElementalEntity) {
                return super.getModelLocation(baseElementalEntity);
            }
        });
    }

    @Override
    public ResourceLocation getTextureLocation(BaseElementalEntity entity) {
        return null;
    }

}
