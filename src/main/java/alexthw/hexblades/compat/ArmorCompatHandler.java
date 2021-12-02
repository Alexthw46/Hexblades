package alexthw.hexblades.compat;

import alexthw.hexblades.common.items.armors.HexWArmor;
import alexthw.hexblades.registers.HexItem;
import alexthw.hexblades.util.CompatUtil;
import elucent.eidolon.Registry;
import elucent.eidolon.codex.Page;
import elucent.eidolon.codex.TitlePage;
import elucent.eidolon.codex.WorktablePage;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static alexthw.hexblades.ConfigHandler.COMMON;
import static alexthw.hexblades.codex.CodexHexChapters.makePageKey;
import static alexthw.hexblades.codex.CodexHexChapters.nukeRecipe;

public class ArmorCompatHandler {


    public static HexWArmor makeChest(Item.Properties properties) {
        EquipmentSlot slot = EquipmentSlot.CHEST;
        if (CompatUtil.isBotaniaLoaded() && CompatUtil.isArsNovLoaded())
            return ArsBotaniaHandler.makeArmor(slot, properties);
        if (CompatUtil.isBotaniaLoaded()) return BotaniaCompat.makeArmor(slot, properties);
        if (CompatUtil.isArsNovLoaded()) return ArsNouveauCompat.makeArmor(slot, properties);

        return new HexWArmor(slot, properties);
    }

    public static HexWArmor makeHead(Item.Properties properties) {
        EquipmentSlot slot = EquipmentSlot.HEAD;
        if (CompatUtil.isBotaniaLoaded() && CompatUtil.isArsNovLoaded())
            return ArsBotaniaHandler.makeArmor(slot, properties);
        if (CompatUtil.isBotaniaLoaded()) return BotaniaCompat.makeArmor(slot, properties);
        if (CompatUtil.isArsNovLoaded()) return ArsNouveauCompat.makeArmor(slot, properties);

        return new HexWArmor(slot, properties);
    }

    public static HexWArmor makeFeet(Item.Properties properties) {
        EquipmentSlot slot = EquipmentSlot.FEET;
        if (CompatUtil.isBotaniaLoaded() && CompatUtil.isArsNovLoaded())
            return ArsBotaniaHandler.makeArmor(slot, properties);
        if (CompatUtil.isBotaniaLoaded()) return BotaniaCompat.makeArmor(slot, properties);
        if (CompatUtil.isArsNovLoaded()) return ArsNouveauCompat.makeArmor(slot, properties);

        return new HexWArmor(slot, properties);
    }

    public static HexWArmor makeLegs(Item.Properties properties) {
        EquipmentSlot slot = EquipmentSlot.LEGS;
        if (CompatUtil.isBotaniaLoaded() && CompatUtil.isArsNovLoaded())
            return ArsBotaniaHandler.makeArmor(slot, properties);
        if (CompatUtil.isBotaniaLoaded()) return BotaniaCompat.makeArmor(slot, properties);
        if (CompatUtil.isArsNovLoaded()) return ArsNouveauCompat.makeArmor(slot, properties);

        return new HexWArmor(slot, properties);
    }

    public static void attachRenderers() {
        if (CompatUtil.isBotaniaLoaded() && CompatUtil.isArsNovLoaded()) {
            ArsBotaniaHandler.renderer();
            return;
        }
        if (CompatUtil.isBotaniaLoaded()) {
            BotaniaCompat.renderer();
        } else if (CompatUtil.isArsNovLoaded()) {
            ArsNouveauCompat.renderer();
        }
    }

    public static Page[] makeCodex() {

        List<Page> pages = new ArrayList<>();

        TitlePage warlock = new TitlePage(makePageKey("warlock_focus"));
        WorktablePage warlockCraft = new WorktablePage(new ItemStack(HexItem.FOCUS_WARLOCK.get(),1),
                ItemStack.EMPTY, new ItemStack(Registry.WICKED_WEAVE.get()), ItemStack.EMPTY,
                new ItemStack(Registry.WICKED_WEAVE.get()), new ItemStack(HexItem.FOCUS_BASE.get()), new ItemStack(Registry.WICKED_WEAVE.get()),
                ItemStack.EMPTY, new ItemStack(Registry.WICKED_WEAVE.get()), ItemStack.EMPTY,

                new ItemStack(Registry.ARCANE_GOLD_NUGGET.get()),
                new ItemStack(Registry.ARCANE_GOLD_NUGGET.get()),
                new ItemStack(Registry.ARCANE_GOLD_NUGGET.get()),
                new ItemStack(Registry.ARCANE_GOLD_NUGGET.get())
        );

        Collections.addAll(pages, warlock, nukeRecipe(COMMON.NUKE_WORKBENCH.get(),warlockCraft));

        if (CompatUtil.isBotaniaLoaded()) {
            Collections.addAll(pages,BotaniaCompat.makeCodex());
        }
        if (CompatUtil.isArsNovLoaded()) {
            Collections.addAll(pages,ArsNouveauCompat.makeCodex());
        }


        return pages.toArray(new Page[0]);
    }
}
