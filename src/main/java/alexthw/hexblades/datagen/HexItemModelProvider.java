package alexthw.hexblades.datagen;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.common.items.IHexblade;
import alexthw.hexblades.common.items.dulls.HammerDull;
import alexthw.hexblades.common.items.hexblades.EarthHammer;
import alexthw.hexblades.common.items.hexblades.LightningSSword;
import alexthw.hexblades.util.Constants;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

import static alexthw.hexblades.registers.HexItem.ITEMS;
import static alexthw.hexblades.util.HexUtils.prefix;
import static alexthw.hexblades.util.HexUtils.takeAll;

@SuppressWarnings("deprecation")
public class HexItemModelProvider extends ItemModelProvider {

    private static final ResourceLocation GENERATED = new ResourceLocation("item/generated");
    private static final ResourceLocation HANDHELD = new ResourceLocation("item/handheld");
    private static final ResourceLocation SPAWN_EGG = new ResourceLocation("item/template_spawn_egg");

    public HexItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Hexblades.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        Set<RegistryObject<Item>> items = new HashSet<>(ITEMS.getEntries());
        takeAll(items, i -> i.get() instanceof BlockItem && ((BlockItem) i.get()).getBlock() instanceof FenceBlock).forEach(this::fenceBlockItem);
        takeAll(items, i -> i.get() instanceof BlockItem).forEach(this::blockItem);
        takeAll(items, i -> i.get() instanceof HammerDull);
        takeAll(items, i -> i.get() instanceof EarthHammer).forEach(this::hammerDrillItem);
        takeAll(items, i -> i.get() instanceof LightningSSword).forEach(this::awakenThrowItem);
        takeAll(items, i -> i.get() instanceof IHexblade && !(i.get() instanceof HexSwordItem)).forEach(this::awakenableItem);
        takeAll(items, i -> i.get() instanceof IHexblade).forEach(this::hexbladeItem);
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


    private void hexbladeItem(RegistryObject<Item> it) {

        String path = Registry.ITEM.getKey(it.get()).getPath();
        withExistingParent(path, HANDHELD).texture("layer0", prefix("item/" + path));

        ModelFile deactivatedForm = singleTexture("item/variants/" + path, mcLoc("item/handheld"), "layer0", modLoc("item/" + path));
        ModelFile firstForm = singleTexture("item/variants/" + path + "_stage1", mcLoc("item/handheld"), "layer0", modLoc("item/" + path + "_stage1"));
        ModelFile secondForm = singleTexture("item/variants/" + path + "_stage2", mcLoc("item/handheld"),"layer0", modLoc("item/" + path + "_stage2"));
        ModelFile thirdForm = singleTexture("item/variants/" + path + "_stage3", mcLoc("item/handheld"),"layer0", modLoc("item/" + path + "_stage3"));

        getBuilder(path)
                .override().predicate(prefix(Constants.NBT.AW_State), 0).model(deactivatedForm).end()
                .override().predicate(prefix(Constants.NBT.AW_State), 1).predicate(prefix(Constants.NBT.AW_Level), 0).model(firstForm).end()
                .override().predicate(prefix(Constants.NBT.AW_State), 1).predicate(prefix(Constants.NBT.AW_Level), 1).model(secondForm).end()
                .override().predicate(prefix(Constants.NBT.AW_State), 1).predicate(prefix(Constants.NBT.AW_Level), 2).model(thirdForm).end();
    }

    private void awakenableItem(RegistryObject<Item> it) {

        String path = Registry.ITEM.getKey(it.get()).getPath();
        ItemModelBuilder builder = getBuilder(path);
        withExistingParent(path, HANDHELD).texture("layer0", prefix("item/" + path));

        ModelFile activatedFile = singleTexture("item/variants/" + path + "_activated", mcLoc("item/handheld"), "layer0", modLoc("item/" + path + "_activated"));
        ModelFile deactivatedFile = singleTexture("item/variants/" + path + "_deactivated", mcLoc("item/handheld"), "layer0", modLoc("item/" + path));
        builder.override().predicate(prefix(Constants.NBT.AW_State), 0).model(deactivatedFile).end().override().predicate(prefix("awakened"), 1).model(activatedFile).end();

    }

