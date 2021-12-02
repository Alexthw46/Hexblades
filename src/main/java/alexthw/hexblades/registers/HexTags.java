package alexthw.hexblades.registers;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.util.HexUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;

public class HexTags {

    public static final class Blocks {

        private static Tag.Named<Block> forge(String path) {
            return BlockTags.bind(new ResourceLocation("forge", path).toString());
        }
    }

    public static final class Items {
        public static final Tag.Named<Item> HEXIUM_INGOT = forge("ingots/hexium");
        public static final Tag.Named<Item> HEX_BLADE = mod("hexblade");

        public static Tag.Named<Item> forge(String path) {
            return ItemTags.bind(new ResourceLocation("forge", path).toString());
        }

        private static Tag.Named<Item> mod(String path) {
            return ItemTags.bind(new ResourceLocation(Hexblades.MODID, path).toString());
        }
    }

    public static Tag.Named<Item> makeWrapperTag(String id) {
        return ItemTags.createOptional(HexUtils.prefix(id));
    }
}
