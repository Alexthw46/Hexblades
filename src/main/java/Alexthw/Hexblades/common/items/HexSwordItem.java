package Alexthw.Hexblades.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class HexSwordItem extends SwordItem {


    public HexSwordItem(IItemTier material, int attackDamage, float attackSpeed, Properties properties) {
        super(material, attackDamage + 3, attackSpeed - 2.4f, properties.maxDamage(material.getMaxUses()));
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("tooltip.HexSwordItem.test_tooltip"));
    }
}
