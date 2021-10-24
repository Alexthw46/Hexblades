package alexthw.hexblades.common.entity;

import alexthw.hexblades.common.entity.ai.fe.FEMeleeGoal;
import alexthw.hexblades.common.entity.ai.fe.FireCannonAttackGoal;
import alexthw.hexblades.common.entity.ai.fe.FireSpinAttackGoal;
import alexthw.hexblades.registers.HexEntityType;
import elucent.eidolon.Registry;
import elucent.eidolon.particle.Particles;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.BossInfo;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerBossInfo;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.ParticleKeyFrameEvent;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class FireElementalEntity extends BaseElementalEntity implements IRangedAttackMob, IChargeableMob {
    private static final DataParameter<Integer> ANIMATIONSTATE = EntityDataManager.defineId(FireElementalEntity.class, DataSerializers.INT);
    private static final DataParameter<Integer> FIRECHARGE = EntityDataManager.defineId(FireElementalEntity.class, DataSerializers.INT);
    private static final DataParameter<Boolean> LOADING = EntityDataManager.defineId(FireElementalEntity.class, DataSerializers.BOOLEAN);

    public FireElementalEntity(EntityType<FireElementalEntity> type, World worldIn) {
        super(type, worldIn);
        bossEvent = (ServerBossInfo) (new ServerBossInfo(this.getDisplayName(), BossInfo.Color.RED, BossInfo.Overlay.PROGRESS)).setDarkenScreen(true);

        this.registerGoals();
        this.navigation.canFloat();
    }

    @Override
    public void checkDespawn() {
        if (this.level.getDifficulty() == Difficulty.PEACEFUL && this.shouldDespawnInPeaceful()) {
            this.remove();
        } else {
            this.noActionTime = 0;
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
        return SoundEvents.BLAZE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLAZE_DEATH;
    }

    public float getBrightness() {
        return 10.0F;
    }

    public boolean causeFallDamage(float p_225503_1_, float p_225503_2_) {
        return false;
    }

    public int getExperienceReward(PlayerEntity player) {
        return 80;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.applyEntityAI();
        //target selectors
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap createAttributes() {
        return MonsterEntity.createMonsterAttributes().
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
        Vector3d motion;
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
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        //attacks
        this.goalSelector.addGoal(2, new FireCannonAttackGoal(this, 1.0D, 50, 20.0F));
        this.goalSelector.addGoal(4, new FEMeleeGoal(this, 1.0D, false));
        this.goalSelector.addGoal(3, new FireSpinAttackGoal(this, 1.0D, true));
        //no target
        this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));

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
        combat.registerParticleListener(this::particleListener);
        super.registerControllers(data);
    }

    private void particleListener(ParticleKeyFrameEvent<FireElementalEntity> particleKeyFrameEvent) {
        if (isCannonLoaded()) {
            Particles.create(Registry.FLAME_PARTICLE).setAlpha(0.7F, 0.0F).setScale(0.25F, 0.0F).setLifetime(10).randomOffset(0.3D, 0.3D).randomVelocity(0, -0.15F).setColor(1.5F, 0.875F, 0.5F, 0.5F, 0.25F, 1.0F).spawn(this.getCommandSenderWorld(), this.getX(), this.getY() + 5.75F, this.getZ());
        }
    }

    private <T extends IAnimatable> PlayState idleP(AnimationEvent<T> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.hexblades.fe.idle.body"));
        return PlayState.CONTINUE;
    }

    private <T extends IAnimatable> PlayState attackPredicate(AnimationEvent<T> event) {
        AnimationController<FireElementalEntity> controller = event.getController();
        switch (this.getAnimationState()) {
            case 0:
                controller.setAnimation(new AnimationBuilder().addAnimation("animation.hexblades.fe.idle.arms"));
                break;
            case 1:
                controller.setAnimation(new AnimationBuilder().addAnimation("animation.hexblades.fe.attacks.shoot"));
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

    @Override
    public boolean isPowered() {
        return this.getHealth() <= this.getMaxHealth() / 2.0F;
    }

    /**
     * Attack the specified entity using a ranged attack.
     */
    @Override
    public void performRangedAttack(LivingEntity Target, float pDistanceFactor) {

        Vector3d pos = this.position().add(this.getLookAngle().scale(0.7)).add(0.5D * Math.sin(Math.toRadians(225.0F - this.yHeadRot)), this.getBbHeight() * 2 / 3, 0.5D * Math.cos(Math.toRadians(225.0F - this.yHeadRot)));
        double vx = Target.getX() - pos.x;
        double vy = Target.getY() - pos.y;
        double vz = Target.getZ() - pos.z;
        loadCannon(false);

        level.addFreshEntity((new MagmaProjectileEntity(HexEntityType.MAGMA_PROJECTILE.get(), level)).shoot(pos.x, pos.y + 0.5, pos.z, vx * 0.9, vy, vz * 0.9, this.getUUID(), this));

    }

    @Override
    public boolean doHurtTarget(Entity target) {
        float f = (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE);

        boolean flag = target.hurt(DamageSource.mobAttack(this), f);
        if (flag) {
            if (target.invulnerableTime > 0) {
                target.invulnerableTime = 0;
            }
            target.hurt(new EntityDamageSource("lava", this).bypassArmor(), 2.0F);

            this.setLastHurtMob(target);
        }

        return flag;
    }

}
