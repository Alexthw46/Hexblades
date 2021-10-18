package alexthw.hexblades.datagen;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.registers.HexItem;
import alexthw.hexblades.registers.HexTags;
import alexthw.hexblades.util.HexUtils;
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
        super(dataGenerator, blockTagProvider, Hexblades.MODID, existingFileHelper);
    }


    public static ITag.INamedTag<Item> makeWrapperTag(String id) {
        return ItemTags.createOptional(HexUtils.prefix(id));
    }

    @Override
    protected void addTags() {
        this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
        this.copy(BlockTags.STAIRS, ItemTags.STAIRS);
        this.copy(BlockTags.SLABS, ItemTags.SLABS);
        this.copy(BlockTags.WOODEN_STAIRS, ItemTags.WOODEN_STAIRS);
        this.copy(BlockTags.WOODEN_SLABS, ItemTags.WOODEN_SLABS);
        this.copy(BlockTags.WOODEN_FENCES, ItemTags.WOODEN_FENCES);

        tag(HexTags.Items.HEX_BLADE).add(HexItem.DEV_SWORD.get());
        tag(HexTags.Items.HEXIUM_INGOT).add(HexItem.HEXIUM_INGOT.get());
        tag(Tags.Items.INGOTS).addTag(HexTags.Items.HEXIUM_INGOT);
    }

    @Override
    public String getName() {
        return "HexBlades Block Tags";
    }
}
