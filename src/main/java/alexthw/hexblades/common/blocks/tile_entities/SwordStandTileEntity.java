package alexthw.hexblades.common.blocks.tile_entities;

import alexthw.hexblades.registers.HexTileEntityType;
import elucent.eidolon.tile.TileEntityBase;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
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


    public SwordStandTileEntity() {
        this(HexTileEntityType.SWORD_STAND_TILE_ENTITY);
    }

    public SwordStandTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
        this.stack = ItemStack.EMPTY;
    }

    @Override
    public void onDestroyed(BlockState state, BlockPos pos) {
        if (!this.stack.isEmpty()) {
            assert this.level != null;
            InventoryHelper.dropItemStack(this.level, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, this.stack);
        }

    }

    @Override
    public ActionResultType onActivated(BlockState state, BlockPos pos, PlayerEntity player, Hand hand) {
        if (hand == Hand.MAIN_HAND) {
            if (player.getItemInHand(hand).isEmpty() && !this.stack.isEmpty()) {
                player.addItem(this.stack);
                this.stack = ItemStack.EMPTY;
                assert this.level != null;
                if (!this.level.isClientSide) {
                    this.sync();
                }

                return ActionResultType.SUCCESS;
            }

            if (!player.getItemInHand(hand).isEmpty() && this.stack.isEmpty() && (player.getItemInHand(hand).getItem() instanceof SwordItem)) {
                this.stack = player.getItemInHand(hand).copy();
                this.stack.setCount(1);
                player.getItemInHand(hand).shrink(1);
                if (player.getItemInHand(hand).isEmpty()) {
                    player.setItemInHand(hand, ItemStack.EMPTY);
                }

                assert this.level != null;
                if (!this.level.isClientSide) {
                    this.sync();
                }

                return ActionResultType.SUCCESS;
            }
        }

        return ActionResultType.PASS;
    }

    @Override
    public void load(BlockState state, CompoundNBT tag) {
        super.load(state, tag);
        this.stack = ItemStack.of(tag.getCompound("stack"));
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        tag = super.save(tag);
        tag.put("stack", this.stack.save(new CompoundNBT()));
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
