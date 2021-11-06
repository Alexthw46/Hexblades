package alexthw.hexblades.compat;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.common.items.armors.HexWArmor;
import alexthw.hexblades.common.items.armors.NouveauArmor;
import alexthw.hexblades.registers.HexItem;
import com.hollingsworth.arsnouveau.common.items.SpellBook;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import elucent.eidolon.Registry;
import elucent.eidolon.recipe.WorktableRecipe;
import elucent.eidolon.recipe.WorktableRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class ArsNouveauCompat {

    public static HexWArmor makeArmor(EquipmentSlotType slot, Item.Properties properties) {
        return new NouveauArmor(slot, properties);
    }

    public static boolean spellbookInOffHand(PlayerEntity player) {
        return (player.getItemInHand(Hand.OFF_HAND).getItem() instanceof SpellBook);
    }

    public static void addRecipes() {
        WorktableRegistry.register(new WorktableRecipe(new Object[]{
                ItemStack.EMPTY, ItemsRegistry.BLAZE_FIBER, ItemStack.EMPTY,
                ItemsRegistry.BLAZE_FIBER, ItemsRegistry.carbuncleCharm, ItemsRegistry.BLAZE_FIBER,
                ItemStack.EMPTY, ItemsRegistry.BLAZE_FIBER, ItemStack.EMPTY
        }, new Object[]{
                ItemsRegistry.manaGem,
                Registry.ENDER_CALX.get(),
                ItemsRegistry.manaGem,
                Registry.ENDER_CALX.get()
        }, new ItemStack(HexItem.FOCUS_NOUVEAU.get(), 1)).setRegistryName(Hexblades.MODID, "ars_nouveau_focus"));
    }
}
