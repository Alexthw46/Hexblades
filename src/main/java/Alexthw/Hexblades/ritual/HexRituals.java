package Alexthw.Hexblades.ritual;

import Alexthw.Hexblades.registers.HexItem;
import elucent.eidolon.Registry;
import elucent.eidolon.ritual.ItemRequirement;
import elucent.eidolon.ritual.Ritual;
import elucent.eidolon.ritual.RitualRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

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
        AWAKE_SWORD = RitualRegistry.register(HexItem.DULL_BROADSWORD.get(), (new AwakenRitual(new ItemStack(HexItem.FIRE_BRAND.get())).setRegistryName("hexblades", "awake_flame_sword").addRequirement(new ItemRequirement(HexItem.PATRON_SOUL.get()))).addRequirement(new ItemRequirement(Items.NETHERITE_SCRAP)).addRequirement(new ItemRequirement(Items.NETHERITE_SCRAP)).addRequirement(new ItemRequirement(CRIMSON_ESSENCE.get())).addRequirement(new ItemRequirement(CRIMSON_ESSENCE.get())));
        AWAKE_KATANA = RitualRegistry.register(HexItem.DULL_KATANA.get(), (new AwakenRitual(new ItemStack(HexItem.FROST_RAZOR.get())).setRegistryName("hexblades", "awake_ice_katana").addRequirement(new ItemRequirement(HexItem.PATRON_SOUL.get())).addRequirement(new ItemRequirement(Items.BLUE_ICE)).addRequirement(new ItemRequirement(Items.BLUE_ICE)).addRequirement(new ItemRequirement(Registry.WRAITH_HEART.get())).addRequirement(new ItemRequirement(Registry.TATTERED_CLOTH.get()))));
        AWAKE_SABER = RitualRegistry.register(HexItem.DULL_SABER.get(), (new AwakenRitual(new ItemStack(HexItem.WATER_SABER.get())).setRegistryName("hexblades", "awake_water_saber").addRequirement(new ItemRequirement(HexItem.PATRON_SOUL.get())).addRequirement(new ItemRequirement(Items.PRISMARINE_SHARD)).addRequirement(new ItemRequirement(Items.PRISMARINE_SHARD)).addRequirement(new ItemRequirement(Items.PRISMARINE_SHARD)).addRequirement(new ItemRequirement(HexItem.DROWNED_HEART.get()))));
        AWAKE_HAMMER = RitualRegistry.register(HexItem.DULL_HAMMER.get(), (new AwakenRitual(new ItemStack(HexItem.EARTH_HAMMER.get())).setRegistryName("hexblades", "awake_earth_hammer").addRequirement(new ItemRequirement(HexItem.PATRON_SOUL.get())).addRequirement(new ItemRequirement(Items.OBSIDIAN))).addRequirement(new ItemRequirement(Items.OBSIDIAN)).addRequirement(new ItemRequirement(Registry.LEAD_BLOCK.get())).addRequirement(new ItemRequirement(Registry.LEAD_BLOCK.get())));
        AWAKE_DAGGER = RitualRegistry.register(HexItem.DULL_DAGGER.get(), (new AwakenRitual(new ItemStack(HexItem.LIGHTNING_DAGGER_L.get())).setRegistryName("hexblades", "awake_lightning_dagger").addRequirement(new ItemRequirement(HexItem.PATRON_SOUL.get())).addRequirement(new ItemRequirement(Registry.ARCANE_GOLD_INGOT.get())).addRequirement(new ItemRequirement(Registry.ARCANE_GOLD_INGOT.get())).addRequirement(new ItemRequirement(Registry.SILVER_INGOT.get())).addRequirement(new ItemRequirement(Registry.SILVER_INGOT.get()))));

        EVOLVE_SWORD = RitualRegistry.register(HexItem.FIRE_BRAND.get(), (new EvolveRitual(new ItemStack(HexItem.FIRE_BRAND1.get())).setRegistryName("hexblades", "evolve_flame_sword")));
        EVOLVE_KATANA = RitualRegistry.register(HexItem.FROST_RAZOR.get(), (new EvolveRitual(new ItemStack(HexItem.FROST_RAZOR1.get())).setRegistryName("hexblades", "evolve_ice_katana")));
        EVOLVE_SABER = RitualRegistry.register(HexItem.WATER_SABER.get(), (new EvolveRitual(new ItemStack(HexItem.WATER_SABER1.get())).setRegistryName("hexblades", "evolve_water_saber")));
        EVOLVE_HAMMER = RitualRegistry.register(HexItem.EARTH_HAMMER.get(), (new EvolveRitual(new ItemStack(HexItem.EARTH_HAMMER1.get())).setRegistryName("hexblades", "evolve_earth_hammer")));

    }

}
