package alexthw.hexblades.client.render.entity;

import alexthw.hexblades.client.render.models.HexArmorModel;
import alexthw.hexblades.common.items.armors.HexWArmor;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class HexArmorRenderer extends GeoArmorRenderer<HexWArmor> {
    public HexArmorRenderer() {
        super(new HexArmorModel());
    }
}
