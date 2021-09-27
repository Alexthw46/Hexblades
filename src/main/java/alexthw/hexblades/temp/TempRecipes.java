package alexthw.hexblades.temp;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.registers.HexItem;
import elucent.eidolon.Registry;
import elucent.eidolon.recipe.CrucibleRecipe;
import elucent.eidolon.recipe.CrucibleRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class TempRecipes {

    public static void init() {
        CrucibleRegistry.register(new CrucibleRecipe(new ItemStack(HexItem.HEXIUM_INGOT.get(), 2)).setRegistryName(Hexblades.MOD_ID, "hexium_recipe").addStep(Registry.LEAD_INGOT.get(), Items.GOLD_INGOT).addStirringStep(1, Registry.SOUL_SHARD.get(), Registry.ENCHANTED_ASH.get()));
        CrucibleRegistry.register(new CrucibleRecipe(new ItemStack(HexItem.SOUL_CANDY.get(), 4)).setRegistryName(Hexblades.MOD_ID, "soul_candy_recipe").addStep(Registry.SOUL_SHARD.get()).addStirringStep(1, Items.HONEY_BOTTLE, Items.SUGAR));
    }
}
