package Alexthw.Hexblades.registers;

import Alexthw.Hexblades.Hexblades;
import Alexthw.Hexblades.common.blocks.tile_entities.SwordStandBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static Alexthw.Hexblades.registers.HexItem.addTabProp;

public class HexBlock {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Hexblades.MOD_ID);


    public static final RegistryObject<Block> DEV_BLOCK;
    public static final RegistryObject<Block> SWORD_STAND;
    public static DecoBlockPack DARK_POLISH_PLANKS;


    static {
        DEV_BLOCK = addBlock("dev_block",
                new Block(blockProps(Material.ROCK, MaterialColor.BLUE)
                        .sound(SoundType.STONE)
                        .hardnessAndResistance(1.5f, 3.0f)
                        .harvestTool(ToolType.PICKAXE)
                        .harvestLevel(1)
                        .setRequiresTool()
                ));

        DARK_POLISH_PLANKS = (new DecoBlockPack(BLOCKS, "dark_polished_planks", blockProps(Material.WOOD, MaterialColor.BLACK).sound(SoundType.WOOD)
                .harvestTool(ToolType.AXE)
                .hardnessAndResistance(1.6F, 3.0F)))
                .addFence();


        SWORD_STAND = addBlock("sword_stand", new SwordStandBlock(blockProps(Material.ROCK, MaterialColor.RED)
                .notSolid()
        ));
    }

    static net.minecraft.block.AbstractBlock.Properties blockProps(Material mat, MaterialColor color) {
        return net.minecraft.block.AbstractBlock.Properties.create(mat, color);
    }

    public static class DecoBlockPack {
        DeferredRegister<Block> registry;
        String basename;
        net.minecraft.block.AbstractBlock.Properties props;
        RegistryObject<Block> full = null;
        RegistryObject<Block> slab = null;
        RegistryObject<Block> stair = null;
        RegistryObject<Block> wall = null;
        RegistryObject<Block> fence = null;
        RegistryObject<Block> fence_gate = null;

        public DecoBlockPack(DeferredRegister<Block> blocks, String basename, net.minecraft.block.AbstractBlock.Properties props) {
            this.registry = blocks;
            this.basename = basename;
            this.props = props;
            this.full = addBlock(basename, new Block(props));
            this.slab = addBlock(basename + "_slab", new SlabBlock(props));
            this.stair = addBlock(basename + "_stairs", new StairsBlock(
                    () -> this.full.get().getDefaultState(), props));
        }

        public DecoBlockPack addWall() {
            this.wall = addBlock(this.basename + "_wall", new WallBlock(this.props));
            return this;
        }

        public DecoBlockPack addFence() {
            this.fence = addBlock(this.basename + "_fence", new FenceBlock(this.props));
            this.fence_gate = addBlock(this.basename + "_fence_gate", new FenceGateBlock(this.props));
            return this;
        }

        public Block getBlock() {
            return this.full.get();
        }

        public Block getSlab() {
            return this.slab.get();
        }

        public Block getStairs() {
            return this.stair.get();
        }

        public Block getWall() {
            return this.wall.get();
        }

        public Block getFence() {
            return this.fence.get();
        }

        public Block getFenceGate() {
            return this.fence_gate.get();
        }

    }

    static RegistryObject<Block> addBlock(String name, Block block) {
        HexItem.ITEMS.register(name, () -> new BlockItem(block, addTabProp()));
        return BLOCKS.register(name, () -> block);
    }

}
