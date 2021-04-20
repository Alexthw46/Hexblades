package Alexthw.Hexblades.common.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;

public class FireBroad1 extends HexSwordItem {


    public FireBroad1(Properties props) {
        super(3, -2.7F, props);
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.attackEntityFrom(new EntityDamageSource("lava", attacker).setDamageBypassesArmor(), 2.0f);
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon,devotion/10);
    }
}
