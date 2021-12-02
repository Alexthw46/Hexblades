package alexthw.hexblades.recipes;

import alexthw.hexblades.common.items.ArmorFocus;
import alexthw.hexblades.common.items.armors.HexWArmor;
import alexthw.hexblades.registers.HexItem;
import alexthw.hexblades.recipes.ArmorFocusRecipe;
import com.google.common.collect.ImmutableList;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICustomCraftingCategoryExtension;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.List;

public class ArmorFocusRecipeWrapper implements ICustomCraftingCategoryExtension {

    private final ResourceLocation name;

    public ArmorFocusRecipeWrapper(ArmorFocusRecipe recipe) {
        this.name = recipe.getId();
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IIngredients ingredients) {
        IFocus<?> focus = recipeLayout.getFocus(VanillaTypes.ITEM);
        IGuiItemStackGroup group = recipeLayout.getItemStacks();
        group.set(ingredients);
        if (focus != null) {
            ItemStack focused = (ItemStack) focus.getValue();
            if (focus.getMode() == IFocus.Mode.INPUT && focused.getItem() instanceof ArmorFocus) {
                ItemStack copy = focused.copy();
                copy.setCount(1);
                group.set(2, copy);
                group.set(0, getArmorsWithFocus(((ArmorFocus) focused.getItem()).getModFocus(), ingredients));
            } else if (focused.getItem() instanceof HexWArmor) {
                group.set(1, new ItemStack(focused.getItem()));
                group.set(0, getFociOnPiece(focused.getItem()));
            }
        }
    }

    private List<ItemStack> getFociOnPiece(Item item) {
        if (item instanceof HexWArmor) {
            ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();
            for (String type : ArmorFocus.foci) {
                ItemStack stack = new ItemStack(item);
                HexWArmor.setFocus(stack, type);
                builder.add(stack);
            }
            return builder.build();
        }
        return ImmutableList.of();
    }

    private List<ItemStack> getArmorsWithFocus(String type, IIngredients ingredients) {
        ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();
        for (ItemStack itemStack : ingredients.getOutputs(VanillaTypes.ITEM).get(0)) {
            ItemStack toAdd = itemStack.copy();
            HexWArmor.setFocus(toAdd, type);
            builder.add(toAdd);
        }
        return builder.build();
    }

    @Override
    public void setIngredients(IIngredients ingredients) {
        ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();
        ImmutableList.Builder<ItemStack> armors = ImmutableList.builder();
        ImmutableList.Builder<ItemStack> focus = ImmutableList.builder();
        armors.add(new ItemStack(HexItem.HEX_ARMOR_H.get()));
        armors.add(new ItemStack(HexItem.HEX_ARMOR_C.get()));
        armors.add(new ItemStack(HexItem.HEX_ARMOR_L.get()));
        armors.add(new ItemStack(HexItem.HEX_ARMOR_B.get()));
        focus.add(new ItemStack(HexItem.FOCUS_WARLOCK.get()));
        focus.add(new ItemStack(HexItem.FOCUS_BOTANIA.get()));
        focus.add(new ItemStack(HexItem.FOCUS_NOUVEAU.get()));

        builder.add(armors.build());
        builder.add(focus.build());

        ingredients.setInputLists(VanillaTypes.ITEM, builder.build());
        ingredients.setOutputLists(VanillaTypes.ITEM, ImmutableList.of(armors.build()));
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return name;
    }
}
