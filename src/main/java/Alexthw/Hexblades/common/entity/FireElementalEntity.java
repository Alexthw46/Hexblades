package Alexthw.Hexblades.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.monster.PhantomEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.EnumSet;

public class FireElementalEntity extends BaseElementalEntity {
    private boolean isLit;

    public FireElementalEntity(EntityType<FireElementalEntity> type, World worldIn) {
        super(type, worldIn);
        this.registerGoals();
    }

    public float getBrightness() {
        return 1.5F;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.applyEntityAI();
    }

    public static AttributeModifierMap createAttributes() {
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MAX_HEALTH, 100.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2800000011920929D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 5.0D).createMutableAttribute(Attributes.ARMOR, 10.0D).create();
    }

    @Override
    protected void applyEntityAI() {
        this.goalSelector.addGoal(3, new FireballAttackGoal(this));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(6, new MoveTowardsTargetGoal(this, 1.0D, 6));
        this.goalSelector.addGoal(7, new RandomWalkingGoal(this, 1.0D));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, SpiderEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PhantomEntity.class, true));

    }

    public int getExperiencePoints(PlayerEntity player) {
        return 80;
    }

    private void setOnFire(boolean b) {
        this.isLit = b;
    }

    static class FireballAttackGoal extends Goal {
        private final FireElementalEntity Firenando;
        private int attackStep;
        private int attackTime;
        private int firedRecentlyTimer;

        public FireballAttackGoal(FireElementalEntity fireElemental) {
            this.Firenando = fireElemental;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        /**
         * Returns whether execution should begin. You can also read and cache any state necessary for execution in this
         * method as well.
         */
        public boolean shouldExecute() {
            LivingEntity livingentity = this.Firenando.getAttackTarget();
            return livingentity != null && livingentity.isAlive() && this.Firenando.canAttack(livingentity) && (this.Firenando.getDistanceSq(livingentity) > 8.0F);
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.attackStep = 0;
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        public void resetTask() {
            this.Firenando.setOnFire(false);
            this.firedRecentlyTimer = 0;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        public void tick() {
            --this.attackTime;
            LivingEntity livingentity = this.Firenando.getAttackTarget();
            if (livingentity != null) {
                boolean flag = this.Firenando.getEntitySenses().canSee(livingentity);
                if (flag) {
                    this.firedRecentlyTimer = 0;
                } else {
                    ++this.firedRecentlyTimer;
                }

                double d0 = this.Firenando.getDistanceSq(livingentity);
                if (d0 < 4.0D) {
                    if (!flag) {
                        return;
                    }

                    if (this.attackTime <= 0) {
                        this.attackTime = 20;
                        this.Firenando.attackEntityAsMob(livingentity);
                    }

                    this.Firenando.getMoveHelper().setMoveTo(livingentity.getPosX(), livingentity.getPosY(), livingentity.getPosZ(), 1.0D);
                } else if (d0 < this.getFollowDistance() * this.getFollowDistance() && flag) {
                    double d1 = livingentity.getPosX() - this.Firenando.getPosX();
                    double d2 = livingentity.getPosYHeight(0.5D) - this.Firenando.getPosYHeight(0.5D);
                    double d3 = livingentity.getPosZ() - this.Firenando.getPosZ();
                    if (this.attackTime <= 0) {
                        ++this.attackStep;
                        if (this.attackStep == 1) {
                            this.attackTime = 60;
                            this.Firenando.setOnFire(true);
                        } else if (this.attackStep <= 4) {
                            this.attackTime = 6;
                        } else {
                            this.attackTime = 100;
                            this.attackStep = 0;
                            this.Firenando.setOnFire(false);
                        }

                        if (this.attackStep > 1) {
                            float f = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.5F;

                            for (int i = 0; i < 1; ++i) {
                                SmallFireballEntity smallfireballentity = new SmallFireballEntity(this.Firenando.world, this.Firenando, d1 + (double) f, d2, d3 + (double) f);
                                smallfireballentity.setPosition(smallfireballentity.getPosX(), this.Firenando.getPosYHeight(0.5D), smallfireballentity.getPosZ());
                                this.Firenando.world.addEntity(smallfireballentity);
                            }
                        }
                    }

                    this.Firenando.getLookController().setLookPositionWithEntity(livingentity, 10.0F, 10.0F);
                } else if (this.firedRecentlyTimer < 5) {
                    this.Firenando.getMoveHelper().setMoveTo(livingentity.getPosX(), livingentity.getPosY(), livingentity.getPosZ(), 1.0D);
                }

                super.tick();
            }
        }

        private double getFollowDistance() {
            return this.Firenando.getAttributeValue(Attributes.FOLLOW_RANGE);
        }
    }


}
