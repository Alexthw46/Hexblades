package Alexthw.Hexblades.common.items.dulls;

import Alexthw.Hexblades.registers.Tiers;
import net.minecraft.item.SwordItem;

public class DullSwordItem extends SwordItem {
    public DullSwordItem(int attackDamage, float attackSpeed, Properties properties) {
        super(Tiers.HexiumTier.INSTANCE, attackDamage, attackSpeed, properties);
    }

}
