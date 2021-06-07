package Alexthw.Hexblades.common.items.tier1;

import Alexthw.Hexblades.common.items.HexSwordItem;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EarthHammer1 extends HexSwordItem {

    protected float baseMiningSpeed;
    protected float newMiningSpeed;

    public EarthHammer1(Properties props) {
        super(9, -3.2F, props, 6.0F);
        baseMiningSpeed = 6.0F;
        newMiningSpeed = baseMiningSpeed;
        tooltipText = "tooltip.HexSwordItem.earth_hammer2";
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
        if (getAwakened(stack)) {
            target.attackEntityFrom(new EntityDamageSource("anvil", attacker).setDamageBypassesArmor(), 2.0f);
            power = (float) (1.0F + getDevotion(attacker) / 30);
        }
        double X = attacker.getPosX() - target.getPosX();
        double Z = attacker.getPosZ() - target.getPosZ();

        target.applyKnockback(power, X, Z);
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        setAwakenedState(weapon, !getAwakened(weapon));
        setAttackPower(weapon, devotion / 20);
        setMining_speed(weapon, (float) (devotion / 20));

    }

    public void setMining_speed(ItemStack weapon, float extra_mining) {

        if (getAwakened(weapon)) {
            newMiningSpeed = baseMiningSpeed + extra_mining;
        } else {
            newMiningSpeed = baseMiningSpeed;
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

        if ((material == Material.IRON || material == Material.ANVIL || material == Material.ROCK) && getAwakened(stack)) {
            result = newMiningSpeed + 45;
        } else if (getToolTypes(stack).stream().anyMatch(state::isToolEffective)) {
            result = newMiningSpeed;
        } else {
            result = 1.0F;
        }
        return result;
    }

}
