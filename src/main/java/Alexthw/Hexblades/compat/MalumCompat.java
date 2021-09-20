package Alexthw.Hexblades.compat;

import com.sammy.malum.common.blocks.lighting.EtherBlock;
import com.sammy.malum.common.blocks.lighting.EtherBrazierBlock;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import elucent.eidolon.spell.AltarEntries;
import elucent.eidolon.spell.AltarEntry;
import elucent.eidolon.spell.AltarKeys;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.Predicate;

import static elucent.eidolon.tile.CrucibleTileEntity.HOT_BLOCKS;
import static net.minecraftforge.fml.common.ObfuscationReflectionHelper.getPrivateValue;

public class MalumCompat {

    public static void start() throws InvocationTargetException, InstantiationException, IllegalAccessException {

        HOT_BLOCKS = new Predicate<?>[]{
                (BlockState b) -> b.getBlock() == Blocks.MAGMA_BLOCK,
                (BlockState b) -> b.getBlock() == Blocks.FIRE,
                (BlockState b) -> b.getBlock() == Blocks.SOUL_FIRE,
                (BlockState b) -> b.getBlock() == Blocks.LAVA,
                (BlockState b) -> b.getBlock() == Blocks.CAMPFIRE && b.get(CampfireBlock.LIT),
                (BlockState b) -> b.getBlock() == Blocks.SOUL_CAMPFIRE && b.get(CampfireBlock.LIT),
                (BlockState b) -> b.getBlock() instanceof EtherBlock,
                (BlockState b) -> b.getBlock() instanceof EtherBrazierBlock
        };


        Map<BlockState, AltarEntry> AltarEntriesCopy = getPrivateValue(elucent.eidolon.spell.AltarEntries.class, new AltarEntries(), "entries");

        Constructor<AltarEntry> altarEntryConstructor = ObfuscationReflectionHelper.findConstructor(AltarEntry.class, ResourceLocation.class);
        Method setPowerMethod = ObfuscationReflectionHelper.findMethod(AltarEntry.class, "setPower", double.class);
        if (AltarEntriesCopy != null) {

            AltarEntriesCopy.put(MalumBlocks.BLUE_ETHER_BRAZIER.get().getDefaultState(), (AltarEntry) setPowerMethod.invoke(altarEntryConstructor.newInstance(AltarKeys.LIGHT_KEY), 1.5D));
            AltarEntriesCopy.put(MalumBlocks.RED_ETHER_BRAZIER.get().getDefaultState(), (AltarEntry) setPowerMethod.invoke(altarEntryConstructor.newInstance(AltarKeys.LIGHT_KEY), 1.5D));
            AltarEntriesCopy.put(MalumBlocks.YELLOW_ETHER_BRAZIER.get().getDefaultState(), (AltarEntry) setPowerMethod.invoke(altarEntryConstructor.newInstance(AltarKeys.LIGHT_KEY), 1.5D));
            AltarEntriesCopy.put(MalumBlocks.GREEN_ETHER_BRAZIER.get().getDefaultState(), (AltarEntry) setPowerMethod.invoke(altarEntryConstructor.newInstance(AltarKeys.LIGHT_KEY), 1.5D));
            AltarEntriesCopy.put(MalumBlocks.LIGHT_BLUE_ETHER_BRAZIER.get().getDefaultState(), (AltarEntry) setPowerMethod.invoke(altarEntryConstructor.newInstance(AltarKeys.LIGHT_KEY), 1.5D));
            AltarEntriesCopy.put(MalumBlocks.PINK_ETHER_BRAZIER.get().getDefaultState(), (AltarEntry) setPowerMethod.invoke(altarEntryConstructor.newInstance(AltarKeys.LIGHT_KEY), 1.5D));
            AltarEntriesCopy.put(MalumBlocks.LIME_ETHER_BRAZIER.get().getDefaultState(), (AltarEntry) setPowerMethod.invoke(altarEntryConstructor.newInstance(AltarKeys.LIGHT_KEY), 1.5D));
            AltarEntriesCopy.put(MalumBlocks.CYAN_ETHER_BRAZIER.get().getDefaultState(), (AltarEntry) setPowerMethod.invoke(altarEntryConstructor.newInstance(AltarKeys.LIGHT_KEY), 1.5D));
            AltarEntriesCopy.put(MalumBlocks.PURPLE_ETHER_BRAZIER.get().getDefaultState(), (AltarEntry) setPowerMethod.invoke(altarEntryConstructor.newInstance(AltarKeys.LIGHT_KEY), 1.5D));
            AltarEntriesCopy.put(MalumBlocks.ORANGE_ETHER_BRAZIER.get().getDefaultState(), (AltarEntry) setPowerMethod.invoke(altarEntryConstructor.newInstance(AltarKeys.LIGHT_KEY), 1.5D));
            AltarEntriesCopy.put(MalumBlocks.MAGENTA_ETHER_BRAZIER.get().getDefaultState(), (AltarEntry) setPowerMethod.invoke(altarEntryConstructor.newInstance(AltarKeys.LIGHT_KEY), 1.5D));
            AltarEntriesCopy.put(MalumBlocks.BROWN_ETHER_BRAZIER.get().getDefaultState(), (AltarEntry) setPowerMethod.invoke(altarEntryConstructor.newInstance(AltarKeys.LIGHT_KEY), 1.5D));

        }
    }


}
