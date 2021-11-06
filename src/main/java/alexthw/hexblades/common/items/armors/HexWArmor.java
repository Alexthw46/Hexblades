package alexthw.hexblades.common.items.armors;

import alexthw.hexblades.registers.HexItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

import static alexthw.hexblades.util.Constants.ArmorCompat.focusTag;

public class HexWArmor extends ArmorItem {

    public HexWArmor(EquipmentSlotType slot, Properties builderIn) {
        super(HexWArmor.Material.INSTANCE, slot, builderIn);
    }

    @Override
    public void onCraftedBy(ItemStack pStack, World pLevel, PlayerEntity pPlayer) {
        super.onCraftedBy(pStack, pLevel, pPlayer);
    }

    public String getFocus(ItemStack stack) {
        String focus = stack.getOrCreateTag().getString(focusTag);
        return focus.equals("") ? "none" : focus;
    }

    public void setFocus(ItemStack stack, String focus) {

        CompoundNBT tag = stack.getOrCreateTag();

        tag.putString(focusTag, focus);

        stack.setTag(tag);

    }

    public static int getFocusId(ItemStack stack) {
        String focus = stack.getOrCreateTag().getString(focusTag);
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

    HexWArmorModel model = null;

    @Override
    @OnlyIn(Dist.CLIENT)
    public HexWArmorModel getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
        if (this.model == null) {
            this.model = slot == EquipmentSlotType.LEGS ? new HexWArmorModel(slot, 0.5F) : new HexWArmorModel(slot, 1.0F);
        }

        float pticks = Minecraft.getInstance().getFrameTime();
        float f = MathHelper.rotLerp(pticks, entity.yBodyRotO, entity.yBodyRot);
        float f1 = MathHelper.rotLerp(pticks, entity.yHeadRotO, entity.yHeadRot);
        float netHeadYaw = f1 - f;
        float netHeadPitch = MathHelper.lerp(pticks, entity.xRotO, entity.xRot);
        this.model.setupAnim(entity, entity.animationPosition, entity.animationSpeed, (float) entity.tickCount + pticks, netHeadYaw, netHeadPitch);
        return this.model;
    }

    @OnlyIn(Dist.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return "hexblades:textures/entity/silver_armor.png";
    }

    public static class Material implements IArmorMaterial {
        public static final HexWArmor.Material INSTANCE = new HexWArmor.Material();

        public Material() {
        }

        public int getDurabilityForSlot(EquipmentSlotType slot) {
            return HexWArmor.MAX_DAMAGE_ARRAY[slot.getIndex()] * 21;
        }

        public int getDefenseForSlot(EquipmentSlotType slot) {
            switch (slot) {
                case CHEST:
                    return 7;
                case HEAD:
                    return 3;
                case FEET:
                    return 2;
                default:
                    return 0;
            }
        }

        public int getEnchantmentValue() {
            return 25;
        }

        public SoundEvent getEquipSound() {
            return ArmorMaterial.LEATHER.getEquipSound();
        }

        public Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(HexItem.HEXIUM_INGOT.get()));
        }

        public String getName() {
            return "hexblades:test_armor";
        }

        public float getToughness() {
            return 0.0F;
        }

        public float getKnockbackResistance() {
            return 0.0F;
        }
    }
}

