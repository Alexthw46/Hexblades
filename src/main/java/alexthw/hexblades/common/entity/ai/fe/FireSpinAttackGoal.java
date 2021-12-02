package alexthw.hexblades.common.entity.ai.fe;

import alexthw.hexblades.common.entity.FireElementalEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class FireSpinAttackGoal extends MeleeAttackGoal {


    FireElementalEntity Firenando;
    int cooldown;

    public FireSpinAttackGoal(FireElementalEntity fireElementalEntity, double v, boolean b) {
        super(fireElementalEntity, v, b);
        Firenando = (FireElementalEntity) this.mob;
        cooldown = 0;
    }

    @Override
    public void stop() {
        super.stop();
        this.Firenando.setAnimationState(0);
    }

    @Override
    public boolean canUse() {
        return super.canUse() && (this.Firenando.getFireCharge() >= 3);
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() || this.Firenando.getFireCharge() > 3;
    }

    @Override
    protected void resetAttackCooldown() {
        this.cooldown = 40;
    }

    @Override
    protected boolean isTimeToAttack() {
        return this.cooldown <= 0;
    }

    @Override
    public void tick() {
        super.tick();

        LivingEntity livingentity = this.mob.getTarget();

        if (livingentity == null) return;

        this.cooldown = Math.max(cooldown - 1, 0);

        if (cooldown == 25 && this.Firenando.getAnimationState() == 3) {
            this.mob.doHurtTarget(livingentity);
            livingentity.setSecondsOnFire(2);
        }
        if (cooldown == 15 && this.Firenando.getAnimationState() == 3) {
            this.mob.doHurtTarget(livingentity);
        }

        if (cooldown == 1) {
            Firenando.setAnimationState(0);
            this.Firenando.addFireCharge(-3);
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
