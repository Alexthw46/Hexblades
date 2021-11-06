package alexthw.hexblades.common.items.tier1;

import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.util.Constants;
import alexthw.hexblades.util.HexUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import static alexthw.hexblades.ConfigHandler.COMMON;

public class WaterSaber1 extends HexSwordItem {

    public WaterSaber1(Properties props) {
        super(5, -2.4F, props);
        tooltipText = new TranslationTextComponent("tooltip.hexblades.water_saber");
    }

    public WaterSaber1(int attackDamage, float attackSpeed, Properties props) {
        super(attackDamage, attackSpeed, props);
    }

    @Override
    public boolean hasBonus() {
        return true;
    }

    @Override
    public void applyHexBonus(PlayerEntity entity, boolean awakened) {
        if (awakened) entity.addEffect(new EffectInstance(Effects.WATER_BREATHING, 200, 0, false, false));
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        boolean awakening = setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon, awakening, devotion / COMMON.SaberDS1.get());
        setShielding(weapon, awakening, (float) (devotion / COMMON.SaberSH1.get()));
    }

    public void setShielding(ItemStack weapon, boolean awakening, float damageReduction) {
        CompoundNBT tag = weapon.getOrCreateTag();
        tag.putFloat(Constants.NBT.SHIELDING, awakening ? damageReduction : 0);
    }

    @Override
    public void talk(PlayerEntity player) {
        player.sendMessage(new TranslationTextComponent(this.getDescriptionId() + ".dialogue." + player.level.getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true).withColor(Color.fromRgb(HexUtils.waterColor))), player.getUUID());
    }

    public float getShielding(ItemStack weapon) {
        return weapon.getOrCreateTag().getFloat(Constants.NBT.SHIELDING);
    }
}
