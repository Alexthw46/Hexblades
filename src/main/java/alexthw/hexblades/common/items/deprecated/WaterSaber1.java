package alexthw.hexblades.common.items.deprecated;

import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.util.Constants;
import alexthw.hexblades.util.HexUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import static alexthw.hexblades.ConfigHandler.COMMON;

public class WaterSaber1 extends HexSwordItem {

    public WaterSaber1(Properties props) {
        super(5, -2.4F, props);
        tooltipText = new TranslatableComponent("tooltip.hexblades.water_saber");
    }

    public WaterSaber1(int attackDamage, float attackSpeed, Properties props) {
        super(attackDamage, attackSpeed, props);
    }

    @Override
    public void applyHexBonus(Player entity, boolean awakened, int souls) {
        if (awakened) entity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 200, 0, false, false));
    }

    @Override
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {
        double devotion = getDevotion(player);

        boolean awakening = setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon, devotion , COMMON.SaberDS1.get());
        setShielding(weapon, awakening, (float) (devotion / COMMON.SaberSH1.get()));
    }

    public void setShielding(ItemStack weapon, boolean awakening, float damageReduction) {
        CompoundTag tag = weapon.getOrCreateTag();
        tag.putFloat(Constants.NBT.SHIELDING, awakening ? damageReduction : 0);
    }

    @Override
    public void talk(Player player) {
        player.sendMessage(new TranslatableComponent(this.getDescriptionId() + ".dialogue." + player.level.getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true).withColor(TextColor.fromRgb(HexUtils.waterColor))), player.getUUID());
    }

    public float getShielding(ItemStack weapon) {
        return weapon.getOrCreateTag().getFloat(Constants.NBT.SHIELDING);
    }
}
