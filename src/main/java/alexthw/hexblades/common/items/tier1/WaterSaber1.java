package alexthw.hexblades.common.items.tier1;

import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.util.HexUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import static alexthw.hexblades.ConfigHandler.COMMON;

public class WaterSaber1 extends HexSwordItem {

    public float shield;

    public WaterSaber1(Properties props) {
        super(5, -2.4F, props);
        tooltipText = new TranslationTextComponent("tooltip.hexblades.water_saber");
        shield = 0;
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
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker) {
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon, devotion / COMMON.SaberDS1.get());
        setShielding(weapon, (float) (devotion / COMMON.SaberSH1.get()));
    }

    public void setShielding(ItemStack weapon, float damageReduction) {
        shield = isActivated ? damageReduction : 0;
    }

    @Override
    public void talk(PlayerEntity player) {
        player.sendMessage(new TranslationTextComponent(this.getDescriptionId() + ".dialogue." + player.level.getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true).withColor(Color.fromRgb(HexUtils.waterColor))), player.getUUID());
    }
}