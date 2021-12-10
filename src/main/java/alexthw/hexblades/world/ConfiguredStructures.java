package alexthw.hexblades.world;

import alexthw.hexblades.Hexblades;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.PlainVillagePools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;

import static alexthw.hexblades.registers.HexStructures.FIRE_TEMPLE;

public class ConfiguredStructures {
    /**
     * Static instance of our structure so we can reference it and add it to biomes easily.
     */
    public static ConfiguredStructureFeature<?, ?> CONFIGURED_FIRE_TEMPLE = FIRE_TEMPLE.get().configured(new JigsawConfiguration(
            // Dummy values for now. We will modify the pool at runtime since we cannot get json pool files here at mod init.
            // You can create and register your pools in code, pass in the code create pool here, and delete line 137 in RunDownHouseStructure
            () -> PlainVillagePools.START,

            // We will set size at runtime too as JigsawConfiguration will not handle sizes above 7.
            // If your size is below 7, you can set the size here and delete line 153 in RunDownHouseStructure
            0

            /*
             * The only reason we are using JigsawConfiguration here is because in RunDownHouseStructure's createPiecesGenerator method,
             * we are using JigsawPlacement.addPieces which requires JigsawConfiguration. However, if you create your own
             * JigsawPlacement.addPieces, you could reduce the amount of workarounds like above that you need and give yourself more
             * opportunities and control over your structures.
             *
             * An example of a custom JigsawPlacement.addPieces in action can be found here:
             * https://github.com/TelepathicGrunt/RepurposedStructures/blob/1.18/src/main/java/com/telepathicgrunt/repurposedstructures/world/structures/pieces/PieceLimitedJigsawManager.java
             */
    ));

    public static void registerConfiguredStructures() {
        net.minecraft.core.Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation(Hexblades.MODID, "configured_fire_temple"), CONFIGURED_FIRE_TEMPLE);
    }

}


