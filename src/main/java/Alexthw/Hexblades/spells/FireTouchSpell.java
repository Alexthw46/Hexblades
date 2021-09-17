package Alexthw.Hexblades.spells;

import elucent.eidolon.network.IgniteEffectPacket;
import elucent.eidolon.network.Networking;
import elucent.eidolon.spell.Sign;
import elucent.eidolon.spell.StaticSpell;
import elucent.eidolon.tile.BrazierTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.CampfireBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.CampfireTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static Alexthw.Hexblades.util.HexUtils.getTilesWithinAABB;
import static Alexthw.Hexblades.util.HexUtils.getVector;
import static net.minecraftforge.fml.common.ObfuscationReflectionHelper.findMethod;


public class FireTouchSpell extends StaticSpell {

    public FireTouchSpell(ResourceLocation name, Sign... signs) {
        super(name, signs);
    }

    @Override
    public boolean canCast(World world, BlockPos blockPos, PlayerEntity player) {
        Vector3d v = getVector(world, player);
        List<BrazierTileEntity> braziers = getTilesWithinAABB(BrazierTileEntity.class, world, new AxisAlignedBB(v.x - 1.5, v.y - 1.5, v.z - 1.5, v.x + 1.5, v.y + 1.5, v.z + 1.5));
        List<CampfireTileEntity> campfires = getTilesWithinAABB(CampfireTileEntity.class, world, new AxisAlignedBB(v.x - 1.5, v.y - 1.5, v.z - 1.5, v.x + 1.5, v.y + 1.5, v.z + 1.5));

        return braziers.size() + campfires.size() > 0;
    }

    @Override
    public void cast(World world, BlockPos blockPos, PlayerEntity player) {

        if (!world.isRemote) {
            Vector3d v = getVector(world, player);
            List<BrazierTileEntity> braziers = getTilesWithinAABB(BrazierTileEntity.class, world, new AxisAlignedBB(v.x - 1.5, v.y - 1.5, v.z - 1.5, v.x + 1.5, v.y + 1.5, v.z + 1.5));
            List<CampfireTileEntity> campfires = getTilesWithinAABB(CampfireTileEntity.class, world, new AxisAlignedBB(v.x - 1.5, v.y - 1.5, v.z - 1.5, v.x + 1.5, v.y + 1.5, v.z + 1.5));

            for (BrazierTileEntity b : braziers) {

                Method burn = findMethod(BrazierTileEntity.class, "startBurning");

                try {
                    burn.invoke(b);
                    world.playSound(player, blockPos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
            for (CampfireTileEntity c : campfires) {
                BlockPos cPos = c.getPos();
                BlockState campfire = world.getBlockState(cPos);
                if (CampfireBlock.canBeLit(campfire)) {
                    Networking.sendToTracking(world, cPos, new IgniteEffectPacket(cPos, 1.0F, 0.5F, 0.25F));
                    world.setBlockState(c.getPos(), campfire.with(BlockStateProperties.LIT, Boolean.TRUE), 11);
                }

            }
        }
    }
}
