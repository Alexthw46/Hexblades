package alexthw.hexblades.compat;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.client.render.entity.ArmorRenderer;
import alexthw.hexblades.common.items.armors.BotaniaArmor;
import alexthw.hexblades.common.items.armors.HexWArmor;
import alexthw.hexblades.network.RefillEffectPacket;
import alexthw.hexblades.registers.HexItem;
import alexthw.hexblades.util.HexUtils;
import elucent.eidolon.network.Networking;
import elucent.eidolon.recipe.WorktableRecipe;
import elucent.eidolon.recipe.WorktableRegistry;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import vazkii.botania.api.item.IPetalApothecary.State;
import vazkii.botania.common.block.tile.TileAltar;
import vazkii.botania.common.item.ModItems;

import java.util.List;

public class BotaniaCompat {

    public static void refillApotecaries(World world, BlockPos urn) {

        List<TileAltar> apothecaries = HexUtils.getTilesWithinAABB(TileAltar.class, world, new AxisAlignedBB(urn.offset(-2, -1, -2), urn.offset(3, 2, 3)));

        for (TileAltar fillable : apothecaries) {
            if (fillable.getFluid() == State.EMPTY) {
                fillable.setFluid(State.WATER);
                Networking.sendToTracking(world, urn, new RefillEffectPacket(fillable.getBlockPos(), 0.5F));
            }
        }

    }

    public static HexWArmor makeArmor(EquipmentSlotType slot, Item.Properties properties) {
        return new BotaniaArmor(slot, properties);
    }


    public static void addRecipes() {
        WorktableRegistry.register(new WorktableRecipe(new Object[]{
                ItemStack.EMPTY, ModItems.manaweaveCloth, ItemStack.EMPTY,
                ModItems.manaweaveCloth, HexItem.FOCUS_BASE, ModItems.manaweaveCloth,
                ItemStack.EMPTY, ModItems.manaweaveCloth, ItemStack.EMPTY
        }, new Object[]{
                ModItems.terrasteelNugget,
                ModItems.terrasteelNugget,
                ModItems.terrasteelNugget,
                ModItems.terrasteelNugget
        }, new ItemStack(HexItem.FOCUS_BOTANIA.get(), 1)).setRegistryName(Hexblades.MODID, "botania_focus"));
    }

    public static void renderer() {
        GeoArmorRenderer.registerArmorRenderer(BotaniaArmor.class, new ArmorRenderer());
    }

}