    private void awakenThrowItem(RegistryObject<Item> it) {

        String path = Registry.ITEM.getKey(it.get()).getPath();
        withExistingParent(path, HANDHELD).texture("layer0", prefix("item/" + path));

        ModelFile deactivatedForm = singleTexture("item/variants/" + path + "_deactivated", mcLoc("item/handheld"), "layer0", modLoc("item/" + path));
        ModelFile deactivatedForm2 = singleTexture("item/variants/" + path + "_deactivated2", mcLoc("item/handheld"), "layer0", modLoc("item/" + path + "_stage2"));

        getBuilder(path)
                .override().predicate(prefix(Constants.NBT.AW_State), 0).predicate(prefix(Constants.NBT.AW_Level), 0).model(deactivatedForm).end()
                .override().predicate(prefix(Constants.NBT.AW_State), 0).predicate(prefix(Constants.NBT.AW_Level), 1).model(deactivatedForm2).end()
                .override().predicate(prefix(Constants.NBT.AW_State), 1).predicate(prefix(Constants.NBT.AW_Level), 0).model(getExistingFile(modLoc("item/variants/" + path + "_activated"))).end()
                .override().predicate(prefix(Constants.NBT.AW_State), 1).predicate(prefix(Constants.NBT.AW_Level), 1).model(getExistingFile(modLoc("item/variants/" + path + "_activated2"))).end();

    }

    private void hammerDrillItem(RegistryObject<Item> it){
        String path = Registry.ITEM.getKey(it.get()).getPath();
        withExistingParent(path, HANDHELD).texture("layer0", prefix("item/" + path));

        ModelFile deactivatedForm = getExistingFile(modLoc("item/variants/" + path));
        ModelFile deactivatedMiningForm = getExistingFile(modLoc("item/variants/" + path + "_mining"));

        ModelFile damageForm_1 = getExistingFile(modLoc("item/variants/" + path + "_1"));
        ModelFile damageForm_2 = getExistingFile(modLoc("item/variants/" + path + "_2"));
        ModelFile damageForm_3 = getExistingFile(modLoc("item/variants/" + path + "_3"));


        ModelFile miningForm_1 = getExistingFile(modLoc("item/variants/" + path + "_mining_1"));
        ModelFile miningForm_2 = getExistingFile(modLoc("item/variants/" + path + "_mining_2"));
        ModelFile miningForm_3 = getExistingFile(modLoc("item/variants/" + path + "_mining_3"));

        getBuilder(path)
                .override().predicate(prefix(Constants.NBT.AW_State), 0).predicate(prefix(Constants.NBT.MiningSwitch), 0).model(deactivatedForm).end()
                .override().predicate(prefix(Constants.NBT.AW_State), 0).predicate(prefix(Constants.NBT.MiningSwitch), 1).model(deactivatedMiningForm).end()
                .override().predicate(prefix(Constants.NBT.AW_State), 1).predicate(prefix(Constants.NBT.AW_Level), 0).model(damageForm_1).end()
                .override().predicate(prefix(Constants.NBT.AW_State), 1).predicate(prefix(Constants.NBT.AW_Level), 1).model(damageForm_2).end()
                .override().predicate(prefix(Constants.NBT.AW_State), 1).predicate(prefix(Constants.NBT.AW_Level), 2).model(damageForm_3).end()
                .override().predicate(prefix(Constants.NBT.AW_State), 1).predicate(prefix(Constants.NBT.AW_Level), 0).predicate(prefix(Constants.NBT.MiningSwitch), 1).model(miningForm_1).end()
                .override().predicate(prefix(Constants.NBT.AW_State), 1).predicate(prefix(Constants.NBT.AW_Level), 1).predicate(prefix(Constants.NBT.MiningSwitch), 1).model(miningForm_2).end()
                .override().predicate(prefix(Constants.NBT.AW_State), 1).predicate(prefix(Constants.NBT.AW_Level), 2).predicate(prefix(Constants.NBT.MiningSwitch), 1).model(miningForm_3).end();
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

    @Override
    public @NotNull String getName() {
        return "HexBlades Item Models";
    }
}
