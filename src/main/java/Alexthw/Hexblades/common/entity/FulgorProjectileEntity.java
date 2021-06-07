package Alexthw.Hexblades.common.entity;


import Alexthw.Hexblades.registers.HexRegistry;
import elucent.eidolon.Registry;
import elucent.eidolon.entity.SpellProjectileEntity;
import elucent.eidolon.network.MagicBurstEffectPacket;
import elucent.eidolon.network.Networking;
import elucent.eidolon.particle.Particles;
import elucent.eidolon.util.ColorUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.util.IndirectEntityDamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.UUID;

import static Alexthw.Hexblades.registers.HexParticles.FULGOR_PARTICLE;

public class FulgorProjectileEntity extends SpellProjectileEntity {

    UUID casterId = null;

    public FulgorProjectileEntity(EntityType<? extends FulgorProjectileEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public Entity shoot(double x, double y, double z, double vx, double vy, double vz, UUID caster) {
        this.setPosition(x, y, z);
        this.setMotion(vx, vy, vz);
        this.casterId = caster;
        this.velocityChanged = true;
        return this;
    }

    public void tick() {
        super.tick();
        Vector3d motion = this.getMotion();
        Vector3d pos = this.getPositionVec();
        Vector3d norm = motion.normalize().scale(0.02500000037252903D);

        for (int i = 0; i < 8; ++i) {
            double lerpX = MathHelper.lerp((double) ((float) i / 8.0F), this.prevPosX, pos.x);
            double lerpY = MathHelper.lerp((double) ((float) i / 8.0F), this.prevPosY, pos.y);
            double lerpZ = MathHelper.lerp((double) ((float) i / 8.0F), this.prevPosZ, pos.z);
            Particles.create(FULGOR_PARTICLE).addVelocity(-norm.x, -norm.y, -norm.z).setAlpha(0.0825F, 0.0F).setScale(0.625F, 0.0F).setColor(1.0F, 0.875F, 0.578F, 0.75F, 0.375F, 0.5F).setLifetime(5).spawn(this.world, lerpX, lerpY, lerpZ);
            Particles.create(Registry.SPARKLE_PARTICLE).addVelocity(-norm.x, -norm.y, -norm.z).setAlpha(0.521F, 0.0F).setScale(0.25F, 0.125F).setColor(1.0F, 0.875F, 0.578F, 0.75F, 0.375F, 0.5F).setLifetime(20).spawn(this.world, lerpX, lerpY, lerpZ);
        }

    }

    protected void onImpact(RayTraceResult ray, Entity target) {
        target.attackEntityFrom(new IndirectEntityDamageSource(DamageSource.LIGHTNING_BOLT.getDamageType(), this, this.world.getPlayerByUuid(this.casterId)), 3.0F);
        if (target instanceof LivingEntity) {
            ((LivingEntity) target).addPotionEffect(new EffectInstance(HexRegistry.CHARGED_EFFECT.get(), 100, 0));
        }

        this.onImpact(ray);
    }

    protected void onImpact(RayTraceResult ray) {
        this.setDead();
        if (!this.world.isRemote) {
            Vector3d pos = ray.getHitVec();
            this.world.playSound((PlayerEntity) null, pos.x, pos.y, pos.z, (SoundEvent) Registry.SPLASH_SOULFIRE_EVENT.get(), SoundCategory.NEUTRAL, 0.5F, this.rand.nextFloat() * 0.2F + 0.9F);
            Networking.sendToTracking(this.world, this.getPosition(), new MagicBurstEffectPacket(pos.x, pos.y, pos.z, ColorUtil.packColor(255, 255, 255, 72), ColorUtil.packColor(255, 255, 235, 102)));
        }

    }
}
