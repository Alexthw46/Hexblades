package Alexthw.Hexblades.datagen;

import Alexthw.Hexblades.Hexblades;
import Alexthw.Hexblades.core.init.BlockInit;
import Alexthw.Hexblades.core.util.HexUtils;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static Alexthw.Hexblades.core.util.HexUtils.prefix;

public class HexBlockStateProvider extends BlockStateProvider{

    public HexBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Hexblades.MOD_ID , exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        Set<RegistryObject<Block>> blocks = new HashSet<>(BlockInit.BLOCKS.getEntries());

        Collection<RegistryObject<Block>> slabs = HexUtils.takeAll(blocks, b -> b.get() instanceof SlabBlock);
        blocks.forEach(this::basicBlock);
        slabs.forEach(this::slabBlock);
    }

    public void slabBlock(RegistryObject<Block> blockRegistryObject)
    {
        String name = Registry.BLOCK.getKey(blockRegistryObject.get()).getPath();
        String baseName = name.substring(0, name.length() - 5);
        slabBlock((SlabBlock) blockRegistryObject.get(), prefix(baseName), prefix("block/" + baseName));
    }

    private void basicBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get());
    }
}
