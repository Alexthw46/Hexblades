package Alexthw.Hexblades.datagen;

import Alexthw.Hexblades.registers.HexBlock;
import Alexthw.Hexblades.registers.HexItem;
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
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {

        ShapedRecipeBuilder.shapedRecipe(HexItem.DULL_BROADSWORD.get()).key('#', Items.STICK).key('X', HexItem.HEXIUM_INGOT.get()).patternLine("X").patternLine("X").patternLine("#").addCriterion("has_spirited_metal", hasItem(HexItem.HEXIUM_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(HexItem.DULL_SABER.get()).key('#', Items.STICK).key('X', HexItem.HEXIUM_INGOT.get()).patternLine(" X ").patternLine(" X ").patternLine("X# ").addCriterion("has_spirited_metal", hasItem(HexItem.HEXIUM_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(HexItem.DULL_KATANA.get()).key('#', Items.STICK).key('X', HexItem.HEXIUM_INGOT.get()).patternLine("  X").patternLine(" X ").patternLine("#  ").addCriterion("has_spirited_metal", hasItem(HexItem.HEXIUM_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(HexItem.DULL_HAMMER.get()).key('#', Items.STICK).key('X', HexItem.HEXIUM_INGOT.get()).patternLine("XXX").patternLine("X#X").patternLine(" # ").addCriterion("has_spirited_metal", hasItem(HexItem.HEXIUM_INGOT.get())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(HexItem.DULL_DAGGER.get()).key('#', Items.STICK).key('X', HexItem.HEXIUM_INGOT.get()).patternLine("  ").patternLine(" X").patternLine("# ").addCriterion("has_spirited_metal", hasItem(HexItem.HEXIUM_INGOT.get())).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(HexBlock.DARK_POLISH_PLANKS.getBlock().asItem()).key('#', Items.BLACK_DYE).key('X', Registry.POLISHED_PLANKS.getBlock().asItem()).patternLine("XXX").patternLine("X#X").patternLine("XXX").addCriterion("has_polished_wood", hasItem(Registry.POLISHED_PLANKS.getBlock().asItem())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(HexBlock.DARK_POLISH_PLANKS.getSlab().asItem()).key('X', HexBlock.DARK_POLISH_PLANKS.getBlock().asItem()).patternLine("   ").patternLine("   ").patternLine("XXX").addCriterion("has_dark_polished_wood", hasItem(HexBlock.DARK_POLISH_PLANKS.getBlock().asItem())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(HexBlock.DARK_POLISH_PLANKS.getStairs().asItem()).key('X', HexBlock.DARK_POLISH_PLANKS.getBlock().asItem()).patternLine("X  ").patternLine("XX ").patternLine("XXX").addCriterion("has_dark_polished_wood", hasItem(HexBlock.DARK_POLISH_PLANKS.getBlock().asItem())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(HexBlock.DARK_POLISH_PLANKS.getFenceGate().asItem()).key('#', Items.STICK).key('X', HexBlock.DARK_POLISH_PLANKS.getBlock().asItem()).patternLine("   ").patternLine("#X#").patternLine("#X#").addCriterion("has_dark_polished_wood", hasItem(HexBlock.DARK_POLISH_PLANKS.getBlock().asItem())).build(consumer);
        ShapedRecipeBuilder.shapedRecipe(HexBlock.DARK_POLISH_PLANKS.getFence().asItem()).key('#', Items.STICK).key('X', HexBlock.DARK_POLISH_PLANKS.getBlock().asItem()).patternLine("   ").patternLine("X#X").patternLine("X#X").addCriterion("has_dark_polished_wood", hasItem(HexBlock.DARK_POLISH_PLANKS.getBlock().asItem())).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(HexBlock.SWORD_STAND.get()).key('#', Items.ITEM_FRAME).key('X', Registry.STONE_HAND.get()).key('Y', HexBlock.DARK_POLISH_PLANKS.getBlock().asItem()).patternLine("   ").patternLine(" # ").patternLine("YXY");

    }


}
