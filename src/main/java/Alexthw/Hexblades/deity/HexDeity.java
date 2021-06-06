package Alexthw.Hexblades.deity;

import Alexthw.Hexblades.registers.HexItem;
import elucent.eidolon.capability.IReputation;
import elucent.eidolon.deity.Deity;
import elucent.eidolon.spell.KnowledgeUtil;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class HexDeity extends Deity {

    ResourceLocation id = HexDeities.temp_id;

    public HexDeity(ResourceLocation id, int red, int green, int blue) {
        super(id, red, green, blue);
    }

    @Override
    public void onReputationUnlock(PlayerEntity player, IReputation rep, ResourceLocation lock) {

        if (lock.equals(DeityLocks.EVOLVED_WEAPON)) {
            KnowledgeUtil.grantFact(player, HexFacts.EVOLVE_RITUAL);
        }

    }

    @Override
    public void onReputationChange(PlayerEntity player, IReputation rep, double prev, double current) {
        if (!KnowledgeUtil.knowsFact(player, HexFacts.AWAKENING_RITUAL) && current >= 10.0D) {
            KnowledgeUtil.grantFact(player, HexFacts.AWAKENING_RITUAL);
            World world = player.getEntityWorld();
            world.addEntity(new ItemEntity(world, player.getPosX() + 0.5D, player.getPosY() + 2.5D, player.getPosZ() + 0.5D, new ItemStack(HexItem.ELEMENTAL_CORE.get())));
        } else if (!KnowledgeUtil.knowsFact(player, HexFacts.EVOLVE_RITUAL) && current >= 30.0D) {
            rep.setReputation(player, id, 30.0D);
            rep.lock(player, id, DeityLocks.EVOLVED_WEAPON);
            KnowledgeUtil.grantFact(player, HexFacts.STAR_INFUSION);
        } else if (current >= 60.0D) {
            rep.setReputation(player, id, 60.0D);
            rep.lock(player, id, DeityLocks.MAXDAMAGE);
        }
    }

}
