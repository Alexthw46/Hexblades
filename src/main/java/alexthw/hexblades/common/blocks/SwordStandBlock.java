package alexthw.hexblades.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import java.util.stream.Stream;

public class SwordStandBlock extends HZBlockShaped {

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
