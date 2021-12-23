package alexthw.hexblades.common.items.tier2;

import alexthw.hexblades.common.items.tier1.IceKatana1;
import elucent.eidolon.Registry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

import static alexthw.hexblades.ConfigHandler.COMMON;

public class IceKatana2 extends IceKatana1 {
    public IceKatana2(Properties props) {
        super(COMMON.KatanaBD2.get(), -2.5F, props);
        tooltipText = new TranslationTextComponent("tooltip.hexblades.ice_katana2");
    }

    @Override
    public boolean onHitEffects() {
        return true;
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker, boolean awakened) {
        super.applyHexEffects(stack, target, attacker, awakened);
        if (awakened) {
            target.addEffect(new EffectInstance(Registry.CHILLED_EFFECT.get(), 100, 0));
        }
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        boolean awakening = setAwakenedState(weapon, !getAwakened(weapon));
        if (awakening) updateElementalPower(weapon, COMMON.KatanaED.get(), devotion);

        setAttackPower(weapon, awakening, devotion / COMMON.KatanaDS2.get());
        setAttackSpeed(weapon, awakening, devotion / COMMON.KatanaAS2.get());
    }

    @Override
    protected void addShiftTooltip(ItemStack stack, List<ITextComponent> tooltip) {
        tooltip.add(new StringTextComponent("Armor piercing damage: " + getElementalPower(stack)));
        tooltip.add(new StringTextComponent("Slows and chills enemies"));
    }
}
