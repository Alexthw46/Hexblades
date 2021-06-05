package Alexthw.Hexblades.common.items.tier1;

import Alexthw.Hexblades.common.entity.FulgorProjectileEntity;
import Alexthw.Hexblades.common.items.HexSwordItem;
import Alexthw.Hexblades.registers.HexEntityType;
import Alexthw.Hexblades.registers.HexItem;
import elucent.eidolon.Registry;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Lightning_SSwordL1 extends HexSwordItem {

    int projectileCost = 3000;

    public Lightning_SSwordL1(Item.Properties props) {
        super(1, -1.5F, props);
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
        if (getAwakened(weapon)) {
            double devotion = getDevotion(player);
            rechargeTick = max(1, (int) devotion / 2);
            setAttackPower(weapon, devotion);
            setAttackSpeed(weapon, devotion);
        }

    }

    public int getUseDuration(ItemStack stack) {
        return 72000 - (int) (10 * getAttackSpeed(stack));
    }

    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World world, LivingEntity entity, int timeleft) {
        int i = this.getUseDuration(stack) - timeleft;
        if (!world.isRemote() && i >= 10) {
            Vector3d pos = entity.getPositionVec().add(entity.getLookVec().scale(0.5D)).add(-0.5D * Math.sin(Math.toRadians(225.0F - entity.rotationYawHead)), (double) (entity.getHeight() * 0.75F), -0.5D * Math.cos(Math.toRadians((double) (225.0F - entity.rotationYawHead))));
            Vector3d vel = entity.getEyePosition(0.0F).add(entity.getLookVec().scale(40.0D)).subtract(pos).scale(0.05D);
            world.addEntity((new FulgorProjectileEntity(HexEntityType.FULGOR_PROJECTILE.get(), world)).shoot(pos.x, pos.y, pos.z, vel.x, vel.y, vel.z, entity.getUniqueID()));
            world.playSound(null, pos.x, pos.y, pos.z, Registry.CAST_SOULFIRE_EVENT.get(), SoundCategory.NEUTRAL, 0.75F, random.nextFloat() * 0.2F + 0.9F);
            stack.setDamage(min(stack.getDamage() + projectileCost, stack.getMaxDamage() - 1));
        }
        setAwakenedState(stack, false);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("tooltip.HexSwordItem." + "thunder_knives"));
    }
}
