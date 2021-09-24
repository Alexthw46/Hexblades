package Alexthw.Hexblades.common.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class BaseElementalEntity extends MonsterEntity {

    public BaseElementalEntity(EntityType<? extends BaseElementalEntity> type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.applyEntityAI();
    }

    public static AttributeModifierMap createAttributes() {
        return MonsterEntity.func_234295_eP_().createMutableAttribute(Attributes.MAX_HEALTH, 100.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.28D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 5.0D).createMutableAttribute(Attributes.ARMOR, 10.0D).create();
    }

    protected void applyEntityAI() {
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, false));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    public int getExperiencePoints(PlayerEntity player) {
        return 80;
    }

    public void livingTick() {

        FluidState below = this.world.getBlockState(this.getPositionUnderneath()).getFluidState();
        Vector3d motion;
        if (!below.isEmpty()) {
            motion = this.getMotion();
            this.setOnGround(true);
            if (this.getPosY() + motion.y < (double) ((float) this.getPositionUnderneath().getY() + below.getHeight())) {
                this.setNoGravity(true);
                if (motion.y < 0.0D) {
                    this.setMotion(motion.mul(1.0D, 0.0D, 1.0D));
                }

                this.setPosition(this.getPosX(), (float) this.getPositionUnderneath().getY() + below.getHeight(), this.getPosZ());
            }
        } else {
            this.setNoGravity(false);
        }

        this.fallDistance = 0.0F;
        motion = this.getMotion();
        if (!this.onGround && motion.y < 0.0D) {
            this.setMotion(motion.mul(1.0D, 0.6D, 1.0D));
        }

        super.livingTick();
    }


}
