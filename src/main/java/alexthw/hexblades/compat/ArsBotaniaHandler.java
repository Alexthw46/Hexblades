package alexthw.hexblades.compat;

import alexthw.hexblades.common.items.armors.ArsBotaniaArmor;
import alexthw.hexblades.common.items.armors.HexWArmor;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;

public class ArsBotaniaHandler {
    public static HexWArmor makeArmor(EquipmentSlotType slot, Item.Properties properties) {
        return new ArsBotaniaArmor(slot, properties);
    }
}
