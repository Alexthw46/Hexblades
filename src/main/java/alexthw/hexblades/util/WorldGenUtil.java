package alexthw.hexblades.util;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class WorldGenUtil {

    public static boolean haveCategories(BiomeLoadingEvent context, Biome.BiomeCategory... categories) {
        Set<Biome.BiomeCategory> categorySet = new HashSet<>(Arrays.asList(categories));
        return categorySet.contains(context.getCategory());
    }

    /* TODO check TG new method
    public static BlockPos getHighestLand(ChunkGenerator chunkGenerator, BoundingBox boundingBox, boolean canBeOnLiquid) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos().set(
                boundingBox.getCenter().getX(),
                chunkGenerator.getGenDepth() - 20,
                boundingBox.getCenter().getZ());

        BlockGetter blockView = chunkGenerator.getBaseColumn(mutable.getX(), mutable.getZ());
        BlockState currentBlockstate;
        while (mutable.getY() > chunkGenerator.getSeaLevel()) {
            currentBlockstate = blockView.getBlockState(mutable);
            if (!currentBlockstate.canOcclude()) {
                mutable.move(Direction.DOWN);
                continue;
            } else if (blockView.getBlockState(mutable.offset(0, 3, 0)).getMaterial() == Material.AIR && (canBeOnLiquid ? !currentBlockstate.isAir() : currentBlockstate.canOcclude())) {
                return mutable;
            }
            mutable.move(Direction.DOWN);
        }

        return mutable;
    }

     */

    /* TODO check TG new method
    public static BlockPos getLowestLand(ChunkGenerator chunkGenerator, BoundingBox boundingBox, boolean canBeOnLiquid) {
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos().set(
                boundingBox.getCenter().getX(),
                chunkGenerator.getSeaLevel() + 1,
                boundingBox.getCenter().getZ());

        BlockGetter blockView = chunkGenerator.getBaseColumn(mutable.getX(), mutable.getZ());
        BlockState currentBlockstate = blockView.getBlockState(mutable);
        while (mutable.getY() <= chunkGenerator.getGenDepth() - 20) {

            if ((canBeOnLiquid ? !currentBlockstate.isAir() : currentBlockstate.canOcclude()) &&
                    blockView.getBlockState(mutable.above()).getMaterial() == Material.AIR &&
                    blockView.getBlockState(mutable.above(5)).getMaterial() == Material.AIR) {
                mutable.move(Direction.UP);
                return mutable;
            }

            mutable.move(Direction.UP);
            currentBlockstate = blockView.getBlockState(mutable);
        }

        return mutable.set(mutable.getX(), chunkGenerator.getSeaLevel(), mutable.getZ());
    }
     */

}
