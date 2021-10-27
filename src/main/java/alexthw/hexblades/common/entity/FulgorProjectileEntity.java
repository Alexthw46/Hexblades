package alexthw.hexblades.common.entity;


import alexthw.hexblades.registers.HexRegistry;
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

import static alexthw.hexblades.registers.HexParticles.FULGOR_PARTICLE;

public class FulgorProjectileEntity extends SpellProjectileEntity {

    UUID casterId = null;

    public FulgorProjectileEntity(EntityType<? extends FulgorProjectileEntity> type, World worldIn) {
        super(type, worldIn);
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
        Vector3d motion = this.getDeltaMovement();
        Vector3d pos = this.position();
        Vector3d norm = motion.normalize().scale(0.02500000037252903D);

        for (int i = 0; i < 8; ++i) {
            double lerpX = MathHelper.lerp((double) ((float) i / 8.0F), this.xo, pos.x);
            double lerpY = MathHelper.lerp((double) ((float) i / 8.0F), this.yo, pos.y);
            double lerpZ = MathHelper.lerp((double) ((float) i / 8.0F), this.zo, pos.z);
            Particles.create(FULGOR_PARTICLE).addVelocity(-norm.x, -norm.y, -norm.z).setAlpha(0.0825F, 0.0F).setScale(0.5F, 0.0F).setColor(1.0F, 0.875F, 0.25F, 0.75F, 0.375F, 0.25F).setLifetime(5).spawn(this.level, lerpX, lerpY, lerpZ);
            Particles.create(Registry.SPARKLE_PARTICLE).addVelocity(-norm.x, -norm.y, -norm.z).setAlpha(0.521F, 0.0F).setScale(0.15F).setColor(1.0F, 0.875F, 0.25F, 0.75F, 0.375F, 0.25F).setLifetime(7).spawn(this.level, lerpX, lerpY, lerpZ);
        }

    }

    protected void onImpact(RayTraceResult ray, Entity target) {
        target.hurt(new IndirectEntityDamageSource(DamageSource.LIGHTNING_BOLT.getMsgId(), this, this.level.getPlayerByUUID(this.casterId)).bypassArmor(), 3.0F);
        if (target instanceof LivingEntity) {
            ((LivingEntity) target).addEffect(new EffectInstance(HexRegistry.CHARGED_EFFECT.get(), 100, 0));
        }

        this.onImpact(ray);
    }

    protected void onImpact(RayTraceResult ray) {
        this.removeAfterChangingDimensions();
        if (!this.level.isClientSide) {
            Vector3d pos = ray.getLocation();
            this.level.playSound((PlayerEntity) null, pos.x, pos.y, pos.z, (SoundEvent) Registry.SPLASH_SOULFIRE_EVENT.get(), SoundCategory.NEUTRAL, 0.5F, this.random.nextFloat() * 0.2F + 0.9F);
            Networking.sendToTracking(this.level, this.blockPosition(), new MagicBurstEffectPacket(pos.x, pos.y, pos.z, ColorUtil.packColor(255, 255, 255, 72), ColorUtil.packColor(255, 255, 235, 102)));
        }

    }
}
