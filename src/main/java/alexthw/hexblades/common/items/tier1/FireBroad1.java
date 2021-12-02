package alexthw.hexblades.common.items.tier1;

import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.util.HexUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import static alexthw.hexblades.ConfigHandler.COMMON;

import net.minecraft.world.item.Item.Properties;

public class FireBroad1 extends HexSwordItem {

    public FireBroad1(Properties props) {
        super(6, -2.7F, props);
        tooltipText = new TranslatableComponent("tooltip.hexblades.flame_sword");
    }

    public FireBroad1(int attackDamage, float attackSpeed, Properties props) {
        super(attackDamage, attackSpeed, props);
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, Player attacker, boolean awakened) {
        if (awakened) {
            target.hurt(new EntityDamageSource("lava", attacker).bypassArmor(), (float) (getDevotion(attacker) / COMMON.SwordED1.get()));
            target.setSecondsOnFire(2);
        }
    }

    @Override
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {
        double devotion = getDevotion(player);

        boolean awakening = setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon, awakening, devotion / COMMON.SwordDS1.get());
    }

    @Override
    public void talk(Player player) {
        player.sendMessage(new TranslatableComponent(this.getDescriptionId() + ".dialogue." + player.level.getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true).withColor(TextColor.fromRgb(HexUtils.fireColor))), player.getUUID());
    }

}
