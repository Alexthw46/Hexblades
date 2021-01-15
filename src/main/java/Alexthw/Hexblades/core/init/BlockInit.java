package Alexthw.Hexblades.core.init;

import Alexthw.Hexblades.Hexblades;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockInit {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Hexblades.MOD_ID);


    public static final RegistryObject<Block> DEV_BLOCK;
    public static final RegistryObject<Block> DARK_POLISH_PLANKS;



    static {
        DEV_BLOCK = BLOCKS.register("dev_block",
                () -> new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.BLUE)
                        .sound(SoundType.STONE)
                        .hardnessAndResistance(1.5f, 3.0f)
                        .harvestTool(ToolType.PICKAXE)
                        .harvestLevel(1)
                        .setRequiresTool()));

        DARK_POLISH_PLANKS = BLOCKS.register("dark_polished_planks",
                () -> new Block(AbstractBlock.Properties.create(Material.WOOD,MaterialColor.BLACK)
                        .sound(SoundType.WOOD)
                        .hardnessAndResistance(1.0f,2.0f)
                        .harvestTool(ToolType.AXE)));
    }

}
