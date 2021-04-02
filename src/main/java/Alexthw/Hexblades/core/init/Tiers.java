package Alexthw.Hexblades.core.init;

import net.minecraft.item.IItemTier;
import net.minecraft.item.crafting.Ingredient;

public class Tiers {
    public static class HexiumTier implements IItemTier {
        @Override
        public int getMaxUses() {
            return 10000;
        }

        @Override
        public float getEfficiency() {
            return 0;
        }

        @Override
        public float getAttackDamage() {
            return 3f;
        }

        @Override
        public int getHarvestLevel() {
            return 0;
        }

        @Override
        public int getEnchantability() {
            return 0;
        }

        @Override
        public Ingredient getRepairMaterial() {
            return null;
        }

        public static HexiumTier INSTANCE = new HexiumTier();
    }
}
