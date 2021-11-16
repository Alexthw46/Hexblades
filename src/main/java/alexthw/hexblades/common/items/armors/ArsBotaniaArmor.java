package alexthw.hexblades.common.items.armors;

import alexthw.hexblades.util.Constants;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.mana.IManaDiscountArmor;

import javax.annotation.Nullable;

public class ArsBotaniaArmor extends NouveauArmor implements IManaDiscountArmor {

    public ArsBotaniaArmor(EquipmentSlotType slot, Properties builderIn) {
        super(slot, builderIn);
    }

    @Override
    public float getDiscount(ItemStack stack, int slot, PlayerEntity player, @Nullable ItemStack tool) {
        return getFocus(stack).equals("botania") ? Constants.ArmorCompat.BotaniaDiscount : 0;
    }

}
