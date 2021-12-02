package alexthw.hexblades.recipes;

import alexthw.hexblades.common.items.ArmorFocus;
import alexthw.hexblades.common.items.armors.HexWArmor;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

public class ArmorFocusRecipe extends CustomRecipe {
    public static final SimpleRecipeSerializer<ArmorFocusRecipe> SERIALIZER = new SimpleRecipeSerializer<>(ArmorFocusRecipe::new);

    public ArmorFocusRecipe(ResourceLocation pId) {
        super(pId);
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     *
     * @param inv    grid
     * @param pLevel world
     */
    @Override
    public boolean matches(CraftingContainer inv, Level pLevel) {
        boolean foundFocus = false;
        boolean foundItem = false;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof ArmorFocus && !foundFocus) {
                    foundFocus = true;
                } else if (!foundItem) {
                    if (stack.getItem() instanceof HexWArmor) {
                        foundItem = true;
                    } else {
                        return false;
                    }
                }
            }
        }

        return foundFocus && foundItem;
    }

    /**
     * Returns an Item that is the result of this recipe
     *
     * @param inv grid
     */
    @Override
    public ItemStack assemble(CraftingContainer inv) {
        ItemStack item = ItemStack.EMPTY;
        String focus = null;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof HexWArmor && item.isEmpty()) {
                    item = stack;
                } else {
                    focus = ((ArmorFocus) stack.getItem()).getModFocus();
                }
            }
        }

        ItemStack copy = item.copy();
        HexWArmor.setFocus(copy, focus);
        return copy;
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     *
     * @param width  width
     * @param height height
     */
    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width > 1 || height > 1;
    }

    @Nonnull
    @Override
    public RecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }

}
