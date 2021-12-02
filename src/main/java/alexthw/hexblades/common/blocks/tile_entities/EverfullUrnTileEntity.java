package alexthw.hexblades.common.blocks.tile_entities;

import alexthw.hexblades.compat.BotaniaCompat;
import alexthw.hexblades.network.RefillEffectPacket;
import alexthw.hexblades.registers.HexTileEntityType;
import elucent.eidolon.Registry;
import elucent.eidolon.network.Networking;
import elucent.eidolon.particle.Particles;
import elucent.eidolon.tile.CrucibleTileEntity;
import elucent.eidolon.tile.TileEntityBase;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static alexthw.hexblades.ConfigHandler.COMMON;
import static alexthw.hexblades.util.CompatUtil.isBotaniaLoaded;
import static alexthw.hexblades.util.HexUtils.getTilesWithinAABB;
import static net.minecraftforge.fml.util.ObfuscationReflectionHelper.getPrivateValue;
import static net.minecraftforge.fml.util.ObfuscationReflectionHelper.setPrivateValue;

public class EverfullUrnTileEntity extends TileEntityBase implements BucketPickup {

    //Lists declared if shifting to subscribe scan is needed, do not access to them since they will be overwritten

    public List<CrucibleTileEntity> crucibles;
    public List<BlockPos> cauldrons;


    public EverfullUrnTileEntity(BlockPos worldPosition, BlockState blockState) {
        super(HexTileEntityType.EVERFULL_URN_TILE_ENTITY,worldPosition,blockState);
        cauldrons = null;
        crucibles = null;
    }

    public void tick() {
        if (level == null) return;

        if (level.isClientSide() && (level.getGameTime() % 2 == 0)) {
            //Particles.create(Registry.BUBBLE_PARTICLE).setScale(0.05F).setLifetime(10).randomOffset(0.125D, 0.0D).addVelocity(0, 0.05, 0).randomVelocity(0.0D, 0.15D).setColor(0.25F, 0.5F, 1).setAlpha(1.0F, 0.75F).setSpin(0.05F).spawn(this.level, (double) this.worldPosition.getX() + 0.5, (double) this.worldPosition.getY() + 0.9D, (double) this.worldPosition.getZ() + 0.5);
        } else if (level.getGameTime() % COMMON.UrnTickRate.get() == 0) {

            crucibles = getTilesWithinAABB(CrucibleTileEntity.class, getLevel(), new AABB(worldPosition.offset(-2, -1, -2), worldPosition.offset(3, 2, 3)));
            cauldrons = getCauldrons(getLevel(), new AABB(worldPosition.offset(-2, -1, -2), worldPosition.offset(3, 2, 3)));

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
            /*
            for (BlockPos fillable : cauldrons) {
                CauldronBlock cauldron = (CauldronBlock) level.getBlockState(fillable).getBlock();
                cauldron.setWaterLevel(level, fillable, level.getBlockState(fillable), 3);
                Networking.sendToTracking(this.level, this.worldPosition, new RefillEffectPacket(fillable, 1));

            }
             */

        }

    }

    private List<BlockPos> getCauldrons(Level world, AABB bb) {
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

    @Override
    public ItemStack pickupBlock(LevelAccessor p_152719_, BlockPos p_152720_, BlockState p_152721_) {
        return new ItemStack(Fluids.WATER.getBucket());
    }

    @Override
    public Optional<SoundEvent> getPickupSound() {
        return Optional.of(SoundEvents.BUCKET_FILL);
    }
}