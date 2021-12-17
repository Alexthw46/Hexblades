package alexthw.hexblades.common.entity;


import alexthw.hexblades.registers.HexRegistry;
import elucent.eidolon.Registry;
import elucent.eidolon.entity.SpellProjectileEntity;
import elucent.eidolon.network.MagicBurstEffectPacket;
import elucent.eidolon.network.Networking;
import elucent.eidolon.particle.Particles;
import elucent.eidolon.util.ColorUtil;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

import static alexthw.hexblades.registers.HexParticles.FULGOR_PARTICLE;

public class FulgorProjectileEntity extends SpellProjectileEntity {

    UUID casterId = null;
    float damage = 0;

    public FulgorProjectileEntity(EntityType<? extends FulgorProjectileEntity> type, Level worldIn) {
        super(type, worldIn);
    }

    public FulgorProjectileEntity(EntityType<? extends FulgorProjectileEntity> type, Level worldIn, float damage) {
        super(type, worldIn);
        this.damage = damage;
    }

    @Override
    public Entity shoot(double x, double y, double z, double vx, double vy, double vz, UUID caster) {
        this.setPos(x, y, z);
        this.setDeltaMovement(vx, vy, vz);
        this.casterId = caster;
        this.hurtMarked = true;
        return this;
    }

    public void tick() {
        super.tick();
        Vec3 motion = this.getDeltaMovement();
        Vec3 pos = this.position();
        Vec3 norm = motion.normalize().scale(0.02500000037252903D);

        double j = 8.0F;
        for (int i = 0; i < j; ++i) {
            double lerpX = Mth.lerp((float) i / j, this.xo, pos.x);
            double lerpY = Mth.lerp((float) i / j, this.yo, pos.y);
            double lerpZ = Mth.lerp((float) i / j, this.zo, pos.z);
            Particles.create(FULGOR_PARTICLE).addVelocity(-norm.x, -norm.y, -norm.z).setAlpha(0.0825F, 0.0F).setScale(0.5F, 0.0F).setColor(1.0F, 0.875F, 0.25F, 0.75F, 0.375F, 0.25F).setLifetime(5).spawn(this.level, lerpX, lerpY, lerpZ);
            Particles.create(Registry.SPARKLE_PARTICLE).addVelocity(-norm.x, -norm.y, -norm.z).setAlpha(0.521F, 0.0F).setScale(0.15F).setColor(1.0F, 0.875F, 0.25F, 0.75F, 0.375F, 0.25F).setLifetime(7).spawn(this.level, lerpX, lerpY, lerpZ);
        }

    }

    protected void onImpact(HitResult ray, Entity target) {
        target.hurt(new IndirectEntityDamageSource(DamageSource.LIGHTNING_BOLT.getMsgId(), this, this.level.getPlayerByUUID(this.casterId)).bypassArmor(), Math.max(damage, 3.0F));
        if (target instanceof LivingEntity target1) {
            target1.addEffect(new MobEffectInstance(HexRegistry.CHARGED_EFFECT.get(), 100, 0));
        }

        this.onImpact(ray);
    }

    protected void onImpact(HitResult ray) {
        this.removeAfterChangingDimensions();
        if (!this.level.isClientSide) {
            Vec3 pos = ray.getLocation();
            this.level.playSound(null, pos.x, pos.y, pos.z, Registry.SPLASH_SOULFIRE_EVENT.get(), SoundSource.NEUTRAL, 0.5F, this.random.nextFloat() * 0.2F + 0.9F);
            Networking.sendToTracking(this.level, this.blockPosition(), new MagicBurstEffectPacket(pos.x, pos.y, pos.z, ColorUtil.packColor(255, 255, 255, 72), ColorUtil.packColor(255, 255, 235, 102)));
        }

    }
}
