package alexthw.hexblades.util;

import alexthw.hexblades.Hexblades;
import elucent.eidolon.util.ColorUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.ChunkAccess;

import java.util.*;
import java.util.function.Predicate;

public class HexUtils {

    public static ResourceLocation prefix(String path) {
        return new ResourceLocation(Hexblades.MODID, path);
    }

    @SafeVarargs
    @SuppressWarnings({"varargs", "SuspiciousMethodCalls"})
    public static <T> Collection<T> takeAll(Collection<? extends T> src, T... items) {
        List<T> ret = Arrays.asList(items);
        for (T item : items) {
            if (!src.contains(item)) {
                return Collections.emptyList();
            }
        }

        if (!src.removeAll(ret)) {
            return Collections.emptyList();
        }


        return ret;
    }
    public static <T> Collection<T> takeAll(Collection<T> src, Predicate<T> pred)
    {
        List<T> ret = new ArrayList<>();

        Iterator<T> iter = src.iterator();
        while (iter.hasNext())
        {
            T item = iter.next();
            if (pred.test(item))
            {
                iter.remove();
                ret.add(item);
            }
        }

        if (ret.isEmpty()) {
            return Collections.emptyList();
        }
        return ret;
    }

    public static <T extends BlockEntity> List<T> getTilesWithinAABB(Class<T> type, Level world, AABB bb) {
        List<T> tileList = new ArrayList<>();
        for (int i = (int) Math.floor(bb.minX); i < (int) Math.ceil(bb.maxX) + 16; i += 16) {
            for (int j = (int) Math.floor(bb.minZ); j < (int) Math.ceil(bb.maxZ) + 16; j += 16) {
                ChunkAccess c = world.getChunk(new BlockPos(i, 0, j));
                Set<BlockPos> tiles = c.getBlockEntitiesPos();
                for (BlockPos p : tiles)
                    if (bb.contains(p.getX() + 0.5, p.getY() + 0.5, p.getZ() + 0.5)) {
                        BlockEntity t = world.getBlockEntity(p);
                        if (type.isInstance(t)) {
                            tileList.add((T) t);
                        }
                    }
            }
        }
        return tileList;
    }

    public static Vec3 getVector(Level world, Player player) {
        HitResult ray = world.clip(new ClipContext(player.getEyePosition(0), player.getEyePosition(0).add(player.getLookAngle().scale(4)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));
        return (ray.getType() == HitResult.Type.BLOCK) ? ray.getLocation() : player.getEyePosition(0).add(player.getLookAngle().scale(4));
    }


    public static final int fireColor = ColorUtil.packColor(255, 230, 30, 40);
    public static final int iceColor = ColorUtil.packColor(255, 25, 140, 170);
    public static final int waterColor = ColorUtil.packColor(255, 55, 51, 171);
    public static final int earthColor = ColorUtil.packColor(255, 126, 70, 0);
    public static final int thunderColor = ColorUtil.packColor(255, 255, 255, 65);

    public static boolean chance(int c, Level world) {
        return c >= world.random.nextInt(100);
    }
}
