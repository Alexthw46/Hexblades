package alexthw.hexblades.client.render.models;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.common.entity.EarthElementalEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EarthElementalModel extends AnimatedGeoModel<EarthElementalEntity> {


    @Override
    public ResourceLocation getModelLocation(EarthElementalEntity earthElementalEntity) {
        return new ResourceLocation(Hexblades.MOD_ID, "geo/" + "earth_elemental.geo.json");

    }

    @Override
    public ResourceLocation getTextureLocation(EarthElementalEntity earthElementalEntity) {
        return new ResourceLocation(Hexblades.MOD_ID, "textures/entity/" + "earth_elemental.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EarthElementalEntity earthElementalEntity) {
        return new ResourceLocation(Hexblades.MOD_ID, "animations/animation.hexblades." + "ee.idle.json");
    }
}