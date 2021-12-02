package alexthw.hexblades.recipes;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.compat.ArsNouveauCompat;
import alexthw.hexblades.compat.BotaniaCompat;
import alexthw.hexblades.registers.HexItem;
import elucent.eidolon.Registry;
import elucent.eidolon.recipe.CrucibleRecipe;
import elucent.eidolon.recipe.CrucibleRegistry;
import elucent.eidolon.recipe.WorktableRecipe;
import elucent.eidolon.recipe.WorktableRegistry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.Tags;

import static alexthw.hexblades.ConfigHandler.COMMON;
import static alexthw.hexblades.util.CompatUtil.isArsNovLoaded;
import static alexthw.hexblades.util.CompatUtil.isBotaniaLoaded;

public class TempRecipes {

    public static void init() {
        if (!COMMON.NUKE_CRUCIBLE.get()) {
            //HEX INGOTS
            CrucibleRegistry.register(new CrucibleRecipe(new ItemStack(HexItem.HEXIUM_INGOT.get(), 2)).setRegistryName(Hexblades.MODID, "hexium_recipe").addStep(ItemTags.createOptional(new ResourceLocation("forge", "ingots/lead")), Tags.Items.INGOTS_GOLD).addStirringStep(1, Registry.SOUL_SHARD.get(), Registry.ENCHANTED_ASH.get()));
            CrucibleRegistry.register(new CrucibleRecipe(new ItemStack(HexItem.HEXED_INGOT.get(), 1)).setRegistryName(Hexblades.MODID, "hexed_recipe").addStep(HexItem.HEXIUM_INGOT.get(), Registry.ARCANE_GOLD_INGOT.get()).addStirringStep(1, Registry.DEATH_ESSENCE.get()));
            //SOUL CANDY
            CrucibleRegistry.register(new CrucibleRecipe(new ItemStack(HexItem.SOUL_CANDY.get(), 4)).setRegistryName(Hexblades.MODID, "soul_candy_recipe").addStep(Registry.SOUL_SHARD.get()).addStirringStep(1, Items.HONEY_BOTTLE, Items.SUGAR));
        }
        if (!COMMON.NUKE_WORKBENCH.get()) {
            //ARMOR FOCUS
            WorktableRegistry.register(new WorktableRecipe(new Object[]{
                    ItemStack.EMPTY, HexItem.HEXIUM_INGOT.get(), ItemStack.EMPTY,
                    HexItem.HEXIUM_INGOT.get(), Registry.LESSER_SOUL_GEM.get(), HexItem.HEXIUM_INGOT.get(),
                    ItemStack.EMPTY, HexItem.HEXIUM_INGOT.get(), ItemStack.EMPTY
            }, new Object[]{
                    Registry.UNHOLY_SYMBOL.get(),
                    Registry.GOLD_INLAY.get(),
                    Registry.WICKED_WEAVE.get(),
                    Registry.GOLD_INLAY.get()
            }, new ItemStack(HexItem.FOCUS_BASE.get(), 1)).setRegistryName(Hexblades.MODID, "blank_focus"));
            //WARLOCK
            WorktableRegistry.register(new WorktableRecipe(new Object[]{
                    ItemStack.EMPTY, Registry.WICKED_WEAVE.get(), ItemStack.EMPTY,
                    Registry.WICKED_WEAVE.get(), HexItem.FOCUS_BASE.get(), Registry.WICKED_WEAVE.get(),
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
