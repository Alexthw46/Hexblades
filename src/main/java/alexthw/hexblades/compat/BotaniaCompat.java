package alexthw.hexblades.compat;

import alexthw.hexblades.client.render.entity.HexArmorRenderer;
import alexthw.hexblades.common.items.armors.BotaniaArmor;
import alexthw.hexblades.common.items.armors.HexWArmor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
//import vazkii.botania.api.item.IPetalApothecary.State;
//import vazkii.botania.common.block.tile.TileAltar;
//import vazkii.botania.common.item.ModItems;


public class BotaniaCompat {

    public static void refillApotecaries(Level world, BlockPos urn) {

        /*TODO
        List<TileAltar> apothecaries = HexUtils.getTilesWithinAABB(TileAltar.class, world, new AABB(urn.offset(-2, -1, -2), urn.offset(3, 2, 3)));

        for (TileAltar fillable : apothecaries) {
            if (fillable.getFluid() == State.EMPTY) {
                fillable.setFluid(State.WATER);
                Networking.sendToTracking(world, urn, new RefillEffectPacket(fillable.getBlockPos(), 0.5F));
            }
        }
        */
    }

    public static HexWArmor makeArmor(EquipmentSlot slot, Item.Properties properties) {
        return new BotaniaArmor(slot, properties);
    }

    @OnlyIn(Dist.CLIENT)
    public static void renderer() {
        GeoArmorRenderer.registerArmorRenderer(BotaniaArmor.class, new HexArmorRenderer());
    }

    /* TODO reneable when ported
    public static Page[] makeCodex() {
        TitlePage titled = new TitlePage(makePageKey("botania_focus"));
        WorktablePage worktable = new WorktablePage(new ItemStack(HexItem.FOCUS_BOTANIA.get(),1),
                ItemStack.EMPTY, new ItemStack(ModItems.manaweaveCloth), ItemStack.EMPTY,
                new ItemStack(ModItems.manaweaveCloth), new ItemStack(HexItem.FOCUS_BASE.get()), new ItemStack(ModItems.manaweaveCloth),
                ItemStack.EMPTY, new ItemStack(ModItems.manaweaveCloth), ItemStack.EMPTY,

                new ItemStack(ModItems.terrasteelNugget),
                new ItemStack(ModItems.terrasteelNugget),
                new ItemStack(ModItems.terrasteelNugget),
                new ItemStack(ModItems.terrasteelNugget)
        );
        return new Page[]{titled,nukeRecipe(COMMON.NUKE_WORKBENCH.get(),worktable)};
    }
     */
}
