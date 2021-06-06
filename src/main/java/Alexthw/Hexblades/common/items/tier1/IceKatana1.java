package Alexthw.Hexblades.common.items.tier1;

import Alexthw.Hexblades.common.items.HexSwordItem;
import elucent.eidolon.Registry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;

public class IceKatana1 extends HexSwordItem {

    public IceKatana1(Properties props) {
        super(5, -2.4F, props);
        tooltipText = "tooltip.HexSwordItem.ice_katana";
    }

    public IceKatana1(int attackDamage, float attackSpeed, Properties props) {
        super(attackDamage, attackSpeed, props);
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker) {
        target.attackEntityFrom(new EntityDamageSource(Registry.FROST_DAMAGE.getDamageType(), attacker).setDamageBypassesArmor(), (float) (getDevotion(attacker) / 20));
        target.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 400, 0));
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon, devotion / 20);
        setAttackSpeed(weapon, devotion / 60);
    }

}
