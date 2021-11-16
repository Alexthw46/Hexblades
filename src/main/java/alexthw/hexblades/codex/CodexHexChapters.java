package alexthw.hexblades.codex;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.deity.HexFacts;
import alexthw.hexblades.registers.HexBlock;
import alexthw.hexblades.registers.HexItem;
import alexthw.hexblades.registers.HexRegistry;
import alexthw.hexblades.ritual.HexRituals;
import alexthw.hexblades.util.CompatUtil;
import com.sammy.malum.core.init.items.MalumItems;
import elucent.eidolon.Registry;
import elucent.eidolon.codex.*;
import elucent.eidolon.spell.Signs;
import elucent.eidolon.util.ColorUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;

import java.util.List;

import static alexthw.hexblades.ConfigHandler.COMMON;
import static net.minecraftforge.fml.common.ObfuscationReflectionHelper.getPrivateValue;

public class CodexHexChapters {

    static Category HEXBLADES;
    static Category HEXBLADES_WEAPONS;
    static Chapter ICE_KATANA;
    static Chapter FLAME_SWORD;
    static Chapter WATER_SABER;
    static Chapter EARTH_HAMMER;
    static Chapter THUNDER_DUALS;
    static Chapter SANGUINE_UPGRADE;
    static Chapter HEX_SUMMONING;
    static Chapter HEX_EVOLUTION;
    static Chapter HEX_CORE;
    static Chapter HEX_PRAY;
    static Chapter HEX_ALLOY;
    static Chapter BLACK_PLANKS;
    static Chapter URN_OF_WATERS;
    static Chapter COMPAT;
    static Chapter MISC;
    static Chapter TEMPLES;
    static Chapter ARMORS;
    static Chapter HEXBLADES_INDEX;
    static Chapter HEXBLADES_WINDEX;

    static String prefix = Hexblades.MODID + ".codex.";

    static String makePageKey(String path) {
        return prefix + "page." + path;
    }

    static String makeChapterKey(String path) {
        return prefix + "chapter." + path;
    }

