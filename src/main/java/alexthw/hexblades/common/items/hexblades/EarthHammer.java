package alexthw.hexblades.common.items.hexblades;

import alexthw.hexblades.common.items.IHexblade;
import alexthw.hexblades.registers.Tiers;
import alexthw.hexblades.util.Constants;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static alexthw.hexblades.ConfigHandler.COMMON;
import static net.minecraftforge.common.ToolActions.*;

import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.common.ToolAction;
import org.jetbrains.annotations.NotNull;

public class EarthHammer extends PickaxeItem implements IHexblade {

    protected final double baseAttack;
    protected final double baseAttackSpeed;
    protected final float baseMiningSpeed;
    protected TranslatableComponent tooltipText = new TranslatableComponent("tooltip.hexblades.earth_hammer");
    protected int rechargeTick = 5;
    protected int dialogueLines = 3;

    public EarthHammer(Properties props) {
        this(Tiers.PatronWeaponTier.INSTANCE, 7, -3.2F, props);
    }

    public EarthHammer(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        baseAttack = attackDamageIn;
        baseAttackSpeed = attackSpeedIn;
        baseMiningSpeed = tier.getSpeed();
    }

    public void switchMining(ItemStack weapon) {
        CompoundTag tag = weapon.getOrCreateTag();
        tag.putBoolean(Constants.NBT.MiningSwitch, !tag.getBoolean(Constants.NBT.MiningSwitch));
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

    }

    @Override
    public boolean onHitEffects() {
        return true;
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, Player attacker, boolean awakened) {
        float power = 1.0F;
        if (awakened) {
            if (getAwakening(stack) > 0) target.hurt(new EntityDamageSource("anvil", attacker).bypassArmor(), COMMON.HammerED1.get().floatValue());
            power = (float) (1.0F + getDevotion(attacker) / 30);
        }
        double X = attacker.getX() - target.getX();
        double Z = attacker.getZ() - target.getZ();

        target.knockback(power, X, Z);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, Player player, @NotNull InteractionHand hand) {
        if (player.isShiftKeyDown() && !world.isClientSide()) {
            switchMining(player.getItemInHand(hand));
        }
        return super.use(world, player, hand);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level worldIn, @NotNull Entity user, int itemSlot, boolean isSelected) {
        if (MineSwitchCheck(stack)) return;
        this.inventoryTick(stack, worldIn, user);
    }

    @Override
    public int getRechargeTicks() {
        return rechargeTick;
    }

    @Override
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {
        int souls = getSouls(weapon);
        //TODO balance this : elemental dmg only on tier 1+
        boolean mineSwitch = MineSwitchCheck(weapon);

        if (!mineSwitch) setAwakenedState(weapon, !getAwakened(weapon));

        boolean awakening = getAwakened(weapon);

        setAttackPower(weapon, mineSwitch ? -6 : souls, COMMON.HammerDS1.get() );
        setMiningSpeed(weapon, awakening, (float) (souls / COMMON.HammerMS1.get()));
    }

    private boolean MineSwitchCheck(ItemStack weapon) {
        return weapon.getOrCreateTag().getBoolean(Constants.NBT.MiningSwitch);
    }

    @Override
    public void applyHexBonus(Player user, int souls) {
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        return this.hurtEnemy(stack, target, attacker, true);
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack stack, Level worldIn, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity entityLiving) {
        if (!worldIn.isClientSide && state.getDestroySpeed(worldIn, pos) > 0.0F && entityLiving instanceof Player player) {
            if (stack.getMaxDamage() - stack.getDamageValue() < 26) {
                switchMining(stack);
                recalculatePowers(stack, worldIn, player);
            } else {
                stack.hurtAndBreak(25, entityLiving, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
            }
        }
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

    @Override
    public boolean isCorrectToolForDrops(@NotNull ItemStack stack, BlockState state) {
        if (state.is(BlockTags.MINEABLE_WITH_PICKAXE) || state.is(BlockTags.MINEABLE_WITH_SHOVEL))
            return TierSortingRegistry.isCorrectTierForDrops(Tiers.PatronWeaponTier.INSTANCE, state);
        return super.isCorrectToolForDrops(stack, state);
    }

    protected float getMiningSpeed(ItemStack weapon) {
        return weapon.getOrCreateTag().getFloat(Constants.NBT.EXTRA_MINING_SPEED);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment.category == EnchantmentCategory.WEAPON) return true;
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
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

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (!tag.getBoolean(Constants.NBT.MiningSwitch) && !tag.getBoolean(Constants.NBT.AW_State)){
            return getDefaultAttributeModifiers(slot);
        }
        else{
            Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
            if (slot == EquipmentSlot.MAINHAND) {
                multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", baseAttack + getAttackPower(stack), AttributeModifier.Operation.ADDITION));
                multimap.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", baseAttackSpeed, AttributeModifier.Operation.ADDITION));
            }
            return multimap;
        }
    }
}
