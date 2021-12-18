package alexthw.hexblades.common.potions;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.common.items.hexblades.LightningSSword;
import alexthw.hexblades.common.items.hexblades.ThunderSSword;
import alexthw.hexblades.util.HexUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeMobEffect;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EChargedEffect extends MobEffect implements IForgeMobEffect {

    public EChargedEffect() {
        super(MobEffectCategory.HARMFUL, HexUtils.thunderColor);
        MinecraftForge.EVENT_BUS.addListener(this::shock);
    }

    protected static final ResourceLocation EFFECT_TEXTURE = new ResourceLocation(Hexblades.MODID, "textures/mob_effect/electro_charged.png");

    public void shock(LivingHurtEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity source && event.getSource() != DamageSource.LIGHTNING_BOLT) {
            if ((source.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof ThunderSSword) || event.getEntityLiving().isInWaterOrRain()) {
                if (event.getEntityLiving().hasEffect(this)) {
                    event.setAmount(event.getAmount() + 2.0F);
                    event.getEntityLiving().removeEffect(this);
                }
            }
        }
    }

}

