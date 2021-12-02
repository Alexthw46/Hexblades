package alexthw.hexblades.common.items;

import alexthw.hexblades.deity.DeityLocks;
import alexthw.hexblades.deity.HexDeities;
import alexthw.hexblades.deity.HexFacts;
import elucent.eidolon.capability.ReputationProvider;
import elucent.eidolon.deity.Deity;
import elucent.eidolon.spell.KnowledgeUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.Item.Properties;

public class ElementalSoul extends Item {

    public ElementalSoul(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack stack, Level world, Entity entityIn, int itemSlot, boolean isSelected) {

        if (!world.isClientSide() && entityIn instanceof Player) {
            Player player = (Player) entityIn;
            if (!KnowledgeUtil.knowsFact(player, HexFacts.EVOLVE_RITUAL)) {
                world.getCapability(ReputationProvider.CAPABILITY, null).ifPresent(rep -> {
                    Deity deity = HexDeities.HEX_DEITY;
                    if (rep.unlock(player, deity.getId(), DeityLocks.EVOLVED_WEAPON)) {
                        deity.onReputationUnlock(player, rep, DeityLocks.EVOLVED_WEAPON);
                    }
                });
            }
        }

    }

}
