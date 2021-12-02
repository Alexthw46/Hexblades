package alexthw.hexblades.common.items.armors;

import alexthw.hexblades.util.Constants;
import com.hollingsworth.arsnouveau.api.mana.IManaEquipment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import net.minecraft.world.item.Item.Properties;

public class NouveauArmor extends HexWArmor implements IManaEquipment {
    public NouveauArmor(EquipmentSlot slot, Properties builderIn) {
        super(slot, builderIn);
    }

    @Override
    public int getMaxManaBoost(ItemStack stack) {
        return getFocus(stack).equals("ars nouveau") ? Constants.ArmorCompat.ArsManaBuff : 0;
    }

    @Override
    public int getManaRegenBonus(ItemStack stack) {
        return getFocus(stack).equals("ars nouveau") ? Constants.ArmorCompat.ArsManaRegen : 0;
    }

    @Override
    public int getManaDiscount(ItemStack stack) {
        return getFocus(stack).equals("ars nouveau") ? Constants.ArmorCompat.ArsManaDiscount : 0;
    }
}
