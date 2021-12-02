package alexthw.hexblades.common.items.tier2;

import alexthw.hexblades.common.items.tier1.EarthHammer1;
import alexthw.hexblades.registers.Tiers;
import alexthw.hexblades.util.Constants;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.Level;

import static alexthw.hexblades.ConfigHandler.COMMON;

import net.minecraft.world.item.Item.Properties;

public class EarthHammer2 extends EarthHammer1 {

    public EarthHammer2(Properties props) {
        super(8, -3.2F,
                props.addToolType(net.minecraftforge.common.ToolType.PICKAXE, Tiers.PatronWeaponTier.INSTANCE.getLevel()));
        baseMiningSpeed = 8.0F;
        tooltipText = new TranslatableComponent("tooltip.hexblades.earth_hammer2");
    }

    @Override
    public boolean onHitEffects() {
        return true;
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, Player attacker, boolean awakened) {
        float power = 1.5F;
        if (awakened) {
            target.hurt(new EntityDamageSource("anvil", attacker).bypassArmor(), COMMON.HammerED2.get().floatValue());
            target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 300, 0));
            power = (float) (2.0F + getDevotion(attacker) / 20);
        }
        double X = attacker.getX() - target.getX();
        double Z = attacker.getZ() - target.getZ();
        target.knockback(power, X, Z);

    }

    @Override
    public void recalculatePowers(ItemStack weapon, Level world, Player player) {
        double devotion = getDevotion(player);

        boolean mineSwitch = weapon.getOrCreateTag().getBoolean(Constants.NBT.MiningSwitch);

        if (!mineSwitch) setAwakenedState(weapon, !getAwakened(weapon));

        boolean awakening = getAwakened(weapon);

        setAttackPower(weapon, awakening || mineSwitch, mineSwitch ? -8 : (devotion / COMMON.HammerDS2.get()));
        setMiningSpeed(weapon, awakening, (float) (devotion / COMMON.HammerMS2.get()));
    }

}
