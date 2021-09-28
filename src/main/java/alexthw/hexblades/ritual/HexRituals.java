package alexthw.hexblades.ritual;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.registers.HexEntityType;
import alexthw.hexblades.registers.HexItem;
import elucent.eidolon.Registry;
import elucent.eidolon.ritual.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtils;

import static alexthw.hexblades.util.HexUtils.*;
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

    public static Ritual SUMMON_FIRE;
    public static Ritual SUMMON_TEST;



    public static void init() {

        //awake
        AWAKE_SWORD = RitualRegistry.register(HexItem.DULL_BROADSWORD.get(),
                (new AwakenRitual(new ItemStack(HexItem.FIRE_BRAND.get()), fireColor)
                        .setRegistryName("hexblades", "awake_flame_sword")
                        .addRequirement(new ItemRequirement(Items.NETHERITE_SCRAP))
                        .addRequirement(new ItemRequirement(Items.NETHERITE_SCRAP))
                        .addRequirement(new ItemRequirement(HexItem.ELEMENTAL_CORE.get()))
                        .addRequirement(new ItemRequirement(Items.MAGMA_CREAM))

                )
        );

        AWAKE_KATANA = RitualRegistry.register(HexItem.DULL_KATANA.get(),
                (new AwakenRitual(new ItemStack(HexItem.FROST_RAZOR.get()), iceColor)
                        .setRegistryName("hexblades", "awake_ice_katana")
                        .addRequirement(new ItemRequirement(Items.BLUE_ICE))
                        .addRequirement(new ItemRequirement(Items.BLUE_ICE))
                        .addRequirement(new ItemRequirement(HexItem.ELEMENTAL_CORE.get()))
                        .addRequirement(new ItemRequirement(Registry.WRAITH_HEART.get()))
                        .addRequirement(new ItemRequirement(Registry.TATTERED_CLOTH.get()))
                )
        );

        AWAKE_SABER = RitualRegistry.register(HexItem.DULL_SABER.get(),
                (new AwakenRitual(new ItemStack(HexItem.WATER_SABER.get()), waterColor)
                        .setRegistryName("hexblades", "awake_water_saber")
                        .addRequirement(new ItemRequirement(Items.PRISMARINE_SHARD))
                        .addRequirement(new ItemRequirement(Items.PRISMARINE_SHARD))
                        .addRequirement(new ItemRequirement(HexItem.ELEMENTAL_CORE.get()))
                        .addRequirement(new ItemRequirement(Items.PRISMARINE_SHARD))
                        .addRequirement(new ItemRequirement(HexItem.DROWNED_HEART.get()))
                )
        );

        AWAKE_HAMMER = RitualRegistry.register(HexItem.DULL_HAMMER.get(),
                (new AwakenRitual(new ItemStack(HexItem.EARTH_HAMMER.get()), earthColor)
                        .setRegistryName("hexblades", "awake_earth_hammer")
                        .addRequirement(new ItemRequirement(Items.OBSIDIAN))
                        .addRequirement(new ItemRequirement(Items.OBSIDIAN))
                        .addRequirement(new ItemRequirement(HexItem.ELEMENTAL_CORE.get()))
                        .addRequirement(new ItemRequirement(Registry.LEAD_BLOCK.get()))
                        .addRequirement(new ItemRequirement(Registry.LEAD_BLOCK.get()))
                )
        );

        AWAKE_DAGGER = RitualRegistry.register(HexItem.DULL_DAGGER.get(),
                (new AwakenRitual(new ItemStack(HexItem.LIGHTNING_DAGGER_L.get()), thunderColor)
                        .setRegistryName("hexblades", "awake_lightning_dagger")
                        .addRequirement(new ItemRequirement(Registry.ARCANE_GOLD_INGOT.get()))
                        .addRequirement(new ItemRequirement(Registry.ARCANE_GOLD_INGOT.get()))
                        .addRequirement(new ItemRequirement(HexItem.ELEMENTAL_CORE.get()))
                        .addRequirement(new ItemRequirement(Registry.PEWTER_INGOT.get()))
                        .addRequirement(new ItemRequirement(Registry.PEWTER_INGOT.get()))
                )
        );

        //evolve
        EVOLVE_SWORD = RitualRegistry.register(new MultiItemSacrifice(HexItem.FIRE_BRAND.get(), HexItem.PATRON_SOUL.get()),
                (new EvolveRitual(new ItemStack(HexItem.FIRE_BRAND1.get()), fireColor)
                        .setRegistryName("hexblades", "evolve_flame_sword")
                        .addRequirement(new ItemRequirement(Registry.SHADOW_GEM.get()))
                        .addRequirement(new ItemRequirement(HexItem.PATRON_SOUL.get()))
                        .addRequirement(new ItemRequirement(CRIMSON_ESSENCE.get()))
                        .addRequirement(new ItemRequirement(CRIMSON_ESSENCE.get())))
        );

        EVOLVE_KATANA = RitualRegistry.register(new MultiItemSacrifice(HexItem.FROST_RAZOR.get(), HexItem.PATRON_SOUL.get()),
                (new EvolveRitual(new ItemStack(HexItem.FROST_RAZOR1.get()), iceColor)
                        .setRegistryName("hexblades", "evolve_ice_katana")
                        .addRequirement(new ItemRequirement(Items.QUARTZ))
                        .addRequirement(new ItemRequirement(PotionUtils.setPotion(new ItemStack(Items.POTION), Registry.CHILLED_POTION.get())))
                        .addRequirement(new ItemRequirement(HexItem.PATRON_SOUL.get()))
                        .addRequirement(new ItemRequirement(Registry.WRAITH_HEART.get()))
                        .addRequirement(new ItemRequirement(Items.QUARTZ))

                )
        );

        EVOLVE_SABER = RitualRegistry.register(new MultiItemSacrifice(HexItem.WATER_SABER.get(), HexItem.PATRON_SOUL.get()),
                (new EvolveRitual(new ItemStack(HexItem.WATER_SABER1.get()), waterColor)
                        .setRegistryName("hexblades", "evolve_water_saber")
                        .addRequirement(new ItemRequirement(Items.PRISMARINE_CRYSTALS))
                        .addRequirement(new ItemRequirement(Items.HEART_OF_THE_SEA))
                        .addRequirement(new ItemRequirement(HexItem.PATRON_SOUL.get()))
                        .addRequirement(new ItemRequirement(HexItem.DROWNED_HEART.get()))
                        .addRequirement(new ItemRequirement(Items.PRISMARINE_CRYSTALS))

                )
        );

        EVOLVE_HAMMER = RitualRegistry.register(new MultiItemSacrifice(HexItem.EARTH_HAMMER.get(), HexItem.PATRON_SOUL.get()),
                (new EvolveRitual(new ItemStack(HexItem.EARTH_HAMMER1.get()), earthColor)
                        .setRegistryName("hexblades", "evolve_earth_hammer")
                        .addRequirement(new ItemRequirement(Registry.ARCANE_GOLD_BLOCK.get()))
                        .addRequirement(new ItemRequirement(HexItem.PATRON_SOUL.get()))
                        .addRequirement(new ItemRequirement(Registry.ARCANE_GOLD_BLOCK.get()))
                )
        );

        EVOLVE_DAGGERS = RitualRegistry.register(new MultiItemSacrifice(HexItem.PATRON_SOUL.get(), HexItem.LIGHTNING_DAGGER_R.get(), HexItem.LIGHTNING_DAGGER_L.get()),
                (new EvolveRitual(new ItemStack(HexItem.LIGHTNING_SSWORD_L.get()), thunderColor)
                        .setRegistryName("hexblades", "evolve_duals")
                        .addRequirement(new ItemRequirement(Items.REDSTONE))
                        .addRequirement(new ItemRequirement(Items.PHANTOM_MEMBRANE))
                        .addRequirement(new ItemRequirement(HexItem.LIGHTNING_DAGGER_L.get()))
                        .addRequirement(new ItemRequirement(HexItem.LIGHTNING_DAGGER_R.get()))
                        .addRequirement(new ItemRequirement(Items.PHANTOM_MEMBRANE))
                        .addRequirement(new ItemRequirement(Items.REDSTONE))
                )
        );

        //summon

        SUMMON_FIRE = RitualRegistry.register(HexItem.SOUL_BAIT.get(),
                (new SummonRitual(HexEntityType.FIRE_ELEMENTAL.get())
                        .setRegistryName(Hexblades.MOD_ID, "allure_elemental")
                        .addRequirement(new ItemRequirement(HexItem.SOUL_BAIT.get()))
                        .addRequirement(new HealthRequirement(10))
                )
        );
    }

}
