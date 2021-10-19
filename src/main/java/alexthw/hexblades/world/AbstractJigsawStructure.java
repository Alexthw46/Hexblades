package alexthw.hexblades.world;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;

public abstract class AbstractJigsawStructure extends Structure<NoFeatureConfig> {


    protected int minHeightLimit;
    protected int terrainHeightRadius;
    protected int allowTerrainHeightRange;
    protected boolean canSpawnInWater;

    public AbstractJigsawStructure(Codec<NoFeatureConfig> pCodec, int minHeightLimit, int terrainHeightRadius, int allowTerrainHeightRange, boolean canSpawnInWater) {
        super(pCodec);
        this.allowTerrainHeightRange = allowTerrainHeightRange;
        this.minHeightLimit = minHeightLimit;
        this.terrainHeightRadius = terrainHeightRadius;
        this.canSpawnInWater = canSpawnInWater;
    }

    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoFeatureConfig featureConfig) {

        if (allowTerrainHeightRange != -1) {
            int maxTerrainHeight = Integer.MIN_VALUE;
            int minTerrainHeight = Integer.MAX_VALUE;


            for (int curChunkX = chunkX - terrainHeightRadius; curChunkX <= chunkX + terrainHeightRadius; curChunkX++) {
                for (int curChunkZ = chunkZ - terrainHeightRadius; curChunkZ <= chunkZ + terrainHeightRadius; curChunkZ++) {
                    int height = chunkGenerator.getBaseHeight(curChunkX * 16, curChunkZ * 16, Heightmap.Type.WORLD_SURFACE_WG);
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
            int landHeight = chunkGenerator.getFirstOccupiedHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
            IBlockReader columnOfBlocks = chunkGenerator.getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ());
            BlockState topBlock = columnOfBlocks.getBlockState(centerOfChunk.above(landHeight));
            return topBlock.getFluidState().isEmpty();
        }


        return true;
    }
}
