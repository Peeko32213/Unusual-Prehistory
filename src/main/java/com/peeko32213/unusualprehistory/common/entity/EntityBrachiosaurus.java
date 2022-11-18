package com.peeko32213.unusualprehistory.common.entity;

import com.peeko32213.unusualprehistory.common.entity.part.EntityBrachiosaurusPart;
import com.peeko32213.unusualprehistory.common.entity.util.BabyPanicGoal;
import com.peeko32213.unusualprehistory.common.entity.util.LandCreaturePathNavigation;
import com.peeko32213.unusualprehistory.core.registry.UPEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import javax.annotation.Nullable;
import java.util.function.Predicate;

public class EntityBrachiosaurus extends Animal implements IAnimatable {

    private static final EntityDataAccessor<Float> HEAD_HEIGHT = SynchedEntityData.defineId(EntityBrachiosaurus.class, EntityDataSerializers.FLOAT);
    private final AnimationFactory factory = new AnimationFactory(this);
    public float prevHeadHeight = 0F;

    public final EntityBrachiosaurusPart neck;


    private int headPeakCooldown = 0;

    public final EntityBrachiosaurusPart[] theEntireNeck;
    public final EntityBrachiosaurusPart[] allParts;

    public EntityBrachiosaurus(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
        this.maxUpStep = 2.0f;
        this.neck = new EntityBrachiosaurusPart(this, 3,5.3F);
        this.theEntireNeck = new EntityBrachiosaurusPart[]{this.neck};
        this.allParts = new EntityBrachiosaurusPart[]{this.neck};
    }


