package Alexthw.Hexblades.codex;

import Alexthw.Hexblades.registers.HexItem;
import elucent.eidolon.Registry;
import elucent.eidolon.codex.*;
import elucent.eidolon.spell.Sign;
import elucent.eidolon.spell.Signs;
import elucent.eidolon.util.ColorUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;

import java.util.List;

import static net.minecraftforge.fml.common.ObfuscationReflectionHelper.getPrivateValue;

public class CodexHexChapters {


    static Category HEXBLADES;
    static Chapter ICE_KATANA;
    static Chapter FLAME_SWORD;
    static Chapter WATER_SABER;
    static Chapter EARTH_HAMMER;
    static Chapter THUNDER_KNIVES;
    static Chapter HEX_CONVERSION;
    static Chapter HEXBLADES_INDEX;


    public static void init() {
        CodexChapters Cchapters = null;
        List<Category> Ccategories = getPrivateValue(elucent.eidolon.codex.CodexChapters.class, Cchapters, "categories");
        ICE_KATANA = new Chapter("hexblades.codex.chapter.ice_katana", new TitlePage("hexblades.codex.page.ice_katana"), new WorktablePage(new ItemStack(Registry.CLEAVING_AXE.get()), new ItemStack((IItemProvider) Registry.PEWTER_INGOT.get()), new ItemStack((IItemProvider) Registry.PEWTER_INGOT.get()), ItemStack.EMPTY, new ItemStack((IItemProvider) Registry.PEWTER_INGOT.get()), new ItemStack(Items.STICK), ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(Items.STICK), ItemStack.EMPTY, new ItemStack((IItemProvider) Registry.UNHOLY_SYMBOL.get()), ItemStack.EMPTY, new ItemStack((IItemProvider) Registry.PEWTER_INLAY.get()), ItemStack.EMPTY));
        ;
        HEX_CONVERSION = new Chapter("hexblades.codex.chapter.hex_touch", new Page[]{new ChantPage("hexblades.codex.page.hex_touch.0", new Sign[]{Signs.MIND_SIGN, Signs.SOUL_SIGN, Signs.MIND_SIGN, Signs.SOUL_SIGN}), new TextPage("hexblades.codex.page.hex_touch.1")});
        HEXBLADES_INDEX = new Chapter("hexblades.codex.chapter.index", new Page[]{new TitledIndexPage("hexblades.codex.page_index.0", new IndexPage.IndexEntry[]{new IndexPage.IndexEntry(HEX_CONVERSION, new ItemStack((IItemProvider) HexItem.HEXIUM_INGOT.get()))})});
        Ccategories.add(HEXBLADES = new Category("Hexblades", new ItemStack((IItemProvider) HexItem.PATRON_SOUL.get()), ColorUtil.packColor(255, 255, 255, 255), HEXBLADES_INDEX));


    }

}

