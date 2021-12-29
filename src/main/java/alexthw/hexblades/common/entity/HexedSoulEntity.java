package alexthw.hexblades.common.entity;

import elucent.eidolon.Registry;
import elucent.eidolon.particle.Particles;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class HexedSoulEntity extends Entity{

    private static final int LIFETIME = 2000;
    private static final int ENTITY_SCAN_PERIOD = 20;
    private Player followingPlayer;
    private int age;

    public HexedSoulEntity(EntityType<?> pEntityType, Level pLevel) {
        super(pEntityType,pLevel);
    }

    public HexedSoulEntity(EntityType<?> pEntityType, Level pLevel, double x, double y, double z) {
        this(pEntityType, pLevel);
        this.setPos(x,y,z);
        this.setYRot((float)(this.random.nextDouble() * 360.0D));
        this.setDeltaMovement((this.random.nextDouble() * (double)0.2F - (double)0.1F) * 2.0D, this.random.nextDouble() * 0.2D * 2.0D, (this.random.nextDouble() * (double)0.2F - (double)0.1F) * 2.0D);
    }

    protected Entity.@NotNull MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
    }

    /**
     * Returns true if it's possible to attack this entity with an item.
     */
    public boolean isAttackable() {
        return false;
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void playerTouch(@NotNull Player pEntity) {
        if (!this.level.isClientSide) {
            this.discard();
        }
    }

    private void scanForEntities() {
        if (this.followingPlayer == null || this.followingPlayer.distanceToSqr(this) > 64.0D) {
            this.followingPlayer = this.level.getNearestPlayer(this, 8.0D);
        }
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putShort("Age", (short)this.age);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag pCompound) {
        this.age = pCompound.getShort("Age");
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return null;
    }

    public void tick() {
        super.tick();
        this.xo = this.getX();
        this.yo = this.getY();
        this.zo = this.getZ();

        if (level.isClientSide()){
            Particles.create(Registry.FLAME_PARTICLE).setAlpha(0.5F, 0.0F).setScale(0.375F, 0.0F).setColor(1.0F, 0.5F, 0.15F, 0.65F, 0.25F, 0.075F).setLifetime(3).spawn(this.level, xo,yo,zo);
            return;
        }

        if (this.level.getFluidState(this.blockPosition()).is(FluidTags.LAVA)) {
            this.setDeltaMovement((this.random.nextFloat() - this.random.nextFloat()) * 0.2F, 0.2F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F);
        }

        if (!this.level.noCollision(this.getBoundingBox())) {
            this.moveTowardsClosestSpace(this.getX(), (this.getBoundingBox().minY + this.getBoundingBox().maxY) / 2.0D, this.getZ());
        }

        if (this.tickCount % ENTITY_SCAN_PERIOD == 1) {
            this.scanForEntities();
        }

        if (this.followingPlayer != null && (this.followingPlayer.isSpectator() || this.followingPlayer.isDeadOrDying())) {
            this.followingPlayer = null;
        }

        if (this.followingPlayer != null) {
            Vec3 vec3 = new Vec3(this.followingPlayer.getX() - this.getX(), this.followingPlayer.getY() + (double)this.followingPlayer.getEyeHeight() / 2.0D - this.getY(), this.followingPlayer.getZ() - this.getZ());
            double d0 = vec3.lengthSqr();
            if (d0 < 64.0D) {
                double d1 = 1.0D - Math.sqrt(d0) / 8.0D;
                this.setDeltaMovement(this.getDeltaMovement().add(vec3.normalize().scale(d1 * d1 * 0.1D)));
            }
        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        float f = 0.98F;
        if (this.onGround) {
            BlockPos pos =new BlockPos(this.getX(), this.getY() - 1.0D, this.getZ());
            f = this.level.getBlockState(pos).getFriction(this.level, pos, this) * 0.98F;
        }

        this.setDeltaMovement(this.getDeltaMovement().multiply(f, 0.98D, f));
        if (this.onGround) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, -0.9D, 1.0D));
        }

        ++this.age;
        if (this.age >= LIFETIME) {
            this.discard();
        }

    }
}
