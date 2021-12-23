package alexthw.hexblades.common.items.tier2;

import alexthw.hexblades.common.items.tier1.EarthHammer1;
import alexthw.hexblades.registers.Tiers;
import alexthw.hexblades.util.Constants;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

import static alexthw.hexblades.ConfigHandler.COMMON;

public class EarthHammer2 extends EarthHammer1 {

    public EarthHammer2(Properties props) {
        super(COMMON.HammerBD2.get(), -3.2F,
                props.addToolType(net.minecraftforge.common.ToolType.PICKAXE, Tiers.PatronWeaponTier.INSTANCE.getLevel()));
        baseMiningSpeed = 8.0F;
        tooltipText = new TranslationTextComponent("tooltip.hexblades.earth_hammer2");
    }

    @Override
    public boolean onHitEffects() {
        return true;
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker, boolean awakened) {
        float power = 1.5F;
        if (awakened) {
            target.hurt(new EntityDamageSource("anvil", attacker).bypassArmor(), COMMON.HammerED2.get().floatValue());
            target.addEffect(new EffectInstance(Effects.WEAKNESS, 300, 0));
            power = (float) (2.0F + getDevotion(attacker) / 20);
        }
        double X = attacker.getX() - target.getX();
        double Z = attacker.getZ() - target.getZ();
        target.knockback(power, X, Z);

    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        boolean mineSwitch = weapon.getOrCreateTag().getBoolean(Constants.NBT.MiningSwitch);

        if (!mineSwitch) setAwakenedState(weapon, !getAwakened(weapon));

        boolean awakening = getAwakened(weapon);

        setAttackPower(weapon, awakening || mineSwitch, mineSwitch ? -8 : (devotion / COMMON.HammerDS2.get()));
        setMiningSpeed(weapon, awakening, (float) (devotion / COMMON.HammerMS2.get()));
    }

    @Override
    protected void addShiftTooltip(ItemStack stack, List<ITextComponent> tooltip) {
        tooltip.add(new StringTextComponent("Armor piercing damage: " + COMMON.HammerED2.get()));
        tooltip.add(new StringTextComponent("Massive knockback, weakens enemies"));
    }
}
