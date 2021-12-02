package alexthw.hexblades.spells;

import alexthw.hexblades.deity.HexDeities;
import alexthw.hexblades.registers.HexItem;
import elucent.eidolon.capability.ReputationProvider;
import elucent.eidolon.network.MagicBurstEffectPacket;
import elucent.eidolon.network.Networking;
import elucent.eidolon.spell.Sign;
import elucent.eidolon.spell.Signs;
import elucent.eidolon.spell.StaticSpell;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.AABB;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

import java.util.List;

import static alexthw.hexblades.util.HexUtils.getVector;

public class HexTouchSpell extends StaticSpell {
    public HexTouchSpell(ResourceLocation name, Sign... signs) {
        super(name, signs);
    }

    @Override
    public boolean canCast(Level world, BlockPos pos, Player player) {
        if (!world.getCapability(ReputationProvider.CAPABILITY).isPresent()) return false;

        Vec3 v = getVector(world, player);
        List<ItemEntity> items = world.getEntitiesOfClass(ItemEntity.class, new AABB(v.x - 1.5, v.y - 1.5, v.z - 1.5, v.x + 1.5, v.y + 1.5, v.z + 1.5));
        if (items.size() != 1) return false;
        ItemStack stack = items.get(0).getItem();
        return canTouch(stack);
    }

    @Override
    public void cast(Level world, BlockPos pos, Player player) {

        Vec3 v = getVector(world, player);
        List<ItemEntity> items = world.getEntitiesOfClass(ItemEntity.class, new AABB(v.x - 1.5, v.y - 1.5, v.z - 1.5, v.x + 1.5, v.y + 1.5, v.z + 1.5));
        if (items.size() == 1) {
            if (!world.isClientSide) {
                ItemStack stack = items.get(0).getItem();
                if (canTouch(stack)) {
                    items.get(0).setItem(touchResult(stack));
                    Vec3 p = items.get(0).position();
                    items.get(0).setDefaultPickUpDelay();
                    Networking.sendToTracking(world, items.get(0).blockPosition(), new MagicBurstEffectPacket(p.x, p.y, p.z, Signs.SOUL_SIGN.getColor(), Signs.MIND_SIGN.getColor()));
                }
            } else {
                world.playSound(player, player.blockPosition(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.NEUTRAL, 1.0F, 0.6F + world.random.nextFloat() * 0.2F);
            }
        }
    }

    boolean canTouch(ItemStack stack) {
        Item stackItem = stack.getItem();
        return stackItem == Items.DIAMOND;
    }

    ItemStack touchResult(ItemStack stack) { // assumes canTouch is true

        if (stack.getItem() == Items.DIAMOND)
            return new ItemStack(HexItem.ELEMENTAL_CORE.get());

        return stack;
    }

}
