package Alexthw.Hexblades.common.items.tier1;

import Alexthw.Hexblades.common.items.HexSwordItem;
import Alexthw.Hexblades.util.HexUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class EarthHammer1 extends HexSwordItem {

    protected float baseMiningSpeed;
    protected float newMiningSpeed;
    protected boolean mineSwitch;

    public EarthHammer1(Properties props) {
        super(8, -3.2F, props, 6.0F);
        baseMiningSpeed = 6.0F;
        newMiningSpeed = baseMiningSpeed;
        tooltipText = "tooltip.HexSwordItem.earth_hammer";
        mineSwitch = false;
    }

    public EarthHammer1(int attackDamage, float attackSpeed, Properties props, float mining_speed) {
        super(attackDamage, attackSpeed, props, mining_speed);
    }

    @Override
    protected boolean onHitEffects() {
        return true;
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker) {
        float power = 1.0F;
        if (isActivated) {
            target.attackEntityFrom(new EntityDamageSource("anvil", attacker).setDamageBypassesArmor(), 2.0f);
            power = (float) (1.0F + getDevotion(attacker) / 30);
        }
        double X = attacker.getPosX() - target.getPosX();
        double Z = attacker.getPosZ() - target.getPosZ();

        target.applyKnockback(power, X, Z);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        if (player.isSneaking() && !world.isRemote()) {
            mineSwitch = !mineSwitch;
        }
        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World worldIn, Entity user, int itemSlot, boolean isSelected) {
        if (mineSwitch) {
            return;
        }
        super.inventoryTick(stack, worldIn, user, itemSlot, isSelected);
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        if (!mineSwitch) setAwakenedState(weapon, !getAwakened(weapon));
        setAttackPower(weapon, mineSwitch ? -6 : (devotion / 10));
        setMiningSpeed((float) (devotion / 20));

    }

    @Override
    public void setAttackPower(ItemStack weapon, double extradamage) {
        if (mineSwitch) isActivated = true;
        super.setAttackPower(weapon, extradamage);
        if (mineSwitch) isActivated = false;
    }

    public void setMiningSpeed(float extra_mining) {

        if (mineSwitch) {
            if (isActivated) {
                newMiningSpeed = baseMiningSpeed + extra_mining / 2;
            } else {
                newMiningSpeed = baseMiningSpeed;
            }
        } else {
            newMiningSpeed = 1.0F;
        }

    }

    @Override
    public boolean canHarvestBlock(BlockState blockIn) {
        int i = this.getTier().getHarvestLevel();
        if (blockIn.getHarvestTool() == net.minecraftforge.common.ToolType.PICKAXE) {
            return i >= blockIn.getHarvestLevel();
        }
        Material material = blockIn.getMaterial();
        return material == Material.ROCK || material == Material.IRON || material == Material.ANVIL;
    }

    public boolean canPlayerBreakBlockWhileHolding(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
        return true;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {

        float result;

        Material material = state.getMaterial();

        if ((material == Material.ROCK) && isActivated) {
            result = newMiningSpeed + 2.0F;
        } else if (material == Material.IRON || material == Material.ANVIL || getToolTypes(stack).stream().anyMatch(state::isToolEffective)) {
            result = newMiningSpeed;
        } else {
            result = 1.0F;
        }
        return result;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("Mining mode:" + (mineSwitch ? "On" : "Off")));
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!worldIn.isRemote && state.getBlockHardness(worldIn, pos) != 0.0F) {
            if (stack.getMaxDamage() - stack.getDamage() < 101) {
                mineSwitch = false;
                setAwakenedState(stack, false);
            } else {
                stack.damageItem(100, entityLiving, (entity) -> entity.sendBreakAnimation(EquipmentSlotType.MAINHAND));
            }
        }
        return true;
    }

    @Override
    public void talk(PlayerEntity player) {
        player.sendMessage(new TranslationTextComponent(this.getTranslationKey() + ".dialogue." + player.world.getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.setItalic(true).setColor(Color.fromInt(HexUtils.earthColor))), player.getUniqueID());

    }

}
