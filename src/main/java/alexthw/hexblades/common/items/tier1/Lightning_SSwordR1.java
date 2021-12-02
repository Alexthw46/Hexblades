package alexthw.hexblades.common.items.tier1;

import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.registers.HexItem;
import alexthw.hexblades.util.HexUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import static alexthw.hexblades.ConfigHandler.COMMON;


public class Lightning_SSwordR1 extends HexSwordItem {

    public Lightning_SSwordR1(Properties props) {
        super(4, -1.5F, props);
        tooltipText = new TranslatableComponent("tooltip.hexblades.thunder_knives");
    }

    @Override
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {
        double devotion = getDevotion(player);


        if (!hasTwin(player)) {
            setAwakenedState(weapon, false);
        } else if (getAwakened(weapon)) {
            setAwakenedState(weapon, weapon.getDamageValue() <= getMaxDamage(weapon) * 0.9);
        } else {
            setAwakenedState(weapon, true);
        }

        boolean awakening = getAwakened(weapon);

        setAttackPower(weapon, awakening, devotion / COMMON.DualsDS1.get());
        setAttackSpeed(weapon, awakening, devotion / COMMON.DualsAS1.get());

    }

    public boolean hasTwin(Player player) {
        return player.getOffhandItem().getItem() == HexItem.LIGHTNING_DAGGER_L.get();
    }

    @Override
    public void talk(Player player) {
        player.sendMessage(new TranslatableComponent(this.getDescriptionId() + ".dialogue." + player.level.getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true).withColor(TextColor.fromRgb(HexUtils.thunderColor))), player.getUUID());
    }

}
