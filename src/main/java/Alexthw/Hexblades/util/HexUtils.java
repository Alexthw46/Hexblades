package Alexthw.Hexblades.util;

import Alexthw.Hexblades.Hexblades;
import elucent.eidolon.util.ColorUtil;
import net.minecraft.util.ResourceLocation;

import java.util.*;
import java.util.function.Predicate;

public class HexUtils {

    public static ResourceLocation prefix(String path) {
        return new ResourceLocation(Hexblades.MOD_ID, path);
    }

    @SafeVarargs
    @SuppressWarnings("varargs")
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

    public static final int fireColor = ColorUtil.packColor(255, 230, 30, 40);
    public static final int iceColor = ColorUtil.packColor(255, 25, 140, 170);
    public static final int waterColor = ColorUtil.packColor(255, 55, 51, 171);
    public static final int earthColor = ColorUtil.packColor(255, 126, 70, 0);
    public static final int thunderColor = ColorUtil.packColor(255, 255, 255, 65);
}
