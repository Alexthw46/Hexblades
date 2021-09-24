package Alexthw.Hexblades.common.items.armors;

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
        newbox.setRotationPoint(box.rotationPointX, box.rotationPointY, box.rotationPointZ);
        this.setRotationAngle(newbox, box.rotateAngleX, box.rotateAngleY, box.rotateAngleZ);
        newbox.mirror = box.mirror;
        newbox.showModel = box.showModel;
        return newbox;
    }

    public ArmorModel(EquipmentSlotType slot, int texWidth, int texHeight) {
        this(slot, 0.0F, texWidth, texHeight);
    }

    public ArmorModel(EquipmentSlotType slot, float size, int texWidth, int texHeight) {
        super(size, 0.0F, texWidth, texHeight);
        this.slot = slot;
        this.bipedHead = this.copyWithoutBoxes(this.bipedHead);
        this.bipedBody = this.copyWithoutBoxes(this.bipedBody);
        this.bipedLeftArm = this.copyWithoutBoxes(this.bipedLeftArm);
        this.bipedLeftLeg = this.copyWithoutBoxes(this.bipedLeftLeg);
        this.bipedRightArm = this.copyWithoutBoxes(this.bipedRightArm);
        this.bipedRightLeg = this.copyWithoutBoxes(this.bipedRightLeg);
    }

    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.bipedHeadwear.showModel = false;
        this.bipedBody.showModel = this.bipedLeftArm.showModel = this.bipedRightArm.showModel = this.bipedHead.showModel = this.bipedLeftLeg.showModel = this.bipedRightLeg.showModel = false;
        if (this.slot == EquipmentSlotType.CHEST) {
            this.bipedBody.showModel = true;
            this.bipedLeftArm.showModel = true;
            this.bipedRightArm.showModel = true;
        }

        if (this.slot == EquipmentSlotType.HEAD) {
            this.bipedHead.showModel = true;
        }

        if (this.slot == EquipmentSlotType.FEET) {
            this.bipedLeftLeg.showModel = true;
            this.bipedRightLeg.showModel = true;
        }

        if (this.slot == EquipmentSlotType.LEGS) {
            this.bipedBody.showModel = true;
            this.bipedLeftLeg.showModel = true;
            this.bipedRightLeg.showModel = true;
        }

        super.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}

