package alexthw.hexblades.common.items.tier1;

import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.registers.Tiers;
import alexthw.hexblades.util.HexUtils;
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

import static alexthw.hexblades.ConfigHandler.COMMON;

public class EarthHammer1 extends HexSwordItem {

    protected float baseMiningSpeed;
    protected float newMiningSpeed;
    protected boolean mineSwitch;

    public EarthHammer1(Properties props) {
        this(8, -3.2F,
                props.addToolType(net.minecraftforge.common.ToolType.PICKAXE, Tiers.PatronWeaponTier.INSTANCE.getLevel()));
        baseMiningSpeed = 6.0F;
        newMiningSpeed = baseMiningSpeed;
        tooltipText = "tooltip.HexSwordItem.earth_hammer";
        mineSwitch = false;
    }

    public EarthHammer1(int attack, float speed, Properties props) {
        super(attack, speed, props);
    }

    @Override
    protected boolean onHitEffects() {
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
        if (mineSwitch) {
            return;
        }
        super.inventoryTick(stack, worldIn, user, itemSlot, isSelected);
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        if (!mineSwitch) setAwakenedState(weapon, !getAwakened(weapon));
        setAttackPower(weapon, mineSwitch ? -6 : (devotion / COMMON.HammerDS1.get()));
        setMiningSpeed((float) (devotion / COMMON.HammerMS1.get()));

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
    public boolean isCorrectToolForDrops(BlockState blockIn) {
        int i = this.getTier().getLevel();
        if (blockIn.getHarvestTool() == net.minecraftforge.common.ToolType.PICKAXE) {
            return i >= blockIn.getHarvestLevel();
        }
        Material material = blockIn.getMaterial();
        return material == Material.STONE || material == Material.METAL || material == Material.HEAVY_METAL;
    }

    @Override
    public boolean canAttackBlock(BlockState state, World worldIn, BlockPos pos, PlayerEntity player) {
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
        tooltip.add(new TranslationTextComponent("Mining mode:" + (mineSwitch ? "On" : "Off")));
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
    public void talk(PlayerEntity player) {
        player.sendMessage(new TranslationTextComponent(this.getDescriptionId() + ".dialogue." + player.level.getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true).withColor(Color.fromRgb(HexUtils.earthColor))), player.getUUID());

    }

}
