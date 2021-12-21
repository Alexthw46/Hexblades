package alexthw.hexblades.common.items.hexblades;

import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.util.HexUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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

import java.util.List;

import static alexthw.hexblades.ConfigHandler.COMMON;

public class FireSword extends HexSwordItem {

    public FireSword(Properties props) {
        super(COMMON.SwordBD.get(), -2.7F, props);
        loreText = new TranslatableComponent("tooltip.hexblades.flame_sword");
        textColor = ChatFormatting.RED;
    }

    @Override
    protected void addShiftText(ItemStack stack, List<Component> tooltip) {
        switch (getAwakening(stack)){
            default-> tooltip.add(new TranslatableComponent("tooltip.hexblades.flame_sword_shift"));
            case 1 -> tooltip.add(new TranslatableComponent("tooltip.hexblades.flame_sword_shift_1"));
            case 2 -> tooltip.add(new TranslatableComponent("tooltip.hexblades.flame_sword_shift_2"));
        }
    }

    @Override
    public boolean onHitEffects() {
        return true;
    }

    @Override
    public void applyHexEffects(ItemStack weapon, LivingEntity target, Player attacker, boolean awakened) {
        int level = getAwakening(weapon);
        if (awakened) {
            if (level > 0) {
                target.hurt(new EntityDamageSource("lava", attacker).bypassArmor(), getElementalDamage(weapon));
            }
            if (level == 2) {
                target.hurt(new EntityDamageSource("magic", attacker).setMagic(), getElementalDamage(weapon));
            }
        }
        if (!target.isOnFire()) target.setSecondsOnFire(3*level);
    }

    @Override
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {

        if (setAwakenedState(weapon, !getAwakened(weapon))){
            double devotion = getDevotion(player);
            int souls = getSouls(weapon);
            int level = getAwakening(weapon);

            applyHexBonus(player, level);

            switch (level) {
                default -> setAttackPower(weapon, souls, COMMON.SwordDS1.get());
                case (1) -> {
                    updateElementalDamage(weapon, devotion, COMMON.SwordED1.get());
                    setAttackPower(weapon, souls, COMMON.SwordDS1.get());
                }
                case (2) -> {
                    updateElementalDamage(weapon, devotion, COMMON.SwordED2.get());
                    setAttackPower(weapon, souls, COMMON.SwordDS2.get());
                }
            }
        }
    }

    @Override
    public void applyHexBonus(Player user, int level) {
        if (level > 0)
        user.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, level * 200, 0, false, false));
    }

    @Override
    public void talk(Player player) {
        player.sendMessage(new TranslatableComponent(this.getDescriptionId() + ".dialogue." + player.level.getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true).withColor(TextColor.fromRgb(HexUtils.fireColor))), player.getUUID());
    }

}

