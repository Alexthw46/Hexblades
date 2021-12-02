package alexthw.hexblades.compat;

import alexthw.hexblades.util.HexUtils;
import com.sammy.malum.common.blocks.lighting.EtherBlock;
import com.sammy.malum.common.blocks.lighting.EtherBrazierBlock;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import elucent.eidolon.spell.AltarEntries;
import elucent.eidolon.spell.AltarEntry;
import elucent.eidolon.spell.AltarKeys;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.fmllegacy.RegistryObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MalumCompat {

    public static void altar() throws InvocationTargetException, InstantiationException, IllegalAccessException {

        Map<BlockState, AltarEntry> AltarEntriesCopy = ObfuscationReflectionHelper.getPrivateValue(elucent.eidolon.spell.AltarEntries.class, new AltarEntries(), "entries");

        Constructor<AltarEntry> altarEntryConstructor = ObfuscationReflectionHelper.findConstructor(AltarEntry.class, ResourceLocation.class);
        Method setPowerMethod = ObfuscationReflectionHelper.findMethod(AltarEntry.class, "setPower", double.class);

        if (AltarEntriesCopy != null) {

            Set<RegistryObject<Block>> blocks = new HashSet<>(MalumBlocks.BLOCKS.getEntries());
            for (RegistryObject<Block> block : HexUtils.takeAll(blocks, b -> b.get() instanceof EtherBrazierBlock)) {
                AltarEntriesCopy.put(block.get().defaultBlockState(), (AltarEntry) setPowerMethod.invoke(altarEntryConstructor.newInstance(AltarKeys.LIGHT_KEY), 1.5D));
            }

        }

    }

}
