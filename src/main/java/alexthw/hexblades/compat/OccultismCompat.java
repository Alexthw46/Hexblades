package alexthw.hexblades.compat;

import com.github.klikli_dev.occultism.common.block.SpiritFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;

import java.util.function.Predicate;

import static elucent.eidolon.tile.CrucibleTileEntity.HOT_BLOCKS;

public class OccultismCompat {

    public static void start() {

        HOT_BLOCKS = new Predicate<?>[]{
                (BlockState b) -> b.getBlock() == Blocks.MAGMA_BLOCK,
                (BlockState b) -> b.getBlock() == Blocks.FIRE,
                (BlockState b) -> b.getBlock() == Blocks.SOUL_FIRE,
                (BlockState b) -> b.getBlock() == Blocks.LAVA,
                (BlockState b) -> b.getBlock() instanceof CampfireBlock && b.getValue(CampfireBlock.LIT),
                (BlockState b) -> b.getBlock() instanceof SpiritFireBlock
        };
    }
}
