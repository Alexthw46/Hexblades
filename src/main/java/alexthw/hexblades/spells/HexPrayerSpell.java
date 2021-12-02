package alexthw.hexblades.spells;

import elucent.eidolon.Registry;
import elucent.eidolon.block.HorizontalBlockBase;
import elucent.eidolon.capability.ReputationProvider;
import elucent.eidolon.deity.Deity;
import elucent.eidolon.particle.Particles;
import elucent.eidolon.ritual.Ritual;
import elucent.eidolon.spell.AltarInfo;
import elucent.eidolon.spell.Sign;
import elucent.eidolon.spell.StaticSpell;
import elucent.eidolon.tile.EffigyTileEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import java.util.Comparator;
import java.util.List;

public class HexPrayerSpell extends StaticSpell {
    Deity deity;

    public HexPrayerSpell(ResourceLocation name, Deity deity, Sign... signs) {
        super(name, signs);
        this.deity = deity;
    }

    public boolean canCast(Level world, BlockPos pos, Player player) {
        if (world.getCapability(ReputationProvider.CAPABILITY).resolve().isEmpty()) {
            return false;
        } else if (world.getCapability(ReputationProvider.CAPABILITY).resolve().get().canPray(player, world.getGameTime())) {
            return false;
        } else {
            List<EffigyTileEntity> effigies = Ritual.getTilesWithinAABB(EffigyTileEntity.class, world, new AABB(pos.offset(-4, -4, -4), pos.offset(5, 5, 5)));
            if (effigies.size() == 0) {
                return false;
            } else {
                EffigyTileEntity effigy = effigies.stream().min(Comparator.comparingDouble((e) -> e.getBlockPos().distSqr(pos))).get();
                return effigy.ready();
            }
        }
    }

    public void cast(Level world, BlockPos pos, Player player) {
        List<EffigyTileEntity> effigies = Ritual.getTilesWithinAABB(EffigyTileEntity.class, world, new AABB(pos.offset(-4, -4, -4), pos.offset(5, 5, 5)));
        if (effigies.size() != 0) {
            EffigyTileEntity effigy = effigies.stream().min(Comparator.comparingDouble((e) -> e.getBlockPos().distSqr(pos))).get();
            if (!world.isClientSide) {
                effigy.pray();
                AltarInfo info = AltarInfo.getAltarInfo(world, effigy.getBlockPos());
                world.getCapability(ReputationProvider.CAPABILITY, null).ifPresent((rep) -> {
                    rep.pray(player, world.getGameTime());
                    double prev = rep.getReputation(player, this.deity.getId());
                    rep.addReputation(player, this.deity.getId(), 1.0D + 0.25D * info.getPower());
                    this.deity.onReputationChange(player, rep, prev, rep.getReputation(player, this.deity.getId()));
                });
            } else {
                world.playSound(player, effigy.getBlockPos(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.NEUTRAL, 10000.0F, 0.6F + world.random.nextFloat() * 0.2F);
                world.playSound(player, effigy.getBlockPos(), SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.NEUTRAL, 2.0F, 0.5F + world.random.nextFloat() * 0.2F);
                BlockState state = world.getBlockState(effigy.getBlockPos());
                Direction dir = state.getValue(HorizontalBlockBase.HORIZONTAL_FACING);
                Direction tangent = dir.getClockWise();
                float x = (float) effigy.getBlockPos().getX() + 0.5F + (float) dir.getStepX() * 0.21875F;
                float y = (float) effigy.getBlockPos().getY() + 0.8125F;
                float z = (float) effigy.getBlockPos().getZ() + 0.5F + (float) dir.getStepZ() * 0.21875F;
                Particles.create(Registry.FLAME_PARTICLE).setColor(this.deity.getRed(), this.deity.getGreen(), this.deity.getBlue()).setAlpha(0.5F, 0.0F).setScale(0.125F, 0.0625F).randomOffset(0.009999999776482582D).randomVelocity(0.0024999999441206455D).addVelocity(0.0D, 0.004999999888241291D, 0.0D).repeat(world, x + 0.09375F * (float) tangent.getStepX(), y, z + 0.09375F * (float) tangent.getStepZ(), 8);
                Particles.create(Registry.FLAME_PARTICLE).setColor(this.deity.getRed(), this.deity.getGreen(), this.deity.getBlue()).setAlpha(0.5F, 0.0F).setScale(0.1875F, 0.125F).randomOffset(0.009999999776482582D).randomVelocity(0.0024999999441206455D).addVelocity(0.0D, 0.004999999888241291D, 0.0D).repeat(world, x - 0.09375F * (float) tangent.getStepX(), y, z - 0.09375F * (float) tangent.getStepZ(), 8);
            }

        }
    }
}
