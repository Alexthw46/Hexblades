package alexthw.hexblades;

import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.common.items.IHexblade;
import alexthw.hexblades.common.items.armors.HexWArmor;
import alexthw.hexblades.common.items.hexblades.WaterSaber;
import alexthw.hexblades.deity.HexDeities;
import alexthw.hexblades.network.FlameEffectPacket;
import alexthw.hexblades.registers.HexItem;
import alexthw.hexblades.util.HexUtils;
import elucent.eidolon.capability.IReputation;
import elucent.eidolon.deity.Deity;
import elucent.eidolon.event.SpeedFactorEvent;
import elucent.eidolon.network.Networking;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Drowned;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

import static alexthw.hexblades.util.Constants.NBT.*;
import static net.minecraft.world.level.GameRules.RULE_KEEPINVENTORY;

public class Events {

    @SubscribeEvent
    public void onMobDrops(LivingDropsEvent event) {

        LivingEntity entity = event.getEntityLiving();
        Level world = entity.level;

        if (!world.isClientSide()) {
            if (entity instanceof Drowned) {
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
            } else if (entity instanceof Player player) {
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

                    CompoundTag cmp = new CompoundTag();
                    cmp.putInt(TAG_HW_DROP_COUNT, keeps.size());

                    int i = 0;
                    for (ItemEntity keep : keeps) {
                        ItemStack stack = keep.getItem();
                        CompoundTag cmp1 = stack.save(new CompoundTag());
                        cmp.put(TAG_HW_DROP_PREFIX + i, cmp1);
                        i++;
                    }

                    CompoundTag data = player.getPersistentData();
                    if (!data.contains(Player.PERSISTED_NBT_TAG)) {
                        data.put(Player.PERSISTED_NBT_TAG, new CompoundTag());
                    }

                    CompoundTag persist = data.getCompound(Player.PERSISTED_NBT_TAG);
                    persist.put(TAG_HW_KEEP, cmp);
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerRespawnHW(PlayerEvent.PlayerRespawnEvent event) {
        CompoundTag data = event.getPlayer().getPersistentData();
        if (data.contains(Player.PERSISTED_NBT_TAG)) {
            CompoundTag cmp = data.getCompound(Player.PERSISTED_NBT_TAG);
            CompoundTag cmp1 = cmp.getCompound(TAG_HW_KEEP);

            int count = cmp1.getInt(TAG_HW_DROP_COUNT);
            for (int i = 0; i < count; i++) {
                CompoundTag cmp2 = cmp1.getCompound(TAG_HW_DROP_PREFIX + i);
                ItemStack stack = ItemStack.of(cmp2);
                if (!stack.isEmpty()) {
                    ItemStack copy = stack.copy();
                    if (!event.getPlayer().getInventory().add(copy)) {
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
        if (damaged instanceof Player) {
            ItemStack item = damaged.getItemBySlot(EquipmentSlot.MAINHAND);
            if (item.getItem() instanceof WaterSaber saber && saber.getAwakened(item)) {
                float shield = saber.getShielding(item);
                event.setAmount(Math.min(1, event.getAmount() - shield));
            }
        }
    }


    @SubscribeEvent
    public void onKill(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player player && event.getEntityLiving() instanceof Monster enemy) {
            Item item = player.getItemBySlot(EquipmentSlot.MAINHAND).getItem();
            if (item instanceof HexSwordItem hexblade && !player.level.isClientSide()) {
                if (HexUtils.chance((int) (5 + (enemy.getMaxHealth() / 4)), enemy.getCommandSenderWorld())) {
                    Deity HexDeity = HexDeities.HEX_DEITY;
                    player.getCommandSenderWorld().getCapability(IReputation.INSTANCE, null).ifPresent((rep) -> {
                        double prev = rep.getReputation(player, HexDeity.getId());
                        rep.addReputation(player, HexDeity.getId(), 0.5D);
                        HexDeity.onReputationChange(player, rep, prev, rep.getReputation(player, HexDeities.HEX_DEITY.getId()));
                    });
                    Networking.sendToTracking(player.level, enemy.blockPosition(), new FlameEffectPacket(enemy.blockPosition()));
                    hexblade.talk(player);
                }
            }
        }
    }

    @SubscribeEvent
    public void onApplyPotion(PotionEvent.PotionApplicableEvent event) {
        if (event.getPotionEffect().getEffect() == MobEffects.MOVEMENT_SLOWDOWN && event.getEntityLiving().getItemBySlot(EquipmentSlot.FEET).getItem() instanceof HexWArmor) {
            event.setResult(Event.Result.DENY);
        }
    }

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {

        DamageSource source = event.getSource();

        if (!(source.getEntity() instanceof Player livingEntity)) {
            return;
        }

        if (source == DamageSource.WITHER || source.isMagic()) {

            ItemStack stack = livingEntity.getItemBySlot(EquipmentSlot.HEAD);

            if (stack.getItem() instanceof HexWArmor) {
                float multiplier = HexWArmor.getFocusId(stack) == 1 ? 1.5F : 1.25F;
                event.setAmount(event.getAmount() * multiplier);

                if (source == DamageSource.WITHER) {
                    livingEntity.heal(event.getAmount() / 3.0F);
                }
            }

            stack = event.getEntityLiving().getItemBySlot(EquipmentSlot.CHEST);

            if (stack.getItem() instanceof HexWArmor) {
                float multiplier = HexWArmor.getFocusId(stack) == 1 ? 0.5F : 0.75F;
                event.setAmount(event.getAmount() * multiplier);
            }

        } else if (source == DamageSource.FALL || source == DamageSource.ANVIL || source == DamageSource.HOT_FLOOR || source.isExplosion()) {
            ItemStack stack = livingEntity.getItemBySlot(EquipmentSlot.LEGS);
            if (stack.getItem() instanceof HexWArmor) {
                float multiplier = HexWArmor.getFocusId(stack) == 1 ? 0.5F : 0.75F;
                event.setAmount(event.getAmount() * multiplier);
            }
        }else if (source == DamageSource.SWEET_BERRY_BUSH){
            ItemStack stack = livingEntity.getItemBySlot(EquipmentSlot.FEET);
            if (stack.getItem() instanceof HexWArmor && event.isCancelable()) {
                event.setCanceled(true);
            }
        }

    }

    @SubscribeEvent
    public void onGetSpeedFactor(SpeedFactorEvent event) {
        if ((event.getSpeedFactor() < 1.0F) && event.getEntity() instanceof LivingEntity) {
            ItemStack stack = ((LivingEntity) event.getEntity()).getItemBySlot(EquipmentSlot.FEET);
            if ((stack.getItem() instanceof HexWArmor) && HexWArmor.getFocusId(stack) == 1) {
                float diff = 1.0F - event.getSpeedFactor();
                event.setSpeedFactor(1.0F - diff / 2.0F);
            }
        }

    }

}
