package alexthw.hexblades.common.items.tier1;

import alexthw.hexblades.common.items.IHexblade;
import alexthw.hexblades.registers.Tiers;
import alexthw.hexblades.util.Constants;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import java.util.List;

import static alexthw.hexblades.ConfigHandler.COMMON;

import net.minecraft.world.item.Item.Properties;

public class EarthHammer1var extends PickaxeItem implements IHexblade {

    protected final double baseAttack;
    protected final double baseAttackSpeed;
    protected final float baseMiningSpeed;
    protected TranslatableComponent tooltipText = new TranslatableComponent("tooltip.HexSwordItem.earth_hammer");
    protected int rechargeTick = 5;
    protected int dialogueLines = 3;

    public EarthHammer1var(Properties props) {
        this(Tiers.PatronWeaponTier.INSTANCE, 8, -3.2F, props);
    }

    public EarthHammer1var(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        baseAttack = attackDamageIn;
        baseAttackSpeed = attackSpeedIn;
        baseMiningSpeed = tier.getSpeed();
    }

    public void switchMining(ItemStack weapon) {
        CompoundTag tag = weapon.getOrCreateTag();
        tag.putBoolean(Constants.NBT.MiningSwitch, !tag.getBoolean(Constants.NBT.MiningSwitch));
        weapon.setTag(tag);
    }

    public void setAttackPower(ItemStack weapon, boolean awakening, double extradamage) {

        CompoundTag tag = weapon.getOrCreateTag();
        tag.putDouble(Constants.NBT.EXTRA_DAMAGE, awakening ? baseAttack + extradamage : baseAttack);
    }

    public void setAttackSpeed(ItemStack weapon, boolean awakening, double extraspeed) {

        CompoundTag tag = weapon.getOrCreateTag();
        tag.putDouble(Constants.NBT.EXTRA_ATTACK_SPEED, awakening ? baseAttackSpeed + extraspeed : baseAttackSpeed);
    }

    public void setMiningSpeed(ItemStack weapon, boolean awakening, float extra_mining) {

        CompoundTag tag = weapon.getOrCreateTag();

        float newMiningSpeed;

        if (tag.getBoolean(Constants.NBT.MiningSwitch)) {
            if (awakening) {
                newMiningSpeed = baseMiningSpeed + extra_mining / 2;
            } else {
                newMiningSpeed = baseMiningSpeed;
            }
        } else {
            newMiningSpeed = 1.0F;
        }
        tag.putFloat(Constants.NBT.EXTRA_MINING_SPEED, newMiningSpeed);

        weapon.setTag(tag);
    }


    public double getAttackPower(ItemStack weapon) {

        double AP = weapon.getOrCreateTag().getDouble(Constants.NBT.EXTRA_DAMAGE);

        return AP > 0 ? AP : baseAttack;

    }

    public double getAttackSpeed(ItemStack weapon) {

        double AS = weapon.getOrCreateTag().getDouble(Constants.NBT.EXTRA_ATTACK_SPEED);
        return AS != 0 ? AS : baseAttackSpeed;

    }

    @Override
    public boolean onHitEffects() {
        return true;
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, Player attacker, boolean awakened) {
        float power = 1.0F;
        if (awakened) {
            target.hurt(new EntityDamageSource("anvil", attacker).bypassArmor(), COMMON.HammerED1.get().floatValue());
            power = (float) (1.0F + getDevotion(attacker) / 30);
        }
        double X = attacker.getX() - target.getX();
        double Z = attacker.getZ() - target.getZ();

        target.knockback(power, X, Z);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (player.isShiftKeyDown() && !world.isClientSide()) {
            switchMining(player.getItemInHand(hand));
        }
        return super.use(world, player, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity user, int itemSlot, boolean isSelected) {
        if (stack.getOrCreateTag().getBoolean(Constants.NBT.MiningSwitch)) return;
        this.inventoryTick(stack, worldIn, user);
    }

    @Override
    public int getRechargeTicks() {
        return rechargeTick;
    }

    @Override
    public int getEnergyLeft(ItemStack stack) {
        return getMaxDamage(stack) - stack.getDamageValue();
    }

    @Override
    public double getDevotion(Player player) {
        return IHexblade.super.getDevotion(player);
    }

    @Override
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {
        double devotion = getDevotion(player);

        boolean mineSwitch = weapon.getOrCreateTag().getBoolean(Constants.NBT.MiningSwitch);

        if (!mineSwitch) setAwakenedState(weapon, !getAwakened(weapon));

        boolean awakening = getAwakened(weapon);

        setAttackPower(weapon, awakening, mineSwitch ? -6 : (devotion / COMMON.HammerDS1.get()));
        setMiningSpeed(weapon, awakening, (float) (devotion / COMMON.HammerMS1.get()));

    }

    @Override
    public void applyHexBonus(Player user, boolean awakened) {
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return this.hurtEnemy(stack, target, attacker, true);
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!worldIn.isClientSide && (state.getDestroySpeed(worldIn, pos) != 0.0F) && entityLiving instanceof Player) {
            if (stack.getMaxDamage() - stack.getDamageValue() < 101) {
                switchMining(stack);
                recalculatePowers(stack, worldIn, (Player) entityLiving);
            } else {
                stack.hurtAndBreak(100, entityLiving, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }
        return true;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {

        float result;

        Material material = state.getMaterial();

        float newMiningSpeed = getMiningSpeed(stack);
        if ((material == Material.STONE) && getAwakened(stack)) {
            result = newMiningSpeed + 2.0F;
        } else if (material == Material.METAL || material == Material.HEAVY_METAL || getToolTypes(stack).stream().anyMatch(state::isToolEffective)) {
            result = newMiningSpeed;
        } else {
            result = 1.0F;
        }
        return result;
    }

    protected float getMiningSpeed(ItemStack weapon) {
        return weapon.getOrCreateTag().getFloat(Constants.NBT.EXTRA_MINING_SPEED);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(tooltipText);
        tooltip.add(new TranslatableComponent("Mining mode:" + (stack.getOrCreateTag().getBoolean(Constants.NBT.MiningSwitch) ? "On" : "Off")));
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return this.shouldCauseReequipAnimation(oldStack, newStack);
    }

    @Override
    public void talk(Player player) {
        player.sendMessage(new TranslatableComponent(this.getDescriptionId() + ".dialogue." + player.level.getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true)), player.getUUID());

    }

}
