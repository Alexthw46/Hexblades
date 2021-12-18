package alexthw.hexblades.registers;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

public class Tiers {

    public static class HexiumTier implements Tier {
        @Override
        public int getUses() {
            return 750;
        }

        @Override
        public float getSpeed() {
            return 7.0F;
        }

        public float getAttackDamageBonus() {
            return 3.0F;
        }

        public int getLevel() {
            return 2;
        }

        public int getEnchantmentValue() {
            return 25;
        }


        @Override
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.of(new ItemStack(HexItem.HEXIUM_INGOT.get()));
        }

        public static HexiumTier INSTANCE = new HexiumTier();
    }

    public static class PatronWeaponTier implements Tier {
        @Override
        public int getUses() {
            return 1250;
        }

        @Override
        public float getSpeed() {
            return 8.0F;
        }

        public float getAttackDamageBonus() {
            return 0F;
        }

        public int getLevel() {
            return 5;
        }

        public int getEnchantmentValue() {
            return 5;
        }


        @Override
        public @NotNull Ingredient getRepairIngredient() {
            return Ingredient.EMPTY;
        }

        public static PatronWeaponTier INSTANCE = new PatronWeaponTier();
    }

}
