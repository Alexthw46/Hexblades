package alexthw.hexblades.common.items.tier2;

import alexthw.hexblades.common.items.tier1.FireBroad1;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import static alexthw.hexblades.ConfigHandler.COMMON;

import net.minecraft.world.item.Item.Properties;

public class FireBroad2 extends FireBroad1 {


    public FireBroad2(Properties props) {
        super(7, -2.7F, props);
        tooltipText = new TranslatableComponent("tooltip.hexblades.flame_sword2");
    }

    @Override
    public boolean hasBonus() {
        return true;
    }

    @Override
    public void applyHexBonus(Player user, boolean awakened) {
        user.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0, false, false));
    }

    @Override
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {
        double devotion = getDevotion(player);

        boolean awakening = setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon, awakening, devotion / COMMON.SwordDS2.get());
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, Player attacker, boolean awakened) {
        if (awakened) {
            target.hurt(new EntityDamageSource("magic", attacker).bypassArmor(), (float) (getDevotion(attacker) / COMMON.SwordED2.get()));
        }
        target.setSecondsOnFire(3);
    }

}