    @Override
    protected PathNavigation createNavigation(Level level) {
        return new LandCreaturePathNavigation(this, level);
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 200.0D)
                .add(Attributes.ARMOR, 15.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 20.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 10.5D)
                .add(Attributes.ATTACK_KNOCKBACK, 2.0D);
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new EntityBrachiosaurus.BrachiMeleeAttackGoal(this,  1.3F, true));
        this.goalSelector.addGoal(3, new BabyPanicGoal(this, 2.0D));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.0D, Ingredient.of(Items.MELON), false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HEAD_HEIGHT, 0F);
    }

    public int getMaxHeadXRot() {
        return 50;
    }

    public int getMaxHeadYRot() {
        return 50;
    }

    public void tick() {
        super.tick();
        prevHeadHeight = this.getHeadHeight();
        float neckBase = 5.5F;
        if (!this.isNoAi()) {
            Vec3[] avector3d = new Vec3[this.allParts.length];
            for (int j = 0; j < this.allParts.length; ++j) {
                this.allParts[j].collideWithNearbyEntities();
                avector3d[j] = new Vec3(this.allParts[j].getX(), this.allParts[j].getY(), this.allParts[j].getZ());
            }
            float yaw = this.getYRot() * ((float) Math.PI / 180F);
            float neckContraction = 0.3F * Math.abs(getHeadHeight() / 3) + 0.5F * Math.abs(getHeadYaw(0) / 50F);

            for (int l = 0; l < this.theEntireNeck.length; ++l) {
                float f = l / ((float) this.theEntireNeck.length);
                float f1 = -(2.2F + l - f * neckContraction);
                float f2 = Mth.sin(yaw + (float) Math.toRadians(f * getHeadYaw(0))) * (1 - Math.abs((this.getXRot()) / 90F));
                float f3 = Mth.cos(yaw + (float) Math.toRadians(f * getHeadYaw(0))) * (1 - Math.abs((this.getXRot()) / 90F));
                this.setPartPosition(this.theEntireNeck[l], f2 * f1, neckBase + Math.sin(f * Math.PI * 0.5F) * (getHeadHeight() * -11.1F), -f3 * f1);
            }

            for (int l = 0; l < this.allParts.length; ++l) {
                this.allParts[l].xo = avector3d[l].x;
                this.allParts[l].yo = avector3d[l].y;
                this.allParts[l].zo = avector3d[l].z;
                this.allParts[l].xOld = avector3d[l].x;
                this.allParts[l].yOld = avector3d[l].y;
                this.allParts[l].zOld = avector3d[l].z;
            }
        }
        if (!level.isClientSide) {

        }
            if ( headPeakCooldown == 0) {
                float low = getLowHeadHeight();
                this.setHeadHeight(this.getHeadHeight() + (0.5F + ((getLowHeadHeight() + getHighHeadHeight(low)) / 2F) - this.getHeadHeight()) * 0.2F);
            } else {
                if (getMaxFluidHeight() <= this.getBbHeight() * 0.5F && getMaxFluidHeight() >= this.getBbHeight() * 0.25F) {
                    float mot = (float) this.getDeltaMovement().lengthSqr();
                    this.setHeadHeight(Mth.clamp(this.getHeadHeight() + 0.1F - 0.2F * mot, 0, 2));
                    headPeakCooldown = 5;
                }
            }
    }

    private double getMaxFluidHeight() {
        return Math.max(this.getFluidHeight(FluidTags.LAVA), this.getFluidHeight(FluidTags.WATER));
    }

    public float getLowHeadHeight() {
        float checkAt = 0F;
        while (checkAt > -3F && !isHeadInWall((float) this.getY() + checkAt)) {
            checkAt -= 0.2F;
        }
        return checkAt;
    }

    public float getHighHeadHeight(float low) {
        float checkAt = 3F;
        while (checkAt > 0) {
            if(isHeadInWall((float) this.getY() + checkAt)) {
                break;
            }
            checkAt -= 0.2F;
        }

        return checkAt;
    }

    public boolean isHeadInWall(float offset) {
        if (this.noPhysics) {
            return false;
        } else {
            float f = 0.8F;
            Vec3 vec3 = new Vec3(neck.getX(), offset, neck.getZ());
            AABB axisalignedbb = AABB.ofSize(vec3, (double)f, 1.0E-6D, (double)f);
            return this.level.getBlockStates(axisalignedbb).filter(Predicate.not(BlockBehaviour.BlockStateBase::isAir)).anyMatch((p_185969_) -> {
                BlockPos blockpos = new BlockPos(vec3);
                return p_185969_.isSuffocating(this.level, blockpos) && Shapes.joinIsNotEmpty(p_185969_.getCollisionShape(this.level, blockpos).move(vec3.x, vec3.y, vec3.z), Shapes.create(axisalignedbb), BooleanOp.AND);
            });
        }
    }

    public float getHeadHeight() {
        return Mth.clamp(this.entityData.get(HEAD_HEIGHT), -3, 3);
    }

    public void setHeadHeight(float height) {
        this.entityData.set(HEAD_HEIGHT, Mth.clamp(height, -3, 3));
    }

    public float getHeadYaw(float interp) {
        float f;
        if (interp == 0.0F) {
            f = getYHeadRot() - this.yBodyRot;
        } else {
            float yBodyRot1 = this.yBodyRotO + (this.yBodyRot - this.yBodyRotO) * interp;
            float yHeadRot1 = this.yHeadRotO + (getYHeadRot() - this.yHeadRotO) * interp;
            f = yHeadRot1 - yBodyRot1;
        }
        return Mth.clamp(Mth.wrapDegrees(f), -50, 50);
    }

    private void setPartPosition(EntityBrachiosaurusPart part, double offsetX, double offsetY, double offsetZ) {
        part.setPos(this.getX() + offsetX * part.scale, this.getY() + offsetY * part.scale, this.getZ() + offsetZ * part.scale);
    }

    @Override
    public boolean isMultipartEntity() {
        return true;
    }

    @Override
    public net.minecraftforge.entity.PartEntity<?>[] getParts() {
        return this.allParts;
    }

    public boolean attackEntityPartFrom(EntityBrachiosaurusPart part, DamageSource source, float amount) {
        return this.hurt(source, amount);
    }

    public void scaleParts() {
        for (EntityBrachiosaurusPart parts : allParts) {
            float prev = parts.scale;
            parts.scale = this.isBaby() ? 0.5F : 1F;
            if (prev != parts.scale) {
                parts.refreshDimensions();
            }
        }
    }

    public void aiStep() {
        super.aiStep();
        scaleParts();
    }

    static class BrachiMeleeAttackGoal extends MeleeAttackGoal {
        public BrachiMeleeAttackGoal(PathfinderMob pathfinderMob, double speedMultiplier, boolean followingTargetEvenIfNotSeen) {
            super(pathfinderMob, speedMultiplier, followingTargetEvenIfNotSeen);
        }

        protected double getAttackReachSqr(LivingEntity p_25556_) {
            return (double)(this.mob.getBbWidth() * 2.0F * this.mob.getBbWidth() * 0.9F + p_25556_.getBbWidth());
        }
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return UPEntities.BRACHI.get().create(serverLevel);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6) {
            {
                event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.brachiosaurus.walk", true));
                event.getController().setAnimationSpeed(1.5D);
            }
        }
        else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.brachiosaurus.idle", true));
            event.getController().setAnimationSpeed(1.0D);
        }
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
        if (this.swinging && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.brachiosaurus.attack", false));
            this.swinging = false;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(5);
        AnimationController<EntityBrachiosaurus> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        data.addAnimationController(new AnimationController<>(this, "attackController", 0, this::attackPredicate));
        data.addAnimationController(controller);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }


}
