package Alexthw.Hexblades.compat;

import Alexthw.Hexblades.network.RefillEffectPacket;
import Alexthw.Hexblades.util.HexUtils;
import elucent.eidolon.network.Networking;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.botania.common.block.tile.TileAltar;

import java.util.List;

import static vazkii.botania.api.item.IPetalApothecary.State;

public class BotaniaCompat {

    public static void refillApotecaries(World world, BlockPos urn, List<TileAltar> apothecaries) {

        apothecaries = HexUtils.getTilesWithinAABB(TileAltar.class, world, new AxisAlignedBB(urn.add(-2, -1, -2), urn.add(3, 2, 3)));

        for (TileAltar fillable : apothecaries) {
            if (fillable.getFluid() == State.EMPTY) {
                fillable.setFluid(State.WATER);
                Networking.sendToTracking(world, urn, new RefillEffectPacket(fillable.getPos(), 0.5F));
            }
        }

    }


}
