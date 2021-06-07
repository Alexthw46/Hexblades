package Alexthw.Hexblades.common.potions;

import Alexthw.Hexblades.Hexblades;
import Alexthw.Hexblades.common.items.tier1.Lightning_SSwordR1;
import Alexthw.Hexblades.util.HexUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeEffect;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EChargedEffect extends Effect implements IForgeEffect {

    public EChargedEffect() {
        super(EffectType.HARMFUL, HexUtils.thunderColor);
        MinecraftForge.EVENT_BUS.addListener(this::shock);
    }

    protected static final ResourceLocation EFFECT_TEXTURE = new ResourceLocation(Hexblades.MOD_ID, "textures/mob_effect/electro_charged.png");

    public void shock(LivingHurtEvent event) {
        if (event.getSource().getTrueSource() instanceof LivingEntity && event.getSource() != DamageSource.LIGHTNING_BOLT) {
            LivingEntity source = (LivingEntity) event.getSource().getTrueSource();
            if ((source.getItemStackFromSlot(EquipmentSlotType.MAINHAND).getItem() instanceof Lightning_SSwordR1) || event.getEntityLiving().isWet()) {
                if (event.getEntityLiving().isPotionActive(this)) {
                    event.setAmount(event.getAmount() + 2.0F);
                    event.getEntityLiving().removePotionEffect(this);
                }
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void renderInventoryEffect(EffectInstance effect, DisplayEffectsScreen<?> gui, MatrixStack mStack, int x, int y, float z) {
        Minecraft mc = Minecraft.getInstance();
        mc.getTextureManager().bindTexture(EFFECT_TEXTURE);
        gui.blit(mStack, x, y, 0, 0, 18, 18);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void renderHUDEffect(EffectInstance effect, AbstractGui gui, MatrixStack mStack, int x, int y, float z, float alpha) {
        Minecraft mc = Minecraft.getInstance();
        mc.getTextureManager().bindTexture(EFFECT_TEXTURE);
        gui.blit(mStack, x, y, 0, 0, 18, 18);
    }

}

