package alexthw.hexblades.common.items.tier1;

import alexthw.hexblades.common.items.HexSwordItem;
import alexthw.hexblades.util.HexUtils;
import elucent.eidolon.Registry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.text.*;
import net.minecraft.world.World;

import java.util.List;

import static alexthw.hexblades.ConfigHandler.COMMON;

public class IceKatana1 extends HexSwordItem {

    public IceKatana1(Properties props) {
        super(COMMON.KatanaBD1.get(), -2.5F, props);
        tooltipText = new TranslationTextComponent("tooltip.hexblades.ice_katana");
        textColor = TextFormatting.AQUA;
    }

    public IceKatana1(int attackDamage, float attackSpeed, Properties props) {
        super(attackDamage, attackSpeed, props);
        textColor = TextFormatting.AQUA;
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker, boolean awakened) {
        if (awakened) {
            target.hurt(new EntityDamageSource(Registry.FROST_DAMAGE.getMsgId(), attacker).bypassArmor(), getElementalPower(stack));
            target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 200, 0));
        }
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        boolean awakening = setAwakenedState(weapon, !getAwakened(weapon));
        if (awakening) updateElementalPower(weapon, COMMON.KatanaED.get(), devotion);

        setAttackPower(weapon, awakening, devotion / COMMON.KatanaDS1.get());
        setAttackSpeed(weapon, awakening, devotion / COMMON.KatanaAS1.get());
    }

    @Override
    public void talk(PlayerEntity player) {
        player.sendMessage(new TranslationTextComponent(this.getDescriptionId() + ".dialogue." + player.level.getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true).withColor(Color.fromRgb(HexUtils.iceColor))), player.getUUID());
    }

    @Override
    protected void addShiftTooltip(ItemStack stack, List<ITextComponent> tooltip) {
        tooltip.add(new StringTextComponent("Armor piercing damage: " + getElementalPower(stack)));
        tooltip.add(new StringTextComponent("Slows enemies"));
    }
}
