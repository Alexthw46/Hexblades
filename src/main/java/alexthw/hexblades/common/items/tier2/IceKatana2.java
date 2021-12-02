package alexthw.hexblades.common.items.tier2;

import alexthw.hexblades.common.items.tier1.IceKatana1;
import elucent.eidolon.Registry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import static alexthw.hexblades.ConfigHandler.COMMON;

import net.minecraft.world.item.Item.Properties;

public class IceKatana2 extends IceKatana1 {
    public IceKatana2(Properties props) {
        super(5, -2.5F, props);
        tooltipText = new TranslatableComponent("tooltip.hexblades.ice_katana2");
    }

    @Override
    public boolean onHitEffects() {
        return true;
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, Player attacker, boolean awakened) {
        super.applyHexEffects(stack, target, attacker, awakened);
        if (awakened) {
            target.addEffect(new MobEffectInstance(Registry.CHILLED_EFFECT.get(), 100, 0));
        }
    }

    @Override
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {
        double devotion = getDevotion(player);

        boolean awakening = setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon, awakening, devotion / COMMON.KatanaDS2.get());
        setAttackSpeed(weapon, awakening, devotion / COMMON.KatanaAS2.get());
    }
}
