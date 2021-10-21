package alexthw.hexblades.datagen;

import alexthw.hexblades.registers.HexBlock;
import alexthw.hexblades.registers.HexItem;
import elucent.eidolon.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;

import java.util.function.Consumer;

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

    }


}
