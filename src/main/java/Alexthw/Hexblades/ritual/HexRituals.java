package Alexthw.Hexblades.ritual;

import Alexthw.Hexblades.registers.HexItem;
import elucent.eidolon.Registry;
import elucent.eidolon.ritual.ItemRequirement;
import elucent.eidolon.ritual.Ritual;
import elucent.eidolon.ritual.RitualRegistry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.IItemProvider;

import static elucent.eidolon.Registry.CRIMSON_ESSENCE;

public class HexRituals {

    public static Ritual AWAKE_SWORD;
    public static Ritual AWAKE_KATANA;
    public static Ritual AWAKE_SABER;
    public static Ritual AWAKE_HAMMER;
    public static Ritual AWAKE_DAGGER;

    public static Ritual EVOLVE_SWORD;
    public static Ritual EVOLVE_KATANA;
    public static Ritual EVOLVE_SABER;
    public static Ritual EVOLVE_HAMMER;
    public static Ritual EVOLVE_DAGGERS;


    public static void init(){
        AWAKE_SWORD = RitualRegistry.register(HexItem.DULL_BROADSWORD.get(), (new AwakenRitual(new ItemStack((IItemProvider) HexItem.FIRE_BRAND.get())).setRegistryName("hexblades", "awake_flame_sword").addRequirement(new ItemRequirement((Item) HexItem.PATRON_SOUL.get()))).addRequirement(new ItemRequirement((Item) Items.NETHERITE_SCRAP)).addRequirement(new ItemRequirement(Items.NETHERITE_SCRAP)).addRequirement(new ItemRequirement(CRIMSON_ESSENCE.get())).addRequirement(new ItemRequirement(CRIMSON_ESSENCE.get())));
        AWAKE_KATANA = RitualRegistry.register(HexItem.DULL_KATANA.get(), (new AwakenRitual(new ItemStack((IItemProvider) HexItem.FROST_RAZOR.get())).setRegistryName("hexblades", "awake_ice_katana").addRequirement(new ItemRequirement((Item) HexItem.PATRON_SOUL.get())).addRequirement(new ItemRequirement((Item) Items.BLUE_ICE)).addRequirement(new ItemRequirement((Item) Items.BLUE_ICE)).addRequirement(new ItemRequirement((Item) Registry.WRAITH_HEART.get())).addRequirement(new ItemRequirement((Item) Registry.TATTERED_CLOTH.get()))));
        AWAKE_SABER = RitualRegistry.register(HexItem.DULL_SABER.get(), (new AwakenRitual(new ItemStack((IItemProvider) HexItem.WATER_SABER.get())).setRegistryName("hexblades", "awake_water_saber").addRequirement(new ItemRequirement((Item) HexItem.PATRON_SOUL.get())).addRequirement(new ItemRequirement((Item) Items.PRISMARINE_SHARD)).addRequirement(new ItemRequirement((Item) Items.PRISMARINE_SHARD)).addRequirement(new ItemRequirement((Item) Items.PRISMARINE_SHARD)).addRequirement(new ItemRequirement((Item) HexItem.DROWNED_HEART.get()))));
        AWAKE_HAMMER = RitualRegistry.register(HexItem.DULL_HAMMER.get(), (new AwakenRitual(new ItemStack((IItemProvider) HexItem.EARTH_HAMMER.get())).setRegistryName("hexblades", "awake_earth_hammer").addRequirement(new ItemRequirement((Item) HexItem.PATRON_SOUL.get())).addRequirement(new ItemRequirement((Item) Items.OBSIDIAN))).addRequirement(new ItemRequirement((Item) Items.OBSIDIAN)).addRequirement(new ItemRequirement((Block) Registry.LEAD_BLOCK.get())).addRequirement(new ItemRequirement((Block) Registry.LEAD_BLOCK.get())));
        AWAKE_DAGGER = RitualRegistry.register(HexItem.DULL_DAGGER.get(), (new AwakenRitual(new ItemStack((IItemProvider) HexItem.LIGHTNING_DAGGER_L.get())).setRegistryName("hexblades", "awake_lightning_dagger").addRequirement(new ItemRequirement((Item) HexItem.PATRON_SOUL.get())).addRequirement(new ItemRequirement((Item) Registry.ARCANE_GOLD_INGOT.get())).addRequirement(new ItemRequirement((Item) Registry.ARCANE_GOLD_INGOT.get()))));

        EVOLVE_SWORD = RitualRegistry.register(HexItem.FIRE_BRAND.get(), (new EvolveRitual(new ItemStack((IItemProvider) HexItem.FIRE_BRAND1.get())).setRegistryName("hexblades", "evolve_flame_sword")));
        EVOLVE_KATANA = RitualRegistry.register(HexItem.FROST_RAZOR.get(), (new EvolveRitual(new ItemStack((IItemProvider) HexItem.FROST_RAZOR1.get())).setRegistryName("hexblades", "evolve_ice_katana")));
        EVOLVE_SABER = RitualRegistry.register(HexItem.WATER_SABER.get(), (new EvolveRitual(new ItemStack((IItemProvider) HexItem.WATER_SABER1.get())).setRegistryName("hexblades", "evolve_water_saber")));
        EVOLVE_HAMMER = null;

    }

}
