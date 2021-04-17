package Alexthw.Hexblades.spells;

import Alexthw.Hexblades.core.registers.HexItem;
import elucent.eidolon.Registry;
import elucent.eidolon.capability.ReputationProvider;
import elucent.eidolon.deity.Deities;
import elucent.eidolon.network.MagicBurstEffectPacket;
import elucent.eidolon.network.Networking;
import elucent.eidolon.spell.Sign;
import elucent.eidolon.spell.Signs;
import elucent.eidolon.spell.StaticSpell;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;

public class HexTouchSpell extends StaticSpell {
    public HexTouchSpell(ResourceLocation name, Sign... signs) {
        super(name, signs);
    }

    @Override
    public boolean canCast(World world, BlockPos pos, PlayerEntity player) {
        if (!world.getCapability(ReputationProvider.CAPABILITY).isPresent()) return false;
        if (world.getCapability(ReputationProvider.CAPABILITY).resolve().get().getReputation(player, Deities.DARK_DEITY.getId()) < 4.0)
            return false;

        RayTraceResult ray = world.rayTraceBlocks(new RayTraceContext(player.getEyePosition(0), player.getEyePosition(0).add(player.getLookVec().scale(4)), RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
        Vector3d v = ray.getType() == RayTraceResult.Type.BLOCK ? ray.getHitVec() : player.getEyePosition(0).add(player.getLookVec().scale(4));
        List<ItemEntity> items = world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(v.x - 1.5, v.y - 1.5, v.z - 1.5, v.x + 1.5, v.y + 1.5, v.z + 1.5));
        if (items.size() != 1) return false;
        ItemStack stack = items.get(0).getItem();
        return canTouch(stack);
    }

    @Override
    public void cast(World world, BlockPos pos, PlayerEntity player) {
        RayTraceResult ray = world.rayTraceBlocks(new RayTraceContext(player.getEyePosition(0), player.getEyePosition(0).add(player.getLookVec().scale(4)), RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, player));
        Vector3d v = ray.getType() == RayTraceResult.Type.BLOCK ? ray.getHitVec() : player.getEyePosition(0).add(player.getLookVec().scale(4));
        List<ItemEntity> items = world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(v.x - 1.5, v.y - 1.5, v.z - 1.5, v.x + 1.5, v.y + 1.5, v.z + 1.5));
        if (items.size() == 1) {
            if (!world.isRemote) {
                ItemStack stack = items.get(0).getItem();
                if (canTouch(stack)) {
                    items.get(0).setItem(touchResult(stack));
                    Vector3d p = items.get(0).getPositionVec();
                    items.get(0).setDefaultPickupDelay();
                    Networking.sendToTracking(world, items.get(0).getPosition(), new MagicBurstEffectPacket(p.x, p.y, p.z, Signs.SOUL_SIGN.getColor(), Signs.MIND_SIGN.getColor()));
                }
            } else {
                world.playSound(player, player.getPosition(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.NEUTRAL, 1.0F, 0.6F + world.rand.nextFloat() * 0.2F);
            }
        }
    }

    boolean canTouch(ItemStack stack) {
        return stack.getItem() == Registry.SILVER_INGOT.get();           // is silver
    }

    ItemStack touchResult(ItemStack stack) { // assumes canTouch is true
        if (canTouch(stack))
            return new ItemStack(HexItem.HEXIUM_INGOT.get());
        else return stack;
    }
}
