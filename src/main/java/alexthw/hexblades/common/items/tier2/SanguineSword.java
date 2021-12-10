package alexthw.hexblades.common.items.tier2;

import alexthw.hexblades.common.items.IHexblade;
import alexthw.hexblades.compat.ArsNouveauCompat;
import alexthw.hexblades.util.Constants;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import elucent.eidolon.item.SappingSwordItem;
import elucent.eidolon.network.LifestealEffectPacket;
import elucent.eidolon.network.Networking;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

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
        baseAttack = 4;
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
    public void inventoryTick(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull Entity pEntity, int pItemSlot, boolean pIsSelected) {
        this.inventoryTick(pStack, pLevel, pEntity);
    }

    @Override
    public void applyHexBonus(Player user, boolean awakened) {
        if (awakened) user.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 400, 0, false, false));
    }

    /**
     * @param stack    the instance of the item
     * @param target   the entity that is being damaged
     * @param attacker the player who is attacking
     * @param awakened if the blade is awakened
     */
    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, Player attacker, boolean awakened) {
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
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {
        double devotion = getDevotion(player);

        boolean awakening = setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon, awakening, devotion / COMMON.BloodDS.get());

    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return this.hurtEnemy(stack, target, attacker, true);
    }

    public @NotNull InteractionResultHolder<ItemStack> use(Level world, @NotNull Player player, @NotNull InteractionHand hand) {
        if (!world.isClientSide()) {
            if (!(player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof ShieldItem)) {

                if (isArsNovLoaded()) {
                    if (ArsNouveauCompat.spellbookInOffHand(player)) return super.use(world, player, hand);
                }

                recalculatePowers(player.getItemInHand(hand), world, player);
            }
        }
        return super.use(world, player, hand);
    }

    public void talk(Player player) {
        player.sendMessage(new TranslatableComponent(this.getDescriptionId() + ".dialogue." + player.level.getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true)), player.getUUID());
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

        CompoundTag tag = weapon.getOrCreateTag();

        tag.putDouble(Constants.NBT.EXTRA_DAMAGE, awakening ? baseAttack + extradamage : baseAttack);
    }

    public void setAttackSpeed(ItemStack weapon, boolean awakening, double extraspeed) {

        CompoundTag tag = weapon.getOrCreateTag();

        tag.putDouble(Constants.NBT.EXTRA_ATTACK_SPEED, awakening ? baseAttackSpeed + extraspeed : baseAttackSpeed);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return shouldCauseReequipAnimation(oldStack, newStack);
    }


    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
        if (slot == EquipmentSlot.MAINHAND) {
            multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", getAttackPower(stack), AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", getAttackSpeed(stack), AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

}
