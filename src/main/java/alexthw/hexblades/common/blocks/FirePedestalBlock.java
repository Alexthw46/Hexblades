package alexthw.hexblades.common.blocks;

import elucent.eidolon.block.HorizontalBlockBase;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class FirePedestalBlock extends HorizontalBlockBase {


    public FirePedestalBlock(Properties properties) {
        super(properties);
        this.setShape(makeShape());
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    public VoxelShape makeShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(-0.0625, 0.8125, -0.0625, 1.0625, 1.1875, 1.0625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.375, 0.25, 0.375, 0.625, 0.8125, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0, 0.125, 0.875, 0.25, 0.875), BooleanOp.OR);
        return shape;
    }

}
