package alexthw.hexblades.common.potions;

import alexthw.hexblades.Hexblades;
import alexthw.hexblades.common.items.tier1.Lightning_SSwordR1;
import alexthw.hexblades.util.HexUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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
        if (event.getSource().getEntity() instanceof LivingEntity && event.getSource() != DamageSource.LIGHTNING_BOLT) {
            LivingEntity source = (LivingEntity) event.getSource().getEntity();
            if ((source.getItemBySlot(EquipmentSlot.MAINHAND).getItem() instanceof Lightning_SSwordR1) || event.getEntityLiving().isInWaterOrRain()) {
                if (event.getEntityLiving().hasEffect(this)) {
                    event.setAmount(event.getAmount() + 2.0F);
                    event.getEntityLiving().removeEffect(this);
                }
            }
        }
    }


    @OnlyIn(Dist.CLIENT)
    public void renderInventoryEffect(MobEffectInstance effect, EffectRenderingInventoryScreen<?> gui, PoseStack mStack, int x, int y, float z) {
        Minecraft mc = Minecraft.getInstance();
        //mc.getTextureManager().bind(EFFECT_TEXTURE);
        gui.blit(mStack, x, y, 0, 0, 18, 18);
    }

    @OnlyIn(Dist.CLIENT)
    public void renderHUDEffect(MobEffectInstance effect, GuiComponent gui, PoseStack mStack, int x, int y, float z, float alpha) {
        Minecraft mc = Minecraft.getInstance();
        //mc.getTextureManager().bind(EFFECT_TEXTURE);
        gui.blit(mStack, x, y, 0, 0, 18, 18);
    }

}

