package alexthw.hexblades.common.items;

import alexthw.hexblades.compat.ArsNouveauCompat;
import alexthw.hexblades.registers.Tiers;
import alexthw.hexblades.util.Constants;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
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
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

import static alexthw.hexblades.util.CompatUtil.isArsNovLoaded;

public class HexSwordItem extends SwordItem implements IHexblade {

    protected final double baseAttack;
    protected final double baseSpeed;

    protected TranslationTextComponent tooltipText = new TranslationTextComponent("The Dev Sword, you shouldn't read this");
    protected int rechargeTick = 5;
    protected final int dialogueLines = 3;

    public HexSwordItem(int attackDamage, float attackSpeed, Properties properties) {
        super(Tiers.PatronWeaponTier.INSTANCE, attackDamage, attackSpeed, properties);
        baseAttack = attackDamage;
        baseSpeed = attackSpeed;
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return shouldCauseReequipAnimation(oldStack, newStack);
    }

    @Override
    public void inventoryTick(ItemStack pStack, World pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        this.inventoryTick(pStack, pLevel, pEntity);
    }

    @Override
    public boolean hurtEnemy(ItemStack pStack, LivingEntity pTarget, LivingEntity pAttacker) {
        return this.hurtEnemy(pStack, pTarget, pAttacker, true);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClientSide()) {
            if (!(player.getItemInHand(Hand.OFF_HAND).getItem() instanceof ShieldItem)) {

                if (isArsNovLoaded()) {
                    if (ArsNouveauCompat.spellbookInOffHand(player)) return super.use(world, player, hand);
                }

                recalculatePowers(player.getItemInHand(hand), world, player);
            }
        }
        return super.use(world, player, hand);
    }

    //Only apply special effects if wielded by a Player

    @Override
    public void applyHexBonus(PlayerEntity user, boolean awakened) {
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker, boolean awakened) {
    }

    //data getters

    public int getRechargeTicks() {
        return rechargeTick;
    }

    public int getEnergyLeft(ItemStack stack) {
        return (getMaxDamage(stack) - stack.getDamageValue());
    }

    //set or reset awakened state & buffs
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        boolean awakening = setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon, awakening, devotion);
        setAttackSpeed(weapon, awakening, devotion);

    }

    public void setAttackPower(ItemStack weapon, boolean awakening, double extradamage) {

        CompoundNBT tag = weapon.getOrCreateTag();
        if (awakening) {
            tag.putDouble(Constants.NBT.EXTRA_DAMAGE, baseAttack + extradamage);
        } else {
            tag.putDouble(Constants.NBT.EXTRA_DAMAGE, baseAttack);
        }
    }

    public void setAttackSpeed(ItemStack weapon, boolean awakening, double extraspeed) {

        CompoundNBT tag = weapon.getOrCreateTag();

        if (awakening) {
            tag.putDouble(Constants.NBT.EXTRA_ATTACK_SPEED, baseSpeed + extraspeed);
        } else {
            tag.putDouble(Constants.NBT.EXTRA_ATTACK_SPEED, baseSpeed);
        }
    }

    public double getAttackPower(ItemStack weapon) {

        double AP = weapon.getOrCreateTag().getDouble(Constants.NBT.EXTRA_DAMAGE);

        return AP > 0 ? AP : baseAttack;

    }

    public double getAttackSpeed(ItemStack weapon) {

        double AS = weapon.getOrCreateTag().getDouble(Constants.NBT.EXTRA_ATTACK_SPEED);

        return AS != 0 ? AS : baseSpeed;

    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(tooltipText);
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