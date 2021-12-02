package alexthw.hexblades.common.items.armors;

import alexthw.hexblades.util.Constants;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import vazkii.botania.api.mana.IManaDiscountArmor;

import javax.annotation.Nullable;

import net.minecraft.world.item.Item.Properties;

public class BotaniaArmor extends HexWArmor implements IManaDiscountArmor {

    public BotaniaArmor(EquipmentSlot slot, Properties builderIn) {
        super(slot, builderIn);
    }

    @Override
    public float getDiscount(ItemStack stack, int slot, Player player, @Nullable ItemStack tool) {
        return getFocus(stack).equals("botania") ? Constants.ArmorCompat.BotaniaDiscount : 0;
    }

}
