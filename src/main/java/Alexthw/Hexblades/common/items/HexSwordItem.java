package Alexthw.Hexblades.common.items;

import Alexthw.Hexblades.deity.HexDeities;
import Alexthw.Hexblades.registers.Tiers;
import Alexthw.Hexblades.util.Constants;
import Alexthw.Hexblades.util.NBTHelper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import elucent.eidolon.capability.ReputationProvider;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class HexSwordItem extends SwordItem {

    protected double baseAttack;
    protected double baseSpeed;
    protected boolean isActivated;

    protected String tooltipText = "The Dev Sword, you shouldn't read this";
    protected int rechargeTick = 5;
    protected int dialogueLines = 3;

    public HexSwordItem(int attackDamage, float attackSpeed, Properties properties) {
        super(Tiers.PatronWeaponTier.INSTANCE, attackDamage, attackSpeed, properties);
        baseAttack = attackDamage;
        baseSpeed = attackSpeed;
        isActivated = false;
    }

    public HexSwordItem(int attackDamage, float attackSpeed, Properties properties, float mining_speed) {
        this(attackDamage, attackSpeed, properties.addToolType(net.minecraftforge.common.ToolType.PICKAXE, Tiers.PatronWeaponTier.INSTANCE.getHarvestLevel()));
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return oldStack.getItem() != newStack.getItem();
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity user, int itemSlot, boolean isSelected) {
        if (user instanceof PlayerEntity && !worldIn.isRemote()) {
            if (hasBonus()) {
                applyHexBonus((PlayerEntity) user, isActivated);
            }
            if (isActivated && !((PlayerEntity) user).isCreative()) {
                if ((getMaxDamage(stack) - stack.getDamage()) > 5) {
                    stack.damageItem(2, (LivingEntity) user, (entity) -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
                } else {
                    recalculatePowers(((PlayerEntity) user).getHeldItem(Hand.MAIN_HAND), worldIn, (PlayerEntity) user);
                }
            } else if (stack.getDamage() > 0) {
                stack.setDamage(Math.max(stack.getDamage() - rechargeTick, 0));
            }
        }
    }

    public void applyHexBonus(PlayerEntity user, boolean awakened) {
    }

    public boolean hasBonus() {
        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        if (!world.isRemote()) {
            if (!(isActivated && (player.getHeldItem(Hand.OFF_HAND).getItem() instanceof ShieldItem))) {
                recalculatePowers(player.getHeldItem(hand), world, player);
            }
        }
        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity) {
            if (target.hurtResistantTime > 0) {
                target.hurtResistantTime = 0;
                if (isActivated || onHitEffects()) applyHexEffects(stack, target, (PlayerEntity) attacker);
            }
        }
        stack.setDamage(Math.max(stack.getDamage() - 10, 0));
        return super.hitEntity(stack, target, attacker);
    }

    protected boolean onHitEffects() {
        return false;
    }

    //Only apply special effects if wielded by a Player
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker) {
        target.attackEntityFrom(new EntityDamageSource("wither", attacker).setDamageBypassesArmor(), 2.0f);
    }

    //set or reset awakened state & buffs
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon,devotion);
        setAttackSpeed(weapon,devotion);

    }

    public void setAttackPower(ItemStack weapon, double extradamage) {

        CompoundNBT tag = NBTHelper.checkNBT(weapon).getTag();
        if (tag != null) {
            if (isActivated) {
                tag.putDouble(Constants.NBT.EXTRA_DAMAGE, baseAttack + extradamage);
            } else {
                tag.putDouble(Constants.NBT.EXTRA_DAMAGE, baseAttack);
            }
        }
    }

    public void setAttackSpeed(ItemStack weapon, double extraspeed) {

        CompoundNBT tag = NBTHelper.checkNBT(weapon).getTag();
        if (tag != null) {

            if (isActivated) {
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
            isActivated = aws;
        }
    }

    public double getAttackPower(ItemStack weapon) {
        CompoundNBT tag = NBTHelper.checkNBT(weapon).getTag();

        if (tag != null) {
            double AP = tag.getDouble(Constants.NBT.EXTRA_DAMAGE);

            if (AP > 0) {
                return AP;
            }

        }
            return baseAttack;
    }

    public double getAttackSpeed(ItemStack weapon) {
        CompoundNBT tag = NBTHelper.checkNBT(weapon).getTag();

        if (tag != null) {
            double AS = tag.getDouble(Constants.NBT.EXTRA_ATTACK_SPEED);
            if (AS != 0) return AS;
        }

        return baseSpeed;
    }

    public double getDevotion(PlayerEntity player) {
        if (player.getEntityWorld().getCapability(ReputationProvider.CAPABILITY).resolve().isPresent()) {
            return player.getEntityWorld().getCapability(ReputationProvider.CAPABILITY).resolve().get().getReputation(player, HexDeities.HEX_DEITY.getId());
        } else
            return 0;
    }

    public boolean getAwakened(ItemStack stack) {
        CompoundNBT tag = NBTHelper.checkNBT(stack).getTag();
        return tag != null && !stack.isEmpty() && tag.getBoolean(Constants.NBT.AW_State);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent(tooltipText));
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

    public void talk(PlayerEntity player) {
        player.sendMessage(new TranslationTextComponent(TextFormatting.DARK_PURPLE + this.getTranslationKey() + ".dialogue." + player.world.getRandom().nextInt(dialogueLines)), player.getUniqueID());
    }
}