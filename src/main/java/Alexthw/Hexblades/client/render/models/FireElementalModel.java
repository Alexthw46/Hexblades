package Alexthw.Hexblades.client.render.models;// Made with Blockbench 3.9.3
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import Alexthw.Hexblades.common.entity.BaseElementalEntity;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class FireElementalModel extends BaseElementalModel {
    private final ModelRenderer master;
    private final ModelRenderer torso;
    private final ModelRenderer flame_bott;
    private final ModelRenderer r_arm;
    private final ModelRenderer l_arm;
    private final ModelRenderer head;
    private final ModelRenderer horns;

    public FireElementalModel() {
        textureWidth = 128;
        textureHeight = 64;

        master = new ModelRenderer(this);
        master.setRotationPoint(0.0F, 3.0F, 0.0F);


        torso = new ModelRenderer(this);
        torso.setRotationPoint(0.0F, 4.0F, -0.3333F);
        master.addChild(torso);
        torso.setTextureOffset(0, 0).addBox(-7.5F, -14.0F, -7.6667F, 15.0F, 7.0F, 15.0F, 0.0F, false);
        torso.setTextureOffset(0, 22).addBox(-4.5F, -7.0F, -6.1667F, 9.0F, 7.0F, 12.0F, 0.0F, false);

        flame_bott = new ModelRenderer(this);
        flame_bott.setRotationPoint(0.0F, 0.5F, -0.6667F);
        torso.addChild(flame_bott);
        flame_bott.setTextureOffset(32, 22).addBox(-3.0F, -0.5F, -3.0F, 6.0F, 1.0F, 6.0F, 0.0F, false);


        r_arm = new ModelRenderer(this);
        r_arm.setRotationPoint(-7.5F, -6.0F, 0.0F);
        master.addChild(r_arm);
        r_arm.setTextureOffset(40, 43).addBox(-8.0F, -2.0F, -3.0F, 6.0F, 14.0F, 6.0F, 0.0F, false);

        l_arm = new ModelRenderer(this);
        l_arm.setRotationPoint(7.5F, -6.0F, 0.0F);
        master.addChild(l_arm);
        l_arm.setTextureOffset(40, 43).addBox(2.0F, -2.0F, -3.0F, 6.0F, 14.0F, 6.0F, 0.0F, true);

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, -10.75F, 0.0F);
        master.addChild(head);
        head.setTextureOffset(0, 41).addBox(-5.0F, -9.25F, -5.0F, 10.0F, 9.0F, 10.0F, 0.0F, false);

        horns = new ModelRenderer(this);
        horns.setRotationPoint(0.0F, -7.25F, 0.0F);
        head.addChild(horns);


        ModelRenderer horn2 = new ModelRenderer(this);
        horn2.setRotationPoint(-5.125F, -4.625F, 0.0F);
        horns.addChild(horn2);
        setRotationAngle(horn2, 0.0F, 0.0F, -0.0873F);


        ModelRenderer cube_r2 = new ModelRenderer(this);
        cube_r2.setRotationPoint(-1.375F, -1.875F, 0.0F);
        horn2.addChild(cube_r2);
        setRotationAngle(cube_r2, 0.0F, 0.0F, -0.3927F);
        cube_r2.setTextureOffset(2, 9).addBox(-1.0F, 0.5F, -0.5F, 1.0F, 2.0F, 1.0F, 0.0F, false);

        ModelRenderer cube_r3 = new ModelRenderer(this);
        cube_r3.setRotationPoint(-0.8454F, -1.4008F, 0.0F);
        horn2.addChild(cube_r3);
        setRotationAngle(cube_r3, 0.0F, 0.0F, -0.3927F);
        cube_r3.setTextureOffset(0, 12).addBox(-1.5296F, 1.0258F, -1.0F, 2.0F, 1.0F, 2.0F, 0.0F, false);

        ModelRenderer cube_r4 = new ModelRenderer(this);
        cube_r4.setRotationPoint(0.5818F, -0.4555F, -1.0F);
        horn2.addChild(cube_r4);
        setRotationAngle(cube_r4, 0.0F, 0.0F, -0.3927F);
        cube_r4.setTextureOffset(0, 0).addBox(-2.9568F, 0.5805F, -0.5F, 3.0F, 2.0F, 3.0F, 0.0F, false);

        ModelRenderer cube_r5 = new ModelRenderer(this);
        cube_r5.setRotationPoint(0.625F, 1.125F, -0.5F);
        horn2.addChild(cube_r5);
        setRotationAngle(cube_r5, 0.0F, 0.0F, -0.3491F);
        cube_r5.setTextureOffset(45, 0).addBox(-2.5F, 0.5F, -1.5F, 4.0F, 3.0F, 4.0F, 0.0F, false);

        ModelRenderer horn = new ModelRenderer(this);
        horn.setRotationPoint(4.25F, -3.5F, 0.0F);
        horns.addChild(horn);
        setRotationAngle(horn, 0.0F, 0.0F, 0.0873F);


        ModelRenderer cube_r6 = new ModelRenderer(this);
        cube_r6.setRotationPoint(0.2329F, -1.2385F, -1.0F);
        horn.addChild(cube_r6);
        setRotationAngle(cube_r6, 0.0F, 0.0F, 0.3491F);
        cube_r6.setTextureOffset(0, 0).addBox(-0.9829F, 0.2385F, -0.5F, 3.0F, 2.0F, 3.0F, 0.0F, false);

        ModelRenderer cube_r7 = new ModelRenderer(this);
        cube_r7.setRotationPoint(-0.75F, 0.0F, -0.5F);
        horn.addChild(cube_r7);
        setRotationAngle(cube_r7, 0.0F, 0.0F, 0.3491F);
        cube_r7.setTextureOffset(45, 0).addBox(-1.0F, 0.5F, -1.5F, 4.0F, 3.0F, 4.0F, 0.0F, false);
    }

    @Override
    public void setRotationAngles(BaseElementalEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.r_arm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
        this.l_arm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.head.rotateAngleX = (float) Math.toRadians(headPitch);
        this.head.rotateAngleY = (float) Math.toRadians(netHeadYaw);
        this.flame_bott.rotateAngleY = 1 + ((ageInTicks % 360L) / 8);

    }

    @Override
    public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        master.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}