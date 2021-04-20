package Alexthw.Hexblades.core.registers;

import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;

public class Tiers {
    public static class HexiumTier implements IItemTier {
        @Override
        public int getMaxUses() {
            return 10000;
        }

        @Override
        public float getEfficiency() {
            return 7.0F;
        }

        public float getAttackDamage() {
            return 3.0F;
        }

        public int getHarvestLevel() {
            return 2;
        }

        public int getEnchantability() {
            return 20;
        }


        @Override
        public Ingredient getRepairMaterial() {
            return Ingredient.fromStacks(new ItemStack(HexItem.HEXIUM_INGOT.get()));

        }

        public static HexiumTier INSTANCE = new HexiumTier();
    }

    public static class PatronWeaponTier implements IItemTier {
        @Override
        public int getMaxUses() {
            return 10000;
        }

        @Override
        public float getEfficiency() {
            return 7.0F;
        }

        public float getAttackDamage() {
            return 3.0F;
        }

        public int getHarvestLevel() {
            return 2;
        }

        public int getEnchantability() {
            return 20;
        }


        @Override
        public Ingredient getRepairMaterial() {
            return Ingredient.EMPTY;
        }

        public static PatronWeaponTier INSTANCE = new PatronWeaponTier();
    }

}