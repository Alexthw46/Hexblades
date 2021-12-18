package alexthw.hexblades.registers;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.common.items.ArmorFocus;
import alexthw.hexblades.common.items.ElementalSoul;
import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.common.items.dulls.*;
import alexthw.hexblades.common.items.hexblades.*;
import alexthw.hexblades.compat.ArmorCompatHandler;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class HexItem {

    static Item.Properties addTabProp() {
        return new Item.Properties().tab(Hexblades.TAB);
    }

    public static final DeferredRegister<Item> ITEMS = HexRegistry.ITEMS;

    //Items
    public static final RegistryObject<Item> HEXIUM_INGOT;
    public static final RegistryObject<Item> HEXED_INGOT;

    public static final RegistryObject<Item> PATRON_SOUL;
    public static final RegistryObject<Item> PATRON_SOUL2;
    public static final RegistryObject<Item> ELEMENTAL_CORE;
    public static final RegistryObject<Item> DROWNED_HEART;
    public static final RegistryObject<Item> SOUL_CANDY;
    public static final RegistryObject<Item> FIRE_CORE;

    //Armors
    public static final RegistryObject<Item> HEX_ARMOR_H;
    public static final RegistryObject<Item> HEX_ARMOR_C;
    public static final RegistryObject<Item> HEX_ARMOR_L;
    public static final RegistryObject<Item> HEX_ARMOR_B;

    //HWArmor Focus
    public static final RegistryObject<Item> FOCUS_BASE;
    public static final RegistryObject<Item> FOCUS_WARLOCK;
    public static final RegistryObject<Item> FOCUS_BOTANIA;
    public static final RegistryObject<Item> FOCUS_NOUVEAU;

    //Hexblades

    public static final RegistryObject<Item> DEV_SWORD;

    public static final RegistryObject<Item> BLOOD_SWORD;

    //DULLS
    public static final RegistryObject<Item> DULL_KATANA;
    public static final RegistryObject<Item> DULL_BROADSWORD;
    public static final RegistryObject<Item> DULL_SABER;
    public static final RegistryObject<Item> DULL_HAMMER;
    public static final RegistryObject<Item> DULL_DAGGER;

    //AWAKENED
    public static final RegistryObject<Item> FROST_RAZOR;
    public static final RegistryObject<Item> FIRE_BRAND;
    public static final RegistryObject<Item> WATER_SABER;
    public static final RegistryObject<Item> EARTH_HAMMER;
    public static final RegistryObject<Item> LIGHTNING_DAGGER_R;
    public static final RegistryObject<Item> LIGHTNING_DAGGER_L;

    static {

        //Items
        HEXIUM_INGOT = ITEMS.register("hexium_ingot", () -> new Item(addTabProp()));
        HEXED_INGOT = ITEMS.register("hexed_ingot", () -> new Item(addTabProp()));
        PATRON_SOUL = ITEMS.register("elemental_soul", () -> new ElementalSoul(addTabProp().fireResistant().rarity(Rarity.EPIC)));
        PATRON_SOUL2 = ITEMS.register("elemental_soul_2", () -> new Item(addTabProp().rarity(Rarity.EPIC)));
        DROWNED_HEART = ITEMS.register("drowned_heart", () -> new Item(addTabProp()));
        ELEMENTAL_CORE = ITEMS.register("elemental_core", () -> new Item(addTabProp().fireResistant().rarity(Rarity.RARE)));
        SOUL_CANDY = ITEMS.register("soul_candy", () -> new Item(addTabProp().food(new FoodProperties.Builder().effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 40, 1), 1.0F).fast().nutrition(1).saturationMod(0.1F).build())));
        FIRE_CORE = ITEMS.register("fire_core", () -> new Item(addTabProp().rarity(Rarity.UNCOMMON)));

        HEX_ARMOR_H = ITEMS.register("hex_helmet", () -> ArmorCompatHandler.makeHead(addTabProp()));
        HEX_ARMOR_C = ITEMS.register("hex_chestplate", () -> ArmorCompatHandler.makeChest(addTabProp()));
        HEX_ARMOR_L = ITEMS.register("hex_leggings", () -> ArmorCompatHandler.makeLegs(addTabProp()));
        HEX_ARMOR_B = ITEMS.register("hex_boots", () -> ArmorCompatHandler.makeFeet(addTabProp()));

        //HexWArmor Focus
        FOCUS_BASE = ITEMS.register("blank_focus", () -> new Item(addTabProp()));
        FOCUS_WARLOCK = ITEMS.register("eidolon_focus", () -> new ArmorFocus(addTabProp(), "eidolon"));
        FOCUS_BOTANIA = ITEMS.register("botania_focus", () -> new ArmorFocus(addTabProp(), "botania"));
        FOCUS_NOUVEAU = ITEMS.register("ars_nouveau_focus", () -> new ArmorFocus(addTabProp(), "ars nouveau"));

        //Dull Sword/Tools
        DULL_KATANA = ITEMS.register("katana_dull", () -> new DullSwordItem(2, -2.5F, addTabProp().stacksTo(1)));
        DULL_BROADSWORD = ITEMS.register("sword_dull", () -> new DullSwordItem(2, -2.6F, addTabProp().stacksTo(1)));
        DULL_SABER = ITEMS.register("saber_dull", () -> new DullSwordItem(2, -2.4F, addTabProp().stacksTo(1)));
        DULL_HAMMER = ITEMS.register("hammer_dull", () -> new HammerDull(3, -3.4F, addTabProp().stacksTo(1)));
        DULL_DAGGER = ITEMS.register("dagger_dull", () -> new DullSwordItem(0, -1.5F, addTabProp().stacksTo(1)));

        //Hexblades
        DEV_SWORD = ITEMS.register("dev_sword", () -> new HexSwordItem(3, -1.6F, addTabProp().stacksTo(1)));
        BLOOD_SWORD = ITEMS.register("blood_sword", () -> new SanguineSword(addTabProp().stacksTo(1).fireResistant().rarity(Rarity.RARE)));

        FIRE_BRAND = ITEMS.register("flame_sword", () -> new FireSword(addTabProp().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON)));
        FROST_RAZOR = ITEMS.register("ice_katana", () -> new IceKatana(addTabProp().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON)));
        WATER_SABER = ITEMS.register("water_saber", () -> new WaterSaber(addTabProp().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON)));
        EARTH_HAMMER = ITEMS.register("earth_hammer", () -> new EarthHammer(addTabProp().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON)));

        LIGHTNING_DAGGER_R = ITEMS.register("thunder_dagger", () -> new ThunderSSword(addTabProp().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON)));
        LIGHTNING_DAGGER_L = ITEMS.register("lightning_dagger", () -> new LightningSSword(addTabProp().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON)));


        /*

         public static final RegistryObject<Item> FROST_RAZOR1;
         public static final RegistryObject<Item> FIRE_BRAND1;
         public static final RegistryObject<Item> WATER_SABER1;
         public static final RegistryObject<Item> EARTH_HAMMER1;
         public static final RegistryObject<Item> LIGHTNING_SSWORD_R;
         public static final RegistryObject<Item> LIGHTNING_SSWORD_L;

         FROST_RAZOR = ITEMS.register("ice_katana_1", () -> new IceKatana1(addTabProp().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON)));
         FROST_RAZOR1 = ITEMS.register("ice_katana_2", () -> new IceKatana2(addTabProp().stacksTo(1).fireResistant().rarity(Rarity.RARE)));

         FIRE_BRAND = ITEMS.register("flame_sword_1", () -> new FireBroad1(addTabProp().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON)));
         FIRE_BRAND1 = ITEMS.register("flame_sword_2", () -> new FireBroad2(addTabProp().stacksTo(1).fireResistant().rarity(Rarity.RARE)));

         WATER_SABER = ITEMS.register("water_saber_1", () -> new WaterSaber1(addTabProp().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON)));
         WATER_SABER1 = ITEMS.register("water_saber_2", () -> new WaterSaber2(addTabProp().stacksTo(1).fireResistant().rarity(Rarity.RARE)));

         EARTH_HAMMER = ITEMS.register("earth_hammer_1", () -> new EarthHammer1(addTabProp().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON)));
         EARTH_HAMMER1 = ITEMS.register("earth_hammer_2", () -> new EarthHammer2(addTabProp().stacksTo(1).fireResistant().rarity(Rarity.RARE)));

         LIGHTNING_DAGGER_R = ITEMS.register("thunder_knives_right1", () -> new Lightning_SSwordR1(addTabProp().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON)));
         LIGHTNING_DAGGER_L = ITEMS.register("thunder_knives_left1", () -> new Lightning_SSwordL1(addTabProp().stacksTo(1).fireResistant().rarity(Rarity.UNCOMMON)));
         LIGHTNING_SSWORD_R = ITEMS.register("thunder_knives_right2", () -> new Lightning_SSwordR2(addTabProp().fireResistant().rarity(Rarity.RARE)));
         LIGHTNING_SSWORD_L = ITEMS.register("thunder_knives_left2", () -> new Lightning_SSwordL2(addTabProp().fireResistant().rarity(Rarity.RARE)));
        */
    }

}
