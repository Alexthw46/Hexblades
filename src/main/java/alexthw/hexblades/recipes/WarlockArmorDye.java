package alexthw.hexblades.recipes;

import alexthw.hexblades.common.items.armors.DyebleWarlockArmor;
import alexthw.hexblades.registers.HexItem;
import elucent.eidolon.item.WarlockRobesItem;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeColor;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class WarlockArmorDye extends SpecialRecipe {

    public static final SpecialRecipeSerializer<WarlockArmorDye> SERIALIZER = new SpecialRecipeSerializer<>(WarlockArmorDye::new);

    public WarlockArmorDye(ResourceLocation pId) {
        super(pId);
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     *
     * @param inv    grid
     * @param pLevel world
     */
    @Override
    public boolean matches(CraftingInventory inv, World pLevel) {
        boolean foundDye = false;
        boolean foundItem = false;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof DyeItem && !foundDye) {
                    int color = ((DyeItem) stack.getItem()).getDyeColor().getId();
                    if (color == 7 || color == 8) return false;
                    foundDye = true;
                } else if (!foundItem) {
                    if (stack.getItem() instanceof WarlockRobesItem) {
                        foundItem = true;
                    } else {
                        return false;
                    }
                }
            }
        }

        return foundDye && foundItem;
    }

    /**
     * Returns an Item that is the result of this recipe
     *
     * @param inv grid
     */
    @Override
    public ItemStack assemble(CraftingInventory inv) {
        ItemStack item = ItemStack.EMPTY;
        DyeColor dye = null;

        for (int i = 0; i < inv.getContainerSize(); i++) {
            ItemStack stack = inv.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof WarlockRobesItem && item.isEmpty()) {
                    item = stack;
                } else {
                    dye = ((DyeItem) stack.getItem()).getDyeColor();
                }
            }
        }

        ItemStack dyed = item.copy();

        assert dye != null;

        if (item.getItem() instanceof DyebleWarlockArmor) {

            dyed.getOrCreateTag().putInt("color", dye.getId());

        } else {
            CompoundNBT tag = item.getOrCreateTag();
            tag.putInt("color", dye.getId());
            WarlockRobesItem robes = (WarlockRobesItem) item.getItem();
            switch (robes.getSlot()) {
                case HEAD:
                    dyed = new ItemStack(HexItem.DYE_WARLOCK_H.get(), 1);
                    dyed.setTag(tag);
                    break;
                case CHEST:
                    dyed = new ItemStack(HexItem.DYE_WARLOCK_C.get(), 1);
                    dyed.setTag(tag);
                    break;
                case FEET:
                    dyed = new ItemStack(HexItem.DYE_WARLOCK_F.get(), 1);
                    dyed.setTag(tag);
                    break;
            }
        }

        return dyed;
    }


    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return pWidth > 1 || pHeight > 1;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return SERIALIZER;
    }
}
