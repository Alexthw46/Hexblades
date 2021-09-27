package alexthw.hexblades.compat;

import alexthw.hexblades.network.RefillEffectPacket;
import alexthw.hexblades.util.HexUtils;
import elucent.eidolon.network.Networking;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import vazkii.botania.api.item.IPetalApothecary.State;
import vazkii.botania.common.block.tile.TileAltar;

import java.util.List;

public class BotaniaCompat {

    public static void refillApotecaries(World world, BlockPos urn) {

        List<TileAltar> apothecaries = HexUtils.getTilesWithinAABB(TileAltar.class, world, new AxisAlignedBB(urn.offset(-2, -1, -2), urn.offset(3, 2, 3)));

        for (TileAltar fillable : apothecaries) {
            if (fillable.getFluid() == State.EMPTY) {
                fillable.setFluid(State.WATER);
                Networking.sendToTracking(world, urn, new RefillEffectPacket(fillable.getBlockPos(), 0.5F));
            }
        }

    }


}
