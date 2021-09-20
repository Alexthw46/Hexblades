package Alexthw.Hexblades.common.items.tier1;

import Alexthw.Hexblades.common.entity.FulgorProjectileEntity;
import Alexthw.Hexblades.common.items.HexSwordItem;
import Alexthw.Hexblades.registers.HexEntityType;
import Alexthw.Hexblades.registers.HexItem;
import Alexthw.Hexblades.registers.HexRegistry;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import elucent.eidolon.Registry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import static Alexthw.Hexblades.ConfigHandler.COMMON;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class Lightning_SSwordL1 extends HexSwordItem {

    protected int projectileCost = getMaxDamage() / 3;

    public Lightning_SSwordL1(Item.Properties props) {
        super(1, -1.5F, props);
        tooltipText = "tooltip.HexSwordItem.thunder_knives";
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.SPEAR;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        if (handIn == Hand.OFF_HAND && hasTwin(playerIn) && (stack.getMaxDamage() - stack.getDamage() > projectileCost)) {
            playerIn.setActiveHand(handIn);
            setAwakenedState(stack, true);
            recalculatePowers(playerIn.getHeldItem(handIn), worldIn, playerIn);
            return ActionResult.resultConsume(stack);
        }
        return ActionResult.resultPass(playerIn.getHeldItem(handIn));
    }

    public boolean hasTwin(PlayerEntity player) {
        return player.getHeldItem(Hand.MAIN_HAND).getItem() == HexItem.LIGHTNING_DAGGER_R.get();
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        if (isActivated) {
            double devotion = getDevotion(player);
            rechargeTick = max(1, (int) devotion / COMMON.DualsRR.get());
            setAttackPower(weapon, devotion / COMMON.DualsDS1.get());
            setAttackSpeed(weapon, devotion / COMMON.DualsAS1.get());
        }
    }

    public int getUseDuration(ItemStack stack) {
        return 72000 - (int) (10 * getAttackSpeed(stack));
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, LivingEntity entity, int timeleft) {
        int i = this.getUseDuration(stack) - timeleft;
        if (!world.isRemote() && i >= 10) {
            Vector3d pos = entity.getPositionVec().add(entity.getLookVec().scale(0.5D)).add(-0.5D * Math.sin(Math.toRadians(225.0F - entity.rotationYawHead)), entity.getHeight() * 0.75F, -0.5D * Math.cos(Math.toRadians(225.0F - entity.rotationYawHead)));
            Vector3d vel = entity.getEyePosition(0.0F).add(entity.getLookVec().scale(40.0D)).subtract(pos).scale(0.05D);
            world.addEntity((new FulgorProjectileEntity(HexEntityType.FULGOR_PROJECTILE.get(), world)).shoot(pos.x, pos.y, pos.z, vel.x, vel.y, vel.z, entity.getUniqueID()));
            world.playSound(null, pos.x, pos.y, pos.z, Registry.CAST_SOULFIRE_EVENT.get(), SoundCategory.NEUTRAL, 0.75F, random.nextFloat() * 0.2F + 0.9F);
            if ((entity instanceof PlayerEntity) && !(((PlayerEntity) entity).abilities.isCreativeMode)) {
                stack.setDamage(min(stack.getDamage() + projectileCost, stack.getMaxDamage() - 1));
            }
        }
        setAwakenedState(stack, false);
    }

    @Override
    protected boolean onHitEffects() {
        return true;
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker) {
        target.addPotionEffect(new EffectInstance(HexRegistry.CHARGED_EFFECT.get(), 100, 0));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
        if (slot == EquipmentSlotType.OFFHAND) {
            multimap.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", getAttackPower(stack), AttributeModifier.Operation.ADDITION));
            multimap.put(Attributes.ATTACK_SPEED, new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", getAttackSpeed(stack), AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

    @Override
    public void talk(PlayerEntity player) {
    }

}
