package Alexthw.Hexblades.common.items.tier2;

import Alexthw.Hexblades.common.items.tier1.WaterSaber1;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.world.World;

import static Alexthw.Hexblades.ConfigHandler.COMMON;
import static net.minecraft.util.DamageSource.DROWN;
import static net.minecraft.util.DamageSource.MAGIC;

public class WaterSaber2 extends WaterSaber1 {

    public WaterSaber2(Properties props) {
        super(6, -2.4F, props);
        tooltipText = "tooltip.HexSwordItem.water_saber2";
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker) {
        float bonus_dmg = (float) (getDevotion(attacker) / COMMON.SaberED2.get());
        if (target.getCreatureAttribute() == CreatureAttribute.WATER) {
            target.attackEntityFrom(new EntityDamageSource(MAGIC.getDamageType(), attacker).setDamageBypassesArmor(), bonus_dmg);
        } else {
            target.attackEntityFrom(new EntityDamageSource(DROWN.getDamageType(), attacker).setDamageBypassesArmor(), bonus_dmg);
        }
    }

    @Override
    public void applyHexBonus(PlayerEntity entity, boolean awakened) {
        if (awakened) {
            entity.addPotionEffect(new EffectInstance(Effects.CONDUIT_POWER, 200, 0, false, false));
        } else {
            entity.addPotionEffect(new EffectInstance(Effects.WATER_BREATHING, 200));
        }
        entity.addPotionEffect(new EffectInstance(Effects.DOLPHINS_GRACE, 200));
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon, devotion / COMMON.SaberDS2.get());
        setShielding(weapon, (float) (devotion / COMMON.SaberSH2.get()));
    }


}
