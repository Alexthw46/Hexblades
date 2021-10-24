package alexthw.hexblades;

import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.common.items.IHexblade;
import alexthw.hexblades.common.items.tier1.WaterSaber1;
import alexthw.hexblades.deity.HexDeities;
import alexthw.hexblades.network.FlameEffectPacket;
import alexthw.hexblades.registers.HexItem;
import alexthw.hexblades.util.HexUtils;
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

import static alexthw.hexblades.util.Constants.NBT.*;
import static net.minecraft.world.GameRules.RULE_KEEPINVENTORY;

public class Events {
    public Events() {
    }

    @SubscribeEvent
    public void onMobDrops(LivingDropsEvent event) {

        LivingEntity entity = event.getEntityLiving();
        World world = entity.level;

        if (!world.isClientSide) {
            if (entity instanceof DrownedEntity) {
                LivingEntity source = (LivingEntity) event.getSource().getEntity();

                int looting = ForgeHooks.getLootingLevel(entity, source, event.getSource());
                boolean doDrop = false;
                if (entity.level.random.nextInt(10) == 1) doDrop = true;
                else for (int i = 0; i < looting; i++) {
                    if (entity.level.random.nextInt(20) == 1) {
                        doDrop = true;
                        break;
                    }
                }
                if (doDrop) {
                    ItemStack stack = new ItemStack(HexItem.DROWNED_HEART.get());
                    ItemEntity drop = new ItemEntity(entity.level, entity.getX(), entity.getY(), entity.getZ(), stack);
                    event.getDrops().add(drop);
                }
            } else if (entity instanceof PlayerEntity) {
                PlayerEntity player = (PlayerEntity) entity;
                if ((player instanceof FakePlayer) || player.level.getGameRules().getBoolean(RULE_KEEPINVENTORY)) {
                    return;
                }
                List<ItemEntity> keeps = new ArrayList<>();
                for (ItemEntity item : event.getDrops()) {
                    ItemStack stack = item.getItem();
                    if (!stack.isEmpty() && stack.getItem() instanceof IHexblade) {
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
                        CompoundNBT cmp1 = stack.save(new CompoundNBT());
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
                ItemStack stack = ItemStack.of(cmp2);
                if (!stack.isEmpty()) {
                    ItemStack copy = stack.copy();
                    if (!event.getPlayer().inventory.add(copy)) {
                        event.getPlayer().spawnAtLocation(copy);
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
            Item item = damaged.getItemBySlot(EquipmentSlotType.MAINHAND).getItem();

            if (item instanceof WaterSaber1) {
                float shield = ((WaterSaber1) item).shield;
                event.setAmount(Math.min(1, event.getAmount() - shield));
            }

        }
    }

    @SubscribeEvent
    public void onKill(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof PlayerEntity && event.getEntity() instanceof MonsterEntity) {
            PlayerEntity player = (PlayerEntity) event.getSource().getEntity();
            Item item = player.getItemBySlot(EquipmentSlotType.MAINHAND).getItem();
            if (item instanceof HexSwordItem && !player.level.isClientSide()) {
                if (HexUtils.chance((int) (5 + (event.getEntityLiving().getMaxHealth() / 4)), event.getEntity().getCommandSenderWorld())) {
                    Deity HexDeity = HexDeities.HEX_DEITY;
                    event.getEntity().getCommandSenderWorld().getCapability(ReputationProvider.CAPABILITY, null).ifPresent((rep) -> {
                        double prev = rep.getReputation(player, HexDeity.getId());
                        rep.addReputation(player, HexDeity.getId(), 0.5D);
                        HexDeity.onReputationChange(player, rep, prev, rep.getReputation(player, HexDeities.HEX_DEITY.getId()));
                    });
                    Networking.sendToTracking(player.level, event.getEntity().blockPosition(), new FlameEffectPacket(event.getEntity().blockPosition()));
                    ((HexSwordItem) item).talk(player);
                }
            }
        }
    }
}
