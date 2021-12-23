package alexthw.hexblades.common.items;

import alexthw.hexblades.common.items.armors.HexWArmor;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

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

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable World pLevel, List<ITextComponent> pTooltip, ITooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        if (!modFocus.equals("eidolon"))
            pTooltip.add(new StringTextComponent("Requires " + modFocus).withStyle(TextFormatting.ITALIC));
    }
}
