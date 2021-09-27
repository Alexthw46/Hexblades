package alexthw.hexblades.common.items.armors;

import alexthw.hexblades.registers.HexItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class TestArmor extends ArmorItem {
    public TestArmor(EquipmentSlotType slot, Properties builderIn) {
        super(TestArmor.Material.INSTANCE,slot, builderIn);
    }

    private static final int[] MAX_DAMAGE_ARRAY = new int[]{13, 15, 16, 11};


    TestArmorModel model = null;

    @Override
    @OnlyIn(Dist.CLIENT)
    public TestArmorModel getArmorModel(LivingEntity entity, ItemStack stack, EquipmentSlotType slot, BipedModel defaultModel) {
        if (this.model == null) {
            this.model = slot == EquipmentSlotType.LEGS ? new TestArmorModel(slot, 0.5F) : new TestArmorModel(slot, 1.0F);
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
        return "eidolon:textures/entity/silver_armor.png";
    }

    public static class Material implements IArmorMaterial {
        public static final TestArmor.Material INSTANCE = new TestArmor.Material();

        public Material() {
        }

        public int getDurabilityForSlot(EquipmentSlotType slot) {
            return TestArmor.MAX_DAMAGE_ARRAY[slot.getIndex()] * 21;
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

