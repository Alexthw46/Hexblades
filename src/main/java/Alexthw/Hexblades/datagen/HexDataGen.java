package Alexthw.Hexblades.datagen;

import Alexthw.Hexblades.Hexblades;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import javax.naming.directory.ModificationItem;

@Mod.EventBusSubscriber(modid = Hexblades.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)

public final class HexDataGen {
    public static ResourceLocation prefix(String path)
    {
        return new ResourceLocation(Hexblades.MOD_ID, path);
    }

    public static void gatherData(GatherDataEvent event){
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        gen.addProvider(new HexItemModelProvider(gen, existingFileHelper));
        gen.addProvider(new HexBlockStateProvider(gen, existingFileHelper));
    }
}
