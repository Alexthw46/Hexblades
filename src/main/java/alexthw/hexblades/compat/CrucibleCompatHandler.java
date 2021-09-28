package alexthw.hexblades.compat;

import com.github.klikli_dev.occultism.common.block.SpiritFireBlock;
import com.sammy.malum.common.blocks.lighting.EtherBlock;
import com.sammy.malum.common.blocks.lighting.EtherBrazierBlock;
import com.vulp.druidcraft.blocks.SoulfireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;

import java.util.function.Predicate;

import static alexthw.hexblades.util.CompatUtil.*;
import static elucent.eidolon.tile.CrucibleTileEntity.HOT_BLOCKS;


public class CrucibleCompatHandler {

    public static void start() {

        if (!isMalumLoaded() && !isDruidLoaded() && !isOccultismLoaded()) {
            defaultFix();
            return;
        }

        if (isMalumLoaded() && isDruidLoaded() && isOccultismLoaded()) {
            OccMalumDruid();
        } else if (isMalumLoaded() && isDruidLoaded()) {
            MalumDruid();
        } else if (isMalumLoaded() && isOccultismLoaded()) {
            MalumOcc();
        } else if (isMalumLoaded()) {
            MalumCompat.crucible();
        } else if (isDruidLoaded() && isOccultismLoaded()) {
            DruidOcc();
        } else if (isDruidLoaded()) {
            DruidcraftCompat.start();
        } else if (isOccultismLoaded()) {
            OccultismCompat.start();
        }
    }

    private static void defaultFix() {
        HOT_BLOCKS = new Predicate<?>[]{
                (BlockState b) -> b.getBlock() == Blocks.MAGMA_BLOCK,
                (BlockState b) -> b.getBlock() == Blocks.FIRE,
                (BlockState b) -> b.getBlock() == Blocks.SOUL_FIRE,
                (BlockState b) -> b.getBlock() == Blocks.LAVA,
                (BlockState b) -> b.getBlock() instanceof CampfireBlock && b.getValue(CampfireBlock.LIT)
        };
    }

    private static void MalumDruid() {
        HOT_BLOCKS = new Predicate<?>[]{
                (BlockState b) -> b.getBlock() == Blocks.MAGMA_BLOCK,
                (BlockState b) -> b.getBlock() == Blocks.FIRE,
                (BlockState b) -> b.getBlock() == Blocks.SOUL_FIRE,
                (BlockState b) -> b.getBlock() == Blocks.LAVA,
                (BlockState b) -> b.getBlock() instanceof CampfireBlock && b.getValue(CampfireBlock.LIT),
                (BlockState b) -> b.getBlock() instanceof EtherBlock,
                (BlockState b) -> b.getBlock() instanceof EtherBrazierBlock,
                (BlockState b) -> b.getBlock() instanceof SoulfireBlock
        };
    }

    private static void MalumOcc() {
        HOT_BLOCKS = new Predicate<?>[]{
                (BlockState b) -> b.getBlock() == Blocks.MAGMA_BLOCK,
                (BlockState b) -> b.getBlock() == Blocks.FIRE,
                (BlockState b) -> b.getBlock() == Blocks.SOUL_FIRE,
                (BlockState b) -> b.getBlock() == Blocks.LAVA,
                (BlockState b) -> b.getBlock() instanceof CampfireBlock && b.getValue(CampfireBlock.LIT),
                (BlockState b) -> b.getBlock() instanceof EtherBlock,
                (BlockState b) -> b.getBlock() instanceof EtherBrazierBlock,
                (BlockState b) -> b.getBlock() instanceof SpiritFireBlock
        };
    }

    private static void DruidOcc() {
        HOT_BLOCKS = new Predicate<?>[]{
                (BlockState b) -> b.getBlock() == Blocks.MAGMA_BLOCK,
                (BlockState b) -> b.getBlock() == Blocks.FIRE,
                (BlockState b) -> b.getBlock() == Blocks.SOUL_FIRE,
                (BlockState b) -> b.getBlock() == Blocks.LAVA,
                (BlockState b) -> b.getBlock() instanceof CampfireBlock && b.getValue(CampfireBlock.LIT),
                (BlockState b) -> b.getBlock() instanceof SoulfireBlock,
                (BlockState b) -> b.getBlock() instanceof SpiritFireBlock
        };
    }

    private static void OccMalumDruid() {
        HOT_BLOCKS = new Predicate<?>[]{
                (BlockState b) -> b.getBlock() == Blocks.MAGMA_BLOCK,
                (BlockState b) -> b.getBlock() == Blocks.FIRE,
                (BlockState b) -> b.getBlock() == Blocks.SOUL_FIRE,
                (BlockState b) -> b.getBlock() == Blocks.LAVA,
                (BlockState b) -> b.getBlock() instanceof CampfireBlock && b.getValue(CampfireBlock.LIT),
                (BlockState b) -> b.getBlock() instanceof EtherBlock,
                (BlockState b) -> b.getBlock() instanceof EtherBrazierBlock,
                (BlockState b) -> b.getBlock() instanceof SoulfireBlock,
                (BlockState b) -> b.getBlock() instanceof SpiritFireBlock
        };
    }
}
