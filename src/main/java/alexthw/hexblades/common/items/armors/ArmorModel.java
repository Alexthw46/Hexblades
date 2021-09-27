package alexthw.hexblades.common.items.armors;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;

public abstract class ArmorModel<T extends LivingEntity> extends BipedModel<T> {
    EquipmentSlotType slot;

    public ModelRenderer copyWithoutBoxes(ModelRenderer box) {
        ModelRenderer newbox = new ModelRenderer(this);
        newbox.setPos(box.x, box.y, box.z);
        this.setRotationAngle(newbox, box.xRot, box.yRot, box.zRot);
        newbox.mirror = box.mirror;
        newbox.visible = box.visible;
        return newbox;
    }

    public ArmorModel(EquipmentSlotType slot, int texWidth, int texHeight) {
        this(slot, 0.0F, texWidth, texHeight);
    }

    public ArmorModel(EquipmentSlotType slot, float size, int texWidth, int texHeight) {
        super(size, 0.0F, texWidth, texHeight);
        this.slot = slot;
        this.head = this.copyWithoutBoxes(this.head);
        this.body = this.copyWithoutBoxes(this.body);
        this.leftArm = this.copyWithoutBoxes(this.leftArm);
        this.leftLeg = this.copyWithoutBoxes(this.leftLeg);
        this.rightArm = this.copyWithoutBoxes(this.rightArm);
        this.rightLeg = this.copyWithoutBoxes(this.rightLeg);
    }

    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.hat.visible = false;
        this.body.visible = this.leftArm.visible = this.rightArm.visible = this.head.visible = this.leftLeg.visible = this.rightLeg.visible = false;
        if (this.slot == EquipmentSlotType.CHEST) {
            this.body.visible = true;
            this.leftArm.visible = true;
            this.rightArm.visible = true;
        }

        if (this.slot == EquipmentSlotType.HEAD) {
            this.head.visible = true;
        }

        if (this.slot == EquipmentSlotType.FEET) {
            this.leftLeg.visible = true;
            this.rightLeg.visible = true;
        }

        if (this.slot == EquipmentSlotType.LEGS) {
            this.body.visible = true;
            this.leftLeg.visible = true;
            this.rightLeg.visible = true;
        }

        super.renderToBuffer(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}

