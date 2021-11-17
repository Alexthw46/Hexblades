package alexthw.hexblades.common.items.armors;

import alexthw.hexblades.registers.HexItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoArmorItem;

import javax.annotation.Nullable;
import java.util.List;

import static alexthw.hexblades.util.Constants.ArmorCompat.FOCUS_TAG;

public class HexWArmor extends GeoArmorItem implements IAnimatable {

    public HexWArmor(EquipmentSlotType slot, Properties builderIn) {
        super(HexWArmor.Material.INSTANCE, slot, builderIn);
    }

    public static String getFocus(ItemStack stack) {
        String focus = stack.getOrCreateTag().getString(FOCUS_TAG);
        return focus.equals("") ? "none" : focus;
    }

    public static void setFocus(ItemStack stack, String focus) {

        if (!(stack.getItem() instanceof HexWArmor)) return;

        CompoundNBT tag = stack.getOrCreateTag();
        tag.putString(FOCUS_TAG, focus);
        stack.setTag(tag);
    }

    public static int getFocusId(ItemStack stack) {
        String focus = stack.getOrCreateTag().getString(FOCUS_TAG);
        switch (focus) {
            case ("eidolon"):
                return 1;
            case ("botania"):
                return 2;
            case ("ars nouveau"):
                return 3;
            default:
                return 0;
        }
    }

    private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable World pLevel, List<ITextComponent> pTooltip, ITooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        pTooltip.add(new StringTextComponent("Focus: " + getFocus(pStack)));
    }


    public static class Material implements IArmorMaterial {
        public static final HexWArmor.Material INSTANCE = new HexWArmor.Material();

        public Material() {
        }

        public int getDurabilityForSlot(EquipmentSlotType slot) {
            return HexWArmor.MAX_DAMAGE_ARRAY[slot.getIndex()] * 30;
        }

        public int getDefenseForSlot(EquipmentSlotType slot) {
            switch (slot) {
                case CHEST:
                    return 8;
                case HEAD:
                case FEET:
                    return 3;
                case LEGS:
                    return 6;
                default:
                    return 0;
            }
        }

        public int getEnchantmentValue() {
            return 25;
        }

        public SoundEvent getEquipSound() {
            return ArmorMaterial.GOLD.getEquipSound();
        }

        public Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(HexItem.HEXED_INGOT.get()));
        }

        public String getName() {
            return "hexblades:hex_armor";
        }

        public float getToughness() {
            return 2.0F;
        }

        public float getKnockbackResistance() {
            return 0.075F;
        }

    }
    //useless

    private final AnimationFactory factory = new AnimationFactory(this);

    @Override
    public void registerControllers(AnimationData data) {
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

}

