package Alexthw.Hexblades.common.items;

import Alexthw.Hexblades.core.util.Constants;
import Alexthw.Hexblades.core.util.NBTHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
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
        evolveweapon(heldItem);
    }

    public ItemStack evolveweapon(ItemStack stack) {
        if (!stack.isEmpty()) {
            CompoundNBT nbt = NBTHelper.checkNBT(stack).getTag();

            if (nbt.contains(Constants.NBT.AW_Level)) {
                if (nbt.getFloat(Constants.NBT.AW_Level) < 4) {
                    nbt.putFloat(Constants.NBT.AW_Level, nbt.getFloat(Constants.NBT.AW_Level) + 1);
                } else {
                    nbt.putFloat(Constants.NBT.AW_Level, 0f);
                }
            } else {
                nbt.putFloat(Constants.NBT.AW_Level, 0f);
            }

            return stack;
        }

        return stack;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("tooltip.HexSwordItem." + "dev_sword"));
    }
}
