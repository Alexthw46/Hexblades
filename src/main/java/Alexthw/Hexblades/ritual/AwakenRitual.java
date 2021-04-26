package Alexthw.Hexblades.ritual;

import elucent.eidolon.ritual.Ritual;
import elucent.eidolon.util.ColorUtil;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AwakenRitual extends Ritual {
    public static final ResourceLocation SYMBOL = new ResourceLocation("eidolon", "particle/summon_ritual");
    ItemStack result;

    public AwakenRitual(ItemStack result) {
        super(SYMBOL, ColorUtil.packColor(255, 255, 51, 85));
        this.result = result;    }

    public RitualResult start(World world, BlockPos pos) {
        if (!world.isRemote) {
            world.addEntity(new ItemEntity(world, (double)pos.getX() + 0.5D, (double)pos.getY() + 2.5D, (double)pos.getZ() + 0.5D, this.result.copy()));
        }

        return RitualResult.TERMINATE;
    }
}
