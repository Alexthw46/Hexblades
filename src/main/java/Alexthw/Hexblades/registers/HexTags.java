package Alexthw.Hexblades.registers;

import Alexthw.Hexblades.Hexblades;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class HexTags {

    public static final class Blocks {

        private static ITag.INamedTag<Block> forge(String path) {
            return BlockTags.makeWrapperTag(new ResourceLocation("forge", path).toString());
        }
    }

    public static final class Items {
        public static final ITag.INamedTag<Item> HEXIUM_INGOT = forge("ingot/hexium");
        public static final ITag.INamedTag<Item> HEX_BLADE = mod("patron/hexblade");

        private static ITag.INamedTag<Item> forge(String path) {
            return ItemTags.makeWrapperTag(new ResourceLocation("forge", path).toString());
        }

        private static ITag.INamedTag<Item> mod(String path) {
            return ItemTags.makeWrapperTag(new ResourceLocation(Hexblades.MOD_ID, path).toString());
        }
    }

    //public static ITag.INamedTag<Item> makeWrapperTag(String id)    {        return ItemTags.createOptional(HexUtils.prefix(id)); }
}
