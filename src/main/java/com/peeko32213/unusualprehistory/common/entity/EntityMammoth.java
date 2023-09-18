package com.peeko32213.unusualprehistory.common.entity;

import com.google.common.collect.Lists;
import com.peeko32213.unusualprehistory.common.entity.msc.unused.EntityMammothOld;
import com.peeko32213.unusualprehistory.common.entity.msc.util.LeaderHurtTargetGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.MammothFollowLeaderGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.MammothMeleeAttackGoal;
import com.peeko32213.unusualprehistory.common.entity.msc.util.dino.EntityBaseDinosaurAnimal;
import com.peeko32213.unusualprehistory.core.registry.UPItems;
import com.peeko32213.unusualprehistory.core.registry.UPSounds;
import com.peeko32213.unusualprehistory.core.registry.UPTags;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class EntityMammoth extends EntityBaseDinosaurAnimal implements  Shearable, net.minecraftforge.common.IForgeShearable {
    public static final ResourceLocation ERYON_REWARD = new ResourceLocation("unusualprehistory", "gameplay/eryon_reward");
    private static final EntityDataAccessor<Boolean> IS_TRUNKING = SynchedEntityData.defineId(EntityMammoth.class, EntityDataSerializers.BOOLEAN);
    public float prevFeedProgress;
    public float feedProgress;

    private Ingredient temptationItems;

    private int resetLeaderCooldown = 100;
    private LivingEntity leader;
    private int packSize = 1;

    public EntityMammoth(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15D)
                .add(Attributes.ATTACK_DAMAGE, 5D)

                .add(Attributes.ARMOR, 5.0D)
                .add(Attributes.ARMOR_TOUGHNESS, 3.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 8.5D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
        this.goalSelector.addGoal(3, new MammothMeleeAttackGoal(this, 1.0D, true));
        this.targetSelector.addGoal(1, new LeaderHurtTargetGoal(this));
        this.goalSelector.addGoal(4, new TemptGoal(this, 1.2D, getTemptationItems(), false));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(1, new MammothFollowLeaderGoal(this));
        this.targetSelector.addGoal(8, (new HurtByTargetGoal(this)));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(1, new MammothTrunkIdleGoal(this));

    }


    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        if(pHand != InteractionHand.MAIN_HAND) return InteractionResult.FAIL;
        if(pPlayer.getItemBySlot(EquipmentSlot.HEAD).is(UPItems.TYRANTS_CROWN.get()) ) {

            //UnusualPrehistory.LOGGER.info("item " + this.mammothInventory + " level " + pPlayer.level);

        }
        return super.mobInteract(pPlayer, pHand);
    }


    private Ingredient getTemptationItems() {
        if(temptationItems == null)
            temptationItems = Ingredient.merge(Lists.newArrayList(
                    Ingredient.of(ItemTags.LEAVES)
            ));

        return temptationItems;
    }




    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_TRUNKING, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
    }

    public BlockPos getRestrictCenter() {
        return this.leader == null ? super.getRestrictCenter() : this.leader.getOnPos();
    }

    public boolean hasRestriction() {
        return this.isFollower();
    }

    public boolean isTrunking() {
        return this.entityData.get(IS_TRUNKING);
    }

    public void setTrunking(boolean isPecking) {
        this.entityData.set(IS_TRUNKING, isPecking);
    }


    public void tick() {
        super.tick();
        if(!level.isClientSide){
            if(resetLeaderCooldown > 0){
                resetLeaderCooldown--;
            }else{
                resetLeaderCooldown = 200 + this.getRandom().nextInt(200);
            }
        }
        if (this.getTarget() != null && isValidLeader(this.getTarget())) {
            this.setTarget(null);
        }
        if (this.getTarget() != null && !isValidLeader(this.getTarget()) && this.getTarget().isAlive() && (this.getLastHurtByMob() == null || !this.getLastHurtByMob().isAlive())) {
            this.setLastHurtByMob(this.getTarget());
        }
        LivingEntity playerTarget = null;
        if (leader instanceof Player) {
            playerTarget = leader.getLastHurtMob();
            if (playerTarget == null || !playerTarget.isAlive() || playerTarget instanceof EntityMammoth) {
                playerTarget = leader.getLastHurtByMob();
            }
        }
        if (playerTarget != null && playerTarget.isAlive() && !(playerTarget instanceof EntityMammoth)) {
            this.setTarget(playerTarget);
        }
    }


    public boolean isValidLeader(LivingEntity leader) {
        if (leader instanceof Player) {
            if (this.getLastHurtByMob() != null && this.getLastHurtByMob().equals(leader)) {
                return false;
            }
            return leader.getItemBySlot(EquipmentSlot.HEAD).is(UPItems.TYRANTS_CROWN.get());
        } else {
            return leader.isAlive() && leader instanceof EntityMammoth;
        }
    }


    public boolean isFollower() {
        return this.leader != null && isValidLeader(leader);
    }


    public LivingEntity startFollowing(LivingEntity leader) {
        this.leader = leader;
        if (leader instanceof EntityMammoth) {
            ((EntityMammoth) leader).addFollower();
        }
        return leader;
    }

    public void stopFollowing() {
        if (this.leader instanceof EntityMammoth) {
            ((EntityMammoth) this.leader).removeFollower();
        }
        this.leader = null;
    }

    private void addFollower() {
        ++this.packSize;
    }

    private void removeFollower() {
        --this.packSize;
    }

    public boolean canBeFollowed() {
        return this.hasFollowers() && this.packSize < getMaxPackSize() && isValidLeader(this);
    }

    public boolean hasFollowers() {
        return this.packSize > 1;
    }

    public void addFollowers(Stream<EntityMammoth> p_27534_) {
        p_27534_.limit(getMaxPackSize() - this.packSize).filter((p_27538_) -> {
            return p_27538_ != this;
        }).forEach((p_27536_) -> {
            p_27536_.startFollowing(this);
        });
    }

    public int getMaxSpawnClusterSize() {
        return 6;
    }

    public int getMaxPackSize() {
        return this.getMaxSpawnClusterSize();
    }


    public boolean inRangeOfLeader() {
        return this.distanceTo(this.leader) <= 60.0D;
    }

    public boolean hasLeader(){
        return this.leader != null;
    }

    public LivingEntity getLeader() {
        return leader;
    }

    public boolean wantsToAttack(LivingEntity pTarget, LivingEntity pOwner) {
        return true;
    }

    public void pathToLeader() {
        if (this.isFollower()) {
            double speed = 1.0D;
            if (leader instanceof Player) {
                speed = 2.0D;
                if (this.distanceTo(leader) > 24) {
                    speed = 2.5;
                }
            }
            if (this.distanceTo(leader) > 6 && this.getNavigation().isDone()) {
                this.getNavigation().moveTo(this.leader, speed);
            }
        }
    }

    @Override
    public void shear(SoundSource category) {
        this.level.playSound(null, this, SoundEvents.SHEEP_SHEAR, category, 1.0F, 1.0F);
        this.gameEvent(GameEvent.ENTITY_INTERACT);
        if (!this.level.isClientSide()) {
            List<ItemStack> lootList = getDigLoot(this);
            if (lootList.size() > 0) {
                for (ItemStack stack : lootList) {
                    ItemEntity e = this.spawnAtLocation(stack.copy());
                    e.hasImpulse = true;
                }
            }
        }
    }

    private static List<ItemStack> getDigLoot(EntityMammoth crab) {
        LootTable loottable = crab.level.getServer().getLootTables().get(ERYON_REWARD);
        return loottable.getRandomItems((new LootContext.Builder((ServerLevel) crab.level)).withParameter(LootContextParams.THIS_ENTITY, crab).withRandom(crab.level.random).create(LootContextParamSets.PIGLIN_BARTER));
    }

    public boolean readyForShearing() {
        if (leader instanceof Player) {
            return leader.getItemBySlot(EquipmentSlot.HEAD).is(UPItems.TYRANTS_CROWN.get());

        }
        return false;
    }

    @Override
    public boolean isShearable(@javax.annotation.Nonnull ItemStack item, Level world, BlockPos pos) {
        return readyForShearing();
    }

    public class MammothTrunkIdleGoal extends Goal {
        private final EntityMammoth mammoth;

        public MammothTrunkIdleGoal(EntityMammoth mammoth) {
            this.mammoth = mammoth;
        }
        @Override
        public boolean canUse() {
            return this.mammoth.isOnGround() && this.mammoth.getRandom().nextInt(100) == 0;
        }

        @Override
        public void tick() {
            this.mammoth.setTrunking(true);
            if(this.mammoth.getRandom().nextInt(100) <= 25 ) {
              this.mammoth.setTrunking(false);
            }
        }

        @Override
        public boolean canContinueToUse() {
            return this.canUse();
        }
    }

    @Override
    protected SoundEvent getAttackSound() {
        return null;
    }

    @Override
    protected int getKillHealAmount() {
        return 0;
    }

    @Override
    protected boolean canGetHungry() {
        return false;
    }

    @Override
    protected boolean hasTargets() {
        return false;
    }

    @Override
    protected boolean hasAvoidEntity() {
        return false;
    }

    @Override
    protected boolean hasCustomNavigation() {
        return false;
    }

    @Override
    protected boolean hasMakeStuckInBlock() {
        return false;
    }

    @Override
    protected boolean customMakeStuckInBlockCheck(BlockState blockState) {
        return false;
    }

    @Override
    protected TagKey<EntityType<?>> getTargetTag() {
        return null;
    }

    protected SoundEvent getAmbientSound() {
        return UPSounds.MAMMOTH_IDLE.get();
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return UPSounds.MAMMOTH_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return UPSounds.MAMMOTH_DEATH.get();
    }

    protected void playStepSound(BlockPos p_28301_, BlockState p_28302_) {
        this.playSound(UPSounds.MAJUNGA_STEP.get(), 0.1F, 1.0F);
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel pLevel, AgeableMob pOtherParent) {
        return null;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if(this.isFromBook()){
            return PlayState.CONTINUE;
        }
        if (this.getDeltaMovement().horizontalDistanceSqr() > 1.0E-6) {
            event.getController().setAnimation(new AnimationBuilder().loop("animation.mammoth.move"));
            event.getController().setAnimationSpeed(1.5D);
            return PlayState.CONTINUE;
        }


        event.getController().setAnimation(new AnimationBuilder().loop("animation.mammoth.idle"));
        event.getController().setAnimationSpeed(1.0D);
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState trunkPredicate(AnimationEvent<E> event) {
        if (this.isTrunking()) {
            event.getController().setAnimation(new AnimationBuilder().playOnce("animation.mammoth.idle_trunk"));
            return PlayState.CONTINUE;
        }
        event.getController().markNeedsReload();
        return PlayState.STOP;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(5);
        AnimationController<EntityMammoth> controller = new AnimationController<>(this, "controller", 5, this::predicate);
        data.addAnimationController(new AnimationController<>(this, "eatController", 5, this::trunkPredicate));
        data.addAnimationController(controller);
    }
}
