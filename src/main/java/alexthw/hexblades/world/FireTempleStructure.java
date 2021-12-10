package alexthw.hexblades.world;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.registers.HexStructures;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.PostPlacementProcessor;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraftforge.event.world.StructureSpawnListGatherEvent;
import org.apache.logging.log4j.Level;

import java.util.List;
import java.util.Optional;

public class FireTempleStructure extends StructureFeature<JigsawConfiguration> {


    public FireTempleStructure(Codec<JigsawConfiguration> pCodec, int minHeightLimit, int terrainHeightRadius, int allowTerrainHeightRange, boolean canSpawnInWater) {
        super(pCodec, (context) -> {
                    // Check if the spot is valid for structure gen. If false, return nothing to signal to the game to skip this spawn attempt.
                    if (!FireTempleStructure.isFeatureChunk(context)) {
                        return Optional.empty();
                    }
                    // Create the pieces layout of the structure and give it to
                    else {
                        return FireTempleStructure.createPiecesGenerator(context);
                    }
                },
                PostPlacementProcessor.NONE);
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    /**
     * The StructureSpawnListGatherEvent event allows us to have mobs that spawn naturally over time in our structure.
     * No other mobs will spawn in the structure of the same entity classification.
     * The reason you want to match the classifications is so that your structure's mob
     * will contribute to that classification's cap. Otherwise, it may cause a runaway
     * spawning of the mob that will never stop.
     */
    private static final List<MobSpawnSettings.SpawnerData> STRUCTURE_MONSTERS = ImmutableList.of(
            new MobSpawnSettings.SpawnerData(EntityType.ILLUSIONER, 100, 4, 9),
            new MobSpawnSettings.SpawnerData(EntityType.VINDICATOR, 100, 4, 9)
    );
    private static final List<MobSpawnSettings.SpawnerData> STRUCTURE_CREATURES = ImmutableList.of(
            new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 30, 10, 15),
            new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 100, 1, 2)
    );

    // Hooked up in StructureTutorialMain. You can move this elsewhere or change it up.
    public static void setupStructureSpawns(final StructureSpawnListGatherEvent event) {
        if(event.getStructure() == HexStructures.FIRE_TEMPLE.get()) {
            event.addEntitySpawns(MobCategory.MONSTER, STRUCTURE_MONSTERS);
            event.addEntitySpawns(MobCategory.CREATURE, STRUCTURE_CREATURES);
        }
    }

    public String getStructureName() {
        return (new ResourceLocation(Hexblades.MODID, "fire_temple")).toString();
    }

    private static boolean isFeatureChunk(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        BlockPos blockPos = context.chunkPos().getWorldPosition();

        // Grab height of land. Will stop at first non-air block.
        int landHeight = context.chunkGenerator().getFirstOccupiedHeight(blockPos.getX(), blockPos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor());

        // Grabs column of blocks at given position. In overworld, this column will be made of stone, water, and air.
        // In nether, it will be netherrack, lava, and air. End will only be endstone and air. It depends on what block
        // the chunk generator will place for that dimension.
        NoiseColumn columnOfBlocks = context.chunkGenerator().getBaseColumn(blockPos.getX(), blockPos.getZ(), context.heightAccessor());

        // Combine the column of blocks with land height and you get the top block itself which you can test.
        BlockState topBlock = columnOfBlocks.getBlock(landHeight);

        // Now we test to make sure our structure is not spawning on water or other fluids.
        // You can do height check instead too to make it spawn at high elevations.
        return topBlock.getFluidState().isEmpty(); //landHeight > 100;
    }

    public static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        // Turns the chunk coordinates into actual coordinates we can use. (Gets center of that chunk)
        BlockPos blockpos = context.chunkPos().getMiddleBlockPosition(0);

        /*
         * If you are doing Nether structures, you'll probably want to spawn your structure on top of ledges.
         * Best way to do that is to use getBaseColumn to grab a column of blocks at the structure's x/z position.
         * Then loop through it and look for land with air above it and set blockpos's Y value to it.
         * Make sure to set the final boolean in JigsawPlacement.addPieces to false so
         * that the structure spawns at blockpos's y value instead of placing the structure on the Bedrock roof!
         */
        // NoiseColumn blockReader = context.chunkGenerator().getBaseColumn(blockpos.getX(), blockpos.getZ(), context.heightAccessor());

        // We now can access out json template pool so lets set the context to use that json pool.
        // Requires AccessTransformer  (see resources/META-INF/accesstransformer.cfg)
        context.config().startPool =
                () -> context.registryAccess().ownedRegistryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
                        // The path to the starting Template Pool JSON file to read.
                        //
                        // Note, this is "structure_tutorial:run_down_house/start_pool" which means
                        // the game will automatically look into the following path for the template pool:
                        // "resources/data/structure_tutorial/worldgen/template_pool/run_down_house/start_pool.json"
                        // This is why your pool files must be in "data/<modid>/worldgen/template_pool/<the path to the pool here>"
                        // because the game automatically will check in worldgen/template_pool for the pools.
                        .get(new ResourceLocation(Hexblades.MODID, "fire_temple/fire_temple_start"));

        // How many pieces outward from center can a recursive jigsaw structure spawn.
        // Our structure is only 1 piece outward and isn't recursive so any value of 1 or more doesn't change anything.
        // However, I recommend you keep this a decent value like 10 so people can use datapacks to add additional pieces to your structure easily.
        // But don't make it too large for recursive structures like villages or you'll crash server due to hundreds of pieces attempting to generate!
        // Requires AccessTransformer  (see resources/META-INF/accesstransformer.cfg)
        context.config().maxDepth = 10;

        Optional<PieceGenerator<JigsawConfiguration>> structurePiecesGenerator =
                JigsawPlacement.addPieces(
                        context, // Used for JigsawPlacement to get all the proper behaviors done.
                        PoolElementStructurePiece::new, // Needed in order to create a list of jigsaw pieces when making the structure's layout.
                        blockpos, // Position of the structure. Y value is ignored if last parameter is set to true.
                        false,  // Special boundary adjustments for villages. It's... hard to explain. Keep this false and make your pieces not be partially intersecting.
                        // Either not intersecting or fully contained will make children pieces spawn just fine. It's easier that way.
                        false // Place at heightmap (top land). Set this to false for structure to be place at the passed in blockpos's Y value instead.
                        // Definitely keep this false when placing structures in the nether as otherwise, heightmap placing will put the structure on the Bedrock roof.
                );
        /*
         * Note, you are always free to make your own JigsawPlacement class and implementation of how the structure
         * should generate. It is tricky but extremely powerful if you are doing something that vanilla's jigsaw system cannot do.
         *
         * The only reason we are using JigsawConfiguration here is because in RunDownHouseStructure's createPiecesGenerator method,
         * we are using JigsawPlacement.addPieces which requires StructurePoolFeatureConfig. However, if you create your own
         * JigsawPlacement.addPieces, you could reduce the amount of workarounds that you need like line 150 and 130 above
         * and give yourself more opportunities and control over your structures.
         *
         * An example of a custom JigsawPlacement.addPieces in action can be found here (warning, it is using Mojmap mappings):
         * https://github.com/TelepathicGrunt/RepurposedStructures/blob/1.18/src/main/java/com/telepathicgrunt/repurposedstructures/world/structures/pieces/PieceLimitedJigsawManager.java
         */
        if(structurePiecesGenerator.isPresent()) {
            // I use to debug and quickly find out if the structure is spawning or not and where it is.
            // This is returning the coordinates of the center starting piece.
            Hexblades.LOGGER.log(Level.DEBUG, "FireTemple at " + blockpos);
        }
        // Return the pieces generator that is now set up so that the game runs it when it needs to create the layout of structure pieces.
        return structurePiecesGenerator;
    }

}
