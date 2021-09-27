package alexthw.hexblades.common.items.tier1;

import alexthw.hexblades.common.items.IHexblade;
import alexthw.hexblades.registers.Tiers;
import alexthw.hexblades.util.Constants;
import alexthw.hexblades.util.NBTHelper;
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
    protected float newMiningSpeed;
    protected boolean mineSwitch;
    protected boolean isActivated;
    private final String tooltipText = "tooltip.HexSwordItem.earth_hammer";
    protected int rechargeTick = 5;
    protected int dialogueLines = 3;

    public EarthHammer1var(Properties props) {
        this(Tiers.PatronWeaponTier.INSTANCE, 8, -3.2F, props);
    }

    public EarthHammer1var(IItemTier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        baseAttack = attackDamageIn;
        baseAttackSpeed = attackSpeedIn;
        baseMiningSpeed = newMiningSpeed = tier.getSpeed();
        mineSwitch = false;
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
                tag.putDouble(Constants.NBT.EXTRA_ATTACK_SPEED, baseAttackSpeed + extraspeed);
            } else {
                tag.putDouble(Constants.NBT.EXTRA_ATTACK_SPEED, baseAttackSpeed);
            }
        }
    }

    public void setMiningSpeed(float extra_mining) {

        if (isActivated) {
            newMiningSpeed = baseMiningSpeed + extra_mining / 2;
        } else {
            newMiningSpeed = baseMiningSpeed;
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

        return baseAttackSpeed;
    }

    @Override
    public boolean onHitEffects() {
        return true;
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker) {
        float power = 1.0F;
        if (isActivated) {
            target.hurt(new EntityDamageSource("anvil", attacker).bypassArmor(), COMMON.HammerED1.get());
            power = (float) (1.0F + getDevotion(attacker) / 30);
        }
        double X = attacker.getX() - target.getX();
        double Z = attacker.getZ() - target.getZ();

        target.knockback(power, X, Z);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (player.isShiftKeyDown() && !world.isClientSide()) {
            mineSwitch = !mineSwitch;
        }
        return super.use(world, player, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity user, int itemSlot, boolean isSelected) {
        if (!mineSwitch && (user instanceof PlayerEntity) && !worldIn.isClientSide()) {
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

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        if (!mineSwitch) setAwakenedState(weapon, !getAwakened(weapon));
        setAttackPower(weapon, mineSwitch ? -6 : (devotion / COMMON.HammerDS1.get()));
        setMiningSpeed(mineSwitch ? (float) (devotion / COMMON.HammerMS1.get()) : 1.0F);

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

    @Override
    public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!worldIn.isClientSide && (state.getDestroySpeed(worldIn, pos) != 0.0F) && entityLiving instanceof PlayerEntity) {
            if (stack.getMaxDamage() - stack.getDamageValue() < 101) {
                mineSwitch = false;
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

        if ((material == Material.STONE) && isActivated) {
            result = newMiningSpeed + 2.0F;
        } else if (material == Material.METAL || material == Material.HEAVY_METAL || getToolTypes(stack).stream().anyMatch(state::isToolEffective)) {
            result = newMiningSpeed;
        } else {
            result = 1.0F;
        }
        return result;
    }

    @Override
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent(tooltipText));
        tooltip.add(new TranslationTextComponent("Mining mode:" + (mineSwitch ? "On" : "Off")));
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return oldStack.getItem() != newStack.getItem();
    }

    @Override
    public void talk(PlayerEntity player) {
        player.sendMessage(new TranslationTextComponent(this.getDescriptionId() + ".dialogue." + player.level.getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true)), player.getUUID());

    }

}
