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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

import static Alexthw.Hexblades.util.Constants.NBT.*;
import static net.minecraft.world.GameRules.KEEP_INVENTORY;

public class Events {
    public Events() {
    }

    @SubscribeEvent
    public void onMobDrops(LivingDropsEvent event) {

        LivingEntity entity = event.getEntityLiving();
        World world = entity.world;

        if (!world.isRemote) {
            if (entity instanceof DrownedEntity) {
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
            } else if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                if ((player instanceof FakePlayer) || player.world.getGameRules().getBoolean(KEEP_INVENTORY)) {
                    return;
                }
                List<ItemEntity> keeps = new ArrayList<>();
                for (ItemEntity item : event.getDrops()) {
                    ItemStack stack = item.getItem();
                    if (!stack.isEmpty() && stack.getItem() instanceof HexSwordItem) {
                        keeps.add(item);
                    }
                }

                if (keeps.size() > 0) {
                    event.getDrops().removeAll(keeps);

                    CompoundNBT cmp = new CompoundNBT();
                    cmp.putInt(TAG_HW_DROP_COUNT, keeps.size());

                    int i = 0;
                    for (ItemEntity keep : keeps) {
                        ItemStack stack = keep.getItem();
                        CompoundNBT cmp1 = stack.write(new CompoundNBT());
                        cmp.put(TAG_HW_DROP_PREFIX + i, cmp1);
                        i++;
                    }

                    CompoundNBT data = player.getPersistentData();
                    if (!data.contains(PlayerEntity.PERSISTED_NBT_TAG)) {
                        data.put(PlayerEntity.PERSISTED_NBT_TAG, new CompoundNBT());
                    }

                    CompoundNBT persist = data.getCompound(PlayerEntity.PERSISTED_NBT_TAG);
                    persist.put(TAG_HW_KEEP, cmp);
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerRespawnHW(PlayerEvent.PlayerRespawnEvent event) {
        CompoundNBT data = event.getPlayer().getPersistentData();
        if (data.contains(PlayerEntity.PERSISTED_NBT_TAG)) {
            CompoundNBT cmp = data.getCompound(PlayerEntity.PERSISTED_NBT_TAG);
            CompoundNBT cmp1 = cmp.getCompound(TAG_HW_KEEP);

            int count = cmp1.getInt(TAG_HW_DROP_COUNT);
            for (int i = 0; i < count; i++) {
                CompoundNBT cmp2 = cmp1.getCompound(TAG_HW_DROP_PREFIX + i);
                ItemStack stack = ItemStack.read(cmp2);
                if (!stack.isEmpty()) {
                    ItemStack copy = stack.copy();
                    if (!event.getPlayer().inventory.addItemStackToInventory(copy)) {
                        event.getPlayer().entityDropItem(copy);
                    }
                }
            }

            cmp.remove(TAG_HW_KEEP);
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
