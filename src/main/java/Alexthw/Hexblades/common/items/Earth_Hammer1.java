package Alexthw.Hexblades.common.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class Earth_Hammer1 extends HexSwordItem{
    public Earth_Hammer1(Properties props) {
        super(8, -3.4F, props);
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.attackEntityFrom(new EntityDamageSource("anvil", attacker).setDamageBypassesArmor(), 2.0f);
        double X = attacker.getPosX() - target.getPosX();
        double Z = attacker.getPosZ() - target.getPosZ();

        target.applyKnockback(0.8F,X,Z);
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        setAwakenedState(weapon, !getAwakened(weapon));
        setAttackPower(weapon,devotion/20);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("tooltip.HexSwordItem." + "earth_hammer"));
    }

}
