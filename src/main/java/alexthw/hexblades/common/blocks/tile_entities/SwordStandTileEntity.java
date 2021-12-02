package alexthw.hexblades.common.blocks.tile_entities;

import alexthw.hexblades.common.items.IHexblade;
import alexthw.hexblades.registers.HexTileEntityType;
import elucent.eidolon.tile.TileEntityBase;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.Containers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class SwordStandTileEntity extends TileEntityBase implements IAnimatable {

    public ItemStack stack;
    long previous = -1L;
    private final AnimationFactory factory = new AnimationFactory(this);


    public SwordStandTileEntity(BlockPos blockPos, BlockState state) {
        super(HexTileEntityType.SWORD_STAND_TILE_ENTITY,blockPos,state);
        this.stack = ItemStack.EMPTY;
    }

    @Override
    public void onDestroyed(BlockState state, BlockPos pos) {
        if (!this.stack.isEmpty()) {
            assert this.level != null;
            Containers.dropItemStack(this.level, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, this.stack);
        }

    }

    @Override
    public InteractionResult onActivated(BlockState state, BlockPos pos, Player player, InteractionHand hand) {
        if (hand == InteractionHand.MAIN_HAND) {
            ItemStack itemHand = player.getItemInHand(hand);
            if (itemHand.isEmpty() && !this.stack.isEmpty()) {
                player.addItem(this.stack);
                this.stack = ItemStack.EMPTY;
                assert this.level != null;
                if (!this.level.isClientSide) {
                    this.sync();
                }

                return InteractionResult.SUCCESS;
            }

            if (!itemHand.isEmpty() && this.stack.isEmpty() && (itemHand.getItem() instanceof IHexblade || (itemHand.getItem() instanceof SwordItem))) {
                this.stack = itemHand.copy();
                this.stack.setCount(1);
                itemHand.shrink(1);
                if (player.getItemInHand(hand).isEmpty()) {
                    player.setItemInHand(hand, ItemStack.EMPTY);
                }

                assert this.level != null;
                if (!this.level.isClientSide) {
                    this.sync();
                }

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.stack = ItemStack.of(tag.getCompound("stack"));
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        tag = super.save(tag);
        tag.put("stack", this.stack.save(new CompoundTag()));
        return tag;
    }

    public boolean ready() {
        return true;
    }

    public void pray() {
        if (this.level != null && !this.level.isClientSide) {
            this.previous = this.level.getGameTime();
            this.sync();
        }

    }


    public ItemStack provide() {
        return this.stack.copy();
    }

    public void take() {
        this.stack = ItemStack.EMPTY;
        if (this.level != null && !this.level.isClientSide) {
            this.sync();
        }

    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <E extends TileEntityBase & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.sword_stand.cubes", true));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
