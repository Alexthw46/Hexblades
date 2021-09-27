package alexthw.hexblades.common.blocks.tile_entities;

import alexthw.hexblades.compat.BotaniaCompat;
import alexthw.hexblades.network.RefillEffectPacket;
import alexthw.hexblades.registers.HexTileEntityType;
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

import static alexthw.hexblades.ConfigHandler.COMMON;
import static alexthw.hexblades.util.CompatUtil.isBotaniaLoaded;
import static alexthw.hexblades.util.HexUtils.getTilesWithinAABB;
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
    public Fluid takeLiquid(IWorld worldIn, BlockPos pos, BlockState state) {
        return Fluids.WATER;
    }

    @Override
    public void tick() {
        if (level == null) return;

        if (level.isClientSide() && (level.getGameTime() % 2 == 0)) {
            Particles.create(Registry.BUBBLE_PARTICLE).setScale(0.05F).setLifetime(10).randomOffset(0.125D, 0.0D).addVelocity(0, 0.05, 0).randomVelocity(0.0D, 0.15D).setColor(0.25F, 0.5F, 1).setAlpha(1.0F, 0.75F).setSpin(0.05F).spawn(this.level, (double) this.worldPosition.getX() + 0.5, (double) this.worldPosition.getY() + 0.9D, (double) this.worldPosition.getZ() + 0.5);
        } else if (level.getGameTime() % COMMON.UrnTickRate.get() == 0) {

            crucibles = getTilesWithinAABB(CrucibleTileEntity.class, getLevel(), new AxisAlignedBB(worldPosition.offset(-2, -1, -2), worldPosition.offset(3, 2, 3)));
            cauldrons = getCauldrons(getLevel(), new AxisAlignedBB(worldPosition.offset(-2, -1, -2), worldPosition.offset(3, 2, 3)));

            if (isBotaniaLoaded()) {
                BotaniaCompat.refillApotecaries(getLevel(), worldPosition);
            }

            for (CrucibleTileEntity fillable : crucibles) {

                @SuppressWarnings("ConstantConditions") boolean hasWater = getPrivateValue(CrucibleTileEntity.class, fillable, "hasWater");

                if (!hasWater) {
                    setPrivateValue(CrucibleTileEntity.class, fillable, true, "hasWater");
                    fillable.sync();
                    Networking.sendToTracking(this.level, this.worldPosition, new RefillEffectPacket(fillable.getBlockPos(), 1));
                }
            }

            for (BlockPos fillable : cauldrons) {
                CauldronBlock cauldron = (CauldronBlock) level.getBlockState(fillable).getBlock();
                cauldron.setWaterLevel(level, fillable, level.getBlockState(fillable), 3);
                Networking.sendToTracking(this.level, this.worldPosition, new RefillEffectPacket(fillable, 1));

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
                        if (state.getValue(BlockStateProperties.LEVEL_CAULDRON) < 3) blockList.add(scan);
                    }
                }
            }
        }
        return blockList;
    }

}