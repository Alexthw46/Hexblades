package Alexthw.Hexblades.datagen;

import Alexthw.Hexblades.registers.HexItem;
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


    }


}
