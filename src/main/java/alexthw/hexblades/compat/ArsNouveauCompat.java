package alexthw.hexblades.compat;

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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
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

    @OnlyIn(Dist.CLIENT)
    public static void renderer() {
        GeoArmorRenderer.registerArmorRenderer(NouveauArmor.class, new ArmorRenderer());
    }

    public static Page[] makeCodex() {

        TitlePage titled = new TitlePage(makePageKey("ars_focus"));
        WorktablePage worktable = new WorktablePage(new ItemStack(HexItem.FOCUS_NOUVEAU.get(),1),
                ItemStack.EMPTY, new ItemStack((ItemLike) ItemsRegistry.BLAZE_FIBER), ItemStack.EMPTY,
                new ItemStack((ItemLike) ItemsRegistry.BLAZE_FIBER), new ItemStack(HexItem.FOCUS_BASE.get()), new ItemStack((ItemLike) ItemsRegistry.BLAZE_FIBER),
                ItemStack.EMPTY, new ItemStack((ItemLike) ItemsRegistry.BLAZE_FIBER), ItemStack.EMPTY,

                new ItemStack((ItemLike) ItemsRegistry.manaGem),
                new ItemStack(Registry.ENDER_CALX.get()),
                new ItemStack((ItemLike) ItemsRegistry.manaGem),
                new ItemStack(Registry.ENDER_CALX.get())
        );

        return new Page[]{titled,nukeRecipe(COMMON.NUKE_WORKBENCH.get(),worktable)};
    }
}
