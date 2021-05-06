package Alexthw.Hexblades;

import Alexthw.Hexblades.registers.HexItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.DrownedEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Events {
    public Events() {
    }

    @SubscribeEvent
    public void onMobDrops(LivingDropsEvent event) {

        LivingEntity entity = event.getEntityLiving();
        World world = entity.world;

        if (entity instanceof DrownedEntity && !world.isRemote) {
            LivingEntity source = (LivingEntity) event.getSource().getTrueSource();

            int looting = ForgeHooks.getLootingLevel(entity, source, event.getSource());
            boolean doDrop = false;
            if (entity.world.rand.nextInt(10) == 1) doDrop = true;
            else for (int i = 0; i < looting; i++) {
                if (entity.world.rand.nextInt(20) == 1) {
                    doDrop = true;
                    break;
                }
            }
            if (doDrop) {
                ItemStack stack = new ItemStack(HexItem.DROWNED_HEART.get());
                ItemEntity drop = new ItemEntity(entity.world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), stack);
                event.getDrops().add(drop);
            }
        }
    }
}
