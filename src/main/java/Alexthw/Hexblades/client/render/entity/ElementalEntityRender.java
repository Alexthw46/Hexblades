package Alexthw.Hexblades.client.render.entity;

import Alexthw.Hexblades.Hexblades;
import Alexthw.Hexblades.client.render.models.FireElementalModel;
import Alexthw.Hexblades.common.entity.BaseElementalEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import elucent.eidolon.Registry;
import elucent.eidolon.particle.Particles;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;

public class ElementalEntityRender extends MobRenderer<BaseElementalEntity, FireElementalModel> {

    public ElementalEntityRender(EntityRendererManager rendererManager, FireElementalModel entityModelIn, float shadowSizeIn) {
        super(rendererManager, entityModelIn, shadowSizeIn);
    }


    @Override
    public void render(BaseElementalEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        Particles.create(Registry.FLAME_PARTICLE).setAlpha(0.7F, 0.0F).setScale(0.25F, 0.0F).setLifetime(2).randomOffset(0.3D, 0.2D).randomVelocity(0, -0.25F).setColor(1.0F, 0.875F, 0.5F, 0.5F, 0.25F, 1.0F).spawn(entity.getEntityWorld(), entity.getPosX(), entity.getPosY() + 0.5F, entity.getPosZ());
    }

    @Override
    public ResourceLocation getEntityTexture(BaseElementalEntity entity) {
        return new ResourceLocation(Hexblades.MOD_ID, "textures/entity/texture.png");
    }
}
