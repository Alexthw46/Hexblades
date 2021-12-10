package alexthw.hexblades.datagen;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.common.blocks.DecoBlock;
import alexthw.hexblades.registers.HexBlock;
import alexthw.hexblades.util.HexUtils;
import elucent.eidolon.block.BlockBase;
import net.minecraft.data.DataGenerator;
import net.minecraft.core.Registry;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.HashSet;
import java.util.Set;

import static alexthw.hexblades.util.HexUtils.prefix;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("CommentedOutCode")
public class HexBlockStateProvider extends BlockStateProvider{

    public HexBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Hexblades.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        Set<RegistryObject<Block>> blocks = new HashSet<>(HexBlock.BLOCKS.getEntries());
        HexUtils.takeAll(blocks, b -> b.get() instanceof DecoBlock).forEach(this::basicBlock);
        HexUtils.takeAll(blocks, b -> b.get() instanceof SlabBlock).forEach(this::slabBlock);
        HexUtils.takeAll(blocks, b -> b.get() instanceof StairBlock).forEach(this::stairsBlock);
        //HexUtils.takeAll(blocks, b -> b.get() instanceof RotatedPillarBlock).forEach(this::logBlock);
        //HexUtils.takeAll(blocks, b -> b.get() instanceof WallBlock).forEach(this::wallBlock);
        HexUtils.takeAll(blocks, b -> b.get() instanceof FenceBlock).forEach(this::fenceBlock);
        HexUtils.takeAll(blocks, b -> b.get() instanceof FenceGateBlock).forEach(this::fenceGateBlock);
        HexUtils.takeAll(blocks, b -> b.get() instanceof BlockBase);
        blocks.forEach(this::basicBlock);

    }

    public void slabBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 5);
        slabBlock((SlabBlock) blockRegistryObject.get(), prefix(baseName), prefix("block/" + baseName));
    }

    public void fenceGateBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 11);
        fenceGateBlock((FenceGateBlock) blockRegistryObject.get(), prefix("block/" + baseName));
    }

    public void fenceBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 6);
        fenceBlock((FenceBlock) blockRegistryObject.get(), prefix("block/" + baseName));
    }

    public void stairsBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 7);
        stairsBlock((StairBlock) blockRegistryObject.get(), prefix("block/" + baseName));
    }

    private void basicBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get());
    }

    public void glowingBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String glow = name + "_glow";
        ModelFile farmland = models().withExistingParent(name, prefix("block/template_glowing_block")).texture("all", prefix("block/" + name)).texture("particle", prefix("block/" + name)).texture("glow", prefix("block/" + glow));
        getVariantBuilder(blockRegistryObject.get()).forAllStates(s -> ConfiguredModel.builder().modelFile(farmland).build());
    }

    public void rotatedBlock(RegistryObject<Block> blockRegistryObject) {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        ModelFile file = models().cubeAll(name, prefix("block/" + name));

        getVariantBuilder(blockRegistryObject.get()).partialState().modelForState()
                .modelFile(file)
                .nextModel().modelFile(file).rotationY(90)
                .nextModel().modelFile(file).rotationY(180)
                .nextModel().modelFile(file).rotationY(270)
                .addModel();
    }

    @Override
    public @NotNull String getName() {
        return "HexBlades Blockstates";
    }

}
