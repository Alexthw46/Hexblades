package Alexthw.Hexblades.common.items;

import Alexthw.Hexblades.core.registers.Tiers;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class IceKatana1 extends HexSwordItem {

    public IceKatana1(Properties props) {
        super(Tiers.HexiumTier.INSTANCE, 4, -2.4F, props);
    }

    @Override
    public boolean hitEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (target.hurtResistantTime > 0) {
            target.hurtResistantTime = 0;
            float before = target.getHealth();
            target.attackEntityFrom(new EntityDamageSource("wither", attacker).setDamageBypassesArmor(), 2.0f);
            float damaged = before - target.getHealth();

        }
        return super.hitEntity(stack, target, attacker);
    }


    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("tooltip.HexSwordItem." + "ice_katana"));
    }

}
