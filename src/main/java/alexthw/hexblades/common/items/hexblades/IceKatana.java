package alexthw.hexblades.common.items.hexblades;

import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.util.HexUtils;
import elucent.eidolon.Registry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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

import java.util.List;

import static alexthw.hexblades.ConfigHandler.COMMON;

public class IceKatana extends HexSwordItem {

    public IceKatana(Properties props) {
        super(COMMON.KatanaBD.get(), -2.5F, props);
        loreText = new TranslatableComponent("tooltip.hexblades.ice_katana");
        textColor = ChatFormatting.AQUA;
    }

    @Override
    public void addShiftText(ItemStack stack, List<Component> tooltip) {
        switch (getAwakening(stack)){
            default-> tooltip.add(new TranslatableComponent("tooltip.hexblades.ice_katana_shift"));
            case 1 -> tooltip.add(new TranslatableComponent("tooltip.hexblades.ice_katana_shift_1"));
            case 2 -> tooltip.add(new TranslatableComponent("tooltip.hexblades.ice_katana_shift_2"));
        }
    }

    @Override
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {

        if (setAwakenedState(weapon, !getAwakened(weapon))){
            double devotion = getDevotion(player);
            int souls = getSouls(weapon);
            int level = getAwakening(weapon);

            applyHexBonus(player, level);

            switch (level) {
                default -> {
                    updateElementalDamage(weapon, devotion, COMMON.KatanaED.get());
                    setAttackPower(weapon, souls, COMMON.KatanaDS1.get());
                }
                case (1) -> {
                    updateElementalDamage(weapon, devotion, COMMON.KatanaED.get());
                    setAttackPower(weapon, souls, COMMON.KatanaDS1.get());
                    setAttackSpeed(weapon, souls, COMMON.KatanaAS1.get());
                }
                case (2) -> {
                    updateElementalDamage(weapon, devotion, COMMON.KatanaED.get());
                    setAttackPower(weapon, souls, COMMON.KatanaDS2.get());
                    setAttackSpeed(weapon, souls, COMMON.KatanaAS2.get());
                }
            }
        }
    }

    @Override
    public boolean onHitEffects() {
        return true;
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, Player attacker, boolean awakened) {
        if (awakened) {
            int level = getAwakening(stack);
            if (level > 0) {
                target.hurt(new EntityDamageSource(DamageSource.FREEZE.getMsgId(), attacker).bypassArmor(), getElementalDamage(stack));
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, level));
            }
            if (level == 2)
            target.addEffect(new MobEffectInstance(Registry.CHILLED_EFFECT.get(), 100, 0));
        }
    }

    @Override
    public void talk(Player player) {
        player.sendMessage(new TranslatableComponent(this.getDescriptionId() + ".dialogue." + player.level.getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true).withColor(TextColor.fromRgb(HexUtils.iceColor))), player.getUUID());
    }


}
