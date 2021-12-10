package alexthw.hexblades.datagen;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.registers.HexBlock;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import static alexthw.hexblades.registers.HexBlock.BLOCKS;
import static net.minecraft.tags.BlockTags.*;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;
import org.jetbrains.annotations.NotNull;

public class HexBlockTagsProvider extends BlockTagsProvider {

    public HexBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, Hexblades.MODID, existingFileHelper);
    }

    //TODO BLOCK TOOLS

    @Override
    protected void addTags() {

        tag(PLANKS).add(HexBlock.DARK_POLISH_PLANKS.getBlock());
        tag(BlockTags.SLABS).add(getModBlocks(b -> b instanceof SlabBlock));
        tag(BlockTags.STAIRS).add(getModBlocks(b -> b instanceof StairBlock));
        tag(BlockTags.FENCES).add(getModBlocks(b -> b instanceof FenceBlock));
        tag(BlockTags.FENCE_GATES).add(getModBlocks(b -> b instanceof FenceGateBlock));
        tag(WOODEN_SLABS).add(HexBlock.DARK_POLISH_PLANKS.getSlab());
        tag(WOODEN_STAIRS).add(HexBlock.DARK_POLISH_PLANKS.getStairs());
        tag(WOODEN_FENCES).add(HexBlock.DARK_POLISH_PLANKS.getFence());
        addDecoBlockTags(HexBlock.DARK_POLISH_PLANKS, MINEABLE_WITH_AXE);
        addPickMineable(0, HexBlock.EVERFULL_URN.get(), HexBlock.SWORD_STAND.get());
        addPickMineable(1, HexBlock.MAGMA_BRICKS.get());
    }

    void addDecoBlockTags(HexBlock.DecoBlockPack deco, Tag.Named<Block> tag) {
        tag(tag).add(deco.getBlock());
        tag(tag).add(deco.getSlab());
        tag(tag).add(deco.getStairs());
        tag(tag).add(deco.getFence());
        tag(tag).add(deco.getFenceGate());
    }

    void addPickMineable(int level,Block...blocks){
        for (Block block : blocks){
            tag(MINEABLE_WITH_PICKAXE).add(block);
            switch (level) {
                case (1) -> tag(NEEDS_STONE_TOOL).add(block);
                case (2) -> tag(NEEDS_IRON_TOOL).add(block);
                case (3) -> tag(NEEDS_DIAMOND_TOOL).add(block);
                default -> {
                }
            }
        }

    }


    @Nonnull
    private Block[] getModBlocks(Predicate<Block> predicate) {
        List<Block> ret = new ArrayList<>(Collections.emptyList());
        BLOCKS.getEntries().stream()
                .filter(b -> predicate.test(b.get())).forEach(b -> ret.add(b.get()));
        return ret.toArray(new Block[0]);
    }

    @Override
    public @NotNull String getName() {
        return "HexBlades Block Tags";
    }

}
