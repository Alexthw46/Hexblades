package alexthw.hexblades.client.render.models;

import alexthw.hexblades.common.items.armors.HexWArmor;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static alexthw.hexblades.util.HexUtils.prefix;

public class HexArmorModel extends AnimatedGeoModel<HexWArmor> {

    @Override
    public ResourceLocation getModelLocation(HexWArmor armor) {
        return prefix("geo/armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(HexWArmor armor) {
        return prefix("textures/entity/hex_armor.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(HexWArmor animatable) {
        return null;
    }

}
