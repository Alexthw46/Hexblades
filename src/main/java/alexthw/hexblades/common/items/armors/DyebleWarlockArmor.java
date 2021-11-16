package alexthw.hexblades.common.items.armors;

import elucent.eidolon.item.WarlockRobesItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class DyebleWarlockArmor extends WarlockRobesItem {
    public DyebleWarlockArmor(EquipmentSlotType slot, Properties builderIn) {
        super(slot, builderIn);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        int color = getColor(stack);
        switch (color) {
            case 0:
                return "hexblades:textures/entity/warlock_robes/white.png";
            case 1:
                return "hexblades:textures/entity/warlock_robes/orange.png";
            case 2:
                return "hexblades:textures/entity/warlock_robes/magenta.png";
            case 3:
                return "hexblades:textures/entity/warlock_robes/light_blue.png";
            case 4:
                return "hexblades:textures/entity/warlock_robes/yellow.png";
            case 5:
                return "hexblades:textures/entity/warlock_robes/lime.png";
            case 6:
                return "hexblades:textures/entity/warlock_robes/pink.png";
            case 9:
                return "hexblades:textures/entity/warlock_robes/cyan.png";
            case 10:
                return "hexblades:textures/entity/warlock_robes/purple.png";
            case 12:
                return "hexblades:textures/entity/warlock_robes/brown.png";
            case 13:
                return "hexblades:textures/entity/warlock_robes/green.png";
            case 14:
                return "hexblades:textures/entity/warlock_robes/red.png";
            case 15:
                return "hexblades:textures/entity/warlock_robes/black.png";
        }
        return super.getArmorTexture(stack, entity, slot, type);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable World pLevel, List<ITextComponent> pTooltip, ITooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        pTooltip.add(new StringTextComponent(getColor(getColor(pStack))));
    }

    private int getColor(ItemStack stack) {
        CompoundNBT tag = stack.getOrCreateTag();
        return tag.getInt("color");
    }

    private String getColor(int index) {

        switch (index) {
            case (0):
                return "White";
            case (1):
                return "Orange";
            case (2):
                return "Magenta";
            case (3):
                return "Light_blue";
            case (4):
                return "Yellow";
            case (5):
                return "Lime";
            case (6):
                return "Pink";
            case (9):
                return "Cyan";
            case (10):
                return "Purple";
            case (12):
                return "Brown";
            case (13):
                return "Green";
            case (14):
                return "Red";
            case (15):
                return "Black";
            default:
                return "Blue";
        }
    }

}
