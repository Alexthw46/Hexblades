package Alexthw.Hexblades.common.blocks.tile_entities;

import elucent.eidolon.block.HorizontalWaterloggableBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

public class EverfullUrnBlock extends HorizontalWaterloggableBlock {

    public EverfullUrnBlock(Properties properties) {
        super(properties);
        this.setShape(VSHAPE);
        this.setDefaultState(this.getDefaultState().with(WATERLOGGED, false));
    }

    //Voxel Shaping

    static final VoxelShape VSHAPE = VoxelShapes.combineAndSimplify(Block.makeCuboidShape(3, 0, 3, 13, 9, 13), Block.makeCuboidShape(5.5, 9, 5.5, 10.5, 13, 10.5), IBooleanFunction.OR);

    public VoxelShape getRaytraceShape(BlockState state, IBlockReader world, BlockPos pos) {
        return VSHAPE;
    }

}
