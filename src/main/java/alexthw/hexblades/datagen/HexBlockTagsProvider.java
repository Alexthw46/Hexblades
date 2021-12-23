package alexthw.hexblades.datagen;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.registers.HexBlock;
import alexthw.hexblades.registers.HexTags;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import com.sammy.malum.common.blocks.lighting.EtherBlock;
import com.sammy.malum.common.blocks.lighting.EtherBrazierBlock;
import com.vulp.druidcraft.blocks.SoulfireBlock;
import net.minecraft.block.*;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static alexthw.hexblades.registers.HexBlock.BLOCKS;
import static net.minecraft.tags.BlockTags.*;

public class HexBlockTagsProvider extends BlockTagsProvider {

    public HexBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, Hexblades.MODID, existingFileHelper);
    }

    @Override
    protected void addTags() {

        tag(PLANKS).add(HexBlock.DARK_POLISH_PLANKS.getBlock());
        tag(BlockTags.SLABS).add(getModBlocks(b -> b instanceof SlabBlock));
        tag(BlockTags.STAIRS).add(getModBlocks(b -> b instanceof StairsBlock));
        tag(BlockTags.FENCES).add(getModBlocks(b -> b instanceof FenceBlock));
        tag(BlockTags.FENCE_GATES).add(getModBlocks(b -> b instanceof FenceGateBlock));
        tag(WOODEN_SLABS).add(HexBlock.DARK_POLISH_PLANKS.getSlab());
        tag(WOODEN_STAIRS).add(HexBlock.DARK_POLISH_PLANKS.getStairs());
        tag(WOODEN_FENCES).add(HexBlock.DARK_POLISH_PLANKS.getFence());

        tag(HexTags.Blocks.CRUCIBLE_HOT_BLOCKS).add(Blocks.MAGMA_BLOCK, Blocks.FIRE, Blocks.SOUL_FIRE, Blocks.LAVA, OccultismBlocks.SPIRIT_FIRE.get());

        ForgeRegistries.BLOCKS.getValues().stream().filter((b) -> b instanceof EtherBlock || b instanceof EtherBrazierBlock || b instanceof SoulfireBlock).forEach(this::addToCrucible);

    }

    private void addToCrucible(Block block) {
        tag(HexTags.Blocks.CRUCIBLE_HOT_BLOCKS).add(block);
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
