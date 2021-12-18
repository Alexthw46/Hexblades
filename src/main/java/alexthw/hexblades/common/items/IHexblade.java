package alexthw.hexblades.common.items;

import alexthw.hexblades.deity.HexDeities;
import alexthw.hexblades.util.Constants;
import elucent.eidolon.capability.IReputation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Optional;

public interface IHexblade {

    void talk(Player player);

    //NBT GETTERS
    default double getAttackPower(ItemStack weapon) { return weapon.getOrCreateTag().getDouble(Constants.NBT.EXTRA_DAMAGE); }

    default double getAttackSpeed(ItemStack weapon) { return weapon.getOrCreateTag().getDouble(Constants.NBT.EXTRA_ATTACK_SPEED); }

    default float getElementalDamage(ItemStack weapon){ return weapon.getOrCreateTag().getFloat(Constants.NBT.ELEMENTAL_DAMAGE);}

    default boolean getAwakened(ItemStack stack) { return !stack.isEmpty() && stack.getOrCreateTag().getBoolean(Constants.NBT.AW_State);}

    default int getAwakening(ItemStack weapon){ return weapon.getOrCreateTag().getInt(Constants.NBT.AW_Level);}

    default int getSouls(ItemStack weapon){ return weapon.getOrCreateTag().getInt(Constants.NBT.SOUL_LEVEL);}

    //NBT SETTERS
    default void setAttackPower(ItemStack weapon, double devotion, int scaling) {
        double extradamage = devotion/scaling;
        weapon.getOrCreateTag().putDouble(Constants.NBT.EXTRA_DAMAGE, extradamage);
    }

    default void setAttackSpeed(ItemStack weapon, double devotion, int scaling) {
        double extraspeed = devotion/scaling;
        weapon.getOrCreateTag().putDouble(Constants.NBT.EXTRA_ATTACK_SPEED, extraspeed);
    }

    default void updateElementalDamage(ItemStack weapon, double devotion, int scaling){
        double ed = devotion / scaling;
        weapon.getOrCreateTag().putFloat(Constants.NBT.SOUL_LEVEL, (float) ed);
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

    default void absorbSoul(ItemStack weapon, Player owner){
        int souls = getSouls(weapon);
        weapon.getOrCreateTag().putInt(Constants.NBT.SOUL_LEVEL, souls+1);
        talk(owner);
        if (souls > 1){
            int level = souls % 3;
            weapon.getOrCreateTag().putInt(Constants.NBT.AW_Level, level);
        }
    }

    // data getters
    int getRechargeTicks();

    /**
     * @param stack weapon
     * @return how much durability is left
     */
    default int getEnergyLeft(ItemStack stack){
        return stack.getMaxDamage() - stack.getDamageValue();
    }

    //overloaded methods
    default boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack) {
        return oldStack.getItem() != newStack.getItem();
    }

    /**
     * @param stack weapon
     * @param worldIn level, must be serverside
     * @param user only used if the user is a Player
     */
    default void inventoryTick(ItemStack stack, Level worldIn, Entity user) {
        if (user instanceof Player player && !worldIn.isClientSide()) {
            boolean currentState = getAwakened(stack);
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

    /**
     * @param player player that needs devotion check
     * @return devotion to Blade Deity
     */
    default double getDevotion(Player player) {
        Optional<IReputation> reputation = player.getCommandSenderWorld().getCapability(IReputation.INSTANCE).resolve();
        return reputation.map(iReputation -> iReputation.getReputation(player, HexDeities.HEX_DEITY.getId())).orElse(0.0);
    }

    /**
     * This method is called when the keybind is pressed, it will switch (or attempt to) between awakened and dormant
     * state and update values accordingly. This is an example, override this.
     * @param weapon hexblade in hand
     * @param world world of player
     * @param player player
     */
    default void recalculatePowers(ItemStack weapon, Level world, Player player) {
        if (setAwakenedState(weapon, !getAwakened(weapon))) {
            double devotion = getDevotion(player);
            int level = getAwakening(weapon);

            applyHexBonus(player, level);
            setAttackPower(weapon, devotion, 1);
            setAttackSpeed(weapon, devotion, 1);
        }
    }

    /**
     * Used to give the player buffs when they awaken the hexblade
     * @param user player
     * @param level max awakening level of the hexblade
     */
    void applyHexBonus(Player user, int level);

    /**
     * @return whether if the hexblade calls applyHexEffects or not
     */
    default boolean onHitEffects() {
        return false;
    }

    /**
     * Should only be called if onHitEffects returns true
     * @param stack    the instance of the item
     * @param target   the entity that is being damaged
     * @param attacker the player who is attacking
     * @param awakened state of the hexblade
     */
    void applyHexEffects(ItemStack stack, LivingEntity target, Player attacker, boolean awakened);


    /**
     * If attacker is a player, the weapon regains durability. If onHitEffects is true, calls applyHexEffects
     * @param stack weapon
     * @param target entity that is being damaged
     * @param attacker the player who is attacking
     * @param hex needed to overload the method
     * @return true
     */
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
