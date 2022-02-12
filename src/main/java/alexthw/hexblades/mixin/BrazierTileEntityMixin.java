package alexthw.hexblades.mixin;

import elucent.eidolon.tile.BrazierTileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BrazierTileEntity.class)
public interface BrazierTileEntityMixin {

    @Invoker(remap = false)
    void callStartBurning();
}

