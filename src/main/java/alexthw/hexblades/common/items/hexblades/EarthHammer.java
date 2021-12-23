package alexthw.hexblades.common.items.hexblades;

import alexthw.hexblades.common.items.IHexblade;
import alexthw.hexblades.registers.Tiers;
import alexthw.hexblades.util.Constants;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import elucent.eidolon.Registry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.level.block.state.BlockState;
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
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static alexthw.hexblades.ConfigHandler.COMMON;
import static net.minecraftforge.common.ToolActions.*;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.common.ToolAction;
import org.jetbrains.annotations.NotNull;

public class EarthHammer extends PickaxeItem implements IHexblade {

    protected final double baseAttackSpeed;
    protected TranslatableComponent loreText = new TranslatableComponent("tooltip.hexblades.earth_hammer");
    protected ChatFormatting textColor = ChatFormatting.GOLD;
    protected int rechargeTick = 5;
    protected int dialogueLines = 3;

    public EarthHammer(Properties props) {
        this(Tiers.PatronWeaponTier.INSTANCE, 7, -3.2F, props);
    }

    public EarthHammer(Tier tier, int attackDamageIn, float attackSpeedIn, Properties builder) {
        super(tier, attackDamageIn, attackSpeedIn, builder);
        baseAttackSpeed = attackSpeedIn;
    }

    public int getRechargeTicks() {
        return rechargeTick;
    }

    @Override
    public boolean onHitEffects() {
        return true;
    }

    public void setMiningSpeed(ItemStack weapon, int souls, int ratio) {
        float extra_mining = 2.0F + (float) souls/ratio;
        weapon.getOrCreateTag().putFloat(Constants.NBT.EXTRA_MINING_SPEED, extra_mining);
    }

    protected float getMiningSpeed(ItemStack weapon) {
        return weapon.getOrCreateTag().getFloat(Constants.NBT.EXTRA_MINING_SPEED);
    }

    public boolean MineSwitchCheck(ItemStack weapon) {
        return weapon.getOrCreateTag().getBoolean(Constants.NBT.MiningSwitch);
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, Player attacker, boolean awakened) {
        if (MineSwitchCheck(stack) && getAwakening(stack) > 0) {
            target.addEffect(new MobEffectInstance(Registry.VULNERABLE_EFFECT.get()));
        } else {
            if (awakened) {
                if (getAwakening(stack) > 0) target.hurt(new EntityDamageSource("anvil", attacker).bypassArmor(), getElementalDamage(stack));

                double power = 1.0F + getDevotion(attacker) / 30;
                double X = attacker.getX() - target.getX();
                double Z = attacker.getZ() - target.getZ();

                target.knockback(power, X, Z);
            }
        }
    }

    @Override
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {
        if (setAwakenedState(weapon, !getAwakened(weapon))){
            int souls = getSouls(weapon);
            switch (getAwakening(weapon)) {
             case 0 -> setAttackPower(weapon, souls, COMMON.HammerDS1.get());
             case 1 ->   {
                 setAttackPower(weapon, souls, COMMON.HammerDS1.get());
                 updateElementalDamage(weapon, getDevotion(player), COMMON.HammerED1.get());
             }
             case 2 ->   {
                 setAttackPower(weapon, souls, COMMON.HammerDS2.get() );
                 setAttackSpeed(weapon, souls, 1);
                 updateElementalDamage(weapon, getDevotion(player), COMMON.HammerED2.get());
             }
            }
        }else{
            weapon.getOrCreateTag().putBoolean(Constants.NBT.MiningSwitch, false);
        }
    }

    public void switchMining(ItemStack weapon) {
        CompoundTag tag = weapon.getOrCreateTag();
        boolean mSwitch = !tag.getBoolean(Constants.NBT.MiningSwitch);
        tag.putBoolean(Constants.NBT.MiningSwitch, mSwitch);

        if (mSwitch){
            tag.putBoolean(Constants.NBT.AW_State,true);
            int souls = tag.getInt(Constants.NBT.SOUL_LEVEL);
            if (tag.getInt(Constants.NBT.AW_Level) == 2) {
                setMiningSpeed(weapon, souls, COMMON.HammerMS2.get());
            } else {
                setMiningSpeed(weapon, souls, COMMON.HammerMS1.get());
            }
        }
    }

