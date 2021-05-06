package Alexthw.Hexblades.common.items.tier2;

import Alexthw.Hexblades.common.items.HexSwordItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;

public class FireBroad2 extends HexSwordItem {


    public FireBroad2(Properties props) {
        super(3, -2.9F, props);
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon, devotion / 5);
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker) {
        target.attackEntityFrom(new EntityDamageSource("magic", attacker).setDamageBypassesArmor(), 4.0f);
    }

}
