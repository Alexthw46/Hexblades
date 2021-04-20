package Alexthw.Hexblades.codex;

import elucent.eidolon.codex.*;
import elucent.eidolon.spell.Sign;
import elucent.eidolon.spell.Signs;

public class CodexHexChapters {


    static Chapter ICE_KATANA; //new Chapter("hexblades.codex.chapter.ice_katana", new TitlePage("hexblades.codex.page.ice_katana"), new WorktablePage(new ItemStack(Registry.CLEAVING_AXE.get()), new ItemStack((IItemProvider)Registry.PEWTER_INGOT.get()), new ItemStack((IItemProvider)Registry.PEWTER_INGOT.get()), ItemStack.EMPTY, new ItemStack((IItemProvider)Registry.PEWTER_INGOT.get()), new ItemStack(Items.STICK), ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(Items.STICK), ItemStack.EMPTY, new ItemStack((IItemProvider)Registry.UNHOLY_SYMBOL.get()), ItemStack.EMPTY, new ItemStack((IItemProvider)Registry.PEWTER_INLAY.get()), ItemStack.EMPTY));
    static Chapter FLAME_SWORD;
    static Chapter WATER_SABER;
    static Chapter EARTH_HAMMER;
    static Chapter THUNDER_KNIVES;
    static Chapter HEX_CONVERSION;


    public static void init() {
        HEX_CONVERSION = new Chapter("hexblades.codex.chapter.hex_touch", new Page[]{new ChantPage("hexblades.codex.page.hex_touch.0", new Sign[]{Signs.MIND_SIGN, Signs.SOUL_SIGN, Signs.MIND_SIGN, Signs.SOUL_SIGN}), new TextPage("hexblades.codex.page.hex_touch.1")});

        //CodexChapters.categories.add(HEXBLADES = new Category("Hexblades", new ItemStack((IItemProvider) HexItem.PATRON_SOUL.get()), ColorUtil.packColor(255, 89, 143, 76), HEXBLADES_INDEX));


    }

}

