package alexthw.hexblades.ritual;

import elucent.eidolon.ritual.Ritual;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;

import elucent.eidolon.ritual.Ritual.RitualResult;

public class InfusionRitual extends Ritual {

    public static final ResourceLocation SYMBOL = new ResourceLocation("eidolon", "particle/warding_sign");
    ItemStack result;

    InfusionRitual(ItemStack result, int color) {
        this(SYMBOL, color);
        this.result = result;
    }

    public InfusionRitual(ResourceLocation symbol, int color) {
        super(symbol, color);
    }

    public RitualResult start(Level world, BlockPos pos) {
        if (!world.isClientSide) {
            world.addFreshEntity(new ItemEntity(world, (double) pos.getX() + 0.5D, (double) pos.getY() + 2.5D, (double) pos.getZ() + 0.5D, this.result.copy()));
            world.addFreshEntity(new ItemEntity(world, (double) pos.getX() + 0.5D, (double) pos.getY() + 2.5D, (double) pos.getZ() + 0.5D, new ItemStack(Items.BUCKET)));
        }
        return RitualResult.TERMINATE;
    }

}