    public static void init() {
        //Main Index - page 1
        {
            BLACK_PLANKS = new Chapter(makeChapterKey("planks"), new TitlePage(makePageKey("dark_planks")),
                    new CraftingPage(new ItemStack(HexBlock.DARK_POLISH_PLANKS.getBlock().asItem(), 8),
                            new ItemStack(Registry.POLISHED_PLANKS.getBlock().asItem()), new ItemStack(Registry.POLISHED_PLANKS.getBlock().asItem()), new ItemStack(Registry.POLISHED_PLANKS.getBlock().asItem()),
                            new ItemStack(Registry.POLISHED_PLANKS.getBlock().asItem()), new ItemStack(Items.BLACK_DYE), new ItemStack(Registry.POLISHED_PLANKS.getBlock().asItem()),
                            new ItemStack(Registry.POLISHED_PLANKS.getBlock().asItem()), new ItemStack(Registry.POLISHED_PLANKS.getBlock().asItem()), new ItemStack(Registry.POLISHED_PLANKS.getBlock().asItem())
                    )
            );

            URN_OF_WATERS = new Chapter(makeChapterKey("urn"), new TitlePage(makePageKey("everfull_urn")),
                    new CraftingPage(new ItemStack(HexBlock.EVERFULL_URN.get()),
                            new ItemStack(Items.TERRACOTTA), new ItemStack(Items.BUCKET), new ItemStack(Items.TERRACOTTA),
                            new ItemStack(Registry.PEWTER_INGOT.get()), new ItemStack(Items.HEART_OF_THE_SEA), new ItemStack(Registry.PEWTER_INGOT.get()),
                            new ItemStack(Items.TERRACOTTA), new ItemStack(Items.TERRACOTTA), new ItemStack(Items.TERRACOTTA)
                    )
            );

            MISC = new Chapter(makeChapterKey("misc"),
                    new TitlePage(makePageKey("misc.soul_candy")),
                    nukeRecipe(COMMON.NUKE_CRUCIBLE.get(), new CruciblePage(new ItemStack(HexItem.SOUL_CANDY.get(), 4),
                            new CruciblePage.CrucibleStep(new ItemStack(Registry.SOUL_SHARD.get())),
                            new CruciblePage.CrucibleStep(1, new ItemStack(Items.SUGAR), new ItemStack(Items.HONEY_BOTTLE))
                    )), new ChantPage(makePageKey("misc.fire_touch"), Signs.SACRED_SIGN, Signs.SACRED_SIGN, Signs.SACRED_SIGN)
            );

            TEMPLES = new Chapter(makeChapterKey("structures"),
                    new TitlePage(makePageKey("fire_temple")),
                    new CraftingPage(new ItemStack(HexBlock.MAGMA_BRICKS.get(), 4),
                            ItemStack.EMPTY, new ItemStack(Items.NETHER_BRICK), ItemStack.EMPTY,
                            new ItemStack(Items.NETHER_BRICK), new ItemStack(Items.MAGMA_BLOCK), new ItemStack(Items.NETHER_BRICK),
                            ItemStack.EMPTY, new ItemStack(Items.NETHER_BRICK), ItemStack.EMPTY
                    )
            );

            if (CompatUtil.isMalumLoaded()) {
                COMPAT = new Chapter(makeChapterKey("compats"), new TitlePage(makePageKey("compats")), new TitlePage(makePageKey("compats.malum")), new ListPage(makePageKey("altar_ether"), new ListPage.ListEntry("brazier", new ItemStack(MalumItems.BLUE_ETHER_BRAZIER.get()))));
            } else {
                COMPAT = new Chapter(makeChapterKey("compats"), new TitlePage(makePageKey("compats")));
            }

            ARMORS = getArmorsPage();
        }

        //Hex Theurgy - page 2
        {
            HEX_PRAY = new Chapter(makeChapterKey("hex_pray"),
                    new ChantPage(makePageKey("hex_pray.0"), Signs.WICKED_SIGN, Signs.SOUL_SIGN, Signs.WICKED_SIGN),
                    new TextPage(makePageKey("hex_pray.1"))
            );

            HEX_CORE = new Chapter(makeChapterKey("hex_core"),
                    new TextPage(makePageKey("hex_core.0")),
                    new TextPage(makePageKey("hex_core.1"))
            );

            HEX_SUMMONING = new Chapter(makeChapterKey("hex_summon"),
                    new TextPage(makePageKey("hex_summon.0")),
                    new ChantPage(makePageKey("hex_summon.1"), Signs.SACRED_SIGN, Signs.SOUL_SIGN, Signs.MIND_SIGN, Signs.SOUL_SIGN, Signs.SACRED_SIGN),
                    nukeRecipe(COMMON.NUKE_RITUALS.get(), new RitualPage(HexRituals.SUMMON_FIRE, new ItemStack(Registry.SHADOW_GEM.get()),
                            new RitualPage.RitualIngredient(new ItemStack(Items.NETHER_BRICK), false),
                            new RitualPage.RitualIngredient(new ItemStack(Registry.CRIMSON_ESSENCE.get()), false),
                            new RitualPage.RitualIngredient(new ItemStack(Items.QUARTZ), false),
                            new RitualPage.RitualIngredient(new ItemStack(Items.LAVA_BUCKET), false),
                            new RitualPage.RitualIngredient(new ItemStack(Registry.CRIMSON_ESSENCE.get()), false),
                            new RitualPage.RitualIngredient(new ItemStack(Items.NETHER_BRICK), false)
                    ))
            );

            HEX_EVOLUTION = new Chapter(makeChapterKey("evolution"),
                    //evolve katana
                    new TitlePage(makePageKey("evolve_ice_katana")),
                    nukeRecipe(COMMON.NUKE_RITUALS.get(), new RitualPage(HexRituals.EVOLVE_KATANA, new ItemStack(HexItem.FROST_RAZOR.get()),
                            new RitualPage.RitualIngredient(new ItemStack(Items.QUARTZ), false),
                            new RitualPage.RitualIngredient(new ItemStack(Registry.WRAITH_HEART.get()), false),
                            new RitualPage.RitualIngredient(new ItemStack(HexItem.PATRON_SOUL.get()), true),
                            new RitualPage.RitualIngredient(PotionUtils.setPotion(new ItemStack(Items.POTION), Registry.CHILLED_POTION.get()), false),
                            new RitualPage.RitualIngredient(new ItemStack(Items.QUARTZ), false)
                    )),
                    //evolve sword
                    new TitlePage(makePageKey("evolve_flame_sword")),
                    nukeRecipe(COMMON.NUKE_RITUALS.get(), new RitualPage(HexRituals.EVOLVE_SWORD, new ItemStack(HexItem.FIRE_BRAND.get()),
                            new RitualPage.RitualIngredient(new ItemStack(Registry.CRIMSON_ESSENCE.get()), false),
                            new RitualPage.RitualIngredient(new ItemStack(Registry.CRIMSON_ESSENCE.get()), false),
                            new RitualPage.RitualIngredient(new ItemStack(HexItem.PATRON_SOUL.get()), true),
                            new RitualPage.RitualIngredient(new ItemStack(Registry.SHADOW_GEM.get()), false)
                    )),
                    //evolve saber
                    new TitlePage(makePageKey("evolve_water_saber")),
                    nukeRecipe(COMMON.NUKE_RITUALS.get(), new RitualPage(HexRituals.EVOLVE_SABER, new ItemStack(HexItem.WATER_SABER.get()),
                            new RitualPage.RitualIngredient(new ItemStack(Items.PRISMARINE_CRYSTALS), false),
                            new RitualPage.RitualIngredient(new ItemStack(HexItem.DROWNED_HEART.get()), false),
                            new RitualPage.RitualIngredient(new ItemStack(HexItem.PATRON_SOUL.get()), true),
                            new RitualPage.RitualIngredient(new ItemStack(Items.HEART_OF_THE_SEA), false),
                            new RitualPage.RitualIngredient(new ItemStack(Items.PRISMARINE_CRYSTALS), false)
                    )),
                    //evolve hammer
                    new TitlePage(makePageKey("evolve_earth_hammer")),
                    nukeRecipe(COMMON.NUKE_RITUALS.get(), new RitualPage(HexRituals.EVOLVE_HAMMER, new ItemStack(HexItem.EARTH_HAMMER.get()),
                            new RitualPage.RitualIngredient(new ItemStack(Registry.ARCANE_GOLD_BLOCK.get()), false),
                            new RitualPage.RitualIngredient(new ItemStack(HexItem.PATRON_SOUL.get()), true),
                            new RitualPage.RitualIngredient(new ItemStack(Registry.ARCANE_GOLD_BLOCK.get()), false)
                    )),
                    //evolve dual
                    new TitlePage(makePageKey("evolve_duals")),
                    nukeRecipe(COMMON.NUKE_RITUALS.get(), new RitualPage(HexRituals.EVOLVE_DAGGERS, new ItemStack(HexItem.PATRON_SOUL.get()),
                            new RitualPage.RitualIngredient(new ItemStack(Items.PHANTOM_MEMBRANE), false),
                            new RitualPage.RitualIngredient(new ItemStack(Items.REDSTONE), false),
                            new RitualPage.RitualIngredient(new ItemStack(HexItem.LIGHTNING_DAGGER_L.get()), true),
                            new RitualPage.RitualIngredient(new ItemStack(HexItem.LIGHTNING_DAGGER_R.get()), true),
                            new RitualPage.RitualIngredient(new ItemStack(Items.REDSTONE), false),
                            new RitualPage.RitualIngredient(new ItemStack(Items.PHANTOM_MEMBRANE), false)
                    ))
            );
        }

        ///Dull Weapons
        {
            HEX_ALLOY = new Chapter(makeChapterKey("hex_metal"), new TitlePage(makePageKey("hex_metal.0")),
                    //ingot
                    nukeRecipe(COMMON.NUKE_CRUCIBLE.get(), new CruciblePage(new ItemStack(HexItem.HEXIUM_INGOT.get(), 2),
                            new CruciblePage.CrucibleStep(new ItemStack(Items.GOLD_INGOT), new ItemStack(Registry.LEAD_INGOT.get())),
                            new CruciblePage.CrucibleStep(1, new ItemStack(Registry.ENCHANTED_ASH.get()), new ItemStack(Registry.SOUL_SHARD.get())
                            ))
                    ),
                    new TextPage(makePageKey("hex_metal.1")),
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
                            ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY)
            );
        }

