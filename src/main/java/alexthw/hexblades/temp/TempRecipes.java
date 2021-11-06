package alexthw.hexblades.temp;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.compat.ArsNouveauCompat;
import alexthw.hexblades.compat.BotaniaCompat;
import alexthw.hexblades.registers.HexItem;
import elucent.eidolon.Registry;
import elucent.eidolon.recipe.CrucibleRecipe;
import elucent.eidolon.recipe.CrucibleRegistry;
import elucent.eidolon.recipe.WorktableRecipe;
import elucent.eidolon.recipe.WorktableRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import static alexthw.hexblades.ConfigHandler.COMMON;
import static alexthw.hexblades.util.CompatUtil.isArsNovLoaded;
import static alexthw.hexblades.util.CompatUtil.isBotaniaLoaded;

public class TempRecipes {

    public static void init() {
        if (!COMMON.NUKE_CRUCIBLE.get()) {
            CrucibleRegistry.register(new CrucibleRecipe(new ItemStack(HexItem.HEXIUM_INGOT.get(), 2)).setRegistryName(Hexblades.MODID, "hexium_recipe").addStep(Registry.LEAD_INGOT.get(), Items.GOLD_INGOT).addStirringStep(1, Registry.SOUL_SHARD.get(), Registry.ENCHANTED_ASH.get()));
            CrucibleRegistry.register(new CrucibleRecipe(new ItemStack(HexItem.SOUL_CANDY.get(), 4)).setRegistryName(Hexblades.MODID, "soul_candy_recipe").addStep(Registry.SOUL_SHARD.get()).addStirringStep(1, Items.HONEY_BOTTLE, Items.SUGAR));
        }
        if (!COMMON.NUKE_WORKBENCH.get()) {
            WorktableRegistry.register(new WorktableRecipe(new Object[]{
                    ItemStack.EMPTY, Registry.WICKED_WEAVE.get(), ItemStack.EMPTY,
                    Registry.WICKED_WEAVE.get(), Registry.LESSER_SOUL_GEM.get(), Registry.WICKED_WEAVE.get(),
                    ItemStack.EMPTY, Registry.WICKED_WEAVE.get(), ItemStack.EMPTY
            }, new Object[]{
                    Registry.ARCANE_GOLD_NUGGET.get(),
                    Registry.ARCANE_GOLD_NUGGET.get(),
                    Registry.ARCANE_GOLD_NUGGET.get(),
                    Registry.ARCANE_GOLD_NUGGET.get()
            }, new ItemStack(HexItem.FOCUS_WARLOCK.get(), 1)).setRegistryName(Hexblades.MODID, "warlock_focus"));

            if (isBotaniaLoaded()) {
                BotaniaCompat.addRecipes();
            }

            if (isArsNovLoaded()) {
                ArsNouveauCompat.addRecipes();
            }
        }
    }
}
