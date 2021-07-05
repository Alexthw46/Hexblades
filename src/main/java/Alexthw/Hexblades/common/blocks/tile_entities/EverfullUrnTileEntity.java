package Alexthw.Hexblades.common.blocks.tile_entities;

import Alexthw.Hexblades.common.particles.RefillEffectPacket;
import Alexthw.Hexblades.compat.BotaniaCompat;
import Alexthw.Hexblades.registers.HexTileEntityType;
import elucent.eidolon.Registry;
import elucent.eidolon.network.Networking;
import elucent.eidolon.particle.Particles;
import elucent.eidolon.tile.CrucibleTileEntity;
import elucent.eidolon.tile.TileEntityBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.botania.common.block.tile.TileAltar;

import java.util.ArrayList;
import java.util.List;

import static Alexthw.Hexblades.util.CompatUtil.isBotaniaLoaded;
import static Alexthw.Hexblades.util.HexUtils.getTilesWithinAABB;
import static net.minecraftforge.fml.common.ObfuscationReflectionHelper.getPrivateValue;
import static net.minecraftforge.fml.common.ObfuscationReflectionHelper.setPrivateValue;

public class EverfullUrnTileEntity extends TileEntityBase implements ITickableTileEntity {

    public List<CrucibleTileEntity> crucibles;
    public List<BlockPos> cauldrons;
    public List<TileAltar> apothecaries;


    public EverfullUrnTileEntity() {
        this(HexTileEntityType.EVERFULL_URN_TILE_ENTITY);
        cauldrons = null;
        crucibles = null;
        apothecaries = null;

    }

    public EverfullUrnTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public ActionResultType onActivated(BlockState state, BlockPos pos, PlayerEntity player, Hand hand) {
        if (hand == Hand.MAIN_HAND && world != null) {
            ItemStack itemstack = player.getHeldItem(hand);

            if (!player.getHeldItem(hand).isEmpty() && (player.getHeldItem(hand).getItem() instanceof BucketItem)) {

                if (!player.abilities.isCreativeMode) {
                    itemstack.shrink(1);
                    if (itemstack.isEmpty()) {
                        player.setHeldItem(hand, new ItemStack(Items.WATER_BUCKET));
                    } else if (!player.inventory.addItemStackToInventory(new ItemStack(Items.WATER_BUCKET))) {
                        player.dropItem(new ItemStack(Items.WATER_BUCKET), false);
                    }
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
    public void tick() {
        if (world == null) return;

        if (world.isRemote() && (world.getGameTime() % 2 == 0)) {
            Particles.create(Registry.BUBBLE_PARTICLE).setScale(0.05F).setLifetime(10).randomOffset(0.125D, 0.0D).addVelocity(0, 0.05, 0).randomVelocity(0.0D, 0.15D).setColor(0.25F, 0.5F, 1).setAlpha(1.0F, 0.75F).setSpin(0.05F).spawn(this.world, (double) this.pos.getX() + 0.5, (double) this.pos.getY() + 0.9D, (double) this.pos.getZ() + 0.5);
        } else if (world.getGameTime() % 100 == 0) {

            crucibles = getTilesWithinAABB(CrucibleTileEntity.class, getWorld(), new AxisAlignedBB(pos.add(-2, -1, -2), pos.add(3, 2, 3)));
            cauldrons = getCauldrons(getWorld(), new AxisAlignedBB(pos.add(-2, -1, -2), pos.add(3, 2, 3)));

            if (isBotaniaLoaded()) {
                BotaniaCompat.refillApotecaries(getWorld(), pos, apothecaries);
            }

            for (CrucibleTileEntity fillable : crucibles) {

                @SuppressWarnings("ConstantConditions") boolean hasWater = getPrivateValue(CrucibleTileEntity.class, fillable, "hasWater");

                if (!hasWater) {
                    setPrivateValue(CrucibleTileEntity.class, fillable, true, "hasWater");
                    fillable.sync();
                    Networking.sendToTracking(this.world, this.pos, new RefillEffectPacket(fillable.getPos(), 1));
                }
            }

            for (BlockPos fillable : cauldrons) {
                CauldronBlock cauldron = (CauldronBlock) world.getBlockState(fillable).getBlock();
                cauldron.setWaterLevel(world, fillable, world.getBlockState(fillable), 3);
                Networking.sendToTracking(this.world, this.pos, new RefillEffectPacket(fillable, 1));

            }
        }
    }

    private List<BlockPos> getCauldrons(World world, AxisAlignedBB bb) {
        List<BlockPos> blockList = new ArrayList<>();
        for (int i = (int) Math.floor(bb.minX); i < (int) Math.ceil(bb.maxX); i++) {
            for (int j = (int) Math.floor(bb.minZ); j < (int) Math.ceil(bb.maxZ); j++) {
                for (int k = (int) Math.floor(bb.minY); k < (int) Math.ceil(bb.maxY); k++) {
                    BlockPos scan = new BlockPos(i, k, j);
                    BlockState state = world.getBlockState(scan);
                    Block block = state.getBlock();
                    if (block instanceof CauldronBlock) {
                        if (state.get(BlockStateProperties.LEVEL_0_3) < 3) blockList.add(scan);
                    }
                }
            }
        }
        return blockList;
    }

}