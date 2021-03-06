package Alexthw.Hexblades;

import Alexthw.Hexblades.common.items.HexSwordItem;
import Alexthw.Hexblades.common.items.tier1.WaterSaber1;
import Alexthw.Hexblades.deity.HexDeities;
import Alexthw.Hexblades.network.FlameEffectPacket;
import Alexthw.Hexblades.registers.HexItem;
import Alexthw.Hexblades.util.HexUtils;
import elucent.eidolon.capability.ReputationProvider;
import elucent.eidolon.deity.Deity;
import elucent.eidolon.network.Networking;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.DrownedEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
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

    @SubscribeEvent
    public void onDamage(LivingDamageEvent event) {
        LivingEntity damaged = event.getEntityLiving();
        if (damaged instanceof PlayerEntity) {
            Item item = damaged.getItemStackFromSlot(EquipmentSlotType.MAINHAND).getItem();

            if (item instanceof WaterSaber1) {
                float shield = ((WaterSaber1) item).shield;
                event.setAmount(Math.min(1, event.getAmount() - shield));
            }

        }
    }

    @SubscribeEvent
    public void onKill(LivingDeathEvent event) {
        if (event.getSource().getTrueSource() instanceof PlayerEntity && event.getEntity() instanceof MonsterEntity) {
            PlayerEntity player = (PlayerEntity) event.getSource().getTrueSource();
            Item item = player.getItemStackFromSlot(EquipmentSlotType.MAINHAND).getItem();
            if (item instanceof HexSwordItem && !player.world.isRemote()) {
                if (HexUtils.chance((int) (5 + (event.getEntityLiving().getMaxHealth() / 4)), event.getEntity().getEntityWorld())) {
                    Deity HexDeity = HexDeities.HEX_DEITY;
                    event.getEntity().getEntityWorld().getCapability(ReputationProvider.CAPABILITY, null).ifPresent((rep) -> {
                        double prev = rep.getReputation(player, HexDeity.getId());
                        rep.addReputation(player, HexDeity.getId(), 0.5D);
                        HexDeity.onReputationChange(player, rep, prev, rep.getReputation(player, HexDeities.HEX_DEITY.getId()));
                    });
                    Networking.sendToTracking(player.world, event.getEntity().getPosition(), new FlameEffectPacket(event.getEntity().getPosition()));
                    ((HexSwordItem) item).talk(player);
                }
            }
        }
    }
}
