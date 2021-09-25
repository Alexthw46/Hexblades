package Alexthw.Hexblades.client.render.models;// Made with Blockbench 3.9.3
// Exported for Minecraft version 1.15 - 1.16 with MCP mappings
// Paste this class into your mod and generate all required imports


import Alexthw.Hexblades.Hexblades;
import Alexthw.Hexblades.common.entity.FireElementalEntity;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FireElementalModel extends AnimatedGeoModel<FireElementalEntity> {

    @Override
    public ResourceLocation getModelLocation(FireElementalEntity ElementalEntity) {
        return new ResourceLocation(Hexblades.MOD_ID, "geo/" + "fire_elemental.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(FireElementalEntity ElementalEntity) {
        return new ResourceLocation(Hexblades.MOD_ID, "textures/entity/" + "fire_elemental.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(FireElementalEntity ElementalEntity) {
        return new ResourceLocation(Hexblades.MOD_ID, "animations/animation.hexblades.fe.json");
    }

}