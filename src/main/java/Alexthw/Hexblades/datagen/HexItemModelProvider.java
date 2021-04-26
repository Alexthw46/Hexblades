package Alexthw.Hexblades.datagen;

import Alexthw.Hexblades.Hexblades;
import Alexthw.Hexblades.common.items.EarthHammer1;
import Alexthw.Hexblades.common.items.Hammer_dull;
import Alexthw.Hexblades.common.items.HexSwordItem;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

import java.util.HashSet;
import java.util.Set;

import static Alexthw.Hexblades.registers.HexItem.ITEMS;
import static Alexthw.Hexblades.util.HexUtils.prefix;
import static Alexthw.Hexblades.util.HexUtils.takeAll;


public class HexItemModelProvider extends ItemModelProvider {

    public static ResourceLocation rl(String name) {
        return new ResourceLocation(Hexblades.MOD_ID, name);
    }

    private static final ResourceLocation GENERATED = new ResourceLocation("item/generated");
    private static final ResourceLocation HANDHELD = new ResourceLocation("item/handheld");

    public HexItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Hexblades.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Set<RegistryObject<Item>> items = new HashSet<>(ITEMS.getEntries());
        takeAll(items, i -> i.get() instanceof BlockItem).forEach(this::blockItem);
        takeAll(items, i -> i.get() instanceof ToolItem).forEach(this::handheldItem);
        takeAll(items, i -> i.get() instanceof Hammer_dull);
        takeAll(items, i -> i.get() instanceof EarthHammer1);
        takeAll(items, i -> i.get() instanceof HexSwordItem).forEach(this::awakenableItem);
        takeAll(items, i -> i.get() instanceof SwordItem).forEach(this::handheldItem);

        items.forEach(this::generatedItem);

    }

    private void handheldItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, HANDHELD).texture("layer0", prefix("item/" + name));
    }


    private void awakenableItem(RegistryObject<Item> it) {

        String path = Registry.ITEM.getKey(it.get()).getPath();
        ItemModelBuilder builder = getBuilder(path);
        withExistingParent(path, HANDHELD).texture("layer0", prefix("item/" + path));

        ModelFile activatedFile = singleTexture("item/variants/" + path + "_activated", mcLoc("item/handheld"), "layer0", modLoc("item/" + path + "_activated"));
        ModelFile deactivatedFile = singleTexture("item/variants/" + path + "_deactivated", mcLoc("item/handheld"), "layer0", modLoc("item/" + path));
        builder.override().predicate(rl("awakened"), 0).model(deactivatedFile).end().override().predicate(rl("awakened"), 1).model(activatedFile).end();


    }

    private void generatedItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, GENERATED).texture("layer0", prefix("item/" + name));
    }

    private void blockGeneratedItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, GENERATED).texture("layer0", prefix("block/" + name));
    }

    private void blockItem(RegistryObject<Item> i)
    {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(prefix("block/" + name)));
    }

}
