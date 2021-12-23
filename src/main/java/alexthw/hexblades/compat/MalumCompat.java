package alexthw.hexblades.compat;

import alexthw.hexblades.util.HexUtils;
import com.sammy.malum.common.block.ether.EtherBrazierBlock;
import elucent.eidolon.spell.AltarEntries;
import elucent.eidolon.spell.AltarEntry;
import elucent.eidolon.spell.AltarKeys;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import software.bernie.example.registry.BlockRegistry;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MalumCompat {

    public static void altar(){

        AltarEntry ether = new AltarEntry(AltarKeys.LIGHT_KEY).setPower(1.5D).setCapacity(1.5D);

        Set<RegistryObject<Block>> blocks = BlockRegistry.BLOCKS.getEntries().stream().filter(b -> b.get() instanceof EtherBrazierBlock).collect(Collectors.toSet());

        for (RegistryObject<Block> block : blocks) {
            AltarEntries.entries.put(block.get().defaultBlockState(), ether);
        }

    }

}
