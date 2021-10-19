package alexthw.hexblades.common.entity.ai.fe;

import alexthw.hexblades.common.entity.FireElementalEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;

public class FireSpinAttackGoal extends MeleeAttackGoal {


    FireElementalEntity Firenando;

    public FireSpinAttackGoal(FireElementalEntity fireElementalEntity, double v, boolean b) {
        super(fireElementalEntity, v, b);
        Firenando = (FireElementalEntity) this.mob;
    }

    @Override
    public void stop() {
        super.stop();
        this.Firenando.setAnimationState(0);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && (this.Firenando.getFireCharge() >= 4);
    }

    @Override
    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = 40;
    }

    @Override
    public void tick() {
        super.tick();

        LivingEntity livingentity = this.mob.getTarget();

        if (livingentity == null) return;

        if (ticksUntilNextAttack == 25 && this.Firenando.getAnimationState() == 3) {
            this.mob.doHurtTarget(livingentity);
            livingentity.setSecondsOnFire(2);
        }
        if (ticksUntilNextAttack == 15 && this.Firenando.getAnimationState() == 3) {
            this.mob.doHurtTarget(livingentity);
        }

        if (ticksUntilNextAttack == 1) {
            Firenando.setAnimationState(0);
            this.Firenando.addFireCharge(-4);
        }

    }

    @Override
    protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
        double d0 = this.getAttackReachSqr(enemy);
        if (distToEnemySqr <= d0 && isTimeToAttack()) {
            this.resetAttackCooldown();
            this.Firenando.setAnimationState(3);
        }
    }

}
