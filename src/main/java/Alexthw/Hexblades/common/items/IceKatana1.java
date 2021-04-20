package Alexthw.Hexblades.common.items;

import elucent.eidolon.Registry;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class IceKatana1 extends HexSwordItem {

    public IceKatana1(Properties props) {
        super(4, -2.4F, props);
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.attackEntityFrom(new EntityDamageSource(Registry.FROST_DAMAGE.getDamageType(), attacker).setDamageBypassesArmor(), 2.0f);
        target.addPotionEffect(new EffectInstance(Registry.CHILLED_EFFECT.get(), 300, 0));
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon,devotion/20);
        setAttackSpeed(weapon,devotion/30);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("tooltip.HexSwordItem." + "ice_katana"));
    }

}
