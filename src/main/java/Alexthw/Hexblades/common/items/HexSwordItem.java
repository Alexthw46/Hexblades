package Alexthw.Hexblades.common.items;

import Alexthw.Hexblades.core.util.Constants;
import Alexthw.Hexblades.core.util.NBTHelper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

public class HexSwordItem extends SwordItem {

    public final double baseAttack;

    public HexSwordItem(IItemTier material, int attackDamage, float attackSpeed, Properties properties) {
        super(material, attackDamage, attackSpeed, properties);
        baseAttack = attackDamage;
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        recalculatePowers(player.getHeldItem(hand), world, player);
        return super.onItemRightClick(world, player, hand);
    }

    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        float devotion = getDevotion(player);

        setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon, devotion);
    }

    private void setAttackPower(ItemStack weapon, float extradamage) {

        CompoundNBT tag = NBTHelper.checkNBT(weapon).getTag();

        if (getAwakened(weapon)) {
            tag.putDouble(Constants.NBT.EXTRA_DAMAGE, baseAttack + extradamage);
        } else {
            tag.putDouble(Constants.NBT.EXTRA_DAMAGE, baseAttack);
        }
    }

    public void setAwakenedState(ItemStack stack, boolean aws) {
        if (!stack.isEmpty()) {
            NBTHelper.checkNBT(stack).getTag().putBoolean(Constants.NBT.AW_State, aws);
        }
    }

    private double getAttackPower(ItemStack weapon) {
        double AP = NBTHelper.checkNBT(weapon).getTag().getDouble(Constants.NBT.EXTRA_DAMAGE);
        if (AP > 0) {
            return AP;
        } else {
            return baseAttack;
        }
    }

    private float getDevotion(PlayerEntity player) {
        float dev = 10;

        return dev;
    }

    public boolean getAwakened(ItemStack stack) {
        return !stack.isEmpty() && NBTHelper.checkNBT(stack).getTag().getBoolean(Constants.NBT.AW_State);
    }

    /*
    public float getCharge(ItemStack stack){

        float level = 0;
       if (!stack.isEmpty() && NBTHelper.checkNBT(stack).getTag().contains(Constants.NBT.CHARGE_LEVEL)){
                level = stack.getTag().getFloat(Constants.NBT.CHARGE_LEVEL);
        }

        return level;
    }*/

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        //tooltip.add(new TranslationTextComponent("tooltip.HexSwordItem." + "dev_sword"));
        //tooltip.add(new TranslationTextComponent("" + getAwakened(stack)));

    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return oldStack.getItem() != newStack.getItem();
    }


    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
        if (slot == EquipmentSlotType.MAINHAND) {
            multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", getAttackPower(stack), AttributeModifier.Operation.ADDITION));
            //multimap.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", getAttackSpeed(stack), AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }
}