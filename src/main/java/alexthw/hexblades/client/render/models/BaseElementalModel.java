package alexthw.hexblades.client.render.models;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.common.entity.BaseElementalEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public abstract class BaseElementalModel extends AnimatedGeoModel<BaseElementalEntity> {

    @Override
    public ResourceLocation getModelLocation(BaseElementalEntity baseElementalEntity) {
        return new ResourceLocation(Hexblades.MODID, "geo/" + ".geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(BaseElementalEntity baseElementalEntity) {
        return new ResourceLocation(Hexblades.MODID, "textures/entity/" + ".png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(BaseElementalEntity baseElementalEntity) {
        return new ResourceLocation(Hexblades.MODID, "animations/" + "hexblades." + ".animation.json");
    }
}
