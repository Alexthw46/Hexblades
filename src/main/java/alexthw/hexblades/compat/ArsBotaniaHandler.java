package alexthw.hexblades.compat;

import alexthw.hexblades.client.render.entity.ArmorRenderer;
import alexthw.hexblades.common.items.armors.ArsBotaniaArmor;
import alexthw.hexblades.common.items.armors.HexWArmor;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class ArsBotaniaHandler {
    public static HexWArmor makeArmor(EquipmentSlotType slot, Item.Properties properties) {
        return new ArsBotaniaArmor(slot, properties);
    }

    public static void renderer() {
        GeoArmorRenderer.registerArmorRenderer(ArsBotaniaArmor.class, new ArmorRenderer());
    }

}
