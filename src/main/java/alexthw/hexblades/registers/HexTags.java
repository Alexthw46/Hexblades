package alexthw.hexblades.registers;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.util.HexUtils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;

public class HexTags {

    public static final class Blocks {

        public static final ITag.INamedTag<Block> CRUCIBLE_HOT_BLOCKS = makeWrapperTag("crucible_heat_source");

        private static ITag.INamedTag<Block> forge(String path) {
            return BlockTags.bind(new ResourceLocation("forge", path).toString());
        }

        private static ITag.INamedTag<Block> mod(String path) {
            return BlockTags.bind(new ResourceLocation(Hexblades.MODID, path).toString());
        }
    }

    public static final class Items {
        public static final ITag.INamedTag<Item> HEXIUM_INGOT = forge("ingot/hexium");
        public static final ITag.INamedTag<Item> HEX_BLADE = mod("hexblade");

        public static ITag.INamedTag<Item> forge(String path) {
            return ItemTags.bind(new ResourceLocation("forge", path).toString());
        }

        private static ITag.INamedTag<Item> mod(String path) {
            return ItemTags.bind(new ResourceLocation(Hexblades.MODID, path).toString());
        }
    }

    public static ITag.INamedTag<Block> makeWrapperTag(String id) {
        return BlockTags.createOptional(HexUtils.prefix(id));
    }
}
