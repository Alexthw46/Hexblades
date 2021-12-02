package alexthw.hexblades.common.items.tier1;

import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.util.HexUtils;
import elucent.eidolon.Registry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import static alexthw.hexblades.ConfigHandler.COMMON;

import net.minecraft.world.item.Item.Properties;

public class IceKatana1 extends HexSwordItem {

    public IceKatana1(Properties props) {
        super(5, -2.5F, props);
        tooltipText = new TranslatableComponent("tooltip.hexblades.ice_katana");
    }

    public IceKatana1(int attackDamage, float attackSpeed, Properties props) {
        super(attackDamage, attackSpeed, props);
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, Player attacker, boolean awakened) {
        if (awakened) {
            target.hurt(new EntityDamageSource(Registry.FROST_DAMAGE.getMsgId(), attacker).bypassArmor(), (float) (getDevotion(attacker) / COMMON.KatanaED.get()));
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 0));
        }
    }

    @Override
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {
        double devotion = getDevotion(player);

        boolean awakening = setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon, awakening, devotion / COMMON.KatanaDS1.get());
        setAttackSpeed(weapon, awakening, devotion / COMMON.KatanaAS1.get());
    }

    @Override
    public void talk(Player player) {
        player.sendMessage(new TranslatableComponent(this.getDescriptionId() + ".dialogue." + player.level.getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true).withColor(TextColor.fromRgb(HexUtils.iceColor))), player.getUUID());
    }

}
