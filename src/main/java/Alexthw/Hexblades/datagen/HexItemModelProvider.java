package Alexthw.Hexblades.datagen;

import Alexthw.Hexblades.Hexblades;
import Alexthw.Hexblades.common.items.HexSwordItem;
import Alexthw.Hexblades.common.items.dulls.Hammer_dull;
import Alexthw.Hexblades.common.items.tier1.EarthHammer1;
import Alexthw.Hexblades.common.items.tier1.Lightning_SSwordL1;
import net.minecraft.block.FenceBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.*;
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
    private static final ResourceLocation SPAWN_EGG = new ResourceLocation("item/template_spawn_egg");

    public HexItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Hexblades.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Set<RegistryObject<Item>> items = new HashSet<>(ITEMS.getEntries());
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof FenceBlock).forEach(this::fenceBlockItem);
        takeAll(items, i -> i.get() instanceof BlockItem).forEach(this::blockItem);
        takeAll(items, i -> i.get() instanceof ToolItem).forEach(this::handheldItem);
        takeAll(items, i -> i.get() instanceof Hammer_dull);
        takeAll(items, i -> i.get() instanceof EarthHammer1);
        takeAll(items, i -> i.get() instanceof Lightning_SSwordL1).forEach(this::awakenthrowItem);
        takeAll(items, i -> i.get() instanceof HexSwordItem).forEach(this::awakenableItem);
        takeAll(items, i -> i.get() instanceof SwordItem).forEach(this::handheldItem);
        takeAll(items, i -> i.get() instanceof SpawnEggItem).forEach(this::spawnEgg);

        items.forEach(this::generatedItem);

    }

    private void spawnEgg(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, SPAWN_EGG);
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

    private void awakenthrowItem(RegistryObject<Item> it) {

        String path = Registry.ITEM.getKey(it.get()).getPath();
        ItemModelBuilder builder = getBuilder(path);
        withExistingParent(path, HANDHELD).texture("layer0", prefix("item/" + path));

        ModelFile deactivatedFile = singleTexture("item/variants/" + path + "_deactivated", mcLoc("item/handheld"), "layer0", modLoc("item/" + path));
        builder.override().predicate(rl("awakened"), 0).model(deactivatedFile).end().override().predicate(rl("awakened"), 1).model(getExistingFile(modLoc("item/variants/" + path + "_activated"))).end();


    }

    private void generatedItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, GENERATED).texture("layer0", prefix("item/" + name));
    }

    private void blockGeneratedItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        withExistingParent(name, GENERATED).texture("layer0", prefix("block/" + name));
    }

    private void blockItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        getBuilder(name).parent(new ModelFile.UncheckedModelFile(prefix("block/" + name)));
    }

    private void fenceBlockItem(RegistryObject<Item> i) {
        String name = Registry.ITEM.getKey(i.get()).getPath();
        String baseName = name.substring(0, name.length() - 6);
        fenceInventory(name, prefix("block/" + baseName));
    }
}
