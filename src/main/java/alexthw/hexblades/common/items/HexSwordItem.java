package alexthw.hexblades.common.items;

import alexthw.hexblades.deity.HexDeities;
import alexthw.hexblades.registers.Tiers;
import alexthw.hexblades.util.Constants;
import alexthw.hexblades.util.NBTHelper;
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
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public class HexSwordItem extends SwordItem {

    protected final double baseAttack;
    protected final double baseSpeed;
    protected boolean isActivated;

    protected String tooltipText = "The Dev Sword, you shouldn't read this";
    protected int rechargeTick = 5;
    protected final int dialogueLines = 3;

    public HexSwordItem(int attackDamage, float attackSpeed, Properties properties) {
        super(Tiers.PatronWeaponTier.INSTANCE, attackDamage, attackSpeed, properties);
        baseAttack = attackDamage;
        baseSpeed = attackSpeed;
        isActivated = false;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return oldStack.getItem() != newStack.getItem();
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity user, int itemSlot, boolean isSelected) {
        if (user instanceof PlayerEntity && !worldIn.isClientSide()) {
            if (hasBonus()) {
                applyHexBonus((PlayerEntity) user, isActivated);
            }
            if (isActivated && !((PlayerEntity) user).isCreative()) {
                if ((getMaxDamage(stack) - stack.getDamageValue()) > 5) {
                    stack.hurtAndBreak(2, (LivingEntity) user, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
                } else {
                    recalculatePowers(((PlayerEntity) user).getItemInHand(Hand.MAIN_HAND), worldIn, (PlayerEntity) user);
                }
            } else if (stack.getDamageValue() > 0) {
                stack.setDamageValue(Math.max(stack.getDamageValue() - rechargeTick, 0));
            }
        }
    }

    public void applyHexBonus(PlayerEntity user, boolean awakened) {
    }

    public boolean hasBonus() {
        return false;
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClientSide()) {
            if (!(isActivated && (player.getItemInHand(Hand.OFF_HAND).getItem() instanceof ShieldItem))) {
                recalculatePowers(player.getItemInHand(hand), world, player);
            }
        }
        return super.use(world, player, hand);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity) {
            if (target.invulnerableTime > 0) {
                target.invulnerableTime = 0;
            }
            if (isActivated || onHitEffects()) applyHexEffects(stack, target, (PlayerEntity) attacker);
            stack.setDamageValue(Math.max(stack.getDamageValue() - 10, 0));
        }
        return true;
    }

    protected boolean onHitEffects() {
        return false;
    }

    //Only apply special effects if wielded by a Player
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker) {
        target.hurt(new EntityDamageSource("wither", attacker).bypassArmor(), 2.0f);
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
        if (player.getCommandSenderWorld().getCapability(ReputationProvider.CAPABILITY).resolve().isPresent()) {
            return player.getCommandSenderWorld().getCapability(ReputationProvider.CAPABILITY).resolve().get().getReputation(player, HexDeities.HEX_DEITY.getId());
        } else
            return 0;
    }

    public boolean getAwakened(ItemStack stack) {
        CompoundNBT tag = NBTHelper.checkNBT(stack).getTag();
        return tag != null && !stack.isEmpty() && tag.getBoolean(Constants.NBT.AW_State);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent(tooltipText));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
        if (slot == EquipmentSlotType.MAINHAND) {
            multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", getAttackPower(stack), AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", getAttackSpeed(stack), AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

    public void talk(PlayerEntity player) {
        player.sendMessage(new TranslationTextComponent(this.getDescriptionId() + ".dialogue." + player.level.getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true)), player.getUUID());
    }
}