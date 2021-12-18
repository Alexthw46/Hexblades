package alexthw.hexblades.common.blocks;

import alexthw.hexblades.common.blocks.tile_entities.EverfullUrnTileEntity;
import elucent.eidolon.block.HorizontalWaterloggableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class EverfullUrnBlock extends HorizontalWaterloggableBlock implements EntityBlock, BucketPickup {

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

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new EverfullUrnTileEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
        return (level1, pos, state1, tile) -> ((EverfullUrnTileEntity)tile).tick();
    }

    @Override
    public @NotNull ItemStack pickupBlock(@NotNull LevelAccessor p_152719_, @NotNull BlockPos p_152720_, @NotNull BlockState p_152721_) {
        return new ItemStack(Fluids.WATER.getBucket());
    }

    @Override
    public @NotNull Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL);
    }
}
