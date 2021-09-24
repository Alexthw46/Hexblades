package Alexthw.Hexblades.common.items;

import Alexthw.Hexblades.deity.HexDeities;
import Alexthw.Hexblades.util.Constants;
import Alexthw.Hexblades.util.NBTHelper;
import elucent.eidolon.capability.ReputationProvider;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;

public interface IHexblade {

    void talk(PlayerEntity player);

    double getAttackSpeed(ItemStack weapon);

    double getAttackPower(ItemStack weapon);

    void setAttackPower(ItemStack weapon, double extradamage);

    void setAttackSpeed(ItemStack weapon, double extraspeed);

    default double getDevotion(PlayerEntity player) {
        if (player.getEntityWorld().getCapability(ReputationProvider.CAPABILITY).resolve().isPresent()) {
            return player.getEntityWorld().getCapability(ReputationProvider.CAPABILITY).resolve().get().getReputation(player, HexDeities.HEX_DEITY.getId());
        } else
            return 0;
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

    default void applyHexBonus(PlayerEntity user, boolean awakened) {
    }

    /**
     * @param stack    the instance of the item
     * @param target   the entity that is being damaged
     * @param attacker the player who is attacking
     */
    default void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker) {
        //target.attackEntityFrom(new EntityDamageSource("wither", attacker).setDamageBypassesArmor(), 2.0f);
    }

    default boolean onHitEffects() {
        return false;
    }

    default boolean hasBonus() {
        return false;
    }

}
