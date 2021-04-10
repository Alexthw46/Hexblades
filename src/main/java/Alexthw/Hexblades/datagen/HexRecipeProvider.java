package Alexthw.Hexblades.datagen;

import Alexthw.Hexblades.core.registers.HexItem;
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
        ShapedRecipeBuilder.shapedRecipe(HexItem.DEV_SWORD.get()).key('#', Items.STICK).key('X', HexItem.SILVER_INGOT.get()).patternLine("X").patternLine("X").patternLine("#").addCriterion("has_spirited_metal", hasItem(HexItem.SILVER_INGOT.get())).build(consumer);
    }


}
