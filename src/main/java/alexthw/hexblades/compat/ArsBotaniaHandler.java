package alexthw.hexblades.compat;

import alexthw.hexblades.client.render.entity.HexArmorRenderer;
import alexthw.hexblades.common.items.armors.ArsBotaniaArmor;
import alexthw.hexblades.common.items.armors.HexWArmor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class ArsBotaniaHandler {
    public static HexWArmor makeArmor(EquipmentSlot slot, Item.Properties properties) {
        return new ArsBotaniaArmor(slot, properties);
    }

    @OnlyIn(Dist.CLIENT)
    public static void renderer() {
        GeoArmorRenderer.registerArmorRenderer(ArsBotaniaArmor.class, new HexArmorRenderer());
    }

}
