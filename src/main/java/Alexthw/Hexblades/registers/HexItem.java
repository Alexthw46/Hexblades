package Alexthw.Hexblades.registers;

import Alexthw.Hexblades.Hexblades;
import Alexthw.Hexblades.common.items.ElementalSoul;
import Alexthw.Hexblades.common.items.HexSwordItem;
import Alexthw.Hexblades.common.items.PatronSoul;
import Alexthw.Hexblades.common.items.armors.TestArmor;
import Alexthw.Hexblades.common.items.dulls.*;
import Alexthw.Hexblades.common.items.tier1.*;
import Alexthw.Hexblades.common.items.tier2.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;


public class HexItem {


    static Item.Properties addTabProp() {
        return new Item.Properties().group(Hexblades.TAB);
    }

    public static final DeferredRegister<Item> ITEMS = Registry.ITEMS;

    //Items
    public static final RegistryObject<Item> HEXIUM_INGOT;
    public static final RegistryObject<Item> PATRON_SOUL;
    public static final RegistryObject<Item> PATRON_SOUL2;
    public static final RegistryObject<Item> ELEMENTAL_CORE;
    public static final RegistryObject<Item> DROWNED_HEART;
    public static final RegistryObject<Item> SOUL_CANDY;

    public static final RegistryObject<Item> TEST_ARMOR;

    //Hexblades

    public static final RegistryObject<Item> DEV_SWORD;

    //DULLS
    public static final RegistryObject<Item> DULL_KATANA;
    public static final RegistryObject<Item> DULL_BROADSWORD;
    public static final RegistryObject<Item> DULL_SABER;
    public static final RegistryObject<Item> DULL_HAMMER;
    public static final RegistryObject<Item> DULL_DAGGER;

    //AWAKENED
    public static final RegistryObject<Item> FROST_RAZOR;
    public static final RegistryObject<Item> FROST_RAZOR1;
    public static final RegistryObject<Item> FIRE_BRAND;
    public static final RegistryObject<Item> FIRE_BRAND1;
    public static final RegistryObject<Item> WATER_SABER;
    public static final RegistryObject<Item> WATER_SABER1;
    public static final RegistryObject<Item> EARTH_HAMMER;
    public static final RegistryObject<Item> EARTH_HAMMER1;
    public static final RegistryObject<Item> LIGHTNING_DAGGER_R;
    public static final RegistryObject<Item> LIGHTNING_DAGGER_L;
    public static final RegistryObject<Item> LIGHTNING_SSWORD_R;
    public static final RegistryObject<Item> LIGHTNING_SSWORD_L;

    static {
        //Items
        HEXIUM_INGOT = ITEMS.register("hexium_ingot", () -> new Item(addTabProp()));
        PATRON_SOUL = ITEMS.register("elemental_soul", () -> new ElementalSoul(addTabProp()));
        PATRON_SOUL2 = ITEMS.register("elemental_soul_2", () -> new PatronSoul(addTabProp()));
        DROWNED_HEART = ITEMS.register("drowned_heart", () -> new Item(addTabProp()));
        ELEMENTAL_CORE = ITEMS.register("elemental_core", () -> new Item(addTabProp()));
        SOUL_CANDY = ITEMS.register("soul_candy", () -> new Item(addTabProp().food(new Food.Builder().effect(() -> new EffectInstance(Effects.REGENERATION, 40, 1), 1.0F).fastToEat().hunger(1).saturation(0.1F).build())));

        TEST_ARMOR = ITEMS.register("test_chestplate", () -> new TestArmor(EquipmentSlotType.CHEST, addTabProp()));


        //Dull Sword/Tools
        DULL_KATANA = ITEMS.register("katana_dull", () -> new Katana_dull(2, -2.5F, addTabProp().maxStackSize(1)));
        DULL_BROADSWORD = ITEMS.register("sword_dull", () -> new BroadSword_dull(2, -2.6F, addTabProp().maxStackSize(1)));
        DULL_SABER = ITEMS.register("saber_dull", () -> new Saber_dull(2, -2.4F, addTabProp().maxStackSize(1)));
        DULL_HAMMER = ITEMS.register("hammer_dull", () -> new Hammer_dull(3, -3.4F, addTabProp().maxStackSize(1)));
        DULL_DAGGER = ITEMS.register("dagger_dull", () -> new Dagger_dull(0, -1.5F, addTabProp().maxStackSize(1)));

        //Hexblades
        DEV_SWORD = ITEMS.register("dev_sword_1", () -> new HexSwordItem(3, -1.6F, addTabProp().maxStackSize(1)));

        FROST_RAZOR = ITEMS.register("ice_katana_1", () -> new IceKatana1(addTabProp().maxStackSize(1)));
        FROST_RAZOR1 = ITEMS.register("ice_katana_2", () -> new IceKatana2(addTabProp().maxStackSize(1)));

        FIRE_BRAND = ITEMS.register("flame_sword_1", () -> new FireBroad1(addTabProp().maxStackSize(1)));
        FIRE_BRAND1 = ITEMS.register("flame_sword_2", () -> new FireBroad2(addTabProp().maxStackSize(1)));

        WATER_SABER = ITEMS.register("water_saber_1", () -> new WaterSaber1(addTabProp().maxStackSize(1)));
        WATER_SABER1 = ITEMS.register("water_saber_2", () -> new WaterSaber2(addTabProp().maxStackSize(1)));

        EARTH_HAMMER = ITEMS.register("earth_hammer_1", () -> new EarthHammer1(addTabProp().maxStackSize(1)));
        EARTH_HAMMER1 = ITEMS.register("earth_hammer_2", () -> new EarthHammer2(addTabProp().maxStackSize(1)));

        LIGHTNING_DAGGER_R = ITEMS.register("thunder_knives_right1", () -> new Lightning_SSwordR1(addTabProp().maxStackSize(1)));
        LIGHTNING_DAGGER_L = ITEMS.register("thunder_knives_left1", () -> new Lightning_SSwordL1(addTabProp().maxStackSize(1)));
        LIGHTNING_SSWORD_R = ITEMS.register("thunder_knives_right2", () -> new Lightning_SSwordR2(addTabProp()));
        LIGHTNING_SSWORD_L = ITEMS.register("thunder_knives_left2", () -> new Lightning_SSwordL2(addTabProp()));

    }

}
