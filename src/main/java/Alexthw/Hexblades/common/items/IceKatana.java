package Alexthw.Hexblades.common.items;

import Alexthw.Hexblades.core.registers.Tiers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class IceKatana extends HexSwordItem {

    public IceKatana(Properties props) {
        super(Tiers.HexiumTier.INSTANCE, 4, -2.4F, props);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("tooltip.HexSwordItem." + "ice_katana"));
    }

}
