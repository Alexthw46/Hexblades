package alexthw.hexblades.ritual;

import alexthw.hexblades.registers.HexItem;
import elucent.eidolon.ritual.Ritual;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AwakenRitual extends Ritual {
    public static final ResourceLocation SYMBOL = new ResourceLocation("eidolon", "particle/summon_ritual");
    ItemStack result;

    public AwakenRitual(ItemStack result, int color) {
        super(SYMBOL, color);
        this.result = result;
    }

    public RitualResult start(Level world, BlockPos pos) {
        if (!world.isClientSide) {
            world.addFreshEntity(new ItemEntity(world, (double) pos.getX() + 0.5D, (double) pos.getY() + 2.5D, (double) pos.getZ() + 0.5D, this.result.copy()));
            if (result.getItem() == HexItem.LIGHTNING_DAGGER_L.get()) {
                world.addFreshEntity(new ItemEntity(world, (double) pos.getX() + 0.5D, (double) pos.getY() + 2.5D, (double) pos.getZ() + 0.5D, new ItemStack(HexItem.LIGHTNING_DAGGER_R.get())));
            }
        }

        return RitualResult.TERMINATE;
    }
}
