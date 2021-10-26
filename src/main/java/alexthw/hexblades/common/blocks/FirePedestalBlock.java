package alexthw.hexblades.common.blocks;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;

public class FirePedestalBlock extends HZBlockShaped {


    public FirePedestalBlock(Properties properties) {
        super(properties);
        runCalculation(makeShape());
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    public VoxelShape makeShape() {
        VoxelShape shape = VoxelShapes.empty();
        shape = VoxelShapes.join(shape, VoxelShapes.box(-0.0625, 0.8125, -0.0625, 1.0625, 1.1875, 1.0625), IBooleanFunction.OR);
        shape = VoxelShapes.join(shape, VoxelShapes.box(0.375, 0.25, 0.375, 0.625, 0.8125, 0.625), IBooleanFunction.OR);
        shape = VoxelShapes.join(shape, VoxelShapes.box(0.125, 0, 0.125, 0.875, 0.25, 0.875), IBooleanFunction.OR);
        return shape;
    }

}
