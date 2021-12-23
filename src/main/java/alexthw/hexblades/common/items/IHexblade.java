package alexthw.hexblades.common.items;

import alexthw.hexblades.deity.HexDeities;
import alexthw.hexblades.util.Constants;
import elucent.eidolon.capability.ReputationProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public interface IHexblade {

    void talk(PlayerEntity player);

    //NBT GETTERS
    double getAttackSpeed(ItemStack weapon);

    double getAttackPower(ItemStack weapon);

    default float getElementalPower(ItemStack weapon) {
        float EP = weapon.getOrCreateTag().getFloat(Constants.NBT.ELEMENTAL_DAMAGE);

        return EP > 0 ? EP : 0;
    }

    //NBT SETTERS
    void setAttackPower(ItemStack weapon, boolean awakening, double extradamage);

    void setAttackSpeed(ItemStack weapon, boolean awakening, double extraspeed);

    default void updateElementalPower(ItemStack weapon, float ratio, double devotion) {
        float EP = (float) (devotion / ratio);
        weapon.getOrCreateTag().putFloat(Constants.NBT.ELEMENTAL_DAMAGE, EP);
    }

    // data getters
    int getRechargeTicks();

    int getEnergyLeft(ItemStack stack);

    //overloaded methods
    default boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack) {
        return oldStack.getItem() != newStack.getItem();
    }

    default void inventoryTick(ItemStack stack, World worldIn, Entity user) {
        if (user instanceof PlayerEntity && !worldIn.isClientSide()) {
            PlayerEntity player = (PlayerEntity) user;
            boolean currentState = getAwakened(stack);
            if (hasBonus()) {
                applyHexBonus(player, currentState);
            }
            if (currentState && !player.isCreative()) {
                if (getEnergyLeft(stack) > getRechargeTicks() + 1) {
                    stack.hurtAndBreak(2, player, (entity) -> entity.broadcastBreakEvent(EquipmentSlotType.MAINHAND));
                } else {
                    recalculatePowers(player.getItemInHand(Hand.MAIN_HAND), worldIn, player);
                }
            } else if (stack.getDamageValue() > 0) {
                stack.setDamageValue(Math.max(stack.getDamageValue() - getRechargeTicks(), 0));
            }
        }
    }

    default double getDevotion(PlayerEntity player) {
        if (player.getCommandSenderWorld().getCapability(ReputationProvider.CAPABILITY).resolve().isPresent()) {
            return player.getCommandSenderWorld().getCapability(ReputationProvider.CAPABILITY).resolve().get().getReputation(player, HexDeities.HEX_DEITY.getId());
        } else return 0;
    }

    default void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
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
                CompoundNBT tag = stack.getOrCreateTag();
                tag.putBoolean(Constants.NBT.AW_State, aws);
                return aws;
            }
        }
        return false;
    }

    default boolean hasBonus() {
        return false;
    }

    void applyHexBonus(PlayerEntity user, boolean awakened);

    default boolean onHitEffects() {
        return false;
    }

    /**
     * @param stack    the instance of the item
     * @param target   the entity that is being damaged
     * @param attacker the player who is attacking
     * @param awakened state of the hexblade
     */
    void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker, boolean awakened);


    default boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker, boolean hex) {
        if (attacker instanceof PlayerEntity) {
            if (target.invulnerableTime > 0) {
                target.invulnerableTime = 0;
            }
            if (onHitEffects()) applyHexEffects(stack, target, (PlayerEntity) attacker, getAwakened(stack));
            stack.setDamageValue(Math.max(stack.getDamageValue() - 10, 0));
        }
        return true;
    }

}