        //HexBlades
        {
            FLAME_SWORD = new Chapter(makeChapterKey("flame_sword"),
                    new TitlePage(makePageKey("flame_sword")),
                    nukeRecipe(COMMON.NUKE_RITUALS.get(), new RitualPage(HexRituals.AWAKE_SWORD, new ItemStack(HexItem.DULL_BROADSWORD.get()),
                            new RitualPage.RitualIngredient(new ItemStack(Items.NETHERITE_SCRAP), false),
                            new RitualPage.RitualIngredient(new ItemStack(Items.NETHERITE_SCRAP), false),
                            new RitualPage.RitualIngredient(new ItemStack(HexItem.ELEMENTAL_CORE.get()), false),
                            new RitualPage.RitualIngredient(new ItemStack(Items.MAGMA_CREAM), false),
                            new RitualPage.RitualIngredient(new ItemStack(Items.MAGMA_CREAM), false)
                    )),
                    new TextPage(makePageKey("flame_sword.powers"))
            );

            ICE_KATANA = new Chapter(makeChapterKey("ice_katana"),
                    new TitlePage(makePageKey("ice_katana")),
                    nukeRecipe(COMMON.NUKE_RITUALS.get(), new RitualPage(HexRituals.AWAKE_KATANA, new ItemStack(HexItem.DULL_KATANA.get()),
                            new RitualPage.RitualIngredient(new ItemStack(Items.BLUE_ICE), false),
                            new RitualPage.RitualIngredient(new ItemStack(Items.BLUE_ICE), false),
                            new RitualPage.RitualIngredient(new ItemStack(HexItem.ELEMENTAL_CORE.get()), false),
                            new RitualPage.RitualIngredient(new ItemStack(Registry.TATTERED_CLOTH.get()), false),
                            new RitualPage.RitualIngredient(new ItemStack(Registry.WRAITH_HEART.get()), false)
                    )),
                    new TextPage(makePageKey("ice_katana.powers"))
            );

            WATER_SABER = new Chapter(makeChapterKey("water_saber"),
                    new TitlePage(makePageKey("water_saber")),
                    nukeRecipe(COMMON.NUKE_RITUALS.get(), new RitualPage(HexRituals.AWAKE_SABER, new ItemStack(HexItem.DULL_SABER.get()),
                            new RitualPage.RitualIngredient(new ItemStack(Items.PRISMARINE_SHARD), false),
                            new RitualPage.RitualIngredient(new ItemStack(Items.PRISMARINE_SHARD), false),
                            new RitualPage.RitualIngredient(new ItemStack(HexItem.ELEMENTAL_CORE.get()), false),
                            new RitualPage.RitualIngredient(new ItemStack(Items.PRISMARINE_SHARD), false),
                            new RitualPage.RitualIngredient(new ItemStack(HexItem.DROWNED_HEART.get()), false)
                    )),
                    new TextPage(makePageKey("water_saber.powers"))
            );

            EARTH_HAMMER = new Chapter(makeChapterKey("earth_hammer"),
                    new TitlePage(makePageKey("earth_hammer")),
                    nukeRecipe(COMMON.NUKE_RITUALS.get(), new RitualPage(HexRituals.AWAKE_HAMMER, new ItemStack(HexItem.DULL_HAMMER.get()),
                            new RitualPage.RitualIngredient(new ItemStack(Items.OBSIDIAN), false),
                            new RitualPage.RitualIngredient(new ItemStack(Items.OBSIDIAN), false),
                            new RitualPage.RitualIngredient(new ItemStack(HexItem.ELEMENTAL_CORE.get()), false),
                            new RitualPage.RitualIngredient(new ItemStack(Registry.LEAD_BLOCK.get()), false),
                            new RitualPage.RitualIngredient(new ItemStack(Registry.LEAD_BLOCK.get()), false)
                    )),
                    new TextPage(makePageKey("earth_hammer.powers")),
                    new TextPage(makePageKey("earth_hammer.powers_mine"))
            );

            THUNDER_DUALS = new Chapter(makeChapterKey("thunder_duals"),
                    new TitlePage(makePageKey("thunder_duals")),
                    nukeRecipe(COMMON.NUKE_RITUALS.get(), new RitualPage(HexRituals.AWAKE_DAGGER, new ItemStack(HexItem.DULL_DAGGER.get()),
                            new RitualPage.RitualIngredient(new ItemStack(Registry.ARCANE_GOLD_INGOT.get()), false),
                            new RitualPage.RitualIngredient(new ItemStack(Registry.ARCANE_GOLD_INGOT.get()), false),
                            new RitualPage.RitualIngredient(new ItemStack(HexItem.ELEMENTAL_CORE.get()), false),
                            new RitualPage.RitualIngredient(new ItemStack(Registry.PEWTER_INGOT.get()), false),
                            new RitualPage.RitualIngredient(new ItemStack(Registry.PEWTER_INGOT.get()), false)
                    )),
                    new TextPage(makePageKey("thunder_duals.powers"))
            );

            SANGUINE_UPGRADE = new Chapter(makeChapterKey("sanguine_upgrade"),
                    new TitlePage(makePageKey("sanguine_wither")),
                    nukeRecipe(COMMON.NUKE_RITUALS.get(), new RitualPage(HexRituals.EVOLVE_SAPPING, new ItemStack(Registry.SAPPING_SWORD.get()),
                            new RitualPage.RitualIngredient(new ItemStack(Registry.LESSER_SOUL_GEM.get()), false),
                            new RitualPage.RitualIngredient(PotionUtils.setPotion(new ItemStack(Items.POTION), HexRegistry.WITHER_POTION.get()), false),
                            new RitualPage.RitualIngredient(new ItemStack(Items.NETHER_STAR), true),
                            new RitualPage.RitualIngredient(PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.HEALING), false),
                            new RitualPage.RitualIngredient(new ItemStack(Registry.LESSER_SOUL_GEM.get()), false))
                    )
            );
        }

        makeIndexes();
    }

    static IndexPage.IndexEntry getCompatsPage() {
        if (CompatUtil.isMalumLoaded()) {
            return new IndexPage.IndexEntry(COMPAT, new ItemStack(MalumItems.BLUE_ETHER.get()));
        } else {
            return new IndexPage.IndexEntry(COMPAT, new ItemStack(Items.CAMPFIRE));
        }
    }

    static Chapter getArmorsPage() {
        Chapter Armors;
        CraftingPage helmCraft = new CraftingPage(new ItemStack(HexItem.HEX_ARMOR_H.get(), 1),
                new ItemStack(Registry.ARCANE_GOLD_INGOT.get()), new ItemStack(Registry.LESSER_SOUL_GEM.get()), new ItemStack(Registry.ARCANE_GOLD_INGOT.get()),
                new ItemStack(HexItem.HEXIUM_INGOT.get()), ItemStack.EMPTY, new ItemStack(HexItem.HEXIUM_INGOT.get())
        );
        CraftingPage chestCraft = new CraftingPage(new ItemStack(HexItem.HEX_ARMOR_C.get(), 1),
                new ItemStack(Registry.ARCANE_GOLD_INGOT.get()), ItemStack.EMPTY, new ItemStack(Registry.ARCANE_GOLD_INGOT.get()),
                new ItemStack(HexItem.HEXIUM_INGOT.get()), new ItemStack(Registry.SHADOW_GEM.get()), new ItemStack(HexItem.HEXIUM_INGOT.get()),
                new ItemStack(HexItem.HEXIUM_INGOT.get()), new ItemStack(HexItem.HEXIUM_INGOT.get()), new ItemStack(HexItem.HEXIUM_INGOT.get())
        );
        CraftingPage legsCraft = new CraftingPage(new ItemStack(HexItem.HEX_ARMOR_L.get(), 1),
                new ItemStack(Registry.ARCANE_GOLD_INGOT.get()), new ItemStack(HexItem.HEXIUM_INGOT.get()), new ItemStack(Registry.ARCANE_GOLD_INGOT.get()),
                new ItemStack(HexItem.HEXIUM_INGOT.get()), ItemStack.EMPTY, new ItemStack(HexItem.HEXIUM_INGOT.get()),
                new ItemStack(HexItem.HEXIUM_INGOT.get()), ItemStack.EMPTY, new ItemStack(HexItem.HEXIUM_INGOT.get())
        );
        CraftingPage bootsCraft = new CraftingPage(new ItemStack(HexItem.HEX_ARMOR_B.get(), 1),
                new ItemStack(Registry.PEWTER_INGOT.get()), ItemStack.EMPTY, new ItemStack(Registry.PEWTER_INGOT.get()),
                new ItemStack(HexItem.HEXIUM_INGOT.get()), ItemStack.EMPTY, new ItemStack(HexItem.HEXIUM_INGOT.get())
        );
        Armors = new Chapter(makeChapterKey("armors"),
                new TitlePage(makePageKey("circlet")),
                helmCraft,
                new TitlePage(makePageKey("chestplate")),
                chestCraft,
                new TitlePage(makePageKey("leggings")),
                legsCraft,
                new TitlePage(makePageKey("boots")),
                bootsCraft
        );
        return Armors;
    }

    static void makeIndexes() {
        HEXBLADES_INDEX = new Chapter(makeChapterKey("hex_index"),
                new TitledIndexPage("hexblades.codex.hex_index.0",
                        new IndexPage.IndexEntry(BLACK_PLANKS, new ItemStack(HexBlock.DARK_POLISH_PLANKS.getBlock().asItem())),
                        new IndexPage.IndexEntry(URN_OF_WATERS, new ItemStack(HexBlock.EVERFULL_URN.get().asItem())),
                        getCompatsPage(),
                        new IndexPage.IndexEntry(MISC, new ItemStack(HexBlock.SWORD_STAND.get().asItem())),
                        new IndexPage.IndexEntry(TEMPLES, new ItemStack(HexBlock.MAGMA_BRICKS.get().asItem())),
                        new IndexPage.IndexEntry(ARMORS, new ItemStack(HexItem.HEX_ARMOR_C.get()))
                ),
                new IndexPage(
                        new IndexPage.SignLockedEntry(HEX_PRAY, new ItemStack(HexItem.DEV_SWORD.get()), Signs.SOUL_SIGN),
                        new IndexPage.FactLockedEntry(HEX_CORE, new ItemStack(HexItem.ELEMENTAL_CORE.get()), HexFacts.AWAKENING_RITUAL),
                        new IndexPage.FactLockedEntry(HEX_SUMMONING, new ItemStack(HexItem.FIRE_CORE.get()), HexFacts.ELEMENTAL_SUMMON),
                        new IndexPage.FactLockedEntry(HEX_EVOLUTION, new ItemStack(HexItem.PATRON_SOUL2.get()), HexFacts.EVOLVE_RITUAL)
                )
        );

        HEXBLADES_WINDEX = new Chapter(makeChapterKey("weapon_index"),
                new TitledIndexPage("hexblades.codex.weapon_index.0",
                        new IndexPage.IndexEntry(HEX_ALLOY, new ItemStack(HexItem.HEXIUM_INGOT.get())),
                        new IndexPage.FactLockedEntry(ICE_KATANA, new ItemStack(HexItem.FROST_RAZOR.get()), HexFacts.AWAKENING_RITUAL),
                        new IndexPage.FactLockedEntry(FLAME_SWORD, new ItemStack(HexItem.FIRE_BRAND.get()), HexFacts.AWAKENING_RITUAL),
                        new IndexPage.FactLockedEntry(WATER_SABER, new ItemStack(HexItem.WATER_SABER.get()), HexFacts.AWAKENING_RITUAL),
                        new IndexPage.FactLockedEntry(EARTH_HAMMER, new ItemStack(HexItem.EARTH_HAMMER.get()), HexFacts.AWAKENING_RITUAL),
                        new IndexPage.FactLockedEntry(THUNDER_DUALS, new ItemStack(HexItem.LIGHTNING_DAGGER_L.get()), HexFacts.AWAKENING_RITUAL)
                ),
                new IndexPage(
                        new IndexPage.FactLockedEntry(SANGUINE_UPGRADE, new ItemStack(HexItem.BLOOD_SWORD.get()), HexFacts.EVOLVE_RITUAL)
                )
        );

        CodexChapters CChapters = new CodexChapters();
        List<Category> CCategories = getPrivateValue(CodexChapters.class, CChapters, "categories");

        if (CCategories != null) {
            CCategories.add(HEXBLADES = new Category(Hexblades.MODID, new ItemStack(HexItem.PATRON_SOUL.get()), ColorUtil.packColor(220, 0, 30, 66), HEXBLADES_INDEX));
            CCategories.add(HEXBLADES_WEAPONS = new Category("hexblades_weapons", new ItemStack(HexItem.DEV_SWORD.get()), ColorUtil.packColor(220, 0, 0, 46), HEXBLADES_WINDEX));
        }
    }

    static Page nukeRecipe(boolean flag, Page page) {
        return flag ? disabled : page;
    }

    static TextPage disabled = new TextPage("hexblades.codex.disabled");

}

