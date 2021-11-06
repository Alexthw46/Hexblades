package alexthw.hexblades.common.items.tier1;

import alexthw.hexblades.common.items.IHexblade;
import alexthw.hexblades.registers.Tiers;
import alexthw.hexblades.util.Constants;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

import static alexthw.hexblades.ConfigHandler.COMMON;

public class EarthHammer1var extends PickaxeItem implements IHexblade {

    protected final double baseAttack;
    protected final double baseAttackSpeed;
    protected final float baseMiningSpeed;
    protected TranslationTextComponent tooltipText = new TranslationTextComponent("tooltip.HexSwordItem.earth_hammer");
    protected int rechargeTick = 5;
    protected int dialogueLines = 3;

    public EarthHammer1var(Properties props) {
        this(Tiers.PatronWeaponTier.INSTANCE, 8, -3.2F, props);
    }

    public EarthHammer1var(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        baseAttack = attackDamageIn;
        baseAttackSpeed = attackSpeedIn;
        baseMiningSpeed = tier.getSpeed();
    }

    public void switchMining(ItemStack weapon) {
        CompoundNBT tag = weapon.getOrCreateTag();
        tag.putBoolean(Constants.NBT.MiningSwitch, !tag.getBoolean(Constants.NBT.MiningSwitch));
        weapon.setTag(tag);
    }

    public void setAttackPower(ItemStack weapon, boolean awakening, double extradamage) {

        CompoundNBT tag = weapon.getOrCreateTag();
        tag.putDouble(Constants.NBT.EXTRA_DAMAGE, awakening ? baseAttack + extradamage : baseAttack);
    }

    public void setAttackSpeed(ItemStack weapon, boolean awakening, double extraspeed) {

        CompoundNBT tag = weapon.getOrCreateTag();
        tag.putDouble(Constants.NBT.EXTRA_ATTACK_SPEED, awakening ? baseAttackSpeed + extraspeed : baseAttackSpeed);
    }

    public void setMiningSpeed(ItemStack weapon, boolean awakening, float extra_mining) {

        CompoundNBT tag = weapon.getOrCreateTag();

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
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker, boolean awakened) {
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
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (player.isShiftKeyDown() && !world.isClientSide()) {
            switchMining(player.getItemInHand(hand));
        }
        return super.use(world, player, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity user, int itemSlot, boolean isSelected) {
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
    public double getDevotion(PlayerEntity player) {
        return IHexblade.super.getDevotion(player);
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        boolean mineSwitch = weapon.getOrCreateTag().getBoolean(Constants.NBT.MiningSwitch);

        if (!mineSwitch) setAwakenedState(weapon, !getAwakened(weapon));

        boolean awakening = getAwakened(weapon);

        setAttackPower(weapon, awakening, mineSwitch ? -6 : (devotion / COMMON.HammerDS1.get()));
        setMiningSpeed(weapon, awakening, (float) (devotion / COMMON.HammerMS1.get()));

    }

    @Override
    public void applyHexBonus(PlayerEntity user, boolean awakened) {
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        return this.hurtEnemy(stack, target, attacker, true);
    }

    @Override
    public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!worldIn.isClientSide && (state.getDestroySpeed(worldIn, pos) != 0.0F) && entityLiving instanceof PlayerEntity) {
            if (stack.getMaxDamage() - stack.getDamageValue() < 101) {
                switchMining(stack);
                recalculatePowers(stack, worldIn, (PlayerEntity) entityLiving);
            } else {
                stack.hurtAndBreak(100, entityLiving, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
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
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(tooltipText);
        tooltip.add(new TranslationTextComponent("Mining mode:" + (stack.getOrCreateTag().getBoolean(Constants.NBT.MiningSwitch) ? "On" : "Off")));
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return this.shouldCauseReequipAnimation(oldStack, newStack);
    }

    @Override
    public void talk(PlayerEntity player) {
        player.sendMessage(new TranslationTextComponent(this.getDescriptionId() + ".dialogue." + player.level.getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true)), player.getUUID());

    }

}
