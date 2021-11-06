package alexthw.hexblades.common.items.tier1;

import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.registers.Tiers;
import alexthw.hexblades.util.Constants;
import alexthw.hexblades.util.HexUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
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
import static alexthw.hexblades.util.Constants.NBT.MiningSwitch;

public class EarthHammer1 extends HexSwordItem {

    protected float baseMiningSpeed;

    public EarthHammer1(Properties props) {
        this(7, -3.2F,
                props.addToolType(net.minecraftforge.common.ToolType.PICKAXE, Tiers.PatronWeaponTier.INSTANCE.getLevel()));
        baseMiningSpeed = 8.0F;
        tooltipText = new TranslationTextComponent("tooltip.hexblades.earth_hammer");
    }

    public EarthHammer1(int attack, float speed, Properties props) {
        super(attack, speed, props);
    }

    @Override
    public boolean onHitEffects() {
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment.category == EnchantmentType.DIGGER) return true;
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker, boolean awakened) {
        if (stack.getOrCreateTag().getBoolean(MiningSwitch)) return;
        float power = 1.0F;
        if (awakened) {
            target.hurt(new EntityDamageSource("anvil", attacker).bypassArmor(), COMMON.HammerED1.get().floatValue());
            power += (float) (getDevotion(attacker) / 30);
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
        if (stack.getOrCreateTag().getBoolean(MiningSwitch)) {
            return;
        }
        super.inventoryTick(stack, worldIn, user, itemSlot, isSelected);
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        boolean mineSwitch = weapon.getOrCreateTag().getBoolean(MiningSwitch);

        if (!mineSwitch) setAwakenedState(weapon, !getAwakened(weapon));

        boolean awakening = getAwakened(weapon);

        setAttackPower(weapon, awakening || mineSwitch, mineSwitch ? -6 : (devotion / COMMON.HammerDS1.get()));
        setMiningSpeed(weapon, awakening, (float) (devotion / COMMON.HammerMS1.get()));

    }

    public void setMiningSpeed(ItemStack weapon, boolean awakening, float extra_mining) {

        CompoundNBT tag = weapon.getOrCreateTag();

        float newMiningSpeed;

        if (tag.getBoolean(MiningSwitch)) {
            if (awakening) {
                newMiningSpeed = baseMiningSpeed + extra_mining / 2;
            } else {
                newMiningSpeed = baseMiningSpeed;
            }
        } else {
            newMiningSpeed = 1.0F;
        }
        tag.putFloat(Constants.NBT.EXTRA_MINING_SPEED, newMiningSpeed);

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

        if ((material == Material.STONE) && getAwakened(stack)) {
            result = getMiningSpeed(stack) + 2.0F;
        } else if (material == Material.METAL || material == Material.HEAVY_METAL || getToolTypes(stack).stream().anyMatch(state::isToolEffective)) {
            result = getMiningSpeed(stack);
        } else {
            result = 1.0F;
        }
        return result;
    }

    public void switchMining(ItemStack weapon) {
        CompoundNBT tag = weapon.getOrCreateTag();
        tag.putBoolean(MiningSwitch, !tag.getBoolean(MiningSwitch));
        weapon.setTag(tag);
    }

    protected float getMiningSpeed(ItemStack stack) {
        return stack.getOrCreateTag().getFloat(Constants.NBT.EXTRA_MINING_SPEED);
    }

    @Override
    public void appendHoverText(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("Mining mode:" + (stack.getOrCreateTag().getBoolean(MiningSwitch) ? "On" : "Off")));
    }

    @Override
    public boolean mineBlock(ItemStack stack, World worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!worldIn.isClientSide && (state.getDestroySpeed(worldIn, pos) != 0.0F) && entityLiving instanceof PlayerEntity) {
            if (stack.getMaxDamage() - stack.getDamageValue() < 51) {
                switchMining(stack);
                recalculatePowers(stack, worldIn, (PlayerEntity) entityLiving);
            } else {
                stack.hurtAndBreak(50, entityLiving, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
            }
        }
        return true;
    }

    @Override
    public void talk(PlayerEntity player) {
        player.sendMessage(new TranslationTextComponent(this.getDescriptionId() + ".dialogue." + player.level.getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true).withColor(Color.fromRgb(HexUtils.earthColor))), player.getUUID());

    }

}
