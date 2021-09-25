package Alexthw.Hexblades.client.render.models;

import Alexthw.Hexblades.Hexblades;
import Alexthw.Hexblades.common.entity.BaseElementalEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public abstract class BaseElementalModel extends AnimatedGeoModel<BaseElementalEntity> {

    @Override
    public ResourceLocation getModelLocation(BaseElementalEntity baseElementalEntity) {
        return new ResourceLocation(Hexblades.MOD_ID, "geo/" + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(BaseElementalEntity baseElementalEntity) {
        return new ResourceLocation(Hexblades.MOD_ID, "textures/entity/" + ".png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(BaseElementalEntity baseElementalEntity) {
        return new ResourceLocation(Hexblades.MOD_ID, "animations/" + "hexblades." + ".animation.json");
    }
}
