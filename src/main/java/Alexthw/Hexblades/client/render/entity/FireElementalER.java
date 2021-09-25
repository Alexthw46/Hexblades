package Alexthw.Hexblades.client.render.entity;

import Alexthw.Hexblades.client.render.models.FireElementalModel;
import Alexthw.Hexblades.common.entity.FireElementalEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import elucent.eidolon.Registry;
import elucent.eidolon.particle.Particles;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class FireElementalER extends GeoEntityRenderer<FireElementalEntity> {


    public FireElementalER(EntityRendererManager renderManager) {
        super(renderManager, new FireElementalModel());
    }

    public void render(FireElementalEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entity, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        Particles.create(Registry.FLAME_PARTICLE).setAlpha(0.7F, 0.0F).setScale(0.25F, 0.0F).setLifetime(10).randomOffset(0.3D, 0.3D).randomVelocity(0, -0.15F).setColor(1.5F, 0.875F, 0.5F, 0.5F, 0.25F, 1.0F).spawn(entity.getEntityWorld(), entity.getPosX(), entity.getPosY() + 0.75F, entity.getPosZ());
    }

    @Override
    public ResourceLocation getEntityTexture(FireElementalEntity entity) {
        return this.getTextureLocation(entity);
    }
}
