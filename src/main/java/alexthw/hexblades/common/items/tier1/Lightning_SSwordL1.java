package alexthw.hexblades.common.items.tier1;

import alexthw.hexblades.common.entity.FulgorProjectileEntity;
import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.registers.HexEntityType;
import alexthw.hexblades.registers.HexRegistry;
import alexthw.hexblades.util.Constants;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import elucent.eidolon.Registry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import static alexthw.hexblades.ConfigHandler.COMMON;
import static java.lang.Math.min;

public class Lightning_SSwordL1 extends HexSwordItem {

    protected int projectileCost = this.getTier().getUses() / 3;

    public Lightning_SSwordL1(Item.Properties props) {
        super(1, -1.5F, props);
        tooltipText = new TranslatableComponent("tooltip.hexblades.thunder_knives");
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.SPEAR;
    }

    @Override
    public boolean setAwakenedState(ItemStack stack, boolean aws) {
        if (!stack.isEmpty()) {
            stack.getOrCreateTag().putBoolean(Constants.NBT.AW_State, aws);
            return aws;
        }
        return false;
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity user) {
        if (user instanceof Player player && !worldIn.isClientSide()) {
            boolean currentState = hasTwin(player);
            if (stack.getDamageValue() > 0) {
                int rechargeTicksBonus = currentState ? (int) getDevotion(player) / (COMMON.DualsRR.get()) : 0;
                stack.setDamageValue(Math.max(stack.getDamageValue() - (getRechargeTicks() + rechargeTicksBonus), 0));
            }
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker, boolean hex) {
        if (attacker instanceof Player) {
            applyHexEffects(stack, target, (Player) attacker, getAwakened(stack));
            stack.setDamageValue(Math.max(stack.getDamageValue() - 10, 0));
        }
        return true;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level worldIn, @NotNull Player playerIn, @NotNull InteractionHand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        if (handIn == InteractionHand.OFF_HAND && hasTwin(playerIn) && (stack.getMaxDamage() - stack.getDamageValue() > projectileCost)) {
            playerIn.startUsingItem(handIn);
            setAwakenedState(stack, true);
            recalculatePowers(playerIn.getItemInHand(handIn), worldIn, playerIn);
            return InteractionResultHolder.consume(stack);
        }
        return InteractionResultHolder.pass(playerIn.getItemInHand(handIn));
    }

    public boolean hasTwin(Player player) {
        ItemStack is = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (is.getItem() instanceof Lightning_SSwordR1) {
            return (getAwakened(is));
        }
        return false;
    }

    @Override
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {
        boolean awakening = getAwakened(weapon);
        double devotion = getDevotion(player);
        setAttackPower(weapon, awakening, devotion / COMMON.DualsDS1.get());
        setAttackSpeed(weapon, awakening, devotion / COMMON.DualsAS1.get());

    }

    public int getUseDuration(@NotNull ItemStack stack) {
        return 72000 - (int) (10 * getAttackSpeed(stack));
    }

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @NotNull Level world, @NotNull LivingEntity entity, int timeLeft) {
        if (!(entity instanceof Player player)) return;
        int i = this.getUseDuration(stack) - timeLeft;
        if (!world.isClientSide() && i >= 10) {
            Vec3 pos = entity.position().add(entity.getLookAngle().scale(0.5D)).add(-0.5D * Math.sin(Math.toRadians(225.0F - entity.yHeadRot)), entity.getBbHeight() * 0.75F, -0.5D * Math.cos(Math.toRadians(225.0F - entity.yHeadRot)));
            Vec3 vel = entity.getEyePosition(0.0F).add(entity.getLookAngle().scale(40.0D)).subtract(pos).scale(0.05D);
            world.addFreshEntity((new FulgorProjectileEntity(HexEntityType.FULGOR_PROJECTILE.get(), world)).shoot(pos.x, pos.y, pos.z, vel.x, vel.y, vel.z, entity.getUUID()));
            world.playSound(null, pos.x, pos.y, pos.z, Registry.CAST_SOULFIRE_EVENT.get(), SoundSource.NEUTRAL, 0.75F, world.random.nextFloat() * 0.2F + 0.9F);
            if (!(player.isCreative())) {
                stack.setDamageValue(min(stack.getDamageValue() + projectileCost, stack.getMaxDamage() - 1));
            }
        }
        setAwakenedState(stack, false);
        recalculatePowers(stack, world, player);
    }

    @Override
    public boolean onHitEffects() {
        return true;
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, Player attacker, boolean awakened) {
        target.addEffect(new MobEffectInstance(HexRegistry.CHARGED_EFFECT.get(), 100, 0));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
        if (slot == EquipmentSlot.OFFHAND) {
            multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", getAttackPower(stack), AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", getAttackSpeed(stack), AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

    @Override
    public void talk(Player player) {
    }

}
