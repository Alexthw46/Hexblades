package alexthw.hexblades.common.blocks;

import elucent.eidolon.block.HorizontalBlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
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
import java.util.stream.Stream;

public class SwordStandBlock extends HorizontalBlockBase implements IWaterLoggable {

    public SwordStandBlock(Properties properties) {
        super(properties);
        VoxelShape VSHAPE = Stream.of(
                Block.box(2, 0, 2, 14, 1, 14),
                Block.box(2, 1, 3, 3, 2, 14),
                Block.box(13, 1, 2, 14, 2, 13),
                Block.box(2, 1, 2, 13, 2, 3),
                Block.box(3, 1, 13, 14, 2, 14)
        ).reduce((v1, v2) -> VoxelShapes.join(v1, v2, IBooleanFunction.OR)).get();
        runCalculation(VSHAPE);
    }

    //Voxel Shaping

    protected static final Map<Direction, VoxelShape> SHAPES = new HashMap<>();

    protected static void calculateShapes(Direction to, VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[]{shape, VoxelShapes.empty()};

        int times = (to.get2DDataValue() - Direction.NORTH.get2DDataValue() + 4) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = VoxelShapes.or(buffer[1],
                    VoxelShapes.box(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
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
        this.setShape(SHAPES.get(state.getValue(HORIZONTAL_FACING)));
        return this.getInteractionShape(state, world, pos);
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

}
