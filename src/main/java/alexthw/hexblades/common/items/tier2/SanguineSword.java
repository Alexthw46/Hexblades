package alexthw.hexblades.common.items.tier2;

import alexthw.hexblades.common.items.IHexblade;
import alexthw.hexblades.compat.ArsNouveauCompat;
import alexthw.hexblades.util.Constants;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import elucent.eidolon.item.SappingSwordItem;
import elucent.eidolon.network.LifestealEffectPacket;
import elucent.eidolon.network.Networking;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import static alexthw.hexblades.ConfigHandler.COMMON;
import static alexthw.hexblades.util.CompatUtil.isArsNovLoaded;

public class SanguineSword extends SappingSwordItem implements IHexblade {

    protected final double baseAttack;
    protected final double baseAttackSpeed;

    protected String tooltipText = "tooltip.hexblades.sanguine_sword";
    protected int rechargeTick = 5;
    protected final int dialogueLines = 3;

    public SanguineSword(Properties builderIn) {
        super(builderIn);
        setLore(tooltipText);
        baseAttack = COMMON.BloodBD.get();
        baseAttackSpeed = -2.4F;
    }

    @Override
    public boolean hasBonus() {
        return true;
    }

    @Override
    public boolean onHitEffects() {
        return true;
    }

    @Override
    public void inventoryTick(ItemStack pStack, World pLevel, Entity pEntity, int pItemSlot, boolean pIsSelected) {
        this.inventoryTick(pStack, pLevel, pEntity);
    }

    @Override
    public void applyHexBonus(PlayerEntity user, boolean awakened) {
        if (awakened) user.addEffect(new EffectInstance(Effects.NIGHT_VISION, 400, 0, false, false));
    }

    /**
     * @param stack    the instance of the item
     * @param target   the entity that is being damaged
     * @param attacker the player who is attacking
     * @param awakened if the blade is awakened
     */
    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker, boolean awakened) {
        float before = target.getHealth();
        target.hurt((new EntityDamageSource("wither", attacker)).bypassArmor(), awakened ? (float) (2.0F + getDevotion(attacker) / COMMON.BloodED.get()) : 2.0F);
        float healing = before - target.getHealth();
        if (healing > 0.0F) {
            attacker.heal(healing);
            if (!attacker.level.isClientSide) {
                Networking.sendToTracking(attacker.level, attacker.blockPosition(), new LifestealEffectPacket(target.blockPosition(), attacker.blockPosition(), 1.0F, 0.125F, 0.1875F));
            }
        }
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        boolean awakening = setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon, awakening, devotion / COMMON.BloodDS.get());

    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity) {
            if (target.invulnerableTime > 0) {
                target.invulnerableTime = 0;
            }
            if (onHitEffects()) applyHexEffects(stack, target, (PlayerEntity) attacker, getAwakened(stack));
            stack.setDamageValue(Math.max(stack.getDamageValue() - 10, 0));
        }
        return true;
    }

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

    public void talk(PlayerEntity player) {
        player.sendMessage(new TranslationTextComponent(this.getDescriptionId() + ".dialogue." + player.level.getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true)), player.getUUID());
    }

    //data getters
    public int getRechargeTicks() {
        return rechargeTick;
    }

    public int getEnergyLeft(ItemStack stack) {
        return getMaxDamage(stack) - stack.getDamageValue();
    }

    //NBT GETTERS
    public double getAttackPower(ItemStack weapon) {

        double AP = weapon.getOrCreateTag().getDouble(Constants.NBT.EXTRA_DAMAGE);

        return AP > 0 ? AP : baseAttack;

    }

    public double getAttackSpeed(ItemStack weapon) {

        double AS = weapon.getOrCreateTag().getDouble(Constants.NBT.EXTRA_ATTACK_SPEED);

        return AS != 0 ? AS : baseAttackSpeed;

    }

    //NBT SETTERS

    public void setAttackPower(ItemStack weapon, boolean awakening, double extradamage) {

        CompoundNBT tag = weapon.getOrCreateTag();

        tag.putDouble(Constants.NBT.EXTRA_DAMAGE, awakening ? baseAttack + extradamage : baseAttack);
    }

    public void setAttackSpeed(ItemStack weapon, boolean awakening, double extraspeed) {

        CompoundNBT tag = weapon.getOrCreateTag();

        tag.putDouble(Constants.NBT.EXTRA_ATTACK_SPEED, awakening ? baseAttackSpeed + extraspeed : baseAttackSpeed);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return shouldCauseReequipAnimation(oldStack, newStack);
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

}
