package Alexthw.Hexblades.spells;

import Alexthw.Hexblades.deity.HexDeities;
import Alexthw.Hexblades.registers.HexItem;
import elucent.eidolon.capability.ReputationProvider;
import elucent.eidolon.network.MagicBurstEffectPacket;
import elucent.eidolon.network.Networking;
import elucent.eidolon.spell.Sign;
import elucent.eidolon.spell.Signs;
import elucent.eidolon.spell.StaticSpell;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.List;

import static Alexthw.Hexblades.util.HexUtils.getVector;

public class HexTouchSpell extends StaticSpell {
    public HexTouchSpell(ResourceLocation name, Sign... signs) {
        super(name, signs);
    }

    @Override
    public boolean canCast(World world, BlockPos pos, PlayerEntity player) {
        if (!world.getCapability(ReputationProvider.CAPABILITY).isPresent()) return false;
        if (world.getCapability(ReputationProvider.CAPABILITY).resolve().get().getReputation(player, HexDeities.HEX_DEITY.getId()) < 4.0)
            return false;

        Vector3d v = getVector(world, player);
        List<ItemEntity> items = world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(v.x - 1.5, v.y - 1.5, v.z - 1.5, v.x + 1.5, v.y + 1.5, v.z + 1.5));
        if (items.size() != 1) return false;
        ItemStack stack = items.get(0).getItem();
        return canTouch(stack);
    }

    @Override
    public void cast(World world, BlockPos pos, PlayerEntity player) {

        Vector3d v = getVector(world, player);
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
        return (stack.getItem() == Items.NETHER_STAR || stack.getItem() == Items.LAPIS_LAZULI || stack.getItem() == Items.DIAMOND);
    }

    ItemStack touchResult(ItemStack stack) { // assumes canTouch is true

        if (stack.getItem() == Items.NETHER_STAR)
            return new ItemStack(HexItem.PATRON_SOUL.get());

        if (stack.getItem() == Items.LAPIS_LAZULI)
            return new ItemStack(HexItem.PATRON_SOUL2.get());

        if (stack.getItem() == Items.DIAMOND)
            return new ItemStack(HexItem.ELEMENTAL_CORE.get());

        return stack;
    }

}
