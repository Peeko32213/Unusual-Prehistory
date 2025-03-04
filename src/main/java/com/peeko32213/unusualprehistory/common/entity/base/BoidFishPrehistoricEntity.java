package com.peeko32213.unusualprehistory.common.entity.base;

import com.peeko32213.unusualprehistory.common.entity.util.goal.boids.BoidFishSchoolingGoal;
import com.peeko32213.unusualprehistory.common.entity.util.goal.boids.HeightBoundsGoal;
import com.peeko32213.unusualprehistory.common.entity.util.goal.boids.LimitSpeedAndLookInVelocityDirectionGoal;
import com.peeko32213.unusualprehistory.common.entity.util.goal.boids.OrganizeBoidSchoolingGoal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

// Code took and modified from https://github.com/Tomate0613/boids,
// I went ahead and used it because the project's license is MIT,
// but if you are the author or someone that knows the author reading this
// and you are not ok with me using it, please put me in contact with the author directly and I will act accordingly by removing it

public abstract class BoidFishPrehistoricEntity extends AbstractFish {
    @Nullable
    public BoidFishPrehistoricEntity leader;
    public List<BoidFishPrehistoricEntity> ownSchool = new ArrayList<>();

    public BoidFishPrehistoricEntity(EntityType<? extends AbstractFish> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(4, new FishSwimGoal(this));
        this.goalSelector.addGoal(5, new BoidFishSchoolingGoal(this, 0.2f, 0.4f, 8 / 20f, 1 / 20f));
        this.goalSelector.addGoal(3, new HeightBoundsGoal(this));
        this.goalSelector.addGoal(2, new LimitSpeedAndLookInVelocityDirectionGoal(this, 0.3f, 0.4f));
        this.goalSelector.addGoal(5, new OrganizeBoidSchoolingGoal(this));
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return this.getMaxSchoolSize();
    }

    public int getMaxSchoolSize() {
        return super.getMaxSpawnClusterSize();
    }

    public boolean isFollower() {
        return this.leader != null && this.leader.isAlive();
    }

    public void startFollowing(BoidFishPrehistoricEntity abstractSchoolingFish) {
        this.leader = abstractSchoolingFish;
        abstractSchoolingFish.addToOwnSchoolFollower(this);
    }

    public void stopFollowing() {
        if (this.leader != null) {
            this.leader.removeFollowerFromOwnSchool(this);
            this.leader = null;
        }
    }

    @Override
    protected SoundEvent getSwimSound() {
        return (this.isFollower() || this.hasFollowers()) ? this.random.nextInt(this.isFollower() ? this.leader.ownSchool.size() : this.ownSchool.size()) == 0 ? super.getSwimSound() : SoundEvents.EMPTY : super.getSwimSound();
    }

    private void addToOwnSchoolFollower(BoidFishPrehistoricEntity entity) {
        this.ownSchool.add(entity);
    }

    private void removeFollowerFromOwnSchool(BoidFishPrehistoricEntity entity) {
        this.ownSchool.remove(entity);
    }

    public boolean canBeFollowed() {
        return this.hasFollowers() && this.ownSchool.size() < this.getMaxSchoolSize();
    }

    @Override
    public void tick() {
        super.tick();
    }

    public boolean hasFollowers() {
        return this.ownSchool.size() > 1;
    }

    public void addFollowers(Stream<? extends BoidFishPrehistoricEntity> stream) {
        stream.limit(this.getMaxSchoolSize() - this.ownSchool.size()).filter(boidFish -> boidFish != this).forEach(boidFish -> boidFish.startFollowing(this));
    }

    public boolean inRangeOfLeader() {
        return this.distanceToSqr(this.leader) <= 121.0;
    }

    @Override
    @Nullable
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor serverLevelAccessor, DifficultyInstance difficultyInstance, MobSpawnType mobSpawnType, @Nullable SpawnGroupData spawnGroupData, @Nullable CompoundTag compoundTag) {
        super.finalizeSpawn(serverLevelAccessor, difficultyInstance, mobSpawnType, spawnGroupData, compoundTag);
        if (spawnGroupData == null) {
            spawnGroupData = new BoidFishPrehistoricEntity.SchoolSpawnGroupData(this);
        } else {
            this.startFollowing(((BoidFishPrehistoricEntity.SchoolSpawnGroupData)spawnGroupData).leader);
        }
        return spawnGroupData;
    }

    public static class SchoolSpawnGroupData
            implements SpawnGroupData {
        public final BoidFishPrehistoricEntity leader;

        public SchoolSpawnGroupData(BoidFishPrehistoricEntity boidFish) {
            this.leader = boidFish;
        }
    }

    static class FishSwimGoal extends RandomSwimmingGoal {
        private final BoidFishPrehistoricEntity fish;

        public FishSwimGoal(BoidFishPrehistoricEntity boidFish) {
            super(boidFish, 1.0, 40);
            this.fish = boidFish;
        }

        @Override
        public boolean canUse() {
            return !this.fish.isFollower() && !this.fish.hasFollowers() && super.canUse();
        }
    }
}
