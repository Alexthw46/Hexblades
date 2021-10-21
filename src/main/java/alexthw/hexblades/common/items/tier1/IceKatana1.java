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
import net.minecraft.util.text.Color;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import static alexthw.hexblades.ConfigHandler.COMMON;

public class IceKatana1 extends HexSwordItem {

    public IceKatana1(Properties props) {
        super(5, -2.5F, props);
        tooltipText = new TranslationTextComponent("tooltip.HexSwordItem.ice_katana");
    }

    public IceKatana1(int attackDamage, float attackSpeed, Properties props) {
        super(attackDamage, attackSpeed, props);
    }

    @Override
    public void applyHexEffects(ItemStack stack, LivingEntity target, PlayerEntity attacker) {
        target.hurt(new EntityDamageSource(Registry.FROST_DAMAGE.getMsgId(), attacker).bypassArmor(), (float) (getDevotion(attacker) / COMMON.KatanaED.get()));
        target.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 200, 0));
    }

    @Override
    public void recalculatePowers(ItemStack weapon, World world, PlayerEntity player) {
        double devotion = getDevotion(player);

        setAwakenedState(weapon, !getAwakened(weapon));

        setAttackPower(weapon, devotion / COMMON.KatanaDS1.get());
        setAttackSpeed(weapon, devotion / COMMON.KatanaAS1.get());
    }

    @Override
    public void talk(PlayerEntity player) {
        player.sendMessage(new TranslationTextComponent(this.getDescriptionId() + ".dialogue." + player.level.getRandom().nextInt(dialogueLines)).setStyle(Style.EMPTY.withItalic(true).withColor(Color.fromRgb(HexUtils.iceColor))), player.getUUID());
    }

}
