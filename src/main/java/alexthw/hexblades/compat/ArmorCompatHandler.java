package alexthw.hexblades.compat;

import alexthw.hexblades.common.items.armors.HexWArmor;
import alexthw.hexblades.util.CompatUtil;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;

public class ArmorCompatHandler {


    public static HexWArmor makeChest(Item.Properties properties) {
        EquipmentSlotType slot = EquipmentSlotType.CHEST;
        if (CompatUtil.isBotaniaLoaded() && CompatUtil.isArsNovLoaded())
            return ArsBotaniaHandler.makeArmor(slot, properties);
        if (CompatUtil.isBotaniaLoaded()) return BotaniaCompat.makeArmor(slot, properties);
        if (CompatUtil.isArsNovLoaded()) return ArsNouveauCompat.makeArmor(slot, properties);

        return new HexWArmor(slot, properties);
    }

    public static HexWArmor makeHead(Item.Properties properties) {
        EquipmentSlotType slot = EquipmentSlotType.HEAD;
        if (CompatUtil.isBotaniaLoaded() && CompatUtil.isArsNovLoaded())
            return ArsBotaniaHandler.makeArmor(slot, properties);
        if (CompatUtil.isBotaniaLoaded()) return BotaniaCompat.makeArmor(slot, properties);
        if (CompatUtil.isArsNovLoaded()) return ArsNouveauCompat.makeArmor(slot, properties);

        return new HexWArmor(slot, properties);
    }

    public static HexWArmor makeFeet(Item.Properties properties) {
        EquipmentSlotType slot = EquipmentSlotType.FEET;
        if (CompatUtil.isBotaniaLoaded() && CompatUtil.isArsNovLoaded())
            return ArsBotaniaHandler.makeArmor(slot, properties);
        if (CompatUtil.isBotaniaLoaded()) return BotaniaCompat.makeArmor(slot, properties);
        if (CompatUtil.isArsNovLoaded()) return ArsNouveauCompat.makeArmor(slot, properties);

        return new HexWArmor(slot, properties);
    }

    public static HexWArmor makeLegs(Item.Properties properties) {
        EquipmentSlotType slot = EquipmentSlotType.LEGS;
        if (CompatUtil.isBotaniaLoaded() && CompatUtil.isArsNovLoaded())
            return ArsBotaniaHandler.makeArmor(slot, properties);
        if (CompatUtil.isBotaniaLoaded()) return BotaniaCompat.makeArmor(slot, properties);
        if (CompatUtil.isArsNovLoaded()) return ArsNouveauCompat.makeArmor(slot, properties);

        return new HexWArmor(slot, properties);
    }
}
