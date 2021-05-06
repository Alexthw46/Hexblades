package Alexthw.Hexblades.common.items.tier2;

import Alexthw.Hexblades.common.items.tier1.IceKatana1;
import elucent.eidolon.Registry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;

public class IceKatana2 extends IceKatana1 {
    public IceKatana2(Properties props) {
        super(props);
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.attackEntityFrom(new EntityDamageSource(Registry.FROST_DAMAGE.getDamageType(), attacker).setDamageBypassesArmor(), 2.0f);
        target.addPotionEffect(new EffectInstance((Effect)Registry.CHILLED_EFFECT.get(), 300, 0));
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon,devotion/10);
        setAttackSpeed(weapon,devotion/15);
    }
}