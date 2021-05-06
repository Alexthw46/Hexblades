package Alexthw.Hexblades.common.blocks.tile_entities;

import Alexthw.Hexblades.registers.HexTileEntityType;
import elucent.eidolon.tile.TileEntityBase;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;

public class SwordStandTileEntity extends TileEntityBase {

    ItemStack stack;

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
            InventoryHelper.spawnItemStack(this.world, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, this.stack);
        }

    }

    @Override
    public ActionResultType onActivated(BlockState state, BlockPos pos, PlayerEntity player, Hand hand) {
        if (hand == Hand.MAIN_HAND) {
            if (player.getHeldItem(hand).isEmpty() && !this.stack.isEmpty()) {
                player.addItemStackToInventory(this.stack);
                this.stack = ItemStack.EMPTY;
                if (!this.world.isRemote) {
                    this.sync();
                }

                return ActionResultType.SUCCESS;
            }

            if (!player.getHeldItem(hand).isEmpty() && this.stack.isEmpty()) {
                this.stack = player.getHeldItem(hand).copy();
                this.stack.setCount(1);
                player.getHeldItem(hand).shrink(1);
                if (player.getHeldItem(hand).isEmpty()) {
                    player.setHeldItem(hand, ItemStack.EMPTY);
                }

                if (!this.world.isRemote) {
                    this.sync();
                }

                return ActionResultType.SUCCESS;
            }
        }

        return ActionResultType.PASS;
    }

    @Override
    public void read(BlockState state, CompoundNBT tag) {
        super.read(state, tag);
        this.stack = ItemStack.read(tag.getCompound("stack"));
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag = super.write(tag);
        tag.put("stack", this.stack.write(new CompoundNBT()));
        return tag;
    }

    /*
    public ItemStack provide() {
        return this.stack.copy();
    }

    public void take() {
        this.stack = ItemStack.EMPTY;
        if (!this.world.isRemote) {
            this.sync();
        }

    }
 */
}
