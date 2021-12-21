package alexthw.hexblades.common.items.hexblades;

import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.util.HexUtils;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static alexthw.hexblades.ConfigHandler.COMMON;

public class ThunderSSword extends HexSwordItem {

    public ThunderSSword(Properties props) {
        super(4, -1.5F, props);
        loreText = new TranslatableComponent("tooltip.hexblades.thunder_knives");
    }

    @Override
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {
        boolean isAwakened;

        if (!hasTwin(player)) {
            isAwakened = setAwakenedState(weapon, false);
        } else if (getAwakened(weapon)) {
            isAwakened = setAwakenedState(weapon, weapon.getDamageValue() <= getMaxDamage(weapon) * 0.9);
        } else {
            isAwakened = setAwakenedState(weapon, true);
        }

        if (isAwakened) {
            int level = getAwakening(weapon);
            double devotion = getDevotion(player) + (double)getSouls(weapon) / 2;

            switch (level){
                default -> {
                }
                case(1) ->{
                    applyHexBonus(player,level);
                    setAttackPower(weapon, devotion, COMMON.DualsDS1.get());
                    setAttackSpeed(weapon, devotion, COMMON.DualsAS1.get());
                }
                case(2) ->{
                    applyHexBonus(player, level);
                    setAttackPower(weapon, devotion, COMMON.DualsDS2.get());
                    setAttackSpeed(weapon, devotion, COMMON.DualsAS2.get());
                }
            }
        }
    }

    public boolean hasTwin(Player player) {
        return player.getOffhandItem().getItem() instanceof LightningSSword;
    }

    @Override
    public void applyHexBonus(Player user, int level) {
        if (hasTwin(user)) {
            if (level == 2) {
                user.addEffect(new MobEffectInstance(MobEffects.JUMP, 200, 0, false, false));
                user.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 1, false, false));
            } else {
                user.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0, false, false));
            }
        }
    }

    @Override
    public void talk(Player player) {
        player.sendMessage(new TranslatableComponent(this.getDescriptionId() + ".dialogue." + player.level.getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true).withColor(TextColor.fromRgb(HexUtils.thunderColor))), player.getUUID());
    }
}
