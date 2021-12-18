package alexthw.hexblades.common.items.hexblades;

import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.util.Constants;
import alexthw.hexblades.util.HexUtils;
import elucent.eidolon.Registry;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
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

public class WaterSaber extends HexSwordItem {

    public WaterSaber(Properties props) {
        super(5, -2.4F, props);
        tooltipText = new TranslatableComponent("tooltip.hexblades.water_saber");
    }

    @Override
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {
        if (setAwakenedState(weapon, !getAwakened(weapon))) {
            double devotion = getDevotion(player);
            int souls = getSouls(weapon);
            int level = getAwakening(weapon);

            applyHexBonus(player, level);

            switch (level) {
                default -> setAttackPower(weapon, souls, COMMON.SaberDS1.get());
                case (1) -> {
                    setAttackPower(weapon, souls, COMMON.SaberDS1.get());
                    setShielding(weapon, devotion, COMMON.SaberSH1.get());
                }
                case (2) -> {
                    updateElementalDamage(weapon, devotion, COMMON.SaberED2.get());
                    setAttackPower(weapon, souls, COMMON.SaberDS2.get());
                    setShielding(weapon,devotion, COMMON.SaberSH2.get());
                }
            }
        }
    }

    public void setShielding(ItemStack weapon, double devotion, int scaling) {
        float damageReduction = (float) (devotion/scaling);
        weapon.getOrCreateTag().putFloat(Constants.NBT.SHIELDING, damageReduction);
    }

    @Override
    public void talk(Player player) {
        player.sendMessage(new TranslatableComponent(this.getDescriptionId() + ".dialogue." + player.level.getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true).withColor(TextColor.fromRgb(HexUtils.waterColor))), player.getUUID());
    }

    public float getShielding(ItemStack weapon) {
        return weapon.getOrCreateTag().getFloat(Constants.NBT.SHIELDING);
    }

    @Override
    public void applyHexBonus(Player entity, int level) {
        entity.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 200, 0, false, false));
        if (level == 1) entity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 200, 0, false, false));
        if (level == 2) entity.addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, 200, 0, false, false));
    }

    @Override
    public boolean onHitEffects() {
        return true;
    }

    @Override
    public void applyHexEffects(ItemStack weapon, LivingEntity target, Player attacker, boolean awakened) {
        if (awakened && getAwakening(weapon) > 0) {
            if (target.getMobType() == MobType.WATER) {
                target.hurt(new EntityDamageSource(DamageSource.MAGIC.getMsgId(), attacker).setMagic().bypassArmor(), getElementalDamage(weapon));
            } else {
                target.hurt(new EntityDamageSource(DamageSource.DROWN.getMsgId(), attacker).bypassArmor(), getElementalDamage(weapon));
            }
        }
    }
}
