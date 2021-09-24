package Alexthw.Hexblades.client.render.models;// Made with Blockbench 3.9.2
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import Alexthw.Hexblades.common.entity.BaseElementalEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class MinionElementalModel extends BaseElementalModel {
    private final ModelRenderer master;
    private final ModelRenderer head;
    private final ModelRenderer left_arm;
    private final ModelRenderer right_arm;

    public MinionElementalModel() {

        textureWidth = 32;
        textureHeight = 32;

        master = new ModelRenderer(this);
        master.setRotationPoint(0.0F, 16.0F, 0.0F);
        master.setTextureOffset(0, 0).addBox(-4.0F, -15.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
        master.setTextureOffset(0, 24).addBox(-3.0F, -7.0F, -3.0F, 6.0F, 1.0F, 6.0F, 0.0F, false);

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, -16.5F, 0.0F);
        master.addChild(head);
        head.setTextureOffset(0, 16).addBox(-3.0F, -0.5F, -3.0F, 6.0F, 2.0F, 6.0F, 0.0F, false);

        left_arm = new ModelRenderer(this);
        left_arm.setRotationPoint(-4.0F, -11.5F, 0.0F);
        master.addChild(left_arm);
        left_arm.setTextureOffset(24, 23).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, false);

        right_arm = new ModelRenderer(this);
        right_arm.setRotationPoint(4.0F, -11.5F, 0.0F);
        master.addChild(right_arm);
        right_arm.setTextureOffset(24, 23).addBox(0.0F, -1.0F, -1.0F, 2.0F, 7.0F, 2.0F, 0.0F, false);
    }


    @Override
    public void setRotationAngles(BaseElementalEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //this.right_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
        //this.left_leg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.right_arm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
        this.left_arm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        //this.master.rotateAngleX = (float) Math.toRadians(headPitch);
        this.head.rotateAngleY = (float) Math.toRadians(netHeadYaw);
    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.master.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}