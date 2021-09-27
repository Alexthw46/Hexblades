package alexthw.hexblades.common.items;

import alexthw.hexblades.deity.DeityLocks;
import alexthw.hexblades.deity.HexDeities;
import alexthw.hexblades.deity.HexFacts;
import elucent.eidolon.capability.ReputationProvider;
import elucent.eidolon.deity.Deity;
import elucent.eidolon.spell.KnowledgeUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ElementalSoul extends Item {

    public ElementalSoul(Properties properties) {
        super(properties);
        unlock = true;
    }

    boolean unlock;

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
        if (unlock) {
            if (!world.isClientSide && entityIn instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entityIn;
                if (!KnowledgeUtil.knowsFact(player, HexFacts.EVOLVE_RITUAL)) {
                    world.getCapability(ReputationProvider.CAPABILITY, null).ifPresent((rep) -> {
                        Deity deity = HexDeities.HEX_DEITY;
                        if (rep.unlock(player, deity.getId(), DeityLocks.EVOLVED_WEAPON)) {
                            deity.onReputationUnlock(player, rep, DeityLocks.EVOLVED_WEAPON);
                            unlock = false;
                        }
                    });
                }
            }
        }
    }

}
