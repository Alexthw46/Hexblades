package alexthw.hexblades.spells;

import alexthw.hexblades.mixin.BrazierTileEntityMixin;
import elucent.eidolon.network.IgniteEffectPacket;
import elucent.eidolon.network.Networking;
import elucent.eidolon.spell.Sign;
import elucent.eidolon.spell.StaticSpell;
import elucent.eidolon.tile.BrazierTileEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.CampfireBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.entity.CampfireBlockEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.List;

import static alexthw.hexblades.util.HexUtils.getTilesWithinAABB;
import static alexthw.hexblades.util.HexUtils.getVector;
import static net.minecraftforge.fml.util.ObfuscationReflectionHelper.findMethod;


public class FireTouchSpell extends StaticSpell {

    public FireTouchSpell(ResourceLocation name, Sign... signs) {
        super(name, signs);
    }

    @Override
    public boolean canCast(Level world, BlockPos blockPos, Player player) {
        Vec3 v = getVector(world, player);
        List<BrazierTileEntity> braziers = getTilesWithinAABB(BrazierTileEntity.class, world, new AABB(v.x - 1.5, v.y - 1.5, v.z - 1.5, v.x + 1.5, v.y + 1.5, v.z + 1.5));
        List<CampfireBlockEntity> campfires = getTilesWithinAABB(CampfireBlockEntity.class, world, new AABB(v.x - 1.5, v.y - 1.5, v.z - 1.5, v.x + 1.5, v.y + 1.5, v.z + 1.5));

        return braziers.size() + campfires.size() > 0;
    }

    @Override
    public void cast(Level world, BlockPos blockPos, Player player) {

        if (!world.isClientSide) {
            Vec3 v = getVector(world, player);
            List<BrazierTileEntity> braziers = getTilesWithinAABB(BrazierTileEntity.class, world, new AABB(v.x - 1.5, v.y - 1.5, v.z - 1.5, v.x + 1.5, v.y + 1.5, v.z + 1.5));
            List<CampfireBlockEntity> campfires = getTilesWithinAABB(CampfireBlockEntity.class, world, new AABB(v.x - 1.5, v.y - 1.5, v.z - 1.5, v.x + 1.5, v.y + 1.5, v.z + 1.5));

            if (braziers.size() > 0) {

                BrazierTileEntity b = braziers.stream().min(Comparator.comparingDouble((e) -> e.getBlockPos().distSqr(blockPos))).get();

                ((BrazierTileEntityMixin)b).callStartBurning();

                world.playSound(player, blockPos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.4F + 0.8F);

            } else if (campfires.size() > 0) {

                CampfireBlockEntity c = campfires.stream().min(Comparator.comparingDouble((e) -> e.getBlockPos().distSqr(blockPos))).get();
                BlockPos cPos = c.getBlockPos();
                BlockState campfire = world.getBlockState(cPos);

                if (CampfireBlock.canLight(campfire)) {
                    Networking.sendToTracking(world, cPos, new IgniteEffectPacket(cPos, 1.0F, 0.5F, 0.25F));
                    world.setBlock(c.getBlockPos(), campfire.setValue(BlockStateProperties.LIT, Boolean.TRUE), 11);
                }
            }
        }

    }

}
