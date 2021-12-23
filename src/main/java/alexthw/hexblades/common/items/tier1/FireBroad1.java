package alexthw.hexblades.common.items.tier1;

import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.util.HexUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.*;
import net.minecraft.world.World;

import java.util.List;

import static alexthw.hexblades.ConfigHandler.COMMON;

public class FireBroad1 extends HexSwordItem {


    public FireBroad1(Properties props) {
        super(COMMON.SwordBD1.get(), -2.7F, props);
        tooltipText = new TranslationTextComponent("tooltip.hexblades.flame_sword");
        textColor = TextFormatting.RED;
    }

    public FireBroad1(int attackDamage, float attackSpeed, Properties props) {
        super(attackDamage, attackSpeed, props);
        textColor = TextFormatting.RED;
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker, boolean awakened) {
        if (awakened) {
            target.hurt(new EntityDamageSource("lava", attacker).bypassArmor(), getElementalPower(stack));
            target.setSecondsOnFire(4);
        }
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        boolean awakening = setAwakenedState(weapon, !getAwakened(weapon));
        if (awakening) updateElementalPower(weapon, COMMON.SwordED1.get(), devotion);
        setAttackPower(weapon, awakening, devotion / COMMON.SwordDS1.get());
    }

    @Override
    public void talk(PlayerEntity player) {
        player.sendMessage(new TranslationTextComponent(this.getDescriptionId() + ".dialogue." + player.level.getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true).withColor(Color.fromRgb(HexUtils.fireColor))), player.getUUID());
    }

    @Override
    protected void addShiftTooltip(ItemStack stack, List<ITextComponent> tooltip) {
        tooltip.add(new StringTextComponent("Armor piercing damage: " + getElementalPower(stack)));
        tooltip.add(new StringTextComponent("Sets enemies on fire for 4 seconds"));
    }
}
