package alexthw.hexblades.compat;

import alexthw.hexblades.util.HexUtils;
import com.sammy.malum.common.blocks.lighting.EtherBlock;
import com.sammy.malum.common.blocks.lighting.EtherBrazierBlock;
import com.sammy.malum.core.init.blocks.MalumBlocks;
import elucent.eidolon.spell.AltarEntries;
import elucent.eidolon.spell.AltarEntry;
import elucent.eidolon.spell.AltarKeys;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import static elucent.eidolon.tile.CrucibleTileEntity.HOT_BLOCKS;
import static net.minecraftforge.fml.common.ObfuscationReflectionHelper.getPrivateValue;

public class MalumCompat {

    public static void altar() throws InvocationTargetException, InstantiationException, IllegalAccessException {

        Map<BlockState, AltarEntry> AltarEntriesCopy = getPrivateValue(elucent.eidolon.spell.AltarEntries.class, new AltarEntries(), "entries");

        Constructor<AltarEntry> altarEntryConstructor = ObfuscationReflectionHelper.findConstructor(AltarEntry.class, ResourceLocation.class);
        Method setPowerMethod = ObfuscationReflectionHelper.findMethod(AltarEntry.class, "setPower", double.class);

        if (AltarEntriesCopy != null) {

            Set<RegistryObject<Block>> blocks = new HashSet<>(MalumBlocks.BLOCKS.getEntries());
            for (RegistryObject<Block> block : HexUtils.takeAll(blocks, b -> b.get() instanceof EtherBrazierBlock)) {
                AltarEntriesCopy.put(block.get().defaultBlockState(), (AltarEntry) setPowerMethod.invoke(altarEntryConstructor.newInstance(AltarKeys.LIGHT_KEY), 1.5D));
            }

        }

    }

    public static void crucible() {
        HOT_BLOCKS = new Predicate<?>[]{
                (BlockState b) -> b.getBlock() == Blocks.MAGMA_BLOCK,
                (BlockState b) -> b.getBlock() == Blocks.FIRE,
                (BlockState b) -> b.getBlock() == Blocks.SOUL_FIRE,
                (BlockState b) -> b.getBlock() == Blocks.LAVA,
                (BlockState b) -> b.getBlock() instanceof CampfireBlock && b.getValue(CampfireBlock.LIT),
                (BlockState b) -> b.getBlock() instanceof EtherBlock,
                (BlockState b) -> b.getBlock() instanceof EtherBrazierBlock
        };
    }

}
