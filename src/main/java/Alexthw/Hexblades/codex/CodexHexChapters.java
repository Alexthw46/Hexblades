package Alexthw.Hexblades.codex;

import Alexthw.Hexblades.deity.HexFacts;
import Alexthw.Hexblades.registers.HexBlock;
import Alexthw.Hexblades.registers.HexItem;
import Alexthw.Hexblades.ritual.HexRituals;
import elucent.eidolon.Registry;
import elucent.eidolon.codex.*;
import elucent.eidolon.spell.Signs;
import elucent.eidolon.util.ColorUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.List;

import static net.minecraftforge.fml.common.ObfuscationReflectionHelper.getPrivateValue;

public class CodexHexChapters {


    static Category HEXBLADES;
    static Chapter ICE_KATANA;
    static Chapter FLAME_SWORD;
    static Chapter WATER_SABER;
    static Chapter EARTH_HAMMER;
    static Chapter THUNDER_DUALS;
    static Chapter HEX_INFUSION;
    static Chapter HEX_CORE;
    static Chapter HEX_PRAY;
    static Chapter HEX_ALLOY;
    static Chapter DARK_WOOD;
    static Chapter HEXBLADES_INDEX;


    public static void init() {
        CodexChapters Cchapters = new CodexChapters();
        List<Category> Ccategories = getPrivateValue(elucent.eidolon.codex.CodexChapters.class, Cchapters, "categories");

        DARK_WOOD = new Chapter("hexblades.codex.chapter.dark_wood", new TitlePage("hexblades.codex.page.dark_planks"),
                new CraftingPage(new ItemStack(HexBlock.DARK_POLISH_PLANKS.getBlock().asItem()),
                        new ItemStack(Registry.POLISHED_PLANKS.getBlock().asItem()), new ItemStack(Registry.POLISHED_PLANKS.getBlock().asItem()), new ItemStack(Registry.POLISHED_PLANKS.getBlock().asItem()),
                        new ItemStack(Registry.POLISHED_PLANKS.getBlock().asItem()), new ItemStack(Items.BLACK_DYE), new ItemStack(Registry.POLISHED_PLANKS.getBlock().asItem()),
                        new ItemStack(Registry.POLISHED_PLANKS.getBlock().asItem()), new ItemStack(Registry.POLISHED_PLANKS.getBlock().asItem()), new ItemStack(Registry.POLISHED_PLANKS.getBlock().asItem())
                ));

        HEX_ALLOY = new Chapter("hexblades.codex.chapter.hex_metal", new TitlePage("hexblades.codex.page.hex_metal.0"),
                //ingot
                new CruciblePage(new ItemStack(HexItem.HEXIUM_INGOT.get()),
                        new CruciblePage.CrucibleStep(new ItemStack(Registry.SILVER_INGOT.get()), new ItemStack(Registry.LEAD_INGOT.get())),
                        new CruciblePage.CrucibleStep(1, new ItemStack(Registry.ENCHANTED_ASH.get()), new ItemStack(Registry.SOUL_SHARD.get()))),
                new TextPage("hexblades.codex.page.hex_metal.1"),
                //katana
                new CraftingPage(new ItemStack(HexItem.DULL_KATANA.get()),
                        ItemStack.EMPTY, ItemStack.EMPTY, new ItemStack(HexItem.HEXIUM_INGOT.get()),
                        ItemStack.EMPTY, new ItemStack(HexItem.HEXIUM_INGOT.get()), ItemStack.EMPTY,
                        new ItemStack(Items.STICK), ItemStack.EMPTY, ItemStack.EMPTY),
                //sword
                new CraftingPage(new ItemStack(HexItem.DULL_BROADSWORD.get()),
                        ItemStack.EMPTY, new ItemStack(HexItem.HEXIUM_INGOT.get()), ItemStack.EMPTY,
                        ItemStack.EMPTY, new ItemStack(HexItem.HEXIUM_INGOT.get()), ItemStack.EMPTY,
                        ItemStack.EMPTY, new ItemStack(Items.STICK), ItemStack.EMPTY),
                //cutlass
                new CraftingPage(new ItemStack(HexItem.DULL_SABER.get()),
                        ItemStack.EMPTY, new ItemStack(HexItem.HEXIUM_INGOT.get()), ItemStack.EMPTY,
                        ItemStack.EMPTY, new ItemStack(HexItem.HEXIUM_INGOT.get()), ItemStack.EMPTY,
                        new ItemStack(HexItem.HEXIUM_INGOT.get()), new ItemStack(Items.STICK), ItemStack.EMPTY),
                //hammer
                new CraftingPage(new ItemStack(HexItem.DULL_HAMMER.get()),
                        new ItemStack(HexItem.HEXIUM_INGOT.get()), new ItemStack(HexItem.HEXIUM_INGOT.get()), new ItemStack(HexItem.HEXIUM_INGOT.get()),
                        new ItemStack(HexItem.HEXIUM_INGOT.get()), new ItemStack(Items.STICK), new ItemStack(HexItem.HEXIUM_INGOT.get()),
                        ItemStack.EMPTY, new ItemStack(Items.STICK), ItemStack.EMPTY),
                //dagger
                new CraftingPage(new ItemStack(HexItem.DULL_DAGGER.get()),
                        ItemStack.EMPTY, new ItemStack(HexItem.HEXIUM_INGOT.get()), ItemStack.EMPTY,
                        new ItemStack(Items.STICK), ItemStack.EMPTY, ItemStack.EMPTY,
                        ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY));

        //Hex Spells

        HEX_PRAY = new Chapter("hexblades.codex.chapter.hex_pray",
                new ChantPage("hexblades.codex.page.hex_pray.0", Signs.WICKED_SIGN, Signs.SOUL_SIGN, Signs.WICKED_SIGN),
                new TextPage("hexblades.codex.page.hex_pray.1"));

        HEX_CORE = new Chapter("hexblades.codex.chapter.hex_core",
                new TextPage("hexblades.codex.page.hex_core.0"),
                new TextPage("hexblades.codex.page.hex_core.1"));

        HEX_INFUSION = new Chapter("hexblades.codex.chapter.hex_touch",
                new ChantPage("hexblades.codex.page.hex_touch.0", Signs.MIND_SIGN, Signs.SOUL_SIGN, Signs.MIND_SIGN, Signs.SOUL_SIGN),
                new TextPage("hexblades.codex.page.hex_touch.1"),
                //evolve katana
                new TitlePage("hexblades.codex.page.evolve_ice_katana"),
                new RitualPage(HexRituals.EVOLVE_KATANA, new ItemStack(HexItem.PATRON_SOUL.get()),
                        new RitualPage.RitualIngredient(new ItemStack(Registry.WRAITH_HEART.get()), false),
                        new RitualPage.RitualIngredient(new ItemStack(HexItem.FROST_RAZOR.get()), true),
                        new RitualPage.RitualIngredient(new ItemStack(Registry.WRAITH_HEART.get()), false)
                ),
                //evolve sword
                new TitlePage("hexblades.codex.page.evolve_flame_sword"),
                new RitualPage(HexRituals.EVOLVE_SWORD, new ItemStack(HexItem.PATRON_SOUL.get()),
                        new RitualPage.RitualIngredient(new ItemStack(Items.MAGMA_BLOCK), false),
                        new RitualPage.RitualIngredient(new ItemStack(HexItem.FIRE_BRAND.get()), true),
                        new RitualPage.RitualIngredient(new ItemStack(Registry.SHADOW_GEM.get()), false)
                ),
                //evolve saber
                new TitlePage("hexblades.codex.page.evolve_water_saber"),
                new RitualPage(HexRituals.EVOLVE_SABER, new ItemStack(HexItem.PATRON_SOUL.get()),
                        new RitualPage.RitualIngredient(new ItemStack(HexItem.DROWNED_HEART.get()), false),
                        new RitualPage.RitualIngredient(new ItemStack(HexItem.WATER_SABER.get()), true),
                        new RitualPage.RitualIngredient(new ItemStack(Items.HEART_OF_THE_SEA), false
                        )
                ),
                //evolve hammer
                new TitlePage("hexblades.codex.page.evolve_earth_hammer"),
                new RitualPage(HexRituals.EVOLVE_HAMMER, new ItemStack(HexItem.PATRON_SOUL.get()),
                        new RitualPage.RitualIngredient(new ItemStack(HexItem.EARTH_HAMMER.get()), true),
                        new RitualPage.RitualIngredient(new ItemStack(Registry.ARCANE_GOLD_BLOCK.get()), false)
                ),
                //evolve dual
                new TitlePage("hexblades.codex.page.evolve_duals"),
                new RitualPage(HexRituals.EVOLVE_DAGGERS, new ItemStack(HexItem.PATRON_SOUL.get()),
                        new RitualPage.RitualIngredient(new ItemStack(HexItem.LIGHTNING_DAGGER_L.get()), true),
                        new RitualPage.RitualIngredient(new ItemStack(HexItem.LIGHTNING_DAGGER_R.get()), true)
                )
        );

        //HexBlades
        ICE_KATANA = new Chapter("hexblades.codex.chapter.ice_katana",
                new TitlePage("hexblades.codex.page.ice_katana"),
                new RitualPage(HexRituals.AWAKE_KATANA, new ItemStack(HexItem.DULL_KATANA.get()),
                        new RitualPage.RitualIngredient(new ItemStack(Items.BLUE_ICE), false),
                        new RitualPage.RitualIngredient(new ItemStack(Items.BLUE_ICE), false),
                        new RitualPage.RitualIngredient(new ItemStack(HexItem.ELEMENTAL_CORE.get()), false),
                        new RitualPage.RitualIngredient(new ItemStack(Registry.TATTERED_CLOTH.get()), false),
                        new RitualPage.RitualIngredient(new ItemStack(Registry.WRAITH_HEART.get()), false)
                ),
                new TextPage("hexblades.codex.page.ice_katana.powers")
        );
        FLAME_SWORD = new Chapter("hexblades.codex.chapter.flame_sword",
                new TitlePage("hexblades.codex.page.flame_sword"),
                new RitualPage(HexRituals.AWAKE_SWORD, new ItemStack(HexItem.DULL_BROADSWORD.get()),
                        new RitualPage.RitualIngredient(new ItemStack(Items.NETHERITE_SCRAP), false),
                        new RitualPage.RitualIngredient(new ItemStack(Items.NETHERITE_SCRAP), false),
                        new RitualPage.RitualIngredient(new ItemStack(HexItem.ELEMENTAL_CORE.get()), false),
                        new RitualPage.RitualIngredient(new ItemStack(Registry.CRIMSON_ESSENCE.get()), false),
                        new RitualPage.RitualIngredient(new ItemStack(Registry.CRIMSON_ESSENCE.get()), false)
                ),
                new TextPage("hexblades.codex.page.flame_sword.powers"));
        WATER_SABER = new Chapter("hexblades.codex.chapter.water_saber",
                new TitlePage("hexblades.codex.page.water_saber"),
                new RitualPage(HexRituals.AWAKE_SABER, new ItemStack(HexItem.DULL_SABER.get()),
                        new RitualPage.RitualIngredient(new ItemStack(Items.PRISMARINE_SHARD), false),
                        new RitualPage.RitualIngredient(new ItemStack(Items.PRISMARINE_SHARD), false),
                        new RitualPage.RitualIngredient(new ItemStack(HexItem.ELEMENTAL_CORE.get()), false),
                        new RitualPage.RitualIngredient(new ItemStack(Items.PRISMARINE_SHARD), false),
                        new RitualPage.RitualIngredient(new ItemStack(HexItem.DROWNED_HEART.get()), false)
                ),
                new TextPage("hexblades.codex.page.water_saber.powers"));
        EARTH_HAMMER = new Chapter("hexblades.codex.chapter.earth_hammer",
                new TitlePage("hexblades.codex.page.earth_hammer"),
                new RitualPage(HexRituals.AWAKE_HAMMER, new ItemStack(HexItem.DULL_HAMMER.get()),
                        new RitualPage.RitualIngredient(new ItemStack(Items.OBSIDIAN), false),
                        new RitualPage.RitualIngredient(new ItemStack(Items.OBSIDIAN), false),
                        new RitualPage.RitualIngredient(new ItemStack(HexItem.ELEMENTAL_CORE.get()), false),
                        new RitualPage.RitualIngredient(new ItemStack(Registry.LEAD_BLOCK.get()), false),
                        new RitualPage.RitualIngredient(new ItemStack(Registry.LEAD_BLOCK.get()), false)
                ),
                new TextPage("hexblades.codex.page.earth_hammer.powers"),
                new TextPage("hexblades.codex.page.earth_hammer.powers_mine"));

        THUNDER_DUALS = new Chapter("hexblades.codex.chapter.thunder_duals",
                new TitlePage("hexblades.codex.page.thunder_duals"),
                new RitualPage(HexRituals.AWAKE_DAGGER, new ItemStack(HexItem.DULL_DAGGER.get()),
                        new RitualPage.RitualIngredient(new ItemStack(Registry.ARCANE_GOLD_INGOT.get()), false),
                        new RitualPage.RitualIngredient(new ItemStack(Registry.ARCANE_GOLD_INGOT.get()), false),
                        new RitualPage.RitualIngredient(new ItemStack(HexItem.ELEMENTAL_CORE.get()), false),
                        new RitualPage.RitualIngredient(new ItemStack(Registry.SILVER_INGOT.get()), false),
                        new RitualPage.RitualIngredient(new ItemStack(Registry.SILVER_INGOT.get()), false)
                ),
                new TextPage("hexblades.codex.page.thunder_duals.powers"));

        HEXBLADES_INDEX = new Chapter("hexblades.codex.chapter.hex_index",
                new TitledIndexPage("hexblades.codex.hex_index.0",
                        new IndexPage.IndexEntry(DARK_WOOD, new ItemStack(HexBlock.DARK_POLISH_PLANKS.getBlock().asItem())),
                        new IndexPage.IndexEntry(HEX_ALLOY, new ItemStack(HexItem.HEXIUM_INGOT.get())),
                        new IndexPage.SignLockedEntry(HEX_PRAY, new ItemStack(HexItem.DEV_SWORD.get()), Signs.SOUL_SIGN),
                        new IndexPage.FactLockedEntry(HEX_CORE, new ItemStack(HexItem.ELEMENTAL_CORE.get()), HexFacts.AWAKENING_RITUAL),
                        new IndexPage.FactLockedEntry(HEX_INFUSION, new ItemStack(HexItem.PATRON_SOUL2.get()), HexFacts.STAR_INFUSION)
                ),
                new IndexPage(
                        new IndexPage.FactLockedEntry(ICE_KATANA, new ItemStack(HexItem.FROST_RAZOR.get()), HexFacts.AWAKENING_RITUAL),
                        new IndexPage.FactLockedEntry(FLAME_SWORD, new ItemStack(HexItem.FIRE_BRAND.get()), HexFacts.AWAKENING_RITUAL),
                        new IndexPage.FactLockedEntry(WATER_SABER, new ItemStack(HexItem.WATER_SABER.get()), HexFacts.AWAKENING_RITUAL),
                        new IndexPage.FactLockedEntry(EARTH_HAMMER, new ItemStack(HexItem.EARTH_HAMMER.get()), HexFacts.AWAKENING_RITUAL),
                        new IndexPage.FactLockedEntry(THUNDER_DUALS, new ItemStack(HexItem.LIGHTNING_DAGGER_L.get()), HexFacts.AWAKENING_RITUAL)

                ));

        if (Ccategories != null) {
            Ccategories.add(HEXBLADES = new Category("hexblades", new ItemStack(HexItem.PATRON_SOUL.get()), ColorUtil.packColor(255, 0, 0, 46), HEXBLADES_INDEX));
        }
    }

}

