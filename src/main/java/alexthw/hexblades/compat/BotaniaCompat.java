package alexthw.hexblades.compat;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.client.render.entity.ArmorRenderer;
import alexthw.hexblades.common.items.armors.BotaniaArmor;
import alexthw.hexblades.common.items.armors.HexWArmor;
import alexthw.hexblades.network.RefillEffectPacket;
import alexthw.hexblades.registers.HexItem;
import alexthw.hexblades.util.HexUtils;
import elucent.eidolon.codex.Page;
import elucent.eidolon.codex.TitlePage;
import elucent.eidolon.codex.WorktablePage;
import elucent.eidolon.network.Networking;
import elucent.eidolon.recipe.WorktableRecipe;
import elucent.eidolon.recipe.WorktableRegistry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
import vazkii.botania.api.item.IPetalApothecary.State;
import vazkii.botania.common.block.tile.TileAltar;
import vazkii.botania.common.item.ModItems;

import java.util.List;

import static alexthw.hexblades.ConfigHandler.COMMON;
import static alexthw.hexblades.codex.CodexHexChapters.makePageKey;
import static alexthw.hexblades.codex.CodexHexChapters.nukeRecipe;

public class BotaniaCompat {

    public static void refillApotecaries(Level world, BlockPos urn) {

        List<TileAltar> apothecaries = HexUtils.getTilesWithinAABB(TileAltar.class, world, new AABB(urn.offset(-2, -1, -2), urn.offset(3, 2, 3)));

        for (TileAltar fillable : apothecaries) {
            if (fillable.getFluid() == State.EMPTY) {
                fillable.setFluid(State.WATER);
                Networking.sendToTracking(world, urn, new RefillEffectPacket(fillable.getBlockPos(), 0.5F));
            }
        }

    }

    public static HexWArmor makeArmor(EquipmentSlot slot, Item.Properties properties) {
        return new BotaniaArmor(slot, properties);
    }


    public static void addRecipes() {
        WorktableRegistry.register(new WorktableRecipe(new Object[]{
                ItemStack.EMPTY, ModItems.manaweaveCloth, ItemStack.EMPTY,
                ModItems.manaweaveCloth, HexItem.FOCUS_BASE.get(), ModItems.manaweaveCloth,
                ItemStack.EMPTY, ModItems.manaweaveCloth, ItemStack.EMPTY
        }, new Object[]{
                ModItems.terrasteelNugget,
                ModItems.terrasteelNugget,
                ModItems.terrasteelNugget,
                ModItems.terrasteelNugget
        }, new ItemStack(HexItem.FOCUS_BOTANIA.get(), 1)).setRegistryName(Hexblades.MODID, "botania_focus"));
    }

    @OnlyIn(Dist.CLIENT)
    public static void renderer() {
        GeoArmorRenderer.registerArmorRenderer(BotaniaArmor.class, new ArmorRenderer());
    }

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
}
