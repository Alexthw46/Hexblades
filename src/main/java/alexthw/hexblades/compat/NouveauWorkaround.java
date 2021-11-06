package alexthw.hexblades.compat;

import alexthw.hexblades.common.items.armors.NouveauArmor;
import com.hollingsworth.arsnouveau.api.event.ManaRegenCalcEvent;
import com.hollingsworth.arsnouveau.api.event.MaxManaCalcEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class NouveauWorkaround {

    @SubscribeEvent
    public void adjustMaxMana(MaxManaCalcEvent event) {
        int max = event.getMax();
        Entity entity = event.getEntity();
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            for (ItemStack stack : player.getArmorSlots()) {
                if (stack.getItem() instanceof NouveauArmor) {
                    max += ((NouveauArmor) stack.getItem()).getMaxManaBoost(stack);
                }
            }
            event.setMax(max);
        }
    }

    @SubscribeEvent
    public void adjustManaRegen(ManaRegenCalcEvent event) {
        double regen = event.getRegen();
        Entity entity = event.getEntity();
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            for (ItemStack stack : player.getArmorSlots()) {
                if (stack.getItem() instanceof NouveauArmor) {
                    regen += ((NouveauArmor) stack.getItem()).getManaRegenBonus(stack);
                }
            }
            event.setRegen(regen);
        }
    }
}
