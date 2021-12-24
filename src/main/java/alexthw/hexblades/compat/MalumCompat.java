package alexthw.hexblades.compat;

import alexthw.hexblades.mixin.AltarEntryMixin;
import com.sammy.malum.common.blocks.lighting.EtherBlock;
import com.sammy.malum.common.blocks.lighting.EtherBrazierBlock;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import elucent.eidolon.spell.AltarEntries;
import elucent.eidolon.spell.AltarEntry;
import elucent.eidolon.spell.AltarKeys;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static net.minecraftforge.fml.common.ObfuscationReflectionHelper.getPrivateValue;

public class MalumCompat {

    public static void altar() throws InvocationTargetException, InstantiationException, IllegalAccessException {

        Map<BlockState, AltarEntry> AltarEntriesCopy = getPrivateValue(elucent.eidolon.spell.AltarEntries.class, new AltarEntries(), "entries");

        Constructor<AltarEntry> altarEntryConstructor = ObfuscationReflectionHelper.findConstructor(AltarEntry.class, ResourceLocation.class);

        if (AltarEntriesCopy != null) {

            AltarEntry entry = altarEntryConstructor.newInstance(AltarKeys.LIGHT_KEY);
            entry = ((AltarEntryMixin) entry).callSetCapacity(1.5D);
            entry = ((AltarEntryMixin) entry).callSetPower(1.5D);

            Set<RegistryObject<Block>> blocks = MalumBlocks.BLOCKS.getEntries().stream().filter(b -> b.get() instanceof EtherBrazierBlock).collect(Collectors.toSet());
            for (RegistryObject<Block> block : blocks) {
                AltarEntriesCopy.put(block.get().defaultBlockState(), entry);
            }

        }

    }

    public static boolean checkCrucible(Block block) {
        return block instanceof EtherBrazierBlock || block instanceof EtherBlock;
    }
}
