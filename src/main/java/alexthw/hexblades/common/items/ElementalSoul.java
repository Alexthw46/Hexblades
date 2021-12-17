package alexthw.hexblades.common.items;

import alexthw.hexblades.deity.DeityLocks;
import alexthw.hexblades.deity.HexDeities;
import alexthw.hexblades.deity.HexFacts;
import elucent.eidolon.capability.IReputation;
import elucent.eidolon.deity.Deity;
import elucent.eidolon.spell.KnowledgeUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import org.jetbrains.annotations.NotNull;

public class ElementalSoul extends Item {

    public ElementalSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, Level world, @NotNull Entity entityIn, int itemSlot, boolean isSelected) {

        if (!world.isClientSide() && entityIn instanceof Player player) {
            if (!KnowledgeUtil.knowsFact(player, HexFacts.EVOLVE_RITUAL)) {
                world.getCapability(IReputation.INSTANCE, null).ifPresent(rep -> {
                    Deity deity = HexDeities.HEX_DEITY;
                    if (rep.unlock(player, deity.getId(), DeityLocks.EVOLVED_WEAPON)) {
                        deity.onReputationUnlock(player, rep, DeityLocks.EVOLVED_WEAPON);
                    }
                });
            }
        }

    }

}
