package alexthw.hexblades.common.items.hexblades;

import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.util.Constants;
import alexthw.hexblades.util.HexUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
            int level = getAwakening(weapon);

            applyHexBonus(player, true, level);

            switch (level) {
                default -> setAttackPower(weapon, devotion, COMMON.SaberDS1.get());
                case (1) -> {
                    setAttackPower(weapon, devotion, COMMON.SaberDS1.get());
                    setShielding(weapon, devotion, COMMON.SaberSH1.get());
                }
                case (2) -> {
                    updateElementalDamage(weapon, devotion, COMMON.SaberED2.get());
                    setAttackPower(weapon, devotion, COMMON.SaberDS2.get());
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
    public void applyHexBonus(Player entity, boolean awakened, int souls) {
        if (awakened) entity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 200, 0, false, false));
    }

}
