package Alexthw.Hexblades.core.registers;

import Alexthw.Hexblades.Hexblades;
import Alexthw.Hexblades.common.items.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;



public class HexItem {

    private static Item.Properties addTabProp() {
        return new Item.Properties().group(Hexblades.TAB);
    }


    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Hexblades.MOD_ID);


    //Items
    public static final RegistryObject<Item> HEXIUM_INGOT;
    public static final RegistryObject<Item> PATRON_SOUL;

    //Sword/Tools
    public static final RegistryObject<Item> DEV_SWORD;
    public static final RegistryObject<Item> DULL_KATANA;
    public static final RegistryObject<Item> DULL_BROADSWORD;
    public static final RegistryObject<Item> DULL_SABER;
    public static final RegistryObject<Item> DULL_HAMMER;
    //public static final RegistryObject<Item> DULL_DAGGER;



    public static final RegistryObject<Item> FROST_RAZOR;
    public static final RegistryObject<Item> FROST_RAZOR1;
    public static final RegistryObject<Item> FIRE_BRAND;
    public static final RegistryObject<Item> FIRE_BRAND1;
    public static final RegistryObject<Item> WATER_SABER;
    public static final RegistryObject<Item> WATER_SABER1;
    public static final RegistryObject<Item> EARTH_HAMMER;
    /*
    public static final RegistryObject<Item> EARTH_HAMMER1;
    public static final RegistryObject<Item> LIGHTNING_KNIVES;
    public static final RegistryObject<Item> LIGHTNING_KNIVES1;
    */


    //BlockItems
    public static final RegistryObject<BlockItem> DEV_BLOCK;
    public static final RegistryObject<BlockItem> DARK_POLISH_PLANKS;


    static {
        //Items
        HEXIUM_INGOT = ITEMS.register("hexium_ingot", () -> new Item(addTabProp()));
        PATRON_SOUL = ITEMS.register("elemental_soul", () -> new PatronSoul(addTabProp()));

        //Dull Sword/Tools
        DULL_KATANA = ITEMS.register("katana_dull", () -> new Katana_dull(Tiers.HexiumTier.INSTANCE, 3, -2.5F, new Item.Properties().group(Hexblades.TAB).maxStackSize(1)));
        DULL_BROADSWORD = ITEMS.register("sword_dull", () -> new BroadSword_dull(Tiers.HexiumTier.INSTANCE, 3, -2.7F, new Item.Properties().group(Hexblades.TAB).maxStackSize(1)));
        DULL_SABER = ITEMS.register("saber_dull", () -> new Saber_dull(Tiers.HexiumTier.INSTANCE, 3, -2.6F, new Item.Properties().group(Hexblades.TAB).maxStackSize(1)));
        DULL_HAMMER = ITEMS.register("hammer_dull",()-> new Hammer_dull(Tiers.HexiumTier.INSTANCE,4,-3.4F,new Item.Properties().group(Hexblades.TAB).maxStackSize(1)));
        //DULL_DAGGER = ITEMS.register("knives_dull",()-> new Dagger_dull(Tiers.HexiumTier.INSTANCE, 2, 2.5F, new Item.Properties().group(Hexblades.TAB).maxStackSize(1)));

        //Hexblades
        DEV_SWORD = ITEMS.register("dev_sword_1", () -> new HexSwordItem(3, -1.6F, new Item.Properties().group(Hexblades.TAB).maxStackSize(1)));

        FROST_RAZOR = ITEMS.register("ice_katana_1", () -> new IceKatana1(addTabProp()));
        FROST_RAZOR1 = ITEMS.register("ice_katana_2", () -> new IceKatana2(addTabProp()));
        FIRE_BRAND = ITEMS.register("flame_sword_1", () -> new FireBroad1(addTabProp()));
        FIRE_BRAND1 = ITEMS.register("flame_sword_2", () -> new FireBroad2(addTabProp()));
        WATER_SABER = ITEMS.register("water_saber_1", () -> new WaterSaber1(addTabProp()));
        WATER_SABER1 = ITEMS.register("water_saber_2", () -> new WaterSaber2(addTabProp()));
        EARTH_HAMMER = ITEMS.register("earth_hammer_1", () -> new Earth_Hammer1(addTabProp()));
        /*
        EARTH_HAMMER1 = ITEMS.register("earth_hammer_2", () -> new Earth_Hammer2(addTabProp()));
        LIGHTNING_KNIVES = ITEMS.register("thunder_knives_1", () -> new Lightning_Knives1(addTabProp()));
        LIGHTNING_KNIVES1 = ITEMS.register("thunder_knives_2", () -> new Lighting_Knives2(addTabProp()));
        */

        //BlockItems

        DEV_BLOCK = ITEMS.register("dev_block", () -> new BlockItem(HexBlock.DEV_BLOCK.get(), addTabProp()));
        DARK_POLISH_PLANKS = ITEMS.register("dark_polished_planks", () -> new BlockItem(HexBlock.DARK_POLISH_PLANKS.get(), addTabProp()));
    }

}
