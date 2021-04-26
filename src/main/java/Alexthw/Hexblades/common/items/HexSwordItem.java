package Alexthw.Hexblades.common.items;

import Alexthw.Hexblades.registers.Tiers;
import Alexthw.Hexblades.util.Constants;
import Alexthw.Hexblades.util.NBTHelper;
import Alexthw.Hexblades.patrons.HexDeities;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import elucent.eidolon.capability.ReputationProvider;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

import java.util.List;

//@SuppressWarnings("all")
public class HexSwordItem extends SwordItem {

    public final double baseAttack;
    public final double baseSpeed;

    public HexSwordItem(int attackDamage, float attackSpeed, Properties properties) {
        super(Tiers.PatronWeaponTier.INSTANCE, attackDamage, attackSpeed, properties);
        baseAttack = attackDamage;
        baseSpeed = attackSpeed;
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        recalculatePowers(player.getHeldItem(hand), world, player);
        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.hurtResistantTime > 0) {
            target.hurtResistantTime = 0;
            applyHexEffects(stack,target,attacker);
        }
        return super.hitEntity(stack, target, attacker);
    }

    public void applyHexEffects(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.attackEntityFrom(new EntityDamageSource("wither", attacker).setDamageBypassesArmor(), 2.0f);
    }

    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon,devotion);
        setAttackSpeed(weapon,devotion);
    }

    void setAttackPower(ItemStack weapon, double extradamage) {

        CompoundNBT tag = NBTHelper.checkNBT(weapon).getTag();
        if (tag != null){
        if (getAwakened(weapon)) {
            tag.putDouble(Constants.NBT.EXTRA_DAMAGE, baseAttack + extradamage);
        } else {
            tag.putDouble(Constants.NBT.EXTRA_DAMAGE, baseAttack);
        }
        }
    }

    void setAttackSpeed(ItemStack weapon, double extraspeed) {

        CompoundNBT tag = NBTHelper.checkNBT(weapon).getTag();
        if (tag != null){

            if (getAwakened(weapon)) {
                tag.putDouble(Constants.NBT.EXTRA_ATTACK_SPEED, baseSpeed + extraspeed);
            } else {
                tag.putDouble(Constants.NBT.EXTRA_ATTACK_SPEED, baseSpeed);
            }
        }
    }

    public void setAwakenedState(ItemStack stack, boolean aws) {
        if (!stack.isEmpty()) {
            CompoundNBT tag = NBTHelper.checkNBT(stack).getTag();
            if (tag != null) tag.putBoolean(Constants.NBT.AW_State, aws);
        }
    }

    private double getAttackPower(ItemStack weapon) {
        CompoundNBT tag = NBTHelper.checkNBT(weapon).getTag();

        if (tag != null) {
            double AP = NBTHelper.checkNBT(weapon).getTag().getDouble(Constants.NBT.EXTRA_DAMAGE);
            if (AP > 0) {
                return AP;
            }
        }
            return baseAttack;
    }

    private double getAttackSpeed(ItemStack weapon) {
        CompoundNBT tag = NBTHelper.checkNBT(weapon).getTag();

        if (tag != null) {
            double AS = tag.getDouble(Constants.NBT.EXTRA_ATTACK_SPEED);

            if (AS > 0) {
                return AS;
            }
        }

        return baseSpeed;
    }

    double getDevotion(PlayerEntity player) {
        if (player.getEntityWorld().getCapability(ReputationProvider.CAPABILITY).resolve().isPresent()){
        return player.getEntityWorld().getCapability(ReputationProvider.CAPABILITY).resolve().get().getReputation(player, HexDeities.HEX_DEITY.getId());
        }else
        return 0;
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
            multimap.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", getAttackSpeed(stack), AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }
}