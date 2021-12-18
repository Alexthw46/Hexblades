package alexthw.hexblades.spells;

import alexthw.hexblades.common.blocks.tile_entities.SwordStandTileEntity;
import alexthw.hexblades.deity.HexDeities;
import elucent.eidolon.capability.IReputation;
import elucent.eidolon.spell.Sign;
import elucent.eidolon.spell.StaticSpell;
import elucent.eidolon.tile.EffigyTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import java.util.Comparator;
import java.util.List;

import static alexthw.hexblades.util.HexUtils.getTilesWithinAABB;

public class HexEvolutionSpell extends StaticSpell {
    public HexEvolutionSpell(ResourceLocation name, Sign... signs) {
        super(name, signs);
    }

    @Override
    public boolean canCast(Level level, BlockPos pos, Player player) {
        if (level.getCapability(IReputation.INSTANCE).resolve().isEmpty()) {
            return false;
        } else if (level.getCapability(IReputation.INSTANCE).resolve().get().getReputation(player, HexDeities.HEX_DEITY.getId()) < 15) {
            return false;
        } else {
            List<SwordStandTileEntity> stands = getTilesWithinAABB(SwordStandTileEntity.class, level, new AABB(pos.offset(-4, -4, -4), pos.offset(5, 5, 5)));
            return stands.size() > 0;
        }
    }

    @Override
    public void cast(Level level, BlockPos pos, Player player) {
        List<SwordStandTileEntity> stands = getTilesWithinAABB(SwordStandTileEntity.class, level, new AABB(pos.offset(-4, -4, -4), pos.offset(5, 5, 5)));
        if (stands.stream().anyMatch((e)-> !e.isEmpty())){
            SwordStandTileEntity nearest = stands.stream().filter((e)-> !e.isEmpty()).min(Comparator.comparingDouble((e) -> e.getBlockPos().distSqr(pos))).orElse(null);
            if (nearest == null) return;
            if (!level.isClientSide) {
                nearest.pray();
           } else {
               level.playSound(player, nearest.getBlockPos(), SoundEvents.LIGHTNING_BOLT_THUNDER, SoundSource.NEUTRAL, 10000.0F, 0.6F + level.random.nextFloat() * 0.2F);
               level.playSound(player, nearest.getBlockPos(), SoundEvents.LIGHTNING_BOLT_IMPACT, SoundSource.NEUTRAL, 2.0F, 0.5F + level.random.nextFloat() * 0.2F);
           }
        }
    }
}
