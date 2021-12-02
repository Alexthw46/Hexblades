package alexthw.hexblades.common.items.tier1;

import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.registers.Tiers;
import alexthw.hexblades.util.Constants;
import alexthw.hexblades.util.HexUtils;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import java.util.List;

import static alexthw.hexblades.ConfigHandler.COMMON;
import static alexthw.hexblades.util.Constants.NBT.MiningSwitch;

import net.minecraft.world.item.Item.Properties;

public class EarthHammer1 extends HexSwordItem {

    protected float baseMiningSpeed;

    public EarthHammer1(Properties props) {
        this(7, -3.2F,
                props.addToolType(net.minecraftforge.common.ToolType.PICKAXE, Tiers.PatronWeaponTier.INSTANCE.getLevel()));
        baseMiningSpeed = 8.0F;
        tooltipText = new TranslatableComponent("tooltip.hexblades.earth_hammer");
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
        if (enchantment.category == EnchantmentCategory.DIGGER) return true;
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, Player attacker, boolean awakened) {
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
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (player.isShiftKeyDown() && !world.isClientSide()) {
            switchMining(player.getItemInHand(hand));
        }
        return super.use(world, player, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level worldIn, Entity user, int itemSlot, boolean isSelected) {
        if (stack.getOrCreateTag().getBoolean(MiningSwitch)) {
            return;
        }
        super.inventoryTick(stack, worldIn, user, itemSlot, isSelected);
    }

    @Override
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {
        double devotion = getDevotion(player);

        boolean mineSwitch = weapon.getOrCreateTag().getBoolean(MiningSwitch);

        if (!mineSwitch) setAwakenedState(weapon, !getAwakened(weapon));

        boolean awakening = getAwakened(weapon);

        setAttackPower(weapon, awakening || mineSwitch, mineSwitch ? -6 : (devotion / COMMON.HammerDS1.get()));
        setMiningSpeed(weapon, awakening, (float) (devotion / COMMON.HammerMS1.get()));

    }

    public void setMiningSpeed(ItemStack weapon, boolean awakening, float extra_mining) {

        CompoundTag tag = weapon.getOrCreateTag();

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
    public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player) {
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
        CompoundTag tag = weapon.getOrCreateTag();
        tag.putBoolean(MiningSwitch, !tag.getBoolean(MiningSwitch));
        weapon.setTag(tag);
    }

    protected float getMiningSpeed(ItemStack stack) {
        return stack.getOrCreateTag().getFloat(Constants.NBT.EXTRA_MINING_SPEED);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableComponent("Mining mode:" + (stack.getOrCreateTag().getBoolean(MiningSwitch) ? "On" : "Off")));
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level worldIn, BlockState state, BlockPos pos, LivingEntity entityLiving) {
        if (!worldIn.isClientSide && (state.getDestroySpeed(worldIn, pos) != 0.0F) && entityLiving instanceof Player) {
            if (stack.getMaxDamage() - stack.getDamageValue() < 51) {
                switchMining(stack);
                recalculatePowers(stack, worldIn, (Player) entityLiving);
            } else {
                stack.hurtAndBreak(50, entityLiving, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }
        return true;
    }

    @Override
    public void talk(Player player) {
        player.sendMessage(new TranslatableComponent(this.getDescriptionId() + ".dialogue." + player.level.getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true).withColor(TextColor.fromRgb(HexUtils.earthColor))), player.getUUID());

    }

}
