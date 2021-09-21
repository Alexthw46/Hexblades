package Alexthw.Hexblades.client.render.entity;

import Alexthw.Hexblades.Hexblades;
import Alexthw.Hexblades.client.render.models.BaseElementalModel;
import Alexthw.Hexblades.common.entity.BaseElementalEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class ElementalEntityRender extends MobRenderer<BaseElementalEntity, BaseElementalModel> {

    public ElementalEntityRender(EntityRendererManager rendererManager, BaseElementalModel entityModelIn, float shadowSizeIn) {
        super(rendererManager, entityModelIn, shadowSizeIn);
    }

    @Override
    public ResourceLocation getEntityTexture(BaseElementalEntity entity) {
        return new ResourceLocation(Hexblades.MOD_ID, "textures/entity/fire_elemental_big.png");

    }
}