package Alexthw.Hexblades.common.items;

import Alexthw.Hexblades.core.util.Constants;
import Alexthw.Hexblades.core.util.NBTHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class HexSwordItem extends SwordItem {

    /*
    public HexSwordItem(IItemTier mat, Properties props) {
        this(mat, 3, -2.4F, props);
    }
    */

    private int awakening_level;

    public HexSwordItem(IItemTier material, int attackDamage, float attackSpeed, Properties properties) {
        super(material, attackDamage, attackSpeed, properties);

    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        recalculatePowers(player.getHeldItem(hand), world, player);
        return super.onItemRightClick(world, player, hand);
    }

    public void recalculatePowers(ItemStack heldItem, World world, PlayerEntity player) {
        awakenweapon(heldItem);
    }

    public void awakenweapon(ItemStack stack) {

        setAwakenedState(stack, !getAwakened(stack));

    }

    public ItemStack setAwakenedState(ItemStack stack, boolean aws) {
        if (!stack.isEmpty()) {
            NBTHelper.checkNBT(stack).getTag().putBoolean(Constants.NBT.AW_State, aws);

            return stack;
        }
        return stack;
    }

    public boolean getAwakened(ItemStack stack) {
        return !stack.isEmpty() && NBTHelper.checkNBT(stack).getTag().getBoolean(Constants.NBT.AW_State);
    }

    /*public float getAWLevel(ItemStack stack){

        float level = 0;
       if (!stack.isEmpty() && NBTHelper.checkNBT(stack).getTag().contains(Constants.NBT.AW_Level)){
                level = stack.getTag().getFloat(Constants.NBT.AW_Level);
        }

        return level;
    }*/

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("tooltip.HexSwordItem." + "dev_sword"));
        tooltip.add(new TranslationTextComponent("" + getAwakened(stack)));

    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return oldStack.getItem() != newStack.getItem();
    }
}
