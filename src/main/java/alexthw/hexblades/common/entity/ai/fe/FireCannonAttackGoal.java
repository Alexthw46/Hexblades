package alexthw.hexblades.common.entity.ai.fe;

import alexthw.hexblades.common.entity.FireElementalEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.util.Mth;

import java.util.EnumSet;

public class FireCannonAttackGoal extends Goal {
    FireElementalEntity Firenando;

    private LivingEntity target;
    private int attackTime = 0;
    private final double speedModifier;
    private int seeTime;
    private final int attackInterval;
    private final float attackRadius;
    private final double attackRadiusSqr;

    public FireCannonAttackGoal(FireElementalEntity mob, double speed, int interval, float maxRange) {
        this.Firenando = mob;
        this.speedModifier = speed;
        this.attackInterval = interval;
        this.attackRadius = maxRange;
        this.attackRadiusSqr = attackRadius * attackRadius;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity livingentity = this.Firenando.getTarget();
        if ((livingentity != null && this.Firenando.canAttack(livingentity) && livingentity.isAlive())) {
            double distance = this.Firenando.distanceToSqr(livingentity);
            if (distance < 128.0F && distance >= 32.0F) {
                this.target = livingentity;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return this.canUse();
    }

    @Override
    public void stop() {
        this.target = null;
        this.seeTime = 0;
        this.attackTime = 0;
        this.Firenando.loadCannon(false);
        this.Firenando.setAnimationState(0);
    }

    @Override
    public void tick() {
        //pathfinding
        double d0 = this.Firenando.distanceToSqr(this.target.getX(), this.target.getY(), this.target.getZ());
        boolean flag = this.Firenando.getSensing().hasLineOfSight(this.target);
        if (flag) {
            ++this.seeTime;
        } else {
            this.seeTime = 0;
        }
        if ((this.attackTime > (attackInterval / 2)) || (!(d0 > this.attackRadiusSqr) && (this.seeTime >= 5))) {
            this.Firenando.getNavigation().stop();
        } else {
            this.Firenando.getNavigation().moveTo(this.target, this.speedModifier);
        }
        this.Firenando.getLookControl().setLookAt(this.target, 30.0F, 30.0F);

        this.attackTime = Math.max(attackTime - 1, 0);

        //attack zone
        if ((this.attackTime == 0) && flag) {
            this.Firenando.setAnimationState(1);
            this.attackTime = attackInterval;
        } else if ((this.attackTime == 30) && (this.Firenando.getAnimationState() == 1)) {
            this.Firenando.loadCannon(true);
        } else if ((this.attackTime == 15) && (this.Firenando.getAnimationState() == 1)) {
            float f = Mth.sqrt((float) d0) / this.attackRadius;
            float lvt_5_1_ = Mth.clamp(f, 0.1F, 1.0F);
            this.Firenando.performRangedAttack(this.target, lvt_5_1_);
        } else if (this.attackTime == 1) {
            this.Firenando.setAnimationState(0);
        }
    }

}