    @Override
    public void applyHexBonus(Player user, int souls) {
    }

    @Override
    public void talk(Player player) {
        player.sendMessage(new TranslatableComponent(this.getDescriptionId() + ".dialogue." + player.level.getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true)), player.getUUID());
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot, ItemStack stack) {
        CompoundTag tag = stack.getOrCreateTag();
        if (tag.getBoolean(Constants.NBT.MiningSwitch) || !tag.getBoolean(Constants.NBT.AW_State)){
            return getDefaultAttributeModifiers(slot);
        }
        else{
            Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
            if (slot == EquipmentSlot.MAINHAND) {
                multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", getAttackDamage() + getAttackPower(stack), AttributeModifier.Operation.ADDITION));
                multimap.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Weapon modifier", baseAttackSpeed, AttributeModifier.Operation.ADDITION));
            }
            return multimap;
        }
    }

    //multi-tool abilities
    @Override
    public boolean mineBlock(@NotNull ItemStack stack, Level worldIn, @NotNull BlockState state, @NotNull BlockPos pos, @NotNull LivingEntity entityLiving) {
        if (!worldIn.isClientSide && state.getDestroySpeed(worldIn, pos) > 0.0F && entityLiving instanceof Player player) {
            if (stack.getMaxDamage() - stack.getDamageValue() < 51) {
                switchMining(stack);
                recalculatePowers(stack, worldIn, player);
            } else {
                stack.hurtAndBreak(50, entityLiving, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
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
    public float getDestroySpeed(@NotNull ItemStack stack, @NotNull BlockState state) {
        float destroySpeed = super.getDestroySpeed(stack, state);
        if (MineSwitchCheck(stack) && (state.is(BlockTags.MINEABLE_WITH_PICKAXE) || state.is(BlockTags.MINEABLE_WITH_SHOVEL))){

            return destroySpeed + getMiningSpeed(stack);
        }
        return destroySpeed;
    }

    @Override
    public boolean isCorrectToolForDrops(@NotNull ItemStack stack, BlockState state) {
        if (state.is(BlockTags.MINEABLE_WITH_PICKAXE) || state.is(BlockTags.MINEABLE_WITH_SHOVEL))
            return TierSortingRegistry.isCorrectTierForDrops(Tiers.PatronWeaponTier.INSTANCE, state);
        return super.isCorrectToolForDrops(stack, state);
    }

    //item overrides

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, @NotNull Player player, @NotNull InteractionHand hand) {
        if (!world.isClientSide())
            if (player.isShiftKeyDown()) {
                switchMining(player.getItemInHand(hand));
            }else {
                recalculatePowers(player.getItemInHand(hand), world, player);
            }
        return super.use(world, player, hand);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level worldIn, @NotNull Entity user, int itemSlot, boolean isSelected) {
        if (MineSwitchCheck(stack)) return;
        this.inventoryTick(stack, worldIn, user);
    }

    @Override
    public boolean hurtEnemy(@NotNull ItemStack stack, @NotNull LivingEntity target, @NotNull LivingEntity attacker) {
        return this.hurtEnemy(stack, target, attacker, true);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        if (enchantment.category == EnchantmentCategory.WEAPON) return true;
        return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(@NotNull ItemStack stack, Level worldIn, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
        tooltip.add(loreText.withStyle(ChatFormatting.ITALIC).withStyle(textColor));
        tooltip.add(new TextComponent(""));
        if (Screen.hasShiftDown()) {
            addShiftText(stack,tooltip);
        }else if (Screen.hasControlDown()){
            addControlText(stack,tooltip);
        }else{
            tooltip.add(new TranslatableComponent("Awaken with [H], mining [J], [Shift] or [Ctrl] for more info"));
        }
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return this.shouldCauseReequipAnimation(oldStack, newStack);
    }

}
