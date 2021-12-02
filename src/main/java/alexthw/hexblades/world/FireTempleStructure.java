package alexthw.hexblades.world;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.util.WorldGenUtil;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.core.Vec3i;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature.StructureStartFactory;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureStart;

public class FireTempleStructure extends AbstractJigsawStructure {


    public FireTempleStructure(Codec<NoneFeatureConfiguration> pCodec, int minHeightLimit, int terrainHeightRadius, int allowTerrainHeightRange, boolean canSpawnInWater) {
        super(pCodec, minHeightLimit, terrainHeightRadius, allowTerrainHeightRange, canSpawnInWater);
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    @Override
    public StructureStartFactory<NoneFeatureConfiguration> getStartFactory() {
        return FireTempleStructure.Start::new;
    }

    public String getStructureName() {
        return (new ResourceLocation(Hexblades.MODID, "fire_temple")).toString();
    }


    private static class Start extends StructureStart<NoneFeatureConfiguration> {

        public Start(StructureFeature<NoneFeatureConfiguration> config, int chunkX, int chunkZ, BoundingBox bounds, int refs, long seed) {
            super(config, chunkX, chunkZ, bounds, refs, seed);
        }

        @Override
        public void generatePieces(RegistryAccess dynamicRegistryManager, ChunkGenerator chunkGenerator, StructureManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn, NoneFeatureConfiguration config) {

            // Turns the chunk coordinates into actual coordinates we can use

            /*
             * We pass this into addPieces to tell it where to generate the structure.
             * If add Pieces's last parameter is true, blockpos's Y value is ignored and the
             * structure will spawn at terrain height instead. Set that parameter to false to
             * force the structure to spawn at blockpos's Y value instead. You got options here!
             *
             * If you are doing Nether structures, you'll probably want to spawn your structure on top of ledges.
             * Best way to do that is to use getBaseColumn to grab a column of blocks at the structure's x/z position.
             * Then loop through it and look for land with air above it and set blockpos's Y value to it.
             * Make sure to set the final boolean in JigsawManager.addPieces to false so
             * that the structure spawns at blockpos's y value instead of placing the structure on the Bedrock roof!
             */

            BlockPos placementPos = new BlockPos(chunkX * 16, WorldGenUtil.getLowestLand(chunkGenerator, this.getBoundingBox(), false).getY(), chunkZ * 16);

            // All a structure has to do is call this method to turn it into a jigsaw based structure!
            JigsawPlacement.addPieces(
                    dynamicRegistryManager,
                    new JigsawConfiguration(() -> dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
                            // The path to the starting Template Pool JSON file to read.

                            .get(new ResourceLocation(Hexblades.MODID, "fire_temple/fire_temple_start")),

                            // How many pieces outward from center can a recursive jigsaw structure spawn.
                            // I recommend you keep this a decent value like 10 so people can use datapacks to add additional pieces to your structure easily.
                            // But don't make it too large for recursive structures like villages or you'll crash server due to hundreds of pieces attempting to generate!
                            14),
                    PoolElementStructurePiece::new,
                    chunkGenerator,
                    templateManagerIn,
                    placementPos, // Position of the structure. Y value is ignored if last parameter is set to true.
                    this.pieces, // The list that will be populated with the jigsaw pieces after this method.
                    this.random,
                    false, // Special boundary adjustments for villages.
                    false);  // Place at heightmap (top land). Set this to false for structure to be place at the passed in blockpos's Y value instead.

            // Definitely keep this false when placing structures in the nether as otherwise, heightmap placing will put the structure on the Bedrock roof.

            this.pieces.forEach(piece -> piece.move(0, -8, 0));

            // Since by default, the start piece of a structure spawns with it's corner at centerPos
            // and will randomly rotate around that corner, we will center the piece on centerPos instead.
            // This is so that our structure's start piece is now centered on the water check done in isFeatureChunk.
            // Whatever the offset done to center the start piece, that offset is applied to all other pieces
            // so the entire structure is shifted properly to the new spot.
            Vec3i structureCenter = this.pieces.get(0).getBoundingBox().getCenter();
            int xOffset = placementPos.getX() - structureCenter.getX();
            int zOffset = placementPos.getZ() - structureCenter.getZ();
            for (StructurePiece structurePiece : this.pieces) {
                structurePiece.move(xOffset, 0, zOffset);
            }
            // Sets the bounds of the structure once you are finished.
            this.calculateBoundingBox();

            // I use to debug and quickly find out if the structure is spawning or not and where it is.
            // This is returning the coordinates of the center starting piece.
            // Hexblades.LOGGER.log(Level.DEBUG, "Fire Temple spawned at " +
            //         this.pieces.get(0).getBoundingBox().x0 + " " +
            //         this.pieces.get(0).getBoundingBox().y0 + " " +
            //         this.pieces.get(0).getBoundingBox().z0);
        }
    }
}
