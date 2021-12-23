package alexthw.hexblades.mixin;

import elucent.eidolon.spell.AltarEntry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AltarEntry.class)
public interface AltarEntryMixin {

    @Invoker
    AltarEntry callSetPower(double power);

    @Invoker
    AltarEntry callSetCapacity(double capacity);
}
