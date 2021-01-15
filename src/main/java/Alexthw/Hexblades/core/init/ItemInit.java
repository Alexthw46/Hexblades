package Alexthw.Hexblades.core.init;

import Alexthw.Hexblades.Hexblades;
import Alexthw.Hexblades.common.items.HexSwordItem;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Hexblades.MOD_ID);

    //Items

    //Sword/Tools
    public static final RegistryObject<HexSwordItem> DEV_SWORD;

    //BlockItems
    public static final RegistryObject<BlockItem> DEV_BLOCK;
    public static final RegistryObject<BlockItem> DARK_POLISH_PLANKS;


    static {
        //Items

        //Sword/Tools
        DEV_SWORD = ITEMS.register("dev_sword", () -> new HexSwordItem(ItemTier.IRON, 0, 0, new Item.Properties().group(Hexblades.TAB).maxStackSize(1)));

        //BlockItems
        DEV_BLOCK = ITEMS.register("dev_block", () -> new BlockItem(BlockInit.DEV_BLOCK.get(), new Item.Properties().group(Hexblades.TAB)));
        DARK_POLISH_PLANKS = ITEMS.register("dark_polished_planks", () -> new BlockItem(BlockInit.DARK_POLISH_PLANKS.get(), new Item.Properties().group(Hexblades.TAB)));
    }

}
