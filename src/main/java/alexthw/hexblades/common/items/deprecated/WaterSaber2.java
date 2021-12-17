package alexthw.hexblades.common.items.deprecated;

import alexthw.hexblades.common.items.deprecated.WaterSaber1;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static alexthw.hexblades.ConfigHandler.COMMON;
public class WaterSaber2 extends WaterSaber1 {

    public WaterSaber2(Properties props) {
        super(6, -2.4F, props);
        tooltipText = new TranslatableComponent("tooltip.hexblades.water_saber2");
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, Player attacker, boolean awakened) {
        if (awakened) {
            float bonus_dmg = (float) (getDevotion(attacker) / COMMON.SaberED2.get());
            if (target.getMobType() == MobType.WATER) {
                target.hurt(new EntityDamageSource(DamageSource.MAGIC.getMsgId(), attacker).bypassArmor(), bonus_dmg);
            } else {
                target.hurt(new EntityDamageSource(DamageSource.DROWN.getMsgId(), attacker).bypassArmor(), bonus_dmg);
            }
        }
    }

    @Override
    public void applyHexBonus(Player entity, boolean awakened, int souls) {
        if (awakened) {
            entity.addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, 200, 0, false, false));
        } else {
            entity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 200));
        }
        entity.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 200));
    }

    @Override
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {
        double devotion = getDevotion(player);

        boolean awakening = setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon, devotion , COMMON.SaberDS2.get() );
        setShielding(weapon, awakening, (float) (devotion / COMMON.SaberSH2.get()));
    }


}
