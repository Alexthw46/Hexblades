package alexthw.hexblades.common.entity;

import elucent.eidolon.Registry;
import elucent.eidolon.entity.SpellProjectileEntity;
import elucent.eidolon.network.MagicBurstEffectPacket;
import elucent.eidolon.network.Networking;
import elucent.eidolon.particle.Particles;
import elucent.eidolon.util.ColorUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class MagmaProjectileEntity extends SpellProjectileEntity {

    UUID casterID;
    FireElementalEntity FEE;

    public Entity shoot(double x, double y, double z, double vx, double vy, double vz, UUID caster, FireElementalEntity firenando) {
        casterID = caster;
        FEE = firenando;
        return super.shoot(x, y, z, vx, vy, vz, caster);
    }

    public MagmaProjectileEntity(EntityType<?> entityTypeIn, Level worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 pos = this.position();

        for (int i = 0; i < 8; ++i) {
            double lerpX = Mth.lerp((float) i / 8.0F, this.xo, pos.x);
            double lerpY = Mth.lerp((float) i / 8.0F, this.yo, pos.y);
            double lerpZ = Mth.lerp((float) i / 8.0F, this.zo, pos.z);
            Particles.create(Registry.FLAME_PARTICLE).setAlpha(0.5F, 0.0F).setScale(0.375F, 0.0F).setColor(1.0F, 0.5F, 0.15F, 0.65F, 0.25F, 0.075F).setLifetime(3).spawn(this.level, lerpX, lerpY, lerpZ);
        }
    }

    protected void onImpact(HitResult ray, Entity target) {
        target.hurt(DamageSource.indirectMagic(FEE, this.level.getPlayerByUUID(this.casterID)), 7.0F);
        this.onImpact(ray);
    }

    protected void onImpact(HitResult ray) {
        this.removeAfterChangingDimensions();
        if (!this.level.isClientSide) {
            Vec3 pos = ray.getLocation();
            this.level.playSound(null, pos.x, pos.y, pos.z, Registry.SPLASH_SOULFIRE_EVENT.get(), SoundSource.NEUTRAL, 0.6F, this.random.nextFloat() * 0.2F + 0.9F);
            Networking.sendToTracking(this.level, this.blockPosition(), new MagicBurstEffectPacket(pos.x, pos.y, pos.z, ColorUtil.packColor(255, 255, 229, 125), ColorUtil.packColor(255, 124, 57, 247)));
        }

    }

}
