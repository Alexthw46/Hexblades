package alexthw.hexblades.common.items.tier2;

import alexthw.hexblades.common.items.IHexblade;
import alexthw.hexblades.util.Constants;
import alexthw.hexblades.util.NBTHelper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import elucent.eidolon.item.SappingSwordItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class SanguineSword extends SappingSwordItem implements IHexblade {

    protected final double baseAttack;
    protected final double baseAttackSpeed;
    protected boolean isActivated;

    protected String tooltipText = "It desires Blood";
    protected int rechargeTick = 5;
    protected final int dialogueLines = 3;

    public SanguineSword(Properties builderIn) {
        super(builderIn);
        baseAttack = 2;
        baseAttackSpeed = -2.4F;
        isActivated = false;
    }

    @Override
    public boolean hasBonus() {
        return true;
    }

    @Override
    public void applyHexBonus(PlayerEntity user, boolean awakened) {
        user.addEffect(new EffectInstance(Effects.NIGHT_VISION, 200, 0, false, false));
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
    public void talk(PlayerEntity player) {
        player.sendMessage(new TranslationTextComponent(this.getDescriptionId() + ".dialogue." + player.level.getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true)), player.getUUID());
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

        return baseAttackSpeed;
    }

    @Override
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

    @Override
    public void setAttackSpeed(ItemStack weapon, double extraspeed) {

        CompoundNBT tag = NBTHelper.checkNBT(weapon).getTag();
        if (tag != null) {

            if (isActivated) {
                tag.putDouble(Constants.NBT.EXTRA_ATTACK_SPEED, baseAttackSpeed + extraspeed);
            } else {
                tag.putDouble(Constants.NBT.EXTRA_ATTACK_SPEED, baseAttackSpeed);
            }
        }
    }

    @Override
    public void setAwakenedState(ItemStack stack, boolean aws) {
        if (!stack.isEmpty()) {
            CompoundNBT tag = NBTHelper.checkNBT(stack).getTag();
            if (tag != null) tag.putBoolean(Constants.NBT.AW_State, aws);
            this.isActivated = aws;
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return oldStack.getItem() != newStack.getItem();
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

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity user, int itemSlot, boolean isSelected) {
        if (user instanceof PlayerEntity && !worldIn.isClientSide()) {
            PlayerEntity player = (PlayerEntity) user;
            if (hasBonus()) {
                applyHexBonus(player, isActivated);
            }
            if (isActivated && !(player).isCreative()) {
                if ((getMaxDamage(stack) - stack.getDamageValue()) > 5) {
                    stack.hurtAndBreak(2, player, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
                } else {
                    recalculatePowers(player.getItemInHand(Hand.MAIN_HAND), worldIn, player);
                }
            } else if (stack.getDamageValue() > 0) {
                stack.setDamageValue(Math.max(stack.getDamageValue() - rechargeTick, 0));
            }
        }
    }
}
