package alexthw.hexblades.compat;

import com.sammy.malum.common.blocks.lighting.EtherBlock;
import com.sammy.malum.common.blocks.lighting.EtherBrazierBlock;
import com.vulp.druidcraft.blocks.SoulfireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;

import java.util.function.Predicate;

import static alexthw.hexblades.util.CompatUtil.isDruidLoaded;
import static alexthw.hexblades.util.CompatUtil.isMalumLoaded;
import static elucent.eidolon.tile.CrucibleTileEntity.HOT_BLOCKS;


public class CrucibleHandler {

    static Predicate<?>[] NEW_HOT_BLOCKS;

    public static void start() {

        if (isMalumLoaded() && isDruidLoaded()) {
            MalumDruid();
        } else if (isMalumLoaded()) {
            MalumCompat.crucible();
        } else {
            DruidcraftCompat.start();
        }

    }

    private static void MalumDruid() {
        NEW_HOT_BLOCKS = new Predicate<?>[]{
                (BlockState b) -> b.getBlock() == Blocks.MAGMA_BLOCK,
                (BlockState b) -> b.getBlock() == Blocks.FIRE,
                (BlockState b) -> b.getBlock() == Blocks.SOUL_FIRE,
                (BlockState b) -> b.getBlock() == Blocks.LAVA,
                (BlockState b) -> b.getBlock() == Blocks.CAMPFIRE && b.getValue(CampfireBlock.LIT),
                (BlockState b) -> b.getBlock() == Blocks.SOUL_CAMPFIRE && b.getValue(CampfireBlock.LIT),
                (BlockState b) -> b.getBlock() instanceof EtherBlock,
                (BlockState b) -> b.getBlock() instanceof EtherBrazierBlock,
                (BlockState b) -> b.getBlock() instanceof SoulfireBlock
        };
        HOT_BLOCKS = NEW_HOT_BLOCKS;
    }
}
