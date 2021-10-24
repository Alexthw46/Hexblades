package alexthw.hexblades.common.entity.ai.fe;

import alexthw.hexblades.common.entity.FireElementalEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;

public class FEMeleeGoal extends MeleeAttackGoal {

    FireElementalEntity Firenando;

    int cooldown;

    public FEMeleeGoal(FireElementalEntity creature, double speedIn, boolean useLongMemory) {
        super(creature, speedIn, useLongMemory);
        Firenando = (FireElementalEntity) this.mob;
        cooldown = 0;
    }

    @Override
    protected void resetAttackCooldown() {
        this.cooldown = 30;
    }

    @Override
    protected boolean isTimeToAttack() {
        return this.cooldown <= 0;
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse();
    }

    @Override
    public boolean canUse() {
        return super.canUse() && (this.Firenando.getFireCharge() < 3);
    }

    @Override
    public void tick() {
        super.tick();
        LivingEntity livingentity = this.mob.getTarget();
        if (livingentity == null) return;
        this.cooldown = Math.max(cooldown - 1, 0);

        if (cooldown == 15 && this.Firenando.getAnimationState() == 2) {
            this.Firenando.doHurtTarget(livingentity);
            livingentity.knockback(1.0F, mob.getX() - livingentity.getX(), mob.getZ() - livingentity.getZ());
        }

        if (cooldown == 1) {
            Firenando.setAnimationState(0);
            this.Firenando.addFireCharge(1);
        }

    }

    @Override
    protected void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {

        double d0 = this.getAttackReachSqr(enemy);

        if (distToEnemySqr <= d0 && isTimeToAttack()) {
            Firenando.setAnimationState(2);
            this.resetAttackCooldown();
        }

    }

}