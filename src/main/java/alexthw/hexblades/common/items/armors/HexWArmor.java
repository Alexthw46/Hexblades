package alexthw.hexblades.common.items.armors;

import alexthw.hexblades.registers.HexItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoArmorItem;

import javax.annotation.Nullable;
import java.util.List;

import static alexthw.hexblades.util.Constants.ArmorCompat.FOCUS_TAG;

public class HexWArmor extends GeoArmorItem implements IAnimatable {

    public HexWArmor(EquipmentSlot slot, Properties builderIn) {
        super(HexWArmor.Material.INSTANCE, slot, builderIn);
    }

    public static String getFocus(ItemStack stack) {
        String focus = stack.getOrCreateTag().getString(FOCUS_TAG);
        return focus.equals("") ? "none" : focus;
    }

    public static void setFocus(ItemStack stack, String focus) {

        if (!(stack.getItem() instanceof HexWArmor)) return;

        CompoundTag tag = stack.getOrCreateTag();
        tag.putString(FOCUS_TAG, focus);
        stack.setTag(tag);
    }

    public static int getFocusId(ItemStack stack) {
        String focus = stack.getOrCreateTag().getString(FOCUS_TAG);
        return switch (focus) {
            case ("eidolon") -> 1;
            case ("botania") -> 2;
            case ("ars nouveau") -> 3;
            default -> 0;
        };
    }

    private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};

    @Override
    public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltip, @NotNull TooltipFlag pFlag) {
        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
        pTooltip.add(new TextComponent("Focus: " + getFocus(pStack)));
    }


    public static class Material implements ArmorMaterial {
        public static final HexWArmor.Material INSTANCE = new HexWArmor.Material();

        public Material() {
        }

        public int getDurabilityForSlot(EquipmentSlot slot) {
            return HexWArmor.MAX_DAMAGE_ARRAY[slot.getIndex()] * 30;
        }

        public int getDefenseForSlot(@NotNull EquipmentSlot slot) {
            return switch (slot) {
                case CHEST -> 8;
                case HEAD, FEET -> 3;
                case LEGS -> 6;
                default -> 0;
            };
        }

        public int getEnchantmentValue() {
            return 25;
        }

        public @NotNull SoundEvent getEquipSound() {
            return ArmorMaterials.GOLD.getEquipSound();
        }

        @Override
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(HexItem.HEXED_INGOT.get()));
        }

        public @NotNull String getName() {
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

