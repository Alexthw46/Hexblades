package Alexthw.Hexblades.spells;

import elucent.eidolon.spell.Sign;
import elucent.eidolon.spell.StaticSpell;
import elucent.eidolon.tile.BrazierTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static Alexthw.Hexblades.util.HexUtils.getTilesWithinAABB;
import static net.minecraftforge.fml.common.ObfuscationReflectionHelper.findMethod;


public class FireTouchSpell extends StaticSpell {


    public FireTouchSpell(ResourceLocation name, Sign... signs) {
        super(name, signs);
    }

    @Override
    public boolean canCast(World world, BlockPos blockPos, PlayerEntity player) {
        RayTraceResult ray = world.rayTraceBlocks(new RayTraceContext(player.getEyePosition(0), player.getEyePosition(0).add(player.getLookVec().scale(4)), RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
        Vector3d v = (ray.getType() == RayTraceResult.Type.BLOCK) ? ray.getHitVec() : player.getEyePosition(0).add(player.getLookVec().scale(4));
        List<BrazierTileEntity> braziers = getTilesWithinAABB(BrazierTileEntity.class, world, new AxisAlignedBB(v.x - 1.5, v.y - 1.5, v.z - 1.5, v.x + 1.5, v.y + 1.5, v.z + 1.5));

        return braziers.size() > 0;
    }

    @Override
    public void cast(World world, BlockPos blockPos, PlayerEntity player) {

        if (!world.isRemote) {

            RayTraceResult ray = world.rayTraceBlocks(new RayTraceContext(player.getEyePosition(0), player.getEyePosition(0).add(player.getLookVec().scale(4)), RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
            Vector3d v = (ray.getType() == RayTraceResult.Type.BLOCK) ? ray.getHitVec() : player.getEyePosition(0).add(player.getLookVec().scale(4));
            List<BrazierTileEntity> braziers = getTilesWithinAABB(BrazierTileEntity.class, world, new AxisAlignedBB(v.x - 1.5, v.y - 1.5, v.z - 1.5, v.x + 1.5, v.y + 1.5, v.z + 1.5));

            for (BrazierTileEntity b : braziers) {

                Method burn = findMethod(BrazierTileEntity.class, "startBurning");

                try {
                    burn.invoke(b);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
