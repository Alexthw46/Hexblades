package Alexthw.Hexblades.common.items.dulls;

import Alexthw.Hexblades.deity.DeityLocks;
import Alexthw.Hexblades.deity.HexDeities;
import Alexthw.Hexblades.registers.Tiers;
import elucent.eidolon.capability.ReputationProvider;
import elucent.eidolon.deity.Deity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.world.World;

public class DullSwordItem extends SwordItem {
    public DullSwordItem(int attackDamage, float attackSpeed, Properties properties) {
        super(Tiers.HexiumTier.INSTANCE, attackDamage, attackSpeed, properties);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, PlayerEntity player) {
        worldIn.getCapability(ReputationProvider.CAPABILITY, null).ifPresent((rep) -> {
            Deity deity = HexDeities.HEX_DEITY;
            if (rep.unlock(player, deity.getId(), DeityLocks.AWAKENED_WEAPON)) {
                deity.onReputationUnlock(player, rep, DeityLocks.AWAKENED_WEAPON);
            }
        });
        super.onCreated(stack, worldIn, player);
    }
}
