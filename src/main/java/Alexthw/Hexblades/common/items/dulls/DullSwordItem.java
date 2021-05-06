package Alexthw.Hexblades.common.items.dulls;

import Alexthw.Hexblades.deity.HexFacts;
import Alexthw.Hexblades.registers.Tiers;
import elucent.eidolon.spell.KnowledgeUtil;
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
        if (!KnowledgeUtil.knowsFact(player, HexFacts.AWAKENING_RITUAL) && !worldIn.isRemote) {
            KnowledgeUtil.grantFact(player, HexFacts.AWAKENING_RITUAL);
        }
        super.onCreated(stack, worldIn, player);
    }
}
