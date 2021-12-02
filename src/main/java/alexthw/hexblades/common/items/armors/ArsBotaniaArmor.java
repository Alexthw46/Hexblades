package alexthw.hexblades.common.items.armors;

import alexthw.hexblades.util.Constants;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import vazkii.botania.api.mana.IManaDiscountArmor;

import javax.annotation.Nullable;

import net.minecraft.world.item.Item.Properties;

public class ArsBotaniaArmor extends NouveauArmor implements IManaDiscountArmor {

    public ArsBotaniaArmor(EquipmentSlot slot, Properties builderIn) {
        super(slot, builderIn);
    }

    @Override
    public float getDiscount(ItemStack stack, int slot, Player player, @Nullable ItemStack tool) {
        return getFocus(stack).equals("botania") ? Constants.ArmorCompat.BotaniaDiscount : 0;
    }

}
