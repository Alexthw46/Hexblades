package Alexthw.Hexblades.client.render.entity;

import Alexthw.Hexblades.Hexblades;
import Alexthw.Hexblades.client.render.models.BaseElementalModel;
import Alexthw.Hexblades.common.entity.BaseElementalEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import elucent.eidolon.Registry;
import elucent.eidolon.particle.Particles;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class FireElementalER extends ElementalEntityRender {
    public FireElementalER(EntityRendererManager rendererManager, BaseElementalModel entityModelIn, float shadowSizeIn) {
        super(rendererManager, entityModelIn, shadowSizeIn);
    }

    @Override
    public void render(BaseElementalEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        Particles.create(Registry.FLAME_PARTICLE).setAlpha(0.7F, 0.0F).setScale(0.25F, 0.0F).setLifetime(10).randomOffset(0.3D, 0.3D).randomVelocity(0, -0.15F).setColor(1.5F, 0.875F, 0.5F, 0.5F, 0.25F, 1.0F).spawn(entity.getEntityWorld(), entity.getPosX(), entity.getPosY() + 0.75F, entity.getPosZ());
    }

    @Override
    public ResourceLocation getEntityTexture(BaseElementalEntity entity) {
        return new ResourceLocation(Hexblades.MOD_ID, "textures/entity/fire_elemental.png");
    }
}
