package Alexthw.Hexblades.deity;

import elucent.eidolon.capability.IReputation;
import elucent.eidolon.deity.Deity;
import elucent.eidolon.spell.KnowledgeUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public class HexDeity extends Deity {

    ResourceLocation id;

    public HexDeity(ResourceLocation id, int red, int green, int blue) {
        super(id, red, green, blue);
    }

    @Override
    public void onReputationUnlock(PlayerEntity player, IReputation rep, ResourceLocation lock) {

        if (lock.equals(DeityLocks.AWAKENED_WEAPON)) {
            KnowledgeUtil.grantFact(player, HexFacts.AWAKENING_RITUAL);
        } else if (lock.equals(DeityLocks.EVOLVED_WEAPON)) {
            KnowledgeUtil.grantFact(player, HexFacts.STAR_INFUSION);
        }

    }

    @Override
    public void onReputationChange(PlayerEntity player, IReputation rep, double prev, double current) {
        if (!KnowledgeUtil.knowsFact(player, HexFacts.AWAKENING_RITUAL) && current >= 10.0D) {
            rep.setReputation(player, id, 10.0D);
            rep.lock(player, id, DeityLocks.AWAKENED_WEAPON);
        } else if (!KnowledgeUtil.knowsFact(player, HexFacts.EVOLVE_RITUAL) && current >= 30.0D) {
            rep.setReputation(player, id, 50.0D);
            rep.lock(player, id, DeityLocks.EVOLVED_WEAPON);
            KnowledgeUtil.grantFact(player, HexFacts.STAR_INFUSION);
        }
    }

}
