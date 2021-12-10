package alexthw.hexblades.common.items;

import alexthw.hexblades.common.items.armors.HexWArmor;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

import org.jetbrains.annotations.NotNull;

public class ArmorFocus extends Item {

    String modFocus;

    public ArmorFocus(Properties pProperties, String mod) {
        super(pProperties);
        modFocus = mod;
    }

    public String getModFocus() {
        return modFocus;
    }

    public static String[] foci = {"eidolon", "botania", "ars nouveau"};

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pHand) {

        for (ItemStack stack : pPlayer.getArmorSlots()) {
            if (stack.getItem() instanceof HexWArmor) {
                HexWArmor.setFocus(stack, modFocus);
            }
        }

        return super.use(pLevel, pPlayer, pHand);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        if (!modFocus.equals("eidolon")) pTooltip.add(new TextComponent("Requires " + modFocus));
    }
}
