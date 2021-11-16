package alexthw.hexblades.datagen;

import alexthw.hexblades.registers.HexBlock;
import alexthw.hexblades.registers.HexItem;
import alexthw.hexblades.temp.ArmorFocusRecipe;
import alexthw.hexblades.temp.WarlockArmorDye;
import elucent.eidolon.Registry;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;

import static alexthw.hexblades.util.HexUtils.prefix;
import static net.minecraft.util.registry.Registry.RECIPE_SERIALIZER;

public class HexRecipeProvider extends RecipeProvider {

    public HexRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {

        ShapedRecipeBuilder.shaped(HexItem.DULL_BROADSWORD.get()).define('#', Items.STICK).define('X', HexItem.HEXIUM_INGOT.get()).pattern("X").pattern("X").pattern("#").unlockedBy("has_spirited_metal", has(HexItem.HEXIUM_INGOT.get())).save(consumer);
        ShapedRecipeBuilder.shaped(HexItem.DULL_SABER.get()).define('#', Items.STICK).define('X', HexItem.HEXIUM_INGOT.get()).pattern(" X ").pattern(" X ").pattern("X# ").unlockedBy("has_spirited_metal", has(HexItem.HEXIUM_INGOT.get())).save(consumer);
        ShapedRecipeBuilder.shaped(HexItem.DULL_KATANA.get()).define('#', Items.STICK).define('X', HexItem.HEXIUM_INGOT.get()).pattern("  X").pattern(" X ").pattern("#  ").unlockedBy("has_spirited_metal", has(HexItem.HEXIUM_INGOT.get())).save(consumer);
        ShapedRecipeBuilder.shaped(HexItem.DULL_HAMMER.get()).define('#', Items.STICK).define('X', HexItem.HEXIUM_INGOT.get()).pattern("XXX").pattern("X#X").pattern(" # ").unlockedBy("has_spirited_metal", has(HexItem.HEXIUM_INGOT.get())).save(consumer);
        ShapedRecipeBuilder.shaped(HexItem.DULL_DAGGER.get()).define('#', Items.STICK).define('X', HexItem.HEXIUM_INGOT.get()).pattern("  ").pattern(" X").pattern("# ").unlockedBy("has_spirited_metal", has(HexItem.HEXIUM_INGOT.get())).save(consumer);

        ShapedRecipeBuilder.shaped(HexBlock.MAGMA_BRICKS.get().asItem(), 4).define('B', Items.NETHER_BRICK).define('M', Items.MAGMA_BLOCK).pattern("BBB").pattern("BMB").pattern("BBB").unlockedBy("has nether bricks", has(Items.NETHER_BRICK)).save(consumer);

        ShapedRecipeBuilder.shaped(HexBlock.DARK_POLISH_PLANKS.getBlock().asItem(), 8).define('#', Items.BLACK_DYE).define('X', Registry.POLISHED_PLANKS.getBlock().asItem()).pattern("XXX").pattern("X#X").pattern("XXX").unlockedBy("has_polished_wood", has(Registry.POLISHED_PLANKS.getBlock().asItem())).save(consumer);
        ShapedRecipeBuilder.shaped(HexBlock.DARK_POLISH_PLANKS.getSlab().asItem(), 6).define('X', HexBlock.DARK_POLISH_PLANKS.getBlock().asItem()).pattern("   ").pattern("   ").pattern("XXX").unlockedBy("has_dark_polished_wood", has(HexBlock.DARK_POLISH_PLANKS.getBlock().asItem())).save(consumer);
        ShapedRecipeBuilder.shaped(HexBlock.DARK_POLISH_PLANKS.getStairs().asItem(), 4).define('X', HexBlock.DARK_POLISH_PLANKS.getBlock().asItem()).pattern("X  ").pattern("XX ").pattern("XXX").unlockedBy("has_dark_polished_wood", has(HexBlock.DARK_POLISH_PLANKS.getBlock().asItem())).save(consumer);
        ShapedRecipeBuilder.shaped(HexBlock.DARK_POLISH_PLANKS.getFenceGate().asItem()).define('#', Items.STICK).define('X', HexBlock.DARK_POLISH_PLANKS.getBlock().asItem()).pattern("   ").pattern("#X#").pattern("#X#").unlockedBy("has_dark_polished_wood", has(HexBlock.DARK_POLISH_PLANKS.getBlock().asItem())).save(consumer);
        ShapedRecipeBuilder.shaped(HexBlock.DARK_POLISH_PLANKS.getFence().asItem(), 3).define('#', Items.STICK).define('X', HexBlock.DARK_POLISH_PLANKS.getBlock().asItem()).pattern("   ").pattern("X#X").pattern("X#X").unlockedBy("has_dark_polished_wood", has(HexBlock.DARK_POLISH_PLANKS.getBlock().asItem())).save(consumer);

        //make warlock armor dye and back
        ShapelessRecipeBuilder.shapeless(Registry.WARLOCK_HAT.get()).requires(HexItem.DYE_WARLOCK_H.get()).unlockedBy("warlock_hat", has(Registry.WARLOCK_HAT.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(Registry.WARLOCK_CLOAK.get()).requires(HexItem.DYE_WARLOCK_C.get()).unlockedBy("warlock_chest", has(Registry.WARLOCK_CLOAK.get())).save(consumer);
        ShapelessRecipeBuilder.shapeless(Registry.WARLOCK_BOOTS.get()).requires(HexItem.DYE_WARLOCK_F.get()).unlockedBy("warlock_boots", has(Registry.WARLOCK_BOOTS.get())).save(consumer);

        specialRecipe(consumer, ArmorFocusRecipe.SERIALIZER);
        specialRecipe(consumer, WarlockArmorDye.SERIALIZER);

        //hexwarrior recipes
        ShapedRecipeBuilder.shaped(HexItem.HEX_ARMOR_H.get()).define('H', HexItem.HEXED_INGOT.get()).define('F', HexItem.FOCUS_BASE.get()).pattern("HFH").pattern("H H").unlockedBy("has_spirited_metal", has(HexItem.HEXIUM_INGOT.get())).save(consumer);
        ShapedRecipeBuilder.shaped(HexItem.HEX_ARMOR_C.get()).define('H', HexItem.HEXED_INGOT.get()).define('F', HexItem.FOCUS_BASE.get()).pattern("H H").pattern("HFH").pattern("HHH").unlockedBy("has_spirited_metal", has(HexItem.HEXIUM_INGOT.get())).save(consumer);
        ShapedRecipeBuilder.shaped(HexItem.HEX_ARMOR_L.get()).define('H', HexItem.HEXED_INGOT.get()).define('L', Registry.LEAD_INGOT.get()).define('F', HexItem.FOCUS_BASE.get()).pattern("HFH").pattern("H H").pattern("L L").unlockedBy("has_spirited_metal", has(HexItem.HEXIUM_INGOT.get())).save(consumer);
        ShapedRecipeBuilder.shaped(HexItem.HEX_ARMOR_B.get()).define('H', HexItem.HEXED_INGOT.get()).define('P', Registry.PEWTER_INGOT.get()).pattern("P P").pattern("H H").unlockedBy("has_spirited_metal", has(HexItem.HEXIUM_INGOT.get())).save(consumer);

    }

    private void specialRecipe(Consumer<IFinishedRecipe> consumer, SpecialRecipeSerializer<?> serializer) {
        ResourceLocation name = RECIPE_SERIALIZER.getKey(serializer);
        if (name != null)
            CustomRecipeBuilder.special(serializer).save(consumer, prefix(name.getPath()).toString());
    }


}
