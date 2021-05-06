package Alexthw.Hexblades.datagen;

import Alexthw.Hexblades.Hexblades;
import Alexthw.Hexblades.registers.HexItem;
import Alexthw.Hexblades.registers.HexTags;
import Alexthw.Hexblades.util.HexUtils;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class HexItemTagProvider extends ItemTagsProvider {

    public HexItemTagProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, Hexblades.MOD_ID, existingFileHelper);
    }


    public static ITag.INamedTag<Item> makeWrapperTag(String id) {
        return ItemTags.createOptional(HexUtils.prefix(id));
    }

    @Override
    protected void registerTags() {
        this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
        this.copy(BlockTags.STAIRS, ItemTags.STAIRS);
        this.copy(BlockTags.SLABS, ItemTags.SLABS);
        this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        this.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);

        getOrCreateBuilder(HexTags.Items.HEX_BLADE).add(HexItem.DEV_SWORD.get());
        getOrCreateBuilder(HexTags.Items.HEXIUM_INGOT).add(HexItem.HEXIUM_INGOT.get());
        getOrCreateBuilder(Tags.Items.INGOTS).addTag(HexTags.Items.HEXIUM_INGOT);
    }

    @Override
    public String getName() {
        return "HexBlades Block Tags";
    }
}
