package Alexthw.Hexblades.common.items.tier2;

import Alexthw.Hexblades.common.items.HexSwordItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;

public class EarthHammer2 extends HexSwordItem {
    public EarthHammer2(Properties props) {
        super(9, 0.9F, props);
        tooltipText = "tooltip.HexSwordItem.earth_hammer2";
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker) {
        target.attackEntityFrom(new EntityDamageSource("anvil", attacker).setDamageBypassesArmor(), 2.0f);
        double X = attacker.getPosX() - target.getPosX();
        double Z = attacker.getPosZ() - target.getPosZ();

        target.applyKnockback((float) (3.0F + getDevotion(attacker) / 20), X, Z);
        target.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 300, 0));

    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        setAwakenedState(weapon, !getAwakened(weapon));
        setAttackPower(weapon, devotion / 20);
    }

}
