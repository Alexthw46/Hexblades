package alexthw.hexblades.common.items.tier2;

import alexthw.hexblades.common.items.tier1.FireBroad1;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import static alexthw.hexblades.ConfigHandler.COMMON;

public class FireBroad2 extends FireBroad1 {


    public FireBroad2(Properties props) {
        super(7, -2.7F, props);
        tooltipText = new TranslationTextComponent("tooltip.hexblades.flame_sword2");
    }

    @Override
    public boolean hasBonus() {
        return true;
    }

    @Override
    public void applyHexBonus(PlayerEntity user, boolean awakened) {
        user.addEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 200, 0, false, false));
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        boolean awakening = setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon, awakening, devotion / COMMON.SwordDS2.get());
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker, boolean awakened) {
        if (awakened) {
            target.hurt(new EntityDamageSource("magic", attacker).bypassArmor(), (float) (getDevotion(attacker) / COMMON.SwordED2.get()));
        }
        target.setSecondsOnFire(3);
    }

}
