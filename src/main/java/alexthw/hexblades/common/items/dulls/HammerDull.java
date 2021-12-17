package alexthw.hexblades.common.items.dulls;

import alexthw.hexblades.registers.Tiers;
import net.minecraft.world.item.PickaxeItem;

public class HammerDull extends PickaxeItem {

    public HammerDull(int attackDamageIn, float attackSpeedIn, Properties builderIn) {
        super(Tiers.HexiumTier.INSTANCE,attackDamageIn, attackSpeedIn, builderIn);
    }
}
