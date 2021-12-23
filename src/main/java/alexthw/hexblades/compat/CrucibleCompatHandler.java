package alexthw.hexblades.compat;

import alexthw.hexblades.registers.HexTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;

import java.util.function.Predicate;

import static elucent.eidolon.tile.CrucibleTileEntity.HOT_BLOCKS;


public class CrucibleCompatHandler {

    public static void start() {

        HOT_BLOCKS = new Predicate<?>[]{
                (BlockState b) -> b.getBlock().is(HexTags.Blocks.CRUCIBLE_HOT_BLOCKS),
                (BlockState b) -> b.getBlock() instanceof CampfireBlock && b.getValue(CampfireBlock.LIT)
        };

    }

}
