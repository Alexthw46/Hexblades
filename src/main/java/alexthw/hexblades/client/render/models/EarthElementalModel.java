package alexthw.hexblades.client.render.models;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.common.entity.EarthElementalEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class EarthElementalModel extends AnimatedGeoModel<EarthElementalEntity> {


    @Override
    public ResourceLocation getModelLocation(EarthElementalEntity earthElementalEntity) {
        return new ResourceLocation(Hexblades.MODID, "geo/" + "earth_elemental.geo.json");

    }

    @Override
    public ResourceLocation getTextureLocation(EarthElementalEntity earthElementalEntity) {
        return new ResourceLocation(Hexblades.MODID, "textures/entity/" + "earth_elemental.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EarthElementalEntity earthElementalEntity) {
        return new ResourceLocation(Hexblades.MODID, "animations/animation.hexblades." + "ee.idle.json");
    }
}