package alexthw.hexblades.mixin;

import elucent.eidolon.tile.CrucibleTileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CrucibleTileEntity.class)
public interface CrucibleMixin {

    @Accessor boolean isHasWater();

    @Accessor void setHasWater(boolean hasWater);
}
