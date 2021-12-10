package alexthw.hexblades.common.blocks;

import elucent.eidolon.block.HorizontalWaterloggableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EverfullUrnBlock extends HorizontalWaterloggableBlock{

    public EverfullUrnBlock(Properties properties) {
        super(properties);
        this.setShape(VSHAPE);
        this.registerDefaultState(this.defaultBlockState().setValue(WATERLOGGED, false));
    }

    //Voxel Shaping

    static final VoxelShape VSHAPE = Shapes.join(Block.box(3, 0, 3, 13, 9, 13), Block.box(5.5, 9, 5.5, 10.5, 13, 10.5), BooleanOp.OR);

    public VoxelShape getInteractionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return VSHAPE;
    }

}
