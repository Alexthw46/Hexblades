package Alexthw.Hexblades.common.items.armors;

import elucent.eidolon.item.model.ArmorModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.inventory.EquipmentSlotType;

public class TestArmorModel extends ArmorModel {


    public TestArmorModel(EquipmentSlotType slot, float size) {
        super(slot, 0.0F, 64, 64);
        if (slot == EquipmentSlotType.CHEST) {
            ModelRenderer chest = new ModelRenderer(this);
            chest.setRotationPoint(0.0F, 12.0F, 0.0F);
            chest.setTextureOffset(16, 16).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, size, false);
            this.bipedBody.addChild(chest);
            ModelRenderer rightArm = new ModelRenderer(this);
            rightArm.setRotationPoint(0.0F, 0.0F, 0.0F);
            this.bipedRightArm.addChild(rightArm);
            rightArm.setTextureOffset(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 5.0F, 4.0F, size, true);
            ModelRenderer leftArm = new ModelRenderer(this);
            leftArm.setRotationPoint(0.0F, 0.0F, 0.0F);
            this.bipedLeftArm.addChild(leftArm);
            leftArm.setTextureOffset(40, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 5.0F, 4.0F, size, false);
        }

            if (slot == EquipmentSlotType.HEAD) {
                ModelRenderer head = new ModelRenderer(this);
                this.bipedHead.setRotationPoint(0.0F, -12.0F, 0.0F);
                this.bipedHead.addChild(head);
                head.setTextureOffset(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, size, false);
                ModelRenderer guard = new ModelRenderer(this);
                guard.setRotationPoint(0.0F, -3.0F, 0.0F);
                this.bipedHead.addChild(guard);
                this.setRotationAngle(guard, 0.0F, 0.7854F, 0.0F);
                guard.setTextureOffset(6, 41).addBox(-5.0F, -6.0F, -5.5F, 10.0F, 10.0F, 10.0F, size, false);
            }

            if (slot == EquipmentSlotType.FEET) {
                ModelRenderer leftBoot = new ModelRenderer(this);
                leftBoot.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.bipedLeftLeg.addChild(leftBoot);
                leftBoot.setTextureOffset(0, 22).addBox(-2.0F, 6.0F, -2.0F, 4.0F, 6.0F, 4.0F, size, true);
                ModelRenderer rightBoot = new ModelRenderer(this);
                rightBoot.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.bipedRightLeg.addChild(rightBoot);
                rightBoot.setTextureOffset(0, 22).addBox(-2.0F, 6.0F, -2.0F, 4.0F, 6.0F, 4.0F, size, false);
            }

            if (slot == EquipmentSlotType.LEGS) {
                ModelRenderer leftLeg = new ModelRenderer(this);
                leftLeg.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.bipedLeftLeg.addChild(leftLeg);
                leftLeg.setTextureOffset(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, size, false);
                ModelRenderer rightLeg = new ModelRenderer(this);
                rightLeg.setRotationPoint(0.0F, 0.0F, 0.0F);
                this.bipedRightLeg.addChild(rightLeg);
                rightLeg.setTextureOffset(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, size, true);
                ModelRenderer legs = new ModelRenderer(this);
                legs.setRotationPoint(0.0F, 7.0F, 0.0F);
                this.bipedBody.addChild(legs);
                legs.setTextureOffset(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 5.0F, 4.0F, size, false);
            }

        }

        public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
            modelRenderer.rotateAngleX = x;
            modelRenderer.rotateAngleY = y;
            modelRenderer.rotateAngleZ = z;
        }
    }

