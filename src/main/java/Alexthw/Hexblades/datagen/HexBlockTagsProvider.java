package Alexthw.Hexblades.datagen;

import Alexthw.Hexblades.Hexblades;
import Alexthw.Hexblades.registers.HexBlock;
import net.minecraft.block.*;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static Alexthw.Hexblades.registers.HexBlock.BLOCKS;
import static net.minecraft.tags.BlockTags.*;

public class HexBlockTagsProvider extends BlockTagsProvider {

    public HexBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, Hexblades.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags() {

        getOrCreateBuilder(PLANKS).add(HexBlock.DARK_POLISH_PLANKS.getBlock());
        getOrCreateBuilder(BlockTags.SLABS).add(getModBlocks(b -> b instanceof SlabBlock));
        getOrCreateBuilder(BlockTags.STAIRS).add(getModBlocks(b -> b instanceof StairsBlock));
        getOrCreateBuilder(BlockTags.FENCES).add(getModBlocks(b -> b instanceof FenceBlock));
        getOrCreateBuilder(BlockTags.FENCE_GATES).add(getModBlocks(b -> b instanceof FenceGateBlock));
        getOrCreateBuilder(WOODEN_SLABS).add(HexBlock.DARK_POLISH_PLANKS.getSlab());
        getOrCreateBuilder(WOODEN_STAIRS).add(HexBlock.DARK_POLISH_PLANKS.getStairs());
        getOrCreateBuilder(WOODEN_FENCES).add(HexBlock.DARK_POLISH_PLANKS.getFence());

    }


    @Nonnull
    private Block[] getModBlocks(Predicate<Block> predicate) {
        List<Block> ret = new ArrayList<>(Collections.emptyList());
        BLOCKS.getEntries().stream()
                .filter(b -> predicate.test(b.get())).forEach(b -> ret.add(b.get()));
        return ret.toArray(new Block[0]);
    }

    @Override
    public String getName() {
        return "HexBlades Item Tags";
    }

}
