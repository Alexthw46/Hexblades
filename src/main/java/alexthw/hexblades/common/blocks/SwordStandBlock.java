package alexthw.hexblades.common.blocks;

import alexthw.hexblades.common.blocks.tile_entities.SwordStandTileEntity;
import elucent.eidolon.block.HorizontalBlockBase;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class SwordStandBlock extends HorizontalBlockBase implements EntityBlock {

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new SwordStandTileEntity(pos, state);
    }
    public SwordStandBlock(Properties properties) {
        super(properties);
        VoxelShape VSHAPE = Stream.of(
                Block.box(2, 0, 2, 14, 1, 14),
                Block.box(2, 1, 3, 3, 2, 14),
                Block.box(13, 1, 2, 14, 2, 13),
                Block.box(2, 1, 2, 13, 2, 3),
                Block.box(3, 1, 13, 14, 2, 14)
        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
        setShape(VSHAPE);
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

}
