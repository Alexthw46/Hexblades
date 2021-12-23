package alexthw.hexblades.common.items.tier1;

import alexthw.hexblades.common.entity.FulgorProjectileEntity;
import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.registers.HexEntityType;
import alexthw.hexblades.registers.HexRegistry;
import alexthw.hexblades.util.Constants;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import elucent.eidolon.Registry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

import static alexthw.hexblades.ConfigHandler.COMMON;
import static java.lang.Math.min;

public class Lightning_SSwordL1 extends HexSwordItem {

    protected int projectileCost = getMaxDamage() / 3;

    public Lightning_SSwordL1(Item.Properties props) {
        super(COMMON.DaggerBDL.get(), -1.5F, props);
        tooltipText = new TranslationTextComponent("tooltip.hexblades.thunder_knives");
        textColor = TextFormatting.YELLOW;
    }

    @Override
    public UseAction getUseAnimation(ItemStack stack) {
        return UseAction.SPEAR;
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
    public void inventoryTick(ItemStack stack, World worldIn, Entity user) {
        if (user instanceof PlayerEntity && !worldIn.isClientSide()) {
            PlayerEntity player = (PlayerEntity) user;
            boolean currentState = hasTwin(player);
            if (stack.getDamageValue() > 0) {
                int rechargeTicksBonus = currentState ? (int) getDevotion(player) / (COMMON.DualsRR.get()) : 0;
                stack.setDamageValue(Math.max(stack.getDamageValue() - (getRechargeTicks() + rechargeTicksBonus), 0));
            }
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker, boolean hex) {
        if (attacker instanceof PlayerEntity) {
            applyHexEffects(stack, target, (PlayerEntity) attacker, getAwakened(stack));
            stack.setDamageValue(Math.max(stack.getDamageValue() - 10, 0));
        }
        return true;
    }

    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getItemInHand(handIn);
        if (handIn == Hand.OFF_HAND && hasTwin(playerIn) && (stack.getMaxDamage() - stack.getDamageValue() > projectileCost)) {
            playerIn.startUsingItem(handIn);
            setAwakenedState(stack, true);
            recalculatePowers(playerIn.getItemInHand(handIn), worldIn, playerIn);
            return ActionResult.consume(stack);
        }
        return ActionResult.pass(playerIn.getItemInHand(handIn));
    }

    public boolean hasTwin(PlayerEntity player) {
        ItemStack is = player.getItemInHand(Hand.MAIN_HAND);
        if (is.getItem() instanceof Lightning_SSwordR1) {
            return (getAwakened(is));
        }
        return false;
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        boolean awakening = getAwakened(weapon);
        double devotion = getDevotion(player);
        setAttackPower(weapon, awakening, devotion / COMMON.DualsDS1.get());
        setAttackSpeed(weapon, awakening, devotion / COMMON.DualsAS1.get());
    }

    public int getUseDuration(ItemStack stack) {
        return 72000 - (int) (10 * getAttackSpeed(stack));
    }

    @Override
    public void releaseUsing(ItemStack stack, World world, LivingEntity entity, int timeleft) {
        if (!(entity instanceof PlayerEntity)) return;
        PlayerEntity player = (PlayerEntity) entity;
        int i = this.getUseDuration(stack) - timeleft;
        if (!world.isClientSide() && i >= 10) {
            Vector3d pos = entity.position().add(entity.getLookAngle().scale(0.5D)).add(-0.5D * Math.sin(Math.toRadians(225.0F - entity.yHeadRot)), entity.getBbHeight() * 0.75F, -0.5D * Math.cos(Math.toRadians(225.0F - entity.yHeadRot)));
            Vector3d vel = entity.getEyePosition(0.0F).add(entity.getLookAngle().scale(40.0D)).subtract(pos).scale(0.05D);
            world.addFreshEntity((new FulgorProjectileEntity(HexEntityType.FULGOR_PROJECTILE.get(), world)).shoot(pos.x, pos.y, pos.z, vel.x, vel.y, vel.z, entity.getUUID()));
            world.playSound(null, pos.x, pos.y, pos.z, Registry.CAST_SOULFIRE_EVENT.get(), SoundCategory.NEUTRAL, 0.75F, random.nextFloat() * 0.2F + 0.9F);
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
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker, boolean awakened) {
        target.addEffect(new EffectInstance(HexRegistry.CHARGED_EFFECT.get(), 100, 0));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
        if (slot == EquipmentSlotType.OFFHAND) {
            multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", getAttackPower(stack), AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", getAttackSpeed(stack), AttributeModifier.Operation.ADDITION));
        }
        return multimap;
    }

    @Override
    public void talk(PlayerEntity player) {
    }

    @Override
    protected void addShiftTooltip(ItemStack stack, List<ITextComponent> tooltip) {
        tooltip.add(new StringTextComponent("Hold use to fire a fulgor"));
        tooltip.add(new StringTextComponent("Can overcharge enemies"));
    }
}
