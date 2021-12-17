package alexthw.hexblades.common.items.deprecated;

import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.registers.Tiers;
import alexthw.hexblades.util.Constants;
import alexthw.hexblades.util.HexUtils;
import com.google.common.collect.Sets;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.common.ToolAction;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static alexthw.hexblades.ConfigHandler.COMMON;
import static alexthw.hexblades.util.Constants.NBT.MiningSwitch;
import static net.minecraftforge.common.ToolActions.PICKAXE_DIG;
import static net.minecraftforge.common.ToolActions.SHOVEL_DIG;

public class EarthHammer1 extends HexSwordItem {

    protected float baseMiningSpeed;

    public EarthHammer1(Properties props) {
        super(7, -3.2F,props);
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
    public @NotNull InteractionResultHolder<ItemStack> use(Level world, @NotNull Player player, @NotNull InteractionHand hand) {
        if (player.isShiftKeyDown() && !world.isClientSide()) {
            switchMining(player.getItemInHand(hand));
        }
        return super.use(world, player, hand);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level worldIn, @NotNull Entity user, int itemSlot, boolean isSelected) {
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

        setAttackPower(weapon, mineSwitch ? -6 : devotion , COMMON.HammerDS1.get() );
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
    public boolean isCorrectToolForDrops(ItemStack stack, BlockState state) {
        if (state.is(BlockTags.MINEABLE_WITH_PICKAXE))
            return TierSortingRegistry.isCorrectTierForDrops(Tiers.PatronWeaponTier.INSTANCE, state);

        return super.isCorrectToolForDrops(stack, state);
    }

    @Override
    public boolean canAttackBlock(@NotNull BlockState state, @NotNull Level worldIn, @NotNull BlockPos pos, @NotNull Player player) {
        return true;
    }

    private static final Set<ToolAction> TOOL_ACTIONS =  Stream.of(PICKAXE_DIG, SHOVEL_DIG).collect(Collectors.toCollection(Sets::newIdentityHashSet));
    @Override
    public boolean canPerformAction(@NotNull ItemStack stack, @NotNull ToolAction toolAction)
    {
        return TOOL_ACTIONS.contains(toolAction);
    }

    @Override
    public float getDestroySpeed(@NotNull ItemStack stack, BlockState state) {
        Material material = state.getMaterial();

        float newMiningSpeed = getMiningSpeed(stack);
        if ((material == Material.STONE || material == Material.DIRT) && getAwakened(stack)) {
            return newMiningSpeed + 2.0F;
        }
        if (material == Material.METAL || material == Material.HEAVY_METAL || state.is(BlockTags.MINEABLE_WITH_PICKAXE)) {
            return newMiningSpeed;
        }
        return super.getDestroySpeed(stack, state);
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
    public void appendHoverText(@NotNull ItemStack stack, Level worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslatableComponent("Mining mode:" + (stack.getOrCreateTag().getBoolean(MiningSwitch) ? "On" : "Off")));
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack stack, Level worldIn, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity entityLiving) {
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
