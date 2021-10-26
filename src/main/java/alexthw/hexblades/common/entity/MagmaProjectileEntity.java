package alexthw.hexblades.common.entity;

import elucent.eidolon.Registry;
import elucent.eidolon.entity.SpellProjectileEntity;
import elucent.eidolon.network.MagicBurstEffectPacket;
import elucent.eidolon.network.Networking;
import elucent.eidolon.particle.Particles;
import elucent.eidolon.util.ColorUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.UUID;

public class MagmaProjectileEntity extends SpellProjectileEntity {

    UUID casterID;
    FireElementalEntity FEE;

    public Entity shoot(double x, double y, double z, double vx, double vy, double vz, UUID caster, FireElementalEntity firenando) {
        casterID = caster;
        FEE = firenando;
        return super.shoot(x, y, z, vx, vy, vz, caster);
    }

    public MagmaProjectileEntity(EntityType<?> entityTypeIn, World worldIn) {
        super(entityTypeIn, worldIn);
    }

    @Override
    public void tick() {
        super.tick();
        Vector3d pos = this.position();

        for (int i = 0; i < 8; ++i) {
            double lerpX = MathHelper.lerp((float) i / 8.0F, this.xo, pos.x);
            double lerpY = MathHelper.lerp((float) i / 8.0F, this.yo, pos.y);
            double lerpZ = MathHelper.lerp((float) i / 8.0F, this.zo, pos.z);
            Particles.create(Registry.FLAME_PARTICLE).setAlpha(0.5F, 0.0F).setScale(0.375F, 0.0F).setColor(1.0F, 0.5F, 0.15F, 0.65F, 0.25F, 0.075F).setLifetime(3).spawn(this.level, lerpX, lerpY, lerpZ);
        }
    }

    protected void onImpact(RayTraceResult ray, Entity target) {
        target.hurt(DamageSource.indirectMagic(FEE, this.level.getPlayerByUUID(this.casterID)), 7.0F);
        this.onImpact(ray);
    }

    protected void onImpact(RayTraceResult ray) {
        this.removeAfterChangingDimensions();
        if (!this.level.isClientSide) {
            Vector3d pos = ray.getLocation();
            this.level.playSound(null, pos.x, pos.y, pos.z, Registry.SPLASH_SOULFIRE_EVENT.get(), SoundCategory.NEUTRAL, 0.6F, this.random.nextFloat() * 0.2F + 0.9F);
            Networking.sendToTracking(this.level, this.blockPosition(), new MagicBurstEffectPacket(pos.x, pos.y, pos.z, ColorUtil.packColor(255, 255, 229, 125), ColorUtil.packColor(255, 124, 57, 247)));
        }

    }

}
