package alexthw.hexblades.compat;

import alexthw.hexblades.registers.HexTags;
import com.github.klikli_dev.occultism.common.block.SpiritFireBlock;
import com.github.klikli_dev.occultism.registry.OccultismBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;

import java.util.function.Predicate;

import static alexthw.hexblades.util.CompatUtil.*;
import static elucent.eidolon.tile.CrucibleTileEntity.HOT_BLOCKS;


public class CrucibleCompatHandler {

    public static void start() {

        HOT_BLOCKS = new Predicate<?>[]{
                (BlockState b) -> b.getBlock() instanceof CampfireBlock && b.getValue(CampfireBlock.LIT),
                (BlockState b) -> b.getBlock().is(HexTags.Blocks.CRUCIBLE_HOT_BLOCKS),
                (BlockState b) -> checkCompats(b.getBlock())
        };

    }

    public static boolean checkCompats(Block block) {

        if (isMalumLoaded()) {
            return MalumCompat.checkCrucible(block);
        }
        if (isOccultismLoaded()) {
            return block == OccultismBlocks.SPIRIT_FIRE.get();
        }
        if (isDruidLoaded()) {
            return block instanceof SpiritFireBlock;
        }

        return false;
    }

}
