package alexthw.hexblades.common.items;

import alexthw.hexblades.common.items.armors.HexWArmor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ArmorFocus extends Item {

    String modFocus;

    public ArmorFocus(Properties pProperties, String mod) {
        super(pProperties);
        modFocus = mod;
    }

    public String getModFocus() {
        return modFocus;
    }

    public static String[] foci = {"eidolon", "botania", "ars nouveau"};

    @Override
    public ActionResult<ItemStack> use(World pLevel, PlayerEntity pPlayer, Hand pHand) {

        for (ItemStack stack : pPlayer.getArmorSlots()) {
            if (stack.getItem() instanceof HexWArmor) {
                HexWArmor.setFocus(stack, modFocus);
            }
        }

        return super.use(pLevel, pPlayer, pHand);
    }
}
