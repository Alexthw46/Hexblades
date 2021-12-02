package alexthw.hexblades.common.items;

import alexthw.hexblades.deity.HexDeities;
import alexthw.hexblades.util.Constants;
import elucent.eidolon.capability.ReputationProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.Level;

public interface IHexblade {

    void talk(Player player);

    //NBT GETTERS
    double getAttackSpeed(ItemStack weapon);

    double getAttackPower(ItemStack weapon);

    //NBT SETTERS
    void setAttackPower(ItemStack weapon, boolean awakening, double extradamage);

    void setAttackSpeed(ItemStack weapon, boolean awakening, double extraspeed);

    // data getters
    int getRechargeTicks();

    int getEnergyLeft(ItemStack stack);

    //overloaded methods
    default boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack) {
        return oldStack.getItem() != newStack.getItem();
    }

    default void inventoryTick(ItemStack stack, Level worldIn, Entity user) {
        if (user instanceof Player && !worldIn.isClientSide()) {
            Player player = (Player) user;
            boolean currentState = getAwakened(stack);
            if (hasBonus()) {
                applyHexBonus(player, currentState);
            }
            if (currentState && !player.isCreative()) {
                if (getEnergyLeft(stack) > getRechargeTicks() + 1) {
                    stack.hurtAndBreak(2, player, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                } else {
                    recalculatePowers(player.getItemInHand(InteractionHand.MAIN_HAND), worldIn, player);
                }
            } else if (stack.getDamageValue() > 0) {
                stack.setDamageValue(Math.max(stack.getDamageValue() - getRechargeTicks(), 0));
            }
        }
    }

    default double getDevotion(Player player) {
        if (player.getCommandSenderWorld().getCapability(ReputationProvider.CAPABILITY).resolve().isPresent()) {
            return player.getCommandSenderWorld().getCapability(ReputationProvider.CAPABILITY).resolve().get().getReputation(player, HexDeities.HEX_DEITY.getId());
        } else return 0;
    }

    default void recalculatePowers(ItemStack weapon, Level world, Player player) {
        double devotion = getDevotion(player);

        boolean awakening = setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon, awakening, devotion);
        setAttackSpeed(weapon, awakening, devotion);

    }

    default boolean getAwakened(ItemStack stack) {
        return !stack.isEmpty() && stack.getOrCreateTag().getBoolean(Constants.NBT.AW_State);
    }

    default boolean setAwakenedState(ItemStack stack, boolean aws) {
        if (!stack.isEmpty()) {
            if (!aws || stack.getDamageValue() == 0) {
                CompoundTag tag = stack.getOrCreateTag();
                tag.putBoolean(Constants.NBT.AW_State, aws);
                return aws;
            }
        }
        return false;
    }

    default boolean hasBonus() {
        return false;
    }

    void applyHexBonus(Player user, boolean awakened);

    default boolean onHitEffects() {
        return false;
    }

    /**
     * @param stack    the instance of the item
     * @param target   the entity that is being damaged
     * @param attacker the player who is attacking
     * @param awakened state of the hexblade
     */
    void applyHexEffects(ItemStack stack, LivingEntity target, Player attacker, boolean awakened);


    default boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker, boolean hex) {
        if (attacker instanceof Player) {
            if (target.invulnerableTime > 0) {
                target.invulnerableTime = 0;
            }
            if (onHitEffects()) applyHexEffects(stack, target, (Player) attacker, getAwakened(stack));
            stack.setDamageValue(Math.max(stack.getDamageValue() - 10, 0));
        }
        return true;
    }

}
