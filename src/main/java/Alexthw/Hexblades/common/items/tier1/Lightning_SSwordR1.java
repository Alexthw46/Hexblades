package Alexthw.Hexblades.common.items.tier1;

import Alexthw.Hexblades.common.items.HexSwordItem;
import Alexthw.Hexblades.registers.HexItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class Lightning_SSwordR1 extends HexSwordItem {

    public Lightning_SSwordR1(Properties props) {
        super(1, -1.5F, props);
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);
        boolean active = getAwakened(weapon);

        if (hasTwin(player)) {
            if (!active) {
                setAwakenedState(weapon, true);

                setAttackPower(weapon, devotion / 20);
                setAttackSpeed(weapon, devotion / 30);
            }
        } else {
            setAwakenedState(weapon, false);
            setAttackPower(weapon, 0);
            setAttackSpeed(weapon, 0);
        }
    }

    public boolean hasTwin(PlayerEntity player) {
        return player.getHeldItemOffhand().getItem() == HexItem.LIGHTNING_DAGGER_L.get();
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("tooltip.HexSwordItem." + "thunder_knives"));
    }

}