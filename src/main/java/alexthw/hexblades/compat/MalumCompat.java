package alexthw.hexblades.compat;

import alexthw.hexblades.util.HexUtils;
//import com.sammy.malum.common.blocks.lighting.EtherBrazierBlock;
//import com.sammy.malum.core.init.blocks.MalumBlocks;
import elucent.eidolon.spell.AltarEntries;
import elucent.eidolon.spell.AltarEntry;
import elucent.eidolon.spell.AltarKeys;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.fml.util.ObfuscationReflectionHelper;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MalumCompat {

    public static void altar(){

        /*
        Set<RegistryObject<Block>> blocks = new HashSet<>(MalumBlocks.BLOCKS.getEntries());
        for (RegistryObject<Block> block : HexUtils.takeAll(blocks, b -> b.get() instanceof EtherBrazierBlock)) {
            AltarEntries.entries.put(block.get().defaultBlockState(), new AltarEntry(AltarKeys.LIGHT_KEY).setPower(1.5D).setCapacity(1.5D));
        }
        */
    }

}
