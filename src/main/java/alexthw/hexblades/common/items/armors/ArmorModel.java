package alexthw.hexblades.common.items.armors;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;

public abstract class ArmorModel<T extends LivingEntity> extends HumanoidModel<T> {
    EquipmentSlot slot;

    public ModelPart copyWithoutBoxes(ModelPart box) {
        ModelPart newbox = new ModelPart(this);
        newbox.setPos(box.x, box.y, box.z);
        this.setRotationAngle(newbox, box.xRot, box.yRot, box.zRot);
        newbox.mirror = box.mirror;
        newbox.visible = box.visible;
        return newbox;
    }

    public ArmorModel(EquipmentSlot slot, int texWidth, int texHeight) {
        this(slot, 0.0F, texWidth, texHeight);
    }

    public ArmorModel(EquipmentSlot slot, float size, int texWidth, int texHeight) {
        super(size, 0.0F, texWidth, texHeight);
        this.slot = slot;
        this.head = this.copyWithoutBoxes(this.head);
        this.body = this.copyWithoutBoxes(this.body);
        this.leftArm = this.copyWithoutBoxes(this.leftArm);
        this.leftLeg = this.copyWithoutBoxes(this.leftLeg);
        this.rightArm = this.copyWithoutBoxes(this.rightArm);
        this.rightLeg = this.copyWithoutBoxes(this.rightLeg);
    }

    public void renderToBuffer(PoseStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.hat.visible = false;
        this.body.visible = this.leftArm.visible = this.rightArm.visible = this.head.visible = this.leftLeg.visible = this.rightLeg.visible = false;
        if (this.slot == EquipmentSlot.CHEST) {
            this.body.visible = true;
            this.leftArm.visible = true;
            this.rightArm.visible = true;
        }

        if (this.slot == EquipmentSlot.HEAD) {
            this.head.visible = true;
        }

        if (this.slot == EquipmentSlot.FEET) {
            this.leftLeg.visible = true;
            this.rightLeg.visible = true;
        }

        if (this.slot == EquipmentSlot.LEGS) {
            this.body.visible = true;
            this.leftLeg.visible = true;
            this.rightLeg.visible = true;
        }

        super.renderToBuffer(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelPart modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}

