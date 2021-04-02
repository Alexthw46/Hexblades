package Alexthw.Hexblades.core.init;

import Alexthw.Hexblades.Hexblades;
import Alexthw.Hexblades.common.items.FireBroad;
import Alexthw.Hexblades.common.items.FireBroad1;
import Alexthw.Hexblades.common.items.HexSwordItem;
import Alexthw.Hexblades.common.items.IceKatana;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class HexItem {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Hexblades.MOD_ID);

    //Items
    public static final RegistryObject<Item> SILVER_INGOT;
    //Sword/Tools
    public static final RegistryObject<Item> DEV_SWORD;
    public static final RegistryObject<Item> FROST_RAZOR;

    public static final RegistryObject<Item> FIRE_BRAND;
    public static final RegistryObject<Item> FIRE_BRAND1;


    //BlockItems
    public static final RegistryObject<BlockItem> DEV_BLOCK;
    public static final RegistryObject<BlockItem> DARK_POLISH_PLANKS;


    static {
        //Items
        SILVER_INGOT = ITEMS.register("silver_ingot", () -> new Item(new Item.Properties().group(Hexblades.TAB)));
        //Sword/Tools
        DEV_SWORD = ITEMS.register("dev_sword", () -> new HexSwordItem(ItemTier.IRON, 3, 1.6F, new Item.Properties().group(Hexblades.TAB).maxStackSize(1)));
        FROST_RAZOR = ITEMS.register("ice_katana", () -> new IceKatana(new Item.Properties().group(Hexblades.TAB)));
        FIRE_BRAND = ITEMS.register("flame_sword_0", () -> new FireBroad(new Item.Properties().group(Hexblades.TAB)));
        FIRE_BRAND1 = ITEMS.register("flame_sword_1", () -> new FireBroad1(new Item.Properties().group(Hexblades.TAB)));

        //BlockItems
        DEV_BLOCK = ITEMS.register("dev_block", () -> new BlockItem(HexBlock.DEV_BLOCK.get(), new Item.Properties().group(Hexblades.TAB)));
        DARK_POLISH_PLANKS = ITEMS.register("dark_polished_planks", () -> new BlockItem(HexBlock.DARK_POLISH_PLANKS.get(), new Item.Properties().group(Hexblades.TAB)));
    }

}
