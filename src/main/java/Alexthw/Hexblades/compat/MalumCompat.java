package Alexthw.Hexblades.compat;

import com.sammy.malum.common.blocks.lighting.EtherBlock;
import elucent.eidolon.spell.AltarEntries;
import elucent.eidolon.spell.AltarEntry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;

import java.util.Map;
import java.util.function.Predicate;

import static elucent.eidolon.tile.CrucibleTileEntity.HOT_BLOCKS;
import static net.minecraftforge.fml.common.ObfuscationReflectionHelper.getPrivateValue;

public class MalumCompat {

    public static void start() {

        AltarEntries tmp = new AltarEntries();

        Map<BlockState, AltarEntry> entriesC = getPrivateValue(elucent.eidolon.spell.AltarEntries.class, tmp, "entries");

        if (entriesC != null) {
            // entriesC.put(Blocks.TORCH.getDefaultState(), (new AltarEntry(AltarKeys.LIGHT_KEY)).setPower(1.0D));
        }

        HOT_BLOCKS = new Predicate<?>[]{
                (BlockState b) -> b.getBlock() == Blocks.MAGMA_BLOCK,
                (BlockState b) -> b.getBlock() == Blocks.FIRE,
                (BlockState b) -> b.getBlock() == Blocks.SOUL_FIRE,
                (BlockState b) -> b.getBlock() == Blocks.LAVA,
                (BlockState b) -> b.getBlock() == Blocks.CAMPFIRE && b.get(CampfireBlock.LIT),
                (BlockState b) -> b.getBlock() == Blocks.SOUL_CAMPFIRE && b.get(CampfireBlock.LIT),
                (BlockState b) -> b.getBlock() instanceof EtherBlock
        };

    }


}
