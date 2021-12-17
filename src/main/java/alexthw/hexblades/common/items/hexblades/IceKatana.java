package alexthw.hexblades.common.items.hexblades;

import alexthw.hexblades.common.items.HexSwordItem;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import static alexthw.hexblades.ConfigHandler.COMMON;

public class IceKatana extends HexSwordItem {

    public IceKatana(Properties props) {
        super(COMMON.KatanaBD.get(), -2.5F, props);
        tooltipText = new TranslatableComponent("tooltip.hexblades.ice_katana");
    }

    @Override
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {

        if (setAwakenedState(weapon, !getAwakened(weapon))){
            double devotion = getDevotion(player);
            int level = getAwakening(weapon);

            applyHexBonus(player, true, level);

            switch (level) {
                default -> setAttackPower(weapon, devotion, COMMON.KatanaDS1.get());
                case (1) -> {
                    updateElementalDamage(weapon, devotion, COMMON.KatanaED.get());
                    setAttackPower(weapon, devotion, COMMON.KatanaDS1.get());
                    setAttackSpeed(weapon, devotion, COMMON.KatanaAS1.get());
                }
                case (2) -> {
                    updateElementalDamage(weapon, devotion, COMMON.KatanaED.get());
                    setAttackPower(weapon, devotion, COMMON.KatanaDS2.get());
                    setAttackSpeed(weapon, devotion, COMMON.KatanaAS2.get());
                }
            }
        }
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, Player attacker, boolean awakened) {
        if (awakened) {
            target.hurt(new EntityDamageSource(DamageSource.FREEZE.getMsgId(), attacker).bypassArmor(), (float) (getDevotion(attacker) / COMMON.KatanaED.get()));
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 0));
        }
        if (awakened) {
            target.addEffect(new MobEffectInstance(Registry.CHILLED_EFFECT.get(), 100, 0));
        }
    }

    @Override
    public void talk(Player player) {
        player.sendMessage(new TranslatableComponent(this.getDescriptionId() + ".dialogue." + player.level.getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true).withColor(TextColor.fromRgb(HexUtils.iceColor))), player.getUUID());
    }


}
