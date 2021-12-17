package alexthw.hexblades.common.entity;

import alexthw.hexblades.common.entity.ai.fe.FEMeleeGoal;
import alexthw.hexblades.common.entity.ai.fe.FireCannonAttackGoal;
import alexthw.hexblades.common.entity.ai.fe.FireSpinAttackGoal;
import alexthw.hexblades.registers.HexEntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zoglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.BossEvent;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerBossEvent;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.ParticleKeyFrameEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import javax.annotation.Nullable;

import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class FireElementalEntity extends BaseElementalEntity implements RangedAttackMob {
    private static final EntityDataAccessor<Integer> ANIMATIONSTATE = SynchedEntityData.defineId(FireElementalEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> FIRECHARGE = SynchedEntityData.defineId(FireElementalEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> LOADING = SynchedEntityData.defineId(FireElementalEntity.class, EntityDataSerializers.BOOLEAN);

    public FireElementalEntity(EntityType<FireElementalEntity> type, Level worldIn) {
        super(type, worldIn);
        bossEvent = (ServerBossEvent) (new ServerBossEvent(this.getDisplayName(), BossEvent.BossBarColor.RED, BossEvent.BossBarOverlay.PROGRESS)).setDarkenScreen(true);

        this.registerGoals();
        this.navigation.canFloat();
    }

    @Override
    public void checkDespawn() {
        if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.remove(RemovalReason.DISCARDED);
        } else {
            this.noActionTime = 0;
        }
    }

    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource p_184601_1_) {
        return SoundEvents.BLAZE_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.FIRE_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLAZE_DEATH;
    }

    public float getBrightness() {
        return 10.0F;
    }

    @Override
    public boolean causeFallDamage(float p_147187_, float p_147188_, @NotNull DamageSource p_147189_) {
        return false;
    }

    public int getExperienceReward(@NotNull Player player) {
        return 80;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.applyEntityAI();
        //target selectors
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Zoglin.class, true));

    }

    public static AttributeSupplier createAttributes() {
        return Monster.createMonsterAttributes().
                add(Attributes.MAX_HEALTH, 200.0D).
                add(Attributes.FOLLOW_RANGE, 35.0D).
                add(Attributes.MOVEMENT_SPEED, 0.3D).
                add(Attributes.ATTACK_DAMAGE, 5.0D).
                add(Attributes.ARMOR, 10.0D).
                add(Attributes.KNOCKBACK_RESISTANCE, 0.8D)
                .build();
    }

    public void aiStep() {

        FluidState below = this.level.getBlockState(this.getBlockPosBelowThatAffectsMyMovement()).getFluidState();
        Vec3 motion;
        if (!below.isEmpty()) {
            motion = this.getDeltaMovement();
            this.setOnGround(true);
            if (this.getY() + motion.y < (double) ((float) this.getBlockPosBelowThatAffectsMyMovement().getY() + below.getOwnHeight())) {
                this.setNoGravity(true);
                if (motion.y < 0.0D) {
                    this.setDeltaMovement(motion.multiply(1.0D, 0.0D, 1.0D));
                }

                this.setPos(this.getX(), (float) this.getBlockPosBelowThatAffectsMyMovement().getY() + below.getOwnHeight(), this.getZ());
            }
        } else {
            this.setNoGravity(false);
        }

        this.fallDistance = 0.0F;
        motion = this.getDeltaMovement();
        if (!this.onGround && motion.y < 0.0D) {
            this.setDeltaMovement(motion.multiply(1.0D, 0.6D, 1.0D));
        }

        super.aiStep();
    }

    protected void applyEntityAI() {
        //target - no attacks
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        //this.goalSelector.addGoal(8, new LookAtGoal(this, BlazeEntity.class, 8.0F));

        //attacks
        this.goalSelector.addGoal(2, new FireCannonAttackGoal(this, 1.0D, 40, 20.0F));
        this.goalSelector.addGoal(4, new FEMeleeGoal(this, 1.0D, false));
        this.goalSelector.addGoal(3, new FireSpinAttackGoal(this, 1.0D, true));
        //no target
        this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

    }

    public int getAnimationState() {
        return this.entityData.get(ANIMATIONSTATE);
    }

    public int getFireCharge() {
        return this.entityData.get(FIRECHARGE);
    }

    public boolean isCannonLoaded() {
        return this.entityData.get(LOADING);
    }

    public void setAnimationState(int anim) {
        this.entityData.set(ANIMATIONSTATE, anim);
    }

    public void addFireCharge(int charge) {
        this.entityData.set(FIRECHARGE, Math.max(0, getFireCharge() + charge));
    }

    public void loadCannon(boolean b) {
        this.entityData.set(LOADING, b);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIMATIONSTATE, 0);
        this.entityData.define(FIRECHARGE, 0);
        this.entityData.define(LOADING, false);

    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<FireElementalEntity> combat = new AnimationController<>(this, "attack_controller", 5, this::attackPredicate);
        data.addAnimationController(combat);
        data.addAnimationController(new AnimationController<>(this, "idle", 0, this::idleP));
        super.registerControllers(data);
    }

    private <T extends IAnimatable> PlayState idleP(AnimationEvent<T> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.hexblades.fe.idle.body"));
        return PlayState.CONTINUE;
    }

    private <T extends IAnimatable> PlayState attackPredicate(AnimationEvent<T> event) {
        AnimationController<?> controller = event.getController();
        switch (this.getAnimationState()) {
            case 0:
                controller.setAnimation(new AnimationBuilder().addAnimation("animation.hexblades.fe.idle.arms"));
                break;
            case 1:
                controller.setAnimation(new AnimationBuilder().addAnimation("animation.hexblades.fe.attacks.shoot2"));
                break;
            case 2:
                controller.setAnimation(new AnimationBuilder().addAnimation("animation.hexblades.fe.attacks.melee", false));
                break;
            case 3:
                controller.setAnimation(new AnimationBuilder().addAnimation("animation.hexblades.fe.attacks.spin"));
                break;
            default:
                return PlayState.STOP;
        }

        return PlayState.CONTINUE;
    }

    /**
     * Attack the specified entity using a ranged attack.
     */
    @Override
    public void performRangedAttack(@NotNull LivingEntity Target, float pDistanceFactor) {

        this.lookAt(Target, 360, 360);

        Vec3 vel = getEyePosition(0.0F).add(getLookAngle().scale(40.0D)).subtract(this.position()).scale(0.05D);
        Vec3 pos = new Vec3(getX() + Math.cos(Math.toRadians(yBodyRot + 90)), getY() + 2.3F, getZ() + Math.sin(Math.toRadians(yBodyRot + 90)));
        loadCannon(false);

        level.addFreshEntity((new MagmaProjectileEntity(HexEntityType.MAGMA_PROJECTILE.get(), level)).shoot(pos.x, pos.y, pos.z, vel.x * 0.9, vel.y, vel.z * 0.9, this.getUUID(), this));

    }

    @Override
    public boolean doHurtTarget(Entity target) {
        float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);

        boolean flag = target.hurt(DamageSource.mobAttack(this), f);
        if (flag) {
            if (target.invulnerableTime > 0) {
                target.invulnerableTime = 0;
            }
            target.hurt(new EntityDamageSource("lava", this).bypassArmor(), 2.0F + (float) this.level.getDifficulty().getId() / 2);

            this.setLastHurtMob(target);
        }

        return flag;
    }

}
