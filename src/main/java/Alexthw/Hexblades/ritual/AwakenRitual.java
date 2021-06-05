package Alexthw.Hexblades.ritual;

import Alexthw.Hexblades.registers.HexItem;
import elucent.eidolon.ritual.Ritual;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AwakenRitual extends Ritual {
    public static final ResourceLocation SYMBOL = new ResourceLocation("eidolon", "particle/summon_ritual");
    ItemStack result;

    public AwakenRitual(ItemStack result, int color) {
        super(SYMBOL, color);
        this.result = result;
    }

    public RitualResult start(World world, BlockPos pos) {
        if (!world.isRemote) {
            world.addEntity(new ItemEntity(world, (double) pos.getX() + 0.5D, (double) pos.getY() + 2.5D, (double) pos.getZ() + 0.5D, this.result.copy()));
            if (result.getItem() == HexItem.LIGHTNING_DAGGER_L.get()) {
                world.addEntity(new ItemEntity(world, (double) pos.getX() + 0.5D, (double) pos.getY() + 2.5D, (double) pos.getZ() + 0.5D, new ItemStack(HexItem.LIGHTNING_DAGGER_R.get())));
            }
        }

        return RitualResult.TERMINATE;
    }
}
