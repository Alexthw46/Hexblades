package alexthw.hexblades.common.items;

import alexthw.hexblades.deity.HexDeities;
import alexthw.hexblades.util.Constants;
import alexthw.hexblades.util.NBTHelper;
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

    //NBT SETTERS
    void setAttackPower(ItemStack weapon, double extradamage);

    void setAttackSpeed(ItemStack weapon, double extraspeed);

    // data getters
    int getRechargeTicks();

    int getEnergyLeft(ItemStack stack);

    boolean isActivated();

    //overloaded methods
    default boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack) {
        return oldStack.getItem() != newStack.getItem();
    }

    default void inventoryTick(ItemStack stack, World worldIn, Entity user) {
        if (user instanceof PlayerEntity && !worldIn.isClientSide()) {
            PlayerEntity player = (PlayerEntity) user;
            if (hasBonus()) {
                applyHexBonus(player, isActivated());
            }
            if (isActivated() && !(player).isCreative()) {
                if (getEnergyLeft(stack) > 5) {
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

        setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon, devotion);
        setAttackSpeed(weapon, devotion);

    }

    default boolean getAwakened(ItemStack stack) {
        CompoundNBT tag = NBTHelper.checkNBT(stack).getTag();
        return tag != null && !stack.isEmpty() && tag.getBoolean(Constants.NBT.AW_State);
    }

    void setAwakenedState(ItemStack stack, boolean aws);

    default boolean hasBonus() {
        return false;
    }

    default void applyHexBonus(PlayerEntity user, boolean awakened) {
    }

    default boolean onHitEffects() {
        return false;
    }

    /**
     * @param stack    the instance of the item
     * @param target   the entity that is being damaged
     * @param attacker the player who is attacking
     */
    void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker);


    default boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker, boolean hex) {
        if (attacker instanceof PlayerEntity) {
            if (target.invulnerableTime > 0) {
                target.invulnerableTime = 0;
            }
            if (isActivated() || onHitEffects()) applyHexEffects(stack, target, (PlayerEntity) attacker);
            stack.setDamageValue(Math.max(stack.getDamageValue() - 10, 0));
        }
        return true;
    }

    //Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack);

}
