package alexthw.hexblades.compat;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.client.render.entity.ArmorRenderer;
import alexthw.hexblades.common.items.armors.HexWArmor;
import alexthw.hexblades.common.items.armors.NouveauArmor;
import alexthw.hexblades.registers.HexItem;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import elucent.eidolon.Registry;
import elucent.eidolon.codex.Page;
import elucent.eidolon.codex.TitlePage;
import elucent.eidolon.codex.WorktablePage;
import elucent.eidolon.recipe.WorktableRecipe;
import elucent.eidolon.recipe.WorktableRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

import static alexthw.hexblades.ConfigHandler.COMMON;
import static alexthw.hexblades.codex.CodexHexChapters.makePageKey;
import static alexthw.hexblades.codex.CodexHexChapters.nukeRecipe;

public class ArsNouveauCompat {

    public static HexWArmor makeArmor(EquipmentSlot slot, Item.Properties properties) {
        return new NouveauArmor(slot, properties);
    }

    public static boolean spellbookInOffHand(Player player) {
        return (player.getItemInHand(InteractionHand.OFF_HAND).getItem() instanceof SpellBook);
    }

    @Deprecated
    public static void addRecipes() {
        WorktableRegistry.register(new WorktableRecipe(new Object[]{
                ItemStack.EMPTY, ItemsRegistry.BLAZE_FIBER, ItemStack.EMPTY,
                ItemsRegistry.BLAZE_FIBER, HexItem.FOCUS_BASE.get(), ItemsRegistry.BLAZE_FIBER,
                ItemStack.EMPTY, ItemsRegistry.BLAZE_FIBER, ItemStack.EMPTY
        }, new Object[]{
                ItemsRegistry.manaGem,
                Registry.ENDER_CALX.get(),
                ItemsRegistry.manaGem,
                Registry.ENDER_CALX.get()
        }, new ItemStack(HexItem.FOCUS_NOUVEAU.get(), 1)).setRegistryName(Hexblades.MODID, "ars_nouveau_focus"));
    }


    @OnlyIn(Dist.CLIENT)
    public static void renderer() {
        GeoArmorRenderer.registerArmorRenderer(NouveauArmor.class, new ArmorRenderer());
    }

    public static Page[] makeCodex() {

        TitlePage titled = new TitlePage(makePageKey("ars_focus"));
        WorktablePage worktable = new WorktablePage(new ItemStack(HexItem.FOCUS_NOUVEAU.get(),1),
                ItemStack.EMPTY, new ItemStack(ItemsRegistry.BLAZE_FIBER), ItemStack.EMPTY,
                new ItemStack(ItemsRegistry.BLAZE_FIBER), new ItemStack(HexItem.FOCUS_BASE.get()), new ItemStack(ItemsRegistry.BLAZE_FIBER),
                ItemStack.EMPTY, new ItemStack(ItemsRegistry.BLAZE_FIBER), ItemStack.EMPTY,

                new ItemStack(ItemsRegistry.manaGem),
                new ItemStack(Registry.ENDER_CALX.get()),
                new ItemStack(ItemsRegistry.manaGem),
                new ItemStack(Registry.ENDER_CALX.get())
        );

        return new Page[]{titled,nukeRecipe(COMMON.NUKE_WORKBENCH.get(),worktable)};
    }
}
