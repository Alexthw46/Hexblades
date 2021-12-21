package alexthw.hexblades.common.blocks.tile_entities;

import alexthw.hexblades.common.items.IHexblade;
import alexthw.hexblades.registers.HexTileEntityType;
import alexthw.hexblades.util.Constants;
import elucent.eidolon.item.SappingSwordItem;
import elucent.eidolon.tile.TileEntityBase;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
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
    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        this.stack = ItemStack.of(tag.getCompound("stack"));
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag tag) {
        tag = super.save(tag);
        tag.put("stack", this.stack.save(new CompoundTag()));
        return tag;
    }

    public boolean ready() {
        return true;
    }

    public void pray(Player player) {
        if (this.level != null && !this.level.isClientSide) {
            if (this.stack.getItem() instanceof IHexblade hexblade){
                if (hexblade instanceof SappingSwordItem) return;
                CompoundTag tag = this.stack.getOrCreateTag();
                switch (tag.getInt(Constants.NBT.AW_Level)) {
                    case 0 -> {
                        if (tag.getInt(Constants.NBT.SOUL_LEVEL) >= 20) {
                            tag.putInt(Constants.NBT.AW_Level, 1);
                            tag.putInt(Constants.NBT.SOUL_LEVEL, 0);
                        }else{
                            player.sendMessage(new TextComponent("Not enough souls"), player.getUUID());
                        }
                    }
                    case 1 -> {
                        if (tag.getInt(Constants.NBT.SOUL_LEVEL) >= 50) {
                            tag.putInt(Constants.NBT.AW_Level, 2);
                            tag.putInt(Constants.NBT.SOUL_LEVEL, 0);
                        }else{
                            player.sendMessage(new TextComponent("Not enough souls"), player.getUUID());
                        }
                    }
                    default -> player.sendMessage(new TextComponent("Already maxed"), player.getUUID());
                }
            }
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

    public boolean isEmpty() {
        return stack.isEmpty();
    }
}
