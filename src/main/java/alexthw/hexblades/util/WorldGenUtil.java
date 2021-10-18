package alexthw.hexblades.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraftforge.event.world.BiomeLoadingEvent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class WorldGenUtil {

    public static boolean haveCategories(BiomeLoadingEvent context, Biome.Category... categories) {
        Set<Biome.Category> categorySet = new HashSet<>(Arrays.asList(categories));
        return categorySet.contains(context.getCategory());
    }

    public static BlockPos getHighestLand(ChunkGenerator chunkGenerator, MutableBoundingBox boundingBox, boolean canBeOnLiquid) {
        BlockPos.Mutable mutable = new BlockPos.Mutable().set(
                boundingBox.getCenter().getX(),
                chunkGenerator.getGenDepth() - 20,
                boundingBox.getCenter().getZ());

        IBlockReader blockView = chunkGenerator.getBaseColumn(mutable.getX(), mutable.getZ());
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

    public static BlockPos getLowestLand(ChunkGenerator chunkGenerator, MutableBoundingBox boundingBox, boolean canBeOnLiquid) {
        BlockPos.Mutable mutable = new BlockPos.Mutable().set(
                boundingBox.getCenter().getX(),
                chunkGenerator.getSeaLevel() + 1,
                boundingBox.getCenter().getZ());

        IBlockReader blockView = chunkGenerator.getBaseColumn(mutable.getX(), mutable.getZ());
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


}
