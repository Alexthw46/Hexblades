package alexthw.hexblades.client.render.models;// Made with Blockbench 3.9.3
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import alexthw.hexblades.Hexblades;
import alexthw.hexblades.common.entity.FireElementalEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

public class FireElementalModel extends AnimatedGeoModel<FireElementalEntity> {

    @Override
    public ResourceLocation getModelLocation(FireElementalEntity ElementalEntity) {
        return new ResourceLocation(Hexblades.MODID, "geo/" + "fire_elemental.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(FireElementalEntity ElementalEntity) {
        return new ResourceLocation(Hexblades.MODID, "textures/entity/" + "fire_elemental.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(FireElementalEntity ElementalEntity) {
        return new ResourceLocation(Hexblades.MODID, "animations/animation.hexblades.fe.json");
    }

    @Override
    public void setLivingAnimations(FireElementalEntity entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        if (customPredicate == null) return;
        IBone head = this.getAnimationProcessor().getBone("head");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
        head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
    }
}