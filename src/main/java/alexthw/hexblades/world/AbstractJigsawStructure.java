package alexthw.hexblades.world;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.StructureFeature;

public abstract class AbstractJigsawStructure extends StructureFeature<NoneFeatureConfiguration> {


    protected int minHeightLimit;
    protected int terrainHeightRadius;
    protected int allowTerrainHeightRange;
    protected boolean canSpawnInWater;

    public AbstractJigsawStructure(Codec<NoneFeatureConfiguration> pCodec, int minHeightLimit, int terrainHeightRadius, int allowTerrainHeightRange, boolean canSpawnInWater) {
        super(pCodec);
        this.allowTerrainHeightRange = allowTerrainHeightRange;
        this.minHeightLimit = minHeightLimit;
        this.terrainHeightRadius = terrainHeightRadius;
        this.canSpawnInWater = canSpawnInWater;
    }

    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeSource biomeSource, long seed, WorldgenRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoneFeatureConfiguration featureConfig) {

        if (allowTerrainHeightRange != -1) {
            int maxTerrainHeight = Integer.MIN_VALUE;
            int minTerrainHeight = Integer.MAX_VALUE;


            for (int curChunkX = chunkX - terrainHeightRadius; curChunkX <= chunkX + terrainHeightRadius; curChunkX++) {
                for (int curChunkZ = chunkZ - terrainHeightRadius; curChunkZ <= chunkZ + terrainHeightRadius; curChunkZ++) {
                    int height = chunkGenerator.getBaseHeight(curChunkX * 16, curChunkZ * 16, Heightmap.Types.WORLD_SURFACE_WG);
                    maxTerrainHeight = Math.max(maxTerrainHeight, height);
                    minTerrainHeight = Math.min(minTerrainHeight, height);

                    if (minTerrainHeight < this.minHeightLimit) {
                        return false;
                    }
                }
            }

            return maxTerrainHeight - minTerrainHeight <= allowTerrainHeightRange;
        }

        if (!canSpawnInWater) {
            BlockPos centerOfChunk = new BlockPos(chunkX * 16, 0, chunkZ * 16);
            int landHeight = chunkGenerator.getFirstOccupiedHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Types.WORLD_SURFACE_WG);
            BlockGetter columnOfBlocks = chunkGenerator.getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ());
            BlockState topBlock = columnOfBlocks.getBlockState(centerOfChunk.above(landHeight));
            return topBlock.getFluidState().isEmpty();
        }


        return true;
    }
}
