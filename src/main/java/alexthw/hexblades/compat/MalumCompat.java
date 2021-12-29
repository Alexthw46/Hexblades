package alexthw.hexblades.compat;

import alexthw.hexblades.mixin.AltarEntryMixin;
import alexthw.hexblades.util.HexUtils;
import com.sammy.malum.common.block.ether.EtherBrazierBlock;
import elucent.eidolon.spell.AltarEntries;
import elucent.eidolon.spell.AltarEntry;
import elucent.eidolon.spell.AltarKeys;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.RegistryObject;
import software.bernie.example.registry.BlockRegistry;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static net.minecraftforge.fml.util.ObfuscationReflectionHelper.getPrivateValue;

public class MalumCompat {

    public static void altar() throws InvocationTargetException, InstantiationException, IllegalAccessException {

        Map<BlockState, AltarEntry> AltarEntriesCopy = getPrivateValue(elucent.eidolon.spell.AltarEntries.class, new AltarEntries(), "entries");

        Constructor<AltarEntry> altarEntryConstructor = ObfuscationReflectionHelper.findConstructor(AltarEntry.class, ResourceLocation.class);

        if (AltarEntriesCopy != null) {

            AltarEntry entry = altarEntryConstructor.newInstance(AltarKeys.LIGHT_KEY);
            entry = ((AltarEntryMixin) entry).callSetCapacity(1.5D);
            entry = ((AltarEntryMixin) entry).callSetPower(1.5D);

            Set<RegistryObject<Block>> blocks = BlockRegistry.BLOCKS.getEntries().stream().filter(b -> b.get() instanceof EtherBrazierBlock).collect(Collectors.toSet());
            for (RegistryObject<Block> block : blocks) {
                AltarEntriesCopy.put(block.get().defaultBlockState(), entry);
            }

        }
        /*
        AltarEntry ether = new AltarEntry(AltarKeys.LIGHT_KEY).setPower(1.5D).setCapacity(1.5D);

        Set<RegistryObject<Block>> blocks = BlockRegistry.BLOCKS.getEntries().stream().filter(b -> b.get() instanceof EtherBrazierBlock).collect(Collectors.toSet());

        for (RegistryObject<Block> block : blocks) {
            AltarEntries.entries.put(block.get().defaultBlockState(), ether);
        }
*/
    }

}
