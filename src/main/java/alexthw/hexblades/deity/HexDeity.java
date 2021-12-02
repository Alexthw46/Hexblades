package alexthw.hexblades.deity;

import alexthw.hexblades.registers.HexItem;
import elucent.eidolon.capability.IReputation;
import elucent.eidolon.deity.Deity;
import elucent.eidolon.spell.KnowledgeUtil;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class HexDeity extends Deity {

    final ResourceLocation id = HexDeities.temp_id;

    public HexDeity(ResourceLocation id, int red, int green, int blue) {
        super(id, red, green, blue);
    }

    @Override
    public void onReputationUnlock(Player player, IReputation rep, ResourceLocation lock) {

        if (lock.equals(DeityLocks.EVOLVED_WEAPON)) {
            KnowledgeUtil.grantFact(player, HexFacts.EVOLVE_RITUAL);
        }

    }

    @Override
    public void onReputationChange(Player player, IReputation rep, double prev, double current) {
        if (!KnowledgeUtil.knowsFact(player, HexFacts.AWAKENING_RITUAL) && current >= 6.0D) {
            KnowledgeUtil.grantFact(player, HexFacts.AWAKENING_RITUAL);
            Level world = player.getCommandSenderWorld();
            world.addFreshEntity(new ItemEntity(world, player.getX() + 0.5D, player.getY() + 2.5D, player.getZ() + 0.5D, new ItemStack(HexItem.ELEMENTAL_CORE.get())));
        } else if (!KnowledgeUtil.knowsFact(player, HexFacts.EVOLVE_RITUAL) && current >= 30.0D) {
            rep.setReputation(player, id, 30.0D);
            rep.lock(player, id, DeityLocks.EVOLVED_WEAPON);
            KnowledgeUtil.grantFact(player, HexFacts.ELEMENTAL_SUMMON);
        } else if (current >= 60.0D) {
            rep.setReputation(player, id, 60.0D);
            rep.lock(player, id, DeityLocks.MAXDAMAGE);
        }
    }

}
