package Alexthw.Hexblades.common.blocks.tile_entities;

import Alexthw.Hexblades.compat.BotaniaCompat;
import Alexthw.Hexblades.network.RefillEffectPacket;
import Alexthw.Hexblades.registers.HexTileEntityType;
import elucent.eidolon.Registry;
import elucent.eidolon.network.Networking;
import elucent.eidolon.particle.Particles;
import elucent.eidolon.tile.CrucibleTileEntity;
import elucent.eidolon.tile.TileEntityBase;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CauldronBlock;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import static Alexthw.Hexblades.ConfigHandler.COMMON;
import static Alexthw.Hexblades.util.CompatUtil.isBotaniaLoaded;
import static Alexthw.Hexblades.util.HexUtils.getTilesWithinAABB;
import static net.minecraftforge.fml.common.ObfuscationReflectionHelper.getPrivateValue;
import static net.minecraftforge.fml.common.ObfuscationReflectionHelper.setPrivateValue;

public class EverfullUrnTileEntity extends TileEntityBase implements ITickableTileEntity, IBucketPickupHandler {

    //Lists declared if shifting to subscribe scan is needed, do not access to them since they will be overwritten

    public List<CrucibleTileEntity> crucibles;
    public List<BlockPos> cauldrons;


    public EverfullUrnTileEntity() {
        this(HexTileEntityType.EVERFULL_URN_TILE_ENTITY);
        cauldrons = null;
        crucibles = null;
    }

    public EverfullUrnTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public Fluid pickupFluid(IWorld worldIn, BlockPos pos, BlockState state) {
        return Fluids.WATER;
    }

    @Override
    public void tick() {
        if (world == null) return;

        if (world.isRemote() && (world.getGameTime() % 2 == 0)) {
            Particles.create(Registry.BUBBLE_PARTICLE).setScale(0.05F).setLifetime(10).randomOffset(0.125D, 0.0D).addVelocity(0, 0.05, 0).randomVelocity(0.0D, 0.15D).setColor(0.25F, 0.5F, 1).setAlpha(1.0F, 0.75F).setSpin(0.05F).spawn(this.world, (double) this.pos.getX() + 0.5, (double) this.pos.getY() + 0.9D, (double) this.pos.getZ() + 0.5);
        } else if (world.getGameTime() % COMMON.UrnTickRate.get() == 0) {

            crucibles = getTilesWithinAABB(CrucibleTileEntity.class, getWorld(), new AxisAlignedBB(pos.add(-2, -1, -2), pos.add(3, 2, 3)));
            cauldrons = getCauldrons(getWorld(), new AxisAlignedBB(pos.add(-2, -1, -2), pos.add(3, 2, 3)));

            if (isBotaniaLoaded()) {
                BotaniaCompat.refillApotecaries(getWorld(), pos);
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