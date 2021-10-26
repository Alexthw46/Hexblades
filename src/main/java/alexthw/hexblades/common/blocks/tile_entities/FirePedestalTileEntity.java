package alexthw.hexblades.common.blocks.tile_entities;

import alexthw.hexblades.common.entity.FireElementalEntity;
import alexthw.hexblades.registers.HexEntityType;
import alexthw.hexblades.registers.HexItem;
import alexthw.hexblades.registers.HexTileEntityType;
import elucent.eidolon.Registry;
import elucent.eidolon.particle.Particles;
import elucent.eidolon.tile.TileEntityBase;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
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

public class FirePedestalTileEntity extends TileEntityBase implements IAnimatable, ITickableTileEntity {

    private final AnimationFactory factory = new AnimationFactory(this);
    private boolean isSummoning;
    public boolean hasCore;
    private int tickCounter;
    private int animationState;

    public FirePedestalTileEntity() {
        this(HexTileEntityType.FIRE_PEDESTAL_TILE_ENTITY);
        animationState = -1;
        isSummoning = false;
        hasCore = false;
        tickCounter = 0;

    }

    public FirePedestalTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public void tick() {
        if (!isSummoning || getLevel() == null) return;
        if (getLevel().isClientSide()) {
            Particles.create(Registry.WISP_PARTICLE)
                    .setAlpha(0.5F)
                    .setScale(Math.max((float) (tickCounter - 20) / 100, 0), 0.0F)
                    .setLifetime(10)
                    .randomOffset(0.1D, 0.1D)
                    .setColor(1.5F, 0.75F, 0.25F, 1.0F, 0.25F, 0.1F)
                    .spawn(getLevel(), this.getBlockPos().getX() + 0.5, this.getBlockPos().getY() + 3, this.getBlockPos().getZ() + 0.5);
        } else {
            if (tickCounter == 100) {
                FireElementalEntity fe = HexEntityType.FIRE_ELEMENTAL.get().create(getLevel());
                if (fe != null) {
                    fe.setPos((double) getBlockPos().getX() + 0.5D, (double) getBlockPos().getY() + 1.5D, (double) getBlockPos().getZ() + 0.5D);
                    getLevel().addFreshEntity(fe);
                }
                isSummoning = false;
                animationState = -1;
                this.sync();
            }
        }
        tickCounter++;

    }

    public ActionResultType onActivated(BlockState state, BlockPos pos, PlayerEntity player, Hand hand) {

        if (getLevel() != null && !getLevel().isClientSide())
            if (hand == Hand.MAIN_HAND && canUse()) {

                ItemStack itemHand = player.getItemInHand(hand);

                if (!itemHand.isEmpty() && !hasCore && (itemHand.getItem() == HexItem.FIRE_CORE.get())) {

                    itemHand.shrink(1);
                    if (player.getItemInHand(hand).isEmpty()) {
                        player.setItemInHand(hand, ItemStack.EMPTY);
                    }

                    hasCore = true;
                    animationState = 0;
                    this.sync();

                    return ActionResultType.SUCCESS;
                } else if (itemHand.isEmpty() && hasCore) {

                    player.addItem(new ItemStack(HexItem.FIRE_CORE.get()));

                    hasCore = false;
                    animationState = -1;
                    this.sync();

                    return ActionResultType.SUCCESS;
                }

            }
        return ActionResultType.PASS;
    }

    public void startSummoning() {
        animationState = 1;
        tickCounter = 0;
        hasCore = false;
        isSummoning = true;
        this.sync();
    }

    private boolean canUse() {
        return !isSummoning;
    }

    public int getAnimationState() {
        return animationState;
    }


    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <E extends TileEntityBase & IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        switch (getAnimationState()) {
            case 1:
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.fire_pedestal_start"));
                break;
            case 0:
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.core_placed"));
                break;
            default:
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.fire_pedestal_stop"));
                break;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public CompoundNBT save(CompoundNBT tag) {
        tag = super.save(tag);
        tag.putBoolean("isSummoning", isSummoning);
        tag.putBoolean("hasCore", hasCore);
        tag.putInt("tickCounter", tickCounter);
        tag.putInt("animation", getAnimationState());
        return tag;
    }

    @Override
    public void load(BlockState state, CompoundNBT tag) {
        super.load(state, tag);
        this.isSummoning = tag.getBoolean("isSummoning");
        this.hasCore = tag.getBoolean("hasCore");
        this.tickCounter = tag.getInt("tickCounter");
        this.animationState = tag.getInt("animation");
    }
}
