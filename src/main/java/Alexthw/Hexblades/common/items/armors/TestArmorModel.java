package Alexthw.Hexblades.common.items.armors;

import elucent.eidolon.item.model.ArmorModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;

public class TestArmorModel extends ArmorModel {


        private ModelRenderer chest = null;
        private ModelRenderer rightArm = null;
        private ModelRenderer leftArm = null;
        private ModelRenderer head = null;
        private ModelRenderer guard = null;
        private ModelRenderer leftBoot = null;
        private ModelRenderer rightBoot = null;
        private ModelRenderer leftLeg = null;
        private ModelRenderer rightLeg = null;
        private ModelRenderer legs = null;

        public TestArmorModel(EquipmentSlotType slot, float size) {
            super(slot, 0.0F, 64, 64);
            if (slot == EquipmentSlotType.CHEST) {
                this.chest = new ModelRenderer(this);
                this.chest.setRotationPoint(0.0F, 12.0F, 0.0F);
                this.chest.setTextureOffset(16, 16).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, size, false);
                this.bipedBody.addChild(this.chest);
                this.rightArm = new ModelRenderer(this);
                this.rightArm.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.bipedRightArm.addChild(this.rightArm);
                this.rightArm.setTextureOffset(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 5.0F, 4.0F, size, true);
                this.leftArm = new ModelRenderer(this);
                this.leftArm.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.bipedLeftArm.addChild(this.leftArm);
                this.leftArm.setTextureOffset(40, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 5.0F, 4.0F, size, false);
            }

            if (slot == EquipmentSlotType.HEAD) {
                this.head = new ModelRenderer(this);
                this.bipedHead.setRotationPoint(0.0F, -12.0F, 0.0F);
                this.bipedHead.addChild(this.head);
                this.head.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, size, false);
                this.guard = new ModelRenderer(this);
                this.guard.setRotationPoint(0.0F, -3.0F, 0.0F);
                this.bipedHead.addChild(this.guard);
                this.setRotationAngle(this.guard, 0.0F, 0.7854F, 0.0F);
                this.guard.setTextureOffset(6, 41).addBox(-5.0F, -6.0F, -5.5F, 10.0F, 10.0F, 10.0F, size, false);
            }

            if (slot == EquipmentSlotType.FEET) {
                this.leftBoot = new ModelRenderer(this);
                this.leftBoot.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.bipedLeftLeg.addChild(this.leftBoot);
                this.leftBoot.setTextureOffset(0, 22).addBox(-2.0F, 6.0F, -2.0F, 4.0F, 6.0F, 4.0F, size, true);
                this.rightBoot = new ModelRenderer(this);
                this.rightBoot.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.bipedRightLeg.addChild(this.rightBoot);
                this.rightBoot.setTextureOffset(0, 22).addBox(-2.0F, 6.0F, -2.0F, 4.0F, 6.0F, 4.0F, size, false);
            }

            if (slot == EquipmentSlotType.LEGS) {
                this.leftLeg = new ModelRenderer(this);
                this.leftLeg.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.bipedLeftLeg.addChild(this.leftLeg);
                this.leftLeg.setTextureOffset(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, size, false);
                this.rightLeg = new ModelRenderer(this);
                this.rightLeg.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.bipedRightLeg.addChild(this.rightLeg);
                this.rightLeg.setTextureOffset(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, size, true);
                this.legs = new ModelRenderer(this);
                this.legs.setRotationPoint(0.0F, 7.0F, 0.0F);
                this.bipedBody.addChild(this.legs);
                this.legs.setTextureOffset(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 5.0F, 4.0F, size, false);
            }

        }

        public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
            modelRenderer.rotateAngleX = x;
            modelRenderer.rotateAngleY = y;
            modelRenderer.rotateAngleZ = z;
        }
    }

