package alexthw.hexblades.common.items.hexblades;

import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.util.HexUtils;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static alexthw.hexblades.ConfigHandler.COMMON;

public class FireSword extends HexSwordItem {

    public FireSword(Properties props) {
        super(COMMON.SwordBD.get(), -2.7F, props);
        tooltipText = new TranslatableComponent("tooltip.hexblades.flame_sword");
    }

    @Override
    public void applyHexEffects(ItemStack weapon, LivingEntity target, Player attacker, boolean awakened) {
        if (awakened) {
            int level = getAwakening(weapon);
            if (level > 1) {
                target.hurt(new EntityDamageSource("lava", attacker).bypassArmor(), getElementalDamage(weapon));
            }
            if (level > 3) {
                target.hurt(new EntityDamageSource("magic", attacker).bypassArmor(), getElementalDamage(weapon));
            }
        }
        target.setSecondsOnFire(3);
    }

    @Override
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {

        if (setAwakenedState(weapon, !getAwakened(weapon))){
            double devotion = getDevotion(player);
            int level = getAwakening(weapon);

            applyHexBonus(player, true, level);

            switch (level) {
                default -> setAttackPower(weapon, devotion, COMMON.SwordDS1.get());
                case (1) -> {
                    updateElementalDamage(weapon, devotion, COMMON.SwordED1.get());
                    setAttackPower(weapon, devotion, COMMON.SwordDS1.get());
                }
                case (2) -> {
                    updateElementalDamage(weapon, devotion, COMMON.SwordED2.get());
                    setAttackPower(weapon, devotion, COMMON.SwordDS2.get());
                }
            }
        }
    }

    @Override
    public void applyHexBonus(Player user, boolean awakened, int level) {
        if (awakened && level > 0)
        user.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0, false, false));
    }

    @Override
    public void talk(Player player) {
        player.sendMessage(new TranslatableComponent(this.getDescriptionId() + ".dialogue." + player.level.getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true).withColor(TextColor.fromRgb(HexUtils.fireColor))), player.getUUID());
    }

}

