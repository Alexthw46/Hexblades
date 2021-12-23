package alexthw.hexblades.common.items.tier2;

import alexthw.hexblades.common.items.tier1.WaterSaber1;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

import static alexthw.hexblades.ConfigHandler.COMMON;
import static net.minecraft.util.DamageSource.DROWN;
import static net.minecraft.util.DamageSource.MAGIC;

public class WaterSaber2 extends WaterSaber1 {

    public WaterSaber2(Properties props) {
        super(COMMON.SaberBD2.get(), -2.4F, props);
        tooltipText = new TranslationTextComponent("tooltip.hexblades.water_saber2");
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker, boolean awakened) {
        if (awakened) {
            float bonus_dmg = getElementalPower(stack);
            if (target.getMobType() == CreatureAttribute.WATER) {
                target.hurt(new EntityDamageSource(MAGIC.getMsgId(), attacker).bypassArmor().setMagic(), bonus_dmg);
            } else {
                target.hurt(new EntityDamageSource(DROWN.getMsgId(), attacker).bypassArmor(), bonus_dmg);
            }
        }
    }

    @Override
    public void applyHexBonus(PlayerEntity entity, boolean awakened) {
        if (awakened) {
            entity.addEffect(new EffectInstance(Effects.CONDUIT_POWER, 200, 0, false, false));
        } else {
            entity.addEffect(new EffectInstance(Effects.WATER_BREATHING, 200));
        }
        entity.addEffect(new EffectInstance(Effects.DOLPHINS_GRACE, 200));
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);
        boolean awakening = setAwakenedState(weapon, !getAwakened(weapon));
        if (awakening) updateElementalPower(weapon, COMMON.SaberED2.get(), devotion);
        setAttackPower(weapon, awakening, devotion / COMMON.SaberDS2.get());
        setShielding(weapon, awakening, (float) (devotion / COMMON.SaberSH2.get()));
    }

    @Override
    protected void addShiftTooltip(ItemStack stack, List<ITextComponent> tooltip) {
        tooltip.add(new StringTextComponent("Armor piercing damage: " + getElementalPower(stack)));
        tooltip.add(new StringTextComponent("Damage reduction: " + getShielding(stack)));
        tooltip.add(new StringTextComponent("Gives conduit power"));
    }
}
