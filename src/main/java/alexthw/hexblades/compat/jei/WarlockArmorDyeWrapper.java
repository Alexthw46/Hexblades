package alexthw.hexblades.compat.jei;

import alexthw.hexblades.common.items.armors.DyebleWarlockArmor;
import alexthw.hexblades.registers.HexItem;
import alexthw.hexblades.temp.WarlockArmorDye;
import com.google.common.collect.ImmutableList;
import elucent.eidolon.Registry;
import elucent.eidolon.item.WarlockRobesItem;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ingredient.IGuiItemStackGroup;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IFocus;
import mezz.jei.api.recipe.category.extensions.vanilla.crafting.ICustomCraftingCategoryExtension;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.List;

public class WarlockArmorDyeWrapper implements ICustomCraftingCategoryExtension {

    private final ResourceLocation name;

    public WarlockArmorDyeWrapper(WarlockArmorDye recipe) {
        this.name = recipe.getId();
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, IIngredients ingredients) {
        IFocus<?> focus = recipeLayout.getFocus();
        IGuiItemStackGroup group = recipeLayout.getItemStacks();
        group.set(ingredients);

        if (focus != null) {
            ItemStack focused = (ItemStack) focus.getValue();

            if (focus.getMode() == IFocus.Mode.INPUT && focused.getItem() instanceof DyeItem) {
                ItemStack copy = focused.copy();
                copy.setCount(1);
                group.set(2, copy);
                group.set(0, getArmorsWithDye(((DyeItem) focused.getItem()).getDyeColor().getId(), ingredients));
            } else if (focused.getItem() instanceof WarlockRobesItem) {
                group.set(1, new ItemStack(focused.getItem()));
                group.set(0, getColorsOnPiece(focused.getItem()));
            }
        }
    }

    private List<ItemStack> getColorsOnPiece(Item item) {
        ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();

        if (item instanceof WarlockRobesItem) {
            if (!(item instanceof DyebleWarlockArmor)) {
                switch (((WarlockRobesItem) item).getSlot()) {
                    case HEAD:
                        item = HexItem.DYE_WARLOCK_H.get();
                        break;
                    case CHEST:
                        item = HexItem.DYE_WARLOCK_C.get();
                        break;
                    case FEET:
                        item = HexItem.DYE_WARLOCK_F.get();
                }
            }
            for (int i = 0; i < 16; i++) {
                if (i != 8 && i != 7) {
                    ItemStack stack = new ItemStack(item);
                    stack.getOrCreateTag().putInt("color", i);
                    builder.add(stack);
                }
            }
            return builder.build();
        } else return ImmutableList.of();
    }

    private List<ItemStack> getArmorsWithDye(int id, IIngredients ingredients) {
        ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();
        for (ItemStack itemStack : ingredients.getOutputs(VanillaTypes.ITEM).get(0)) {
            ItemStack toColor = itemStack.copy();
            toColor.getOrCreateTag().putInt("color", id);
            builder.add(toColor);
        }
        return builder.build();
    }

    @Override
    public void setIngredients(IIngredients ingredients) {

        ImmutableList.Builder<List<ItemStack>> builder = ImmutableList.builder();
        ImmutableList.Builder<ItemStack> armors = ImmutableList.builder();
        ImmutableList.Builder<ItemStack> dyes = ImmutableList.builder();

        armors.add(new ItemStack(HexItem.DYE_WARLOCK_H.get()));
        armors.add(new ItemStack(HexItem.DYE_WARLOCK_C.get()));
        armors.add(new ItemStack(HexItem.DYE_WARLOCK_F.get()));

        ingredients.setOutputLists(VanillaTypes.ITEM, ImmutableList.of(armors.build()));

        armors.add(new ItemStack(Registry.WARLOCK_HAT.get()));
        armors.add(new ItemStack(Registry.WARLOCK_CLOAK.get()));
        armors.add(new ItemStack(Registry.WARLOCK_BOOTS.get()));

        builder.add(armors.build());

        dyes.add(new ItemStack(Items.WHITE_DYE));
        dyes.add(new ItemStack(Items.ORANGE_DYE));
        dyes.add(new ItemStack(Items.MAGENTA_DYE));
        dyes.add(new ItemStack(Items.LIGHT_BLUE_DYE));
        dyes.add(new ItemStack(Items.YELLOW_DYE));
        dyes.add(new ItemStack(Items.LIME_DYE));
        dyes.add(new ItemStack(Items.PINK_DYE));
        dyes.add(new ItemStack(Items.CYAN_DYE));
        dyes.add(new ItemStack(Items.PURPLE_DYE));
        dyes.add(new ItemStack(Items.BLUE_DYE));
        dyes.add(new ItemStack(Items.BROWN_DYE));
        dyes.add(new ItemStack(Items.GREEN_DYE));
        dyes.add(new ItemStack(Items.RED_DYE));
        dyes.add(new ItemStack(Items.BLACK_DYE));

        builder.add(dyes.build());

        ingredients.setInputLists(VanillaTypes.ITEM, builder.build());
    }

    @Nullable
    @Override
    public ResourceLocation getRegistryName() {
        return name;
    }
}
