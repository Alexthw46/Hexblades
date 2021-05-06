package Alexthw.Hexblades.deity;

import elucent.eidolon.capability.Facts;
import elucent.eidolon.capability.IReputation;
import elucent.eidolon.deity.Deity;
import elucent.eidolon.deity.DeityLocks;
import elucent.eidolon.spell.KnowledgeUtil;
import elucent.eidolon.spell.Signs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public class HexDeity extends Deity {

    ResourceLocation id;

    public HexDeity(ResourceLocation id, int red, int green, int blue) {
        super(id, red, green, blue);
    }

    @Override
    public void onReputationUnlock(PlayerEntity player, IReputation rep, ResourceLocation lock) {

        if (lock.equals(DeityLocks.SACRIFICE_MOB)) {
            KnowledgeUtil.grantSign(player, Signs.SOUL_SIGN);
        } else if (lock.equals(DeityLocks.SACRIFICE_VILLAGER)) {
            KnowledgeUtil.grantSign(player, Signs.MIND_SIGN);
        }

    }

    @Override
    public void onReputationChange(PlayerEntity player, IReputation rep, double prev, double current) {
        if (!KnowledgeUtil.knowsSign(player, Signs.BLOOD_SIGN) && current >= 3.0D) {
            rep.setReputation(player, this.id, 3.0D);
            rep.lock(player, this.id, DeityLocks.SACRIFICE_MOB);
            KnowledgeUtil.grantSign(player, Signs.BLOOD_SIGN);
        } else if (!KnowledgeUtil.knowsFact(player, Facts.VILLAGER_SACRIFICE) && current >= 15.0D) {
            rep.setReputation(player, this.id, 15.0D);
            rep.lock(player, this.id, DeityLocks.SACRIFICE_VILLAGER);
            KnowledgeUtil.grantFact(player, Facts.VILLAGER_SACRIFICE);
        }
    }

}
