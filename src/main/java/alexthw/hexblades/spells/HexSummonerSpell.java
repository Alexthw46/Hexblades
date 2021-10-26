package alexthw.hexblades.spells;

import alexthw.hexblades.common.blocks.tile_entities.FirePedestalTileEntity;
import elucent.eidolon.spell.Sign;
import elucent.eidolon.spell.StaticSpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.Comparator;
import java.util.List;

import static alexthw.hexblades.util.HexUtils.getTilesWithinAABB;
import static alexthw.hexblades.util.HexUtils.getVector;

public class HexSummonerSpell extends StaticSpell {

    public HexSummonerSpell(ResourceLocation name, Sign... signs) {
        super(name, signs);
    }

    @Override
    public boolean canCast(World world, BlockPos blockPos, PlayerEntity player) {
        Vector3d v = getVector(world, player);
        List<FirePedestalTileEntity> pedestals = getTilesWithinAABB(FirePedestalTileEntity.class, world, new AxisAlignedBB(v.x - 1.5, v.y - 1.5, v.z - 1.5, v.x + 1.5, v.y + 1.5, v.z + 1.5));
        return pedestals.stream().anyMatch((f) -> f.hasCore);
    }

    @Override
    public void cast(World world, BlockPos blockPos, PlayerEntity player) {

        if (!world.isClientSide) {
            Vector3d v = getVector(world, player);

            List<FirePedestalTileEntity> pedestals = getTilesWithinAABB(FirePedestalTileEntity.class, world, new AxisAlignedBB(v.x - 1.5, v.y - 1.5, v.z - 1.5, v.x + 1.5, v.y + 1.5, v.z + 1.5));

            if (pedestals.size() > 0) {

                FirePedestalTileEntity fb = pedestals.stream().filter((f) -> f.hasCore).min(Comparator.comparingDouble((e) -> e.getBlockPos().distSqr(blockPos))).get();

                fb.startSummoning();

            }
        }
    }
}
