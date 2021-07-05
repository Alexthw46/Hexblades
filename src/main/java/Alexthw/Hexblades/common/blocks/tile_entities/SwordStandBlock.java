package Alexthw.Hexblades.common.blocks.tile_entities;

import elucent.eidolon.block.HorizontalBlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import java.util.HashMap;
import java.util.Map;

public class SwordStandBlock extends HorizontalBlockBase implements IWaterLoggable {

    public SwordStandBlock(Properties properties) {
        super(properties);
        VoxelShape VSHAPE = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(2, 0, 2, 14, 2, 14), Block.makeCuboidShape(4, 2, 4, 12, 3.5, 12), IBooleanFunction.OR);
        runCalculation(VSHAPE);
    }

    //Voxel Shaping

    protected static final Map<Direction, VoxelShape> SHAPES = new HashMap<>();

    protected static void calculateShapes(Direction to, VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[]{shape, VoxelShapes.empty()};

        int times = (to.getHorizontalIndex() - Direction.NORTH.getHorizontalIndex() + 4) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forEachBox((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = VoxelShapes.or(buffer[1],
                    VoxelShapes.create(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
            buffer[0] = buffer[1];
            buffer[1] = VoxelShapes.empty();
        }

        SHAPES.put(to, buffer[0]);
    }

    protected void runCalculation(VoxelShape shape) {
        for (Direction direction : Direction.values()) {
            calculateShapes(direction, shape);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        this.setShape(SHAPES.get(state.get(HORIZONTAL_FACING)));
        return this.getRaytraceShape(state, world, pos);
    }
}
