package alexthw.hexblades.common.items.armors;


import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class HexWArmorModel extends ArmorModel<LivingEntity> {


    public HexWArmorModel(EquipmentSlotType slot, float size) {
        super(slot, 0.0F, 64, 64);

        if (slot == EquipmentSlotType.CHEST) {
            ModelRenderer chest = new ModelRenderer(this);
            chest.setPos(0.0F, 12.0F, 0.0F);
            chest.texOffs(16, 16).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, size, false);
            this.body.addChild(chest);
            ModelRenderer rightArm = new ModelRenderer(this);
            rightArm.setPos(0.0F, 0.0F, 0.0F);
            this.rightArm.addChild(rightArm);
            rightArm.texOffs(40, 16).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 5.0F, 4.0F, size, true);
            ModelRenderer leftArm = new ModelRenderer(this);
            leftArm.setPos(0.0F, 0.0F, 0.0F);
            this.leftArm.addChild(leftArm);
            leftArm.texOffs(40, 16).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 5.0F, 4.0F, size, false);
        }
        if (slot == EquipmentSlotType.HEAD) {
            ModelRenderer head = new ModelRenderer(this);
            this.head.setPos(0.0F, -12.0F, 0.0F);
            this.head.addChild(head);
            head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, size, false);
            ModelRenderer guard = new ModelRenderer(this);
            guard.setPos(0.0F, -3.0F, 0.0F);
            this.head.addChild(guard);
            this.setRotationAngle(guard, 0.0F, 0.7854F, 0.0F);
            guard.texOffs(6, 41).addBox(-5.0F, -6.0F, -5.5F, 10.0F, 10.0F, 10.0F, size, false);
        }

        if (slot == EquipmentSlotType.FEET) {
            ModelRenderer leftBoot = new ModelRenderer(this);
            leftBoot.setPos(0.0F, 0.0F, 0.0F);
            this.leftLeg.addChild(leftBoot);
            leftBoot.texOffs(0, 22).addBox(-2.0F, 6.0F, -2.0F, 4.0F, 6.0F, 4.0F, size, true);
            ModelRenderer rightBoot = new ModelRenderer(this);
            rightBoot.setPos(0.0F, 0.0F, 0.0F);
            this.rightLeg.addChild(rightBoot);
            rightBoot.texOffs(0, 22).addBox(-2.0F, 6.0F, -2.0F, 4.0F, 6.0F, 4.0F, size, false);
        }

        if (slot == EquipmentSlotType.LEGS) {
            ModelRenderer leftLeg = new ModelRenderer(this);
            leftLeg.setPos(0.0F, 0.0F, 0.0F);
            this.leftLeg.addChild(leftLeg);
            leftLeg.texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, size, false);
            ModelRenderer rightLeg = new ModelRenderer(this);
            rightLeg.setPos(0.0F, 0.0F, 0.0F);
            this.rightLeg.addChild(rightLeg);
            rightLeg.texOffs(0, 32).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, size, true);
            ModelRenderer legs = new ModelRenderer(this);
            legs.setPos(0.0F, 7.0F, 0.0F);
            this.body.addChild(legs);
            legs.texOffs(16, 32).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 5.0F, 4.0F, size, false);
        }

    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}

