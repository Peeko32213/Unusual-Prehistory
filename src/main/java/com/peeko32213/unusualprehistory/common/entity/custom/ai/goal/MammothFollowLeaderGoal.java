//package com.peeko32213.unusualprehistory.common.entity.msc.util.goal;
//
//import com.mojang.datafixers.DataFixUtils;
//import com.peeko32213.unusualprehistory.common.entity.EntityMammoth;
//import net.minecraft.world.entity.ai.goal.Goal;
//import net.minecraft.world.entity.player.Player;
//
//import java.util.List;
//import java.util.function.Predicate;
//
//public class MammothFollowLeaderGoal extends Goal {
//    private static final int INTERVAL_TICKS = 200;
//    private final EntityMammoth mob;
//    private int timeToRecalcPath;
//    private int nextStartTick;
//
//    public MammothFollowLeaderGoal(EntityMammoth entityMammoth) {
//        this.mob = entityMammoth;
//        this.nextStartTick = this.nextStartTick(entityMammoth);
//    }
//
//    protected int nextStartTick(EntityMammoth entityMammoth) {
//        return 100 + entityMammoth.getRandom().nextInt(200) % 80;
//    }
//
//    public boolean canUse() {
//        if (this.mob.hasFollowers()) {
//            return false;
//        } else if (this.mob.isFollower()) {
//            return true;
//        } else if (this.nextStartTick > 0) {
//            --this.nextStartTick;
//            return false;
//        } else {
//            this.nextStartTick = this.nextStartTick(this.mob);
//            Predicate<EntityMammoth> mammothPredicate = (p_25258_) -> {
//                return p_25258_.canBeFollowed() || !p_25258_.isFollower();
//            };
//
//            return this.mob.isFollower();
//        }
//    }
//
//    public boolean canContinueToUse() {
//        return this.mob.isFollower() && this.mob.inRangeOfLeader();
//    }
//
//    public void start() {
//        this.timeToRecalcPath = 0;
//    }
//
//    public void stop() {
//        this.mob.stopFollowing();
//    }
//
//    public void tick() {
//        if (--this.timeToRecalcPath <= 0) {
//            this.timeToRecalcPath = 10;
//            this.mob.pathToLeader();
//
//        }
//    }
//}
